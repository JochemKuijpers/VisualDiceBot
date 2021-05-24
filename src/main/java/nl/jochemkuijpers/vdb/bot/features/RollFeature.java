package nl.jochemkuijpers.vdb.bot.features;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import nl.jochemkuijpers.vdb.dice.*;
import nl.jochemkuijpers.vdb.dice.ast.Node;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedBinaryNode;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedDiceLiteral;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedNode;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedUnaryNode;
import nl.jochemkuijpers.vdb.token.Locator;
import nl.jochemkuijpers.vdb.token.Token;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.*;

public class RollFeature extends ChatFeature {
	/** The maximum allowed input length for a feature. */
	private static final int MAX_INPUT_LENGTH = 100;

	/** The dice roller, for evaluating dice rolls. */
	private final DiceRoller diceRoller;

	/** Create a new RollFeature with a given DiceRoller. */
	public RollFeature(DiceRoller diceRoller) {
		super("?roll");
		this.diceRoller = diceRoller;
	}

	@Override
	public void processMessage(@NotNull MessageReceivedEvent event, @NotNull String input) {
		final Message message = event.getMessage();

		if (input.length() > MAX_INPUT_LENGTH) {
			message.reply(":warning:  Input length exceeds limit of " + MAX_INPUT_LENGTH)
					.mentionRepliedUser(false)
					.queue();
			return;
		}


		final DiceTokenizer tokenizer = new DiceTokenizer(input);
		final List<Token<DiceTokenType>> tokens = tokenizer.tokenize();

		// check for error tokens
		Optional<Token<DiceTokenType>> optErrorToken = tokens.stream().filter(token -> token.type() == DiceTokenType.ERROR).findFirst();
		if (optErrorToken.isPresent()) {
			Locator locator = new Locator(optErrorToken.get());
			String response = MessageFormat.format(
					":warning:  I could not figure out the meaning of this token.\n```\n{0}\n{1}\n```",
					locator.getContextString(2),
					locator.getHighlightString());
			message.reply(response)
					.mentionRepliedUser(false)
					.queue();
			return;
		}

		// parse the tokens
		final DiceParser parser = new DiceParser(tokens);
		final Node expression = parser.parse();

		if (parser.hasError()) {
			Locator locator = new Locator(parser.getErrorToken());
			String response = MessageFormat.format(
					":warning:  {0}\n```\n{1}\n{2}\n```",
					parser.getError(),
					locator.getContextString(2),
					locator.getHighlightString());
			message.reply(response)
					.mentionRepliedUser(false)
					.queue();
			return;
		}

		// evaluate the expression
		ValuedNode<? extends Node> evaluated;
		try {
			evaluated = expression.evaluate(diceRoller);
		} catch (BinaryExpressionException e) {
			// locate the binary operator
			Locator locator = new Locator(e.getBinaryNode().getOpToken());

			// build value strings for the left and right side
			StringBuilder sbNumerator = new StringBuilder();
			StringBuilder sbDenominator = new StringBuilder();
			e.getLeft().buildValueString(sbNumerator);
			e.getRight().buildValueString(sbDenominator);

			String response = MessageFormat.format(
					":warning:  {0}\n```\n{1}\n{2}\n\nLHS: {3} = {4}\nRHS: {5} = {6}\n```",
					e.getMessage(),
					locator.getContextString(2),
					locator.getHighlightString(),
					sbNumerator,
					e.getLeft().getValue(),
					sbDenominator,
					e.getRight().getValue());

			message.reply(response)
					.mentionRepliedUser(false)
					.queue();
			return;
		}

		// output the result
		StringBuilder sbInput = new StringBuilder();
		StringBuilder sbValue = new StringBuilder();
		expression.buildExpressionString(sbInput);
		evaluated.buildValueString(sbValue);


		// get all dice throws
		Queue<ValuedNode<?>> queue = new LinkedList<>();
		queue.add(evaluated);
		List<ValuedDiceLiteral> diceThrows = new ArrayList<>();
		while (!queue.isEmpty()) {
			ValuedNode<?> node = queue.remove();
			if (node instanceof ValuedBinaryNode<?> b) 	{ queue.add(b.getLeft()); queue.add(b.getRight()); }
			if (node instanceof ValuedUnaryNode<?> u)	{ queue.add(u.getChild()); }
			if (node instanceof ValuedDiceLiteral d)	{ diceThrows.add(d); }
		}

		// make a dice throws string
		StringBuilder sbDice = new StringBuilder();
		diceThrows.forEach(
				d -> {
					sbDice.append(' ');
					d.getNode().buildExpressionString(sbDice);
					sbDice.append(": ");
					d.buildValueString(sbDice);
					sbDice.append(" = ").append(d.getValue()).append('\n');
				}
		);

		// make and send the response
		String response = MessageFormat.format(
				":game_die:  You rolled **{0}**.\n```\nInput : {1}\nResult: {2} = {0}\n\nDice rolls:\n{3}\n```",
				evaluated.getValue(),
				sbInput,
				sbValue,
				sbDice);

		message.reply(response)
				.mentionRepliedUser(false)
				.queue();
	}
}

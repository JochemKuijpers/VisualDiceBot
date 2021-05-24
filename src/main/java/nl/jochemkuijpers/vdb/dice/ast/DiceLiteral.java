package nl.jochemkuijpers.vdb.dice.ast;

import nl.jochemkuijpers.vdb.dice.DiceRoller;
import nl.jochemkuijpers.vdb.dice.DiceTokenType;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedDiceLiteral;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedNode;
import nl.jochemkuijpers.vdb.token.Token;
import org.jetbrains.annotations.NotNull;

/**
 * A dice literal represents various (typically) polyhedral dice.
 * <p>
 * However, since we're not constrained by physical reality, we will just accept any integer digit of sides larger than
 * 1 and smaller than 100. A d100 will represent a percentage dice, which is a d10 whose faces contain multiples of 10.
 */
public class DiceLiteral extends Literal {

	/** The number of faces on the die. */
	private final int numFaces;

	/** The number of dice. */
	private final int numDice;

	public DiceLiteral(@NotNull Token<DiceTokenType> token) {
		super(token);
		String value = token.srcString().toLowerCase();
		int dPos = value.indexOf('d');
		if (dPos == 0) {
			this.numDice = 1;
			this.numFaces = Integer.parseInt(value.substring(1));
		} else {
			this.numDice = Integer.parseInt(value.substring(0, dPos));
			this.numFaces = Integer.parseInt(value.substring(dPos + 1));
		}
	}

	@Override
	public ValuedNode<? extends Node> evaluate(DiceRoller diceRoller) {
		int[] rolls = new int[numDice];
		int total = 0;
		for (int i = 0; i < rolls.length; i++) {
			rolls[i] = diceRoller.roll(numFaces);
			total += rolls[i];
		}
		return new ValuedDiceLiteral(this, total, rolls);
	}
}

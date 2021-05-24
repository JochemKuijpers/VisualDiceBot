package nl.jochemkuijpers.vdb.dice.ast;

import nl.jochemkuijpers.vdb.dice.DiceRoller;
import nl.jochemkuijpers.vdb.dice.DiceTokenType;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedLiteral;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedNode;
import nl.jochemkuijpers.vdb.token.Token;
import org.jetbrains.annotations.NotNull;

public class NumberLiteral extends Literal {
	/** The number value. */
	private final int value;

	public NumberLiteral(@NotNull Token<DiceTokenType> token) {
		super(token);
		this.value = Integer.parseInt(token.srcString());
	}

	@Override
	public ValuedNode<? extends Node> evaluate(DiceRoller diceRoller) {
		return new ValuedLiteral<>(this, value);
	}
}

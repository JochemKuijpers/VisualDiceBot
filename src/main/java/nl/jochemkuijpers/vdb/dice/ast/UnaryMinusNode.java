package nl.jochemkuijpers.vdb.dice.ast;

import nl.jochemkuijpers.vdb.dice.BinaryExpressionException;
import nl.jochemkuijpers.vdb.dice.DiceRoller;
import nl.jochemkuijpers.vdb.dice.DiceTokenType;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedNode;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedUnaryMinusNode;
import nl.jochemkuijpers.vdb.token.Token;

public class UnaryMinusNode extends UnaryNode {

	private final Token<DiceTokenType> minus;

	public UnaryMinusNode(Token<DiceTokenType> minus, Node child) {
		super(child);
		this.minus = minus;
	}

	public Token<DiceTokenType> getMinus() {
		return minus;
	}

	@Override
	public ValuedNode<? extends Node> evaluate(DiceRoller diceRoller) throws BinaryExpressionException {
		ValuedNode<? extends Node> child = getChild().evaluate(diceRoller);
		return new ValuedUnaryMinusNode(this, -child.getValue(), child);
	}

	@Override
	public void buildExpressionString(StringBuilder stringBuilder) {
		stringBuilder.append(minus.srcString());
		getChild().buildExpressionString(stringBuilder);
	}
}

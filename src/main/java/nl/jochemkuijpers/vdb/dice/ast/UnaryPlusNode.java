package nl.jochemkuijpers.vdb.dice.ast;

import nl.jochemkuijpers.vdb.dice.BinaryExpressionException;
import nl.jochemkuijpers.vdb.dice.DiceRoller;
import nl.jochemkuijpers.vdb.dice.DiceTokenType;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedNode;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedUnaryPlusNode;
import nl.jochemkuijpers.vdb.token.Token;

public class UnaryPlusNode extends UnaryNode {

	private final Token<DiceTokenType> plus;

	public UnaryPlusNode(Token<DiceTokenType> plus, Node child) {
		super(child);
		this.plus = plus;
	}

	public Token<DiceTokenType> getPlus() {
		return plus;
	}

	@Override
	public ValuedNode<? extends Node> evaluate(DiceRoller diceRoller) throws BinaryExpressionException {
		ValuedNode<? extends Node> child = getChild().evaluate(diceRoller);
		return new ValuedUnaryPlusNode(this, child.getValue(), child);
	}

	@Override
	public void buildExpressionString(StringBuilder stringBuilder) {
		stringBuilder.append(plus.srcString());
		getChild().buildExpressionString(stringBuilder);
	}
}

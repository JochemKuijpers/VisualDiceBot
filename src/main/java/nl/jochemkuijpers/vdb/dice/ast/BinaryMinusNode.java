package nl.jochemkuijpers.vdb.dice.ast;

import nl.jochemkuijpers.vdb.dice.BinaryExpressionException;
import nl.jochemkuijpers.vdb.dice.DiceRoller;
import nl.jochemkuijpers.vdb.dice.DiceTokenType;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedBinaryNode;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedNode;
import nl.jochemkuijpers.vdb.token.Token;

public class BinaryMinusNode extends BinaryNode {

	public BinaryMinusNode(Token<DiceTokenType> opToken, Node left, Node right) {
		super(opToken, left, right);
	}

	@Override
	public ValuedNode<? extends Node> evaluate(DiceRoller diceRoller) throws BinaryExpressionException {
		final ValuedNode<? extends Node> left = getLeft().evaluate(diceRoller);
		final ValuedNode<? extends Node> right = getRight().evaluate(diceRoller);
		return new ValuedBinaryNode<>(this, left.getValue() - right.getValue(), left, right);
	}
}

package nl.jochemkuijpers.vdb.dice.ast;

import nl.jochemkuijpers.vdb.dice.BinaryExpressionException;
import nl.jochemkuijpers.vdb.dice.DiceRoller;
import nl.jochemkuijpers.vdb.dice.DiceTokenType;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedNode;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedParenthesizedNode;
import nl.jochemkuijpers.vdb.token.Token;

public class ParenthesizedNode extends UnaryNode {
	/** The left parenthesis token. */
	private final Token<DiceTokenType> left;
	/** The right parenthesis token. */
	private final Token<DiceTokenType> right;

	public ParenthesizedNode(Token<DiceTokenType> left, Token<DiceTokenType> right, Node child) {
		super(child);
		this.left = left;
		this.right = right;
	}

	public Token<DiceTokenType> getLeft() {
		return left;
	}

	public Token<DiceTokenType> getRight() {
		return right;
	}

	@Override
	public ValuedNode<? extends Node> evaluate(DiceRoller diceRoller) throws BinaryExpressionException {
		ValuedNode<? extends Node> child = getChild().evaluate(diceRoller);
		return new ValuedParenthesizedNode(this, child.getValue(), child);
	}

	@Override
	public void buildExpressionString(StringBuilder stringBuilder) {
		getChild().buildExpressionString(stringBuilder);
	}
}

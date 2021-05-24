package nl.jochemkuijpers.vdb.dice.ast;

import nl.jochemkuijpers.vdb.dice.DiceTokenType;
import nl.jochemkuijpers.vdb.token.Token;

public abstract class BinaryNode implements Node {

	/** The operator token. */
	private final Token<DiceTokenType> opToken;

	/** The source string of the operator. */
	private final String op;

	/** The left operand. */
	private final Node left;

	/** The right operand. */
	private final Node right;

	public BinaryNode(Token<DiceTokenType> opToken, Node left, Node right) {
		this.opToken = opToken;
		this.op = opToken.srcString();
		this.left = left;
		this.right = right;
	}

	public Token<DiceTokenType> getOpToken() {
		return opToken;
	}

	public String getOp() {
		return op;
	}

	public Node getLeft() {
		return left;
	}

	public Node getRight() {
		return right;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public void buildExpressionString(StringBuilder stringBuilder) {
		stringBuilder.append('(');
		left.buildExpressionString(stringBuilder);
		stringBuilder.append(' ').append(op).append(' ');
		right.buildExpressionString(stringBuilder);
		stringBuilder.append(')');
	}
}

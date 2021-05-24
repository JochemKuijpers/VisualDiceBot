package nl.jochemkuijpers.vdb.dice;

import nl.jochemkuijpers.vdb.dice.ast.BinaryNode;
import nl.jochemkuijpers.vdb.dice.ast.Node;
import nl.jochemkuijpers.vdb.dice.ast.valued.ValuedNode;

public class BinaryExpressionException extends EvaluationException {
	private final BinaryNode binaryNode;
	private final ValuedNode<? extends Node> left;
	private final ValuedNode<? extends Node> right;

	public BinaryExpressionException(String message, BinaryNode binaryNode, ValuedNode<? extends Node> left, ValuedNode<? extends Node> right) {
		super(message);
		this.binaryNode = binaryNode;
		this.left = left;
		this.right = right;
	}

	public BinaryNode getBinaryNode() {
		return binaryNode;
	}

	public ValuedNode<? extends Node> getLeft() {
		return left;
	}

	public ValuedNode<? extends Node> getRight() {
		return right;
	}
}

package nl.jochemkuijpers.vdb.dice.ast.valued;

import nl.jochemkuijpers.vdb.dice.ast.BinaryNode;
import nl.jochemkuijpers.vdb.dice.ast.Node;

public class ValuedBinaryNode<T extends BinaryNode> extends ValuedNode<T> {
	/** The left operand. */
	private final ValuedNode<? extends Node> left;
	/** The right operand. */
	private final ValuedNode<? extends Node> right;

	public ValuedBinaryNode(T node, int value, ValuedNode<? extends Node> left, ValuedNode<? extends Node> right) {
		super(node, value);
		this.left = left;
		this.right = right;
	}

	public ValuedNode<? extends Node> getLeft() {
		return left;
	}

	public ValuedNode<? extends Node> getRight() {
		return right;
	}

	@Override
	public void buildValueString(StringBuilder stringBuilder) {
		stringBuilder.append('(');
		left.buildValueString(stringBuilder);
		stringBuilder.append(' ').append(getNode().getOp()).append(' ');
		right.buildValueString(stringBuilder);
		stringBuilder.append(')');
	}
}

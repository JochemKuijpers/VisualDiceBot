package nl.jochemkuijpers.vdb.dice.ast.valued;

import nl.jochemkuijpers.vdb.dice.ast.Node;
import nl.jochemkuijpers.vdb.dice.ast.UnaryNode;

public abstract class ValuedUnaryNode<T extends UnaryNode> extends ValuedNode<T> {
	/** The child node. */
	private final ValuedNode<? extends Node> child;

	protected ValuedUnaryNode(T node, int value, ValuedNode<? extends Node> child) {
		super(node, value);
		this.child = child;
	}

	public ValuedNode<? extends Node> getChild() {
		return child;
	}
}

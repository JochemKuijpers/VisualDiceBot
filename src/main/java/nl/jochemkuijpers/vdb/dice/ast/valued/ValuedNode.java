package nl.jochemkuijpers.vdb.dice.ast.valued;

import nl.jochemkuijpers.vdb.dice.ast.Node;

/** Represents an AST node with an assigned integer value. */
public abstract class ValuedNode<T extends Node> {
	/** The node. */
	private final T node;
	/** The value. */
	private final int value;

	protected ValuedNode(T node, int value) {
		this.node = node;
		this.value = value;
	}

	public T getNode() {
		return node;
	}

	public int getValue() {
		return value;
	}

	/** Add the value to the string builder. */
	public abstract void buildValueString(StringBuilder stringBuilder);
}

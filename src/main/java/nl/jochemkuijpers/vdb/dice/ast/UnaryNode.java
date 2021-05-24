package nl.jochemkuijpers.vdb.dice.ast;

public abstract class UnaryNode implements Node {
	/** The child of this unary node. */
	private final Node child;

	public UnaryNode(Node child) {
		this.child = child;
	}

	public Node getChild() {
		return child;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}
}

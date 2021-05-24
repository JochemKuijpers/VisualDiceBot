package nl.jochemkuijpers.vdb.dice.ast.valued;

import nl.jochemkuijpers.vdb.dice.ast.Node;
import nl.jochemkuijpers.vdb.dice.ast.UnaryPlusNode;

public class ValuedUnaryPlusNode extends ValuedUnaryNode<UnaryPlusNode> {
	public ValuedUnaryPlusNode(UnaryPlusNode node, int value, ValuedNode<? extends Node> child) {
		super(node, value, child);
	}

	@Override
	public void buildValueString(StringBuilder stringBuilder) {
		stringBuilder.append(getNode().getPlus().srcString());
		getChild().buildValueString(stringBuilder);
	}
}

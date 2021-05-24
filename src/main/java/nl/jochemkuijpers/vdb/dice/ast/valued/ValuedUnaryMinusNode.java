package nl.jochemkuijpers.vdb.dice.ast.valued;

import nl.jochemkuijpers.vdb.dice.ast.Node;
import nl.jochemkuijpers.vdb.dice.ast.UnaryMinusNode;

public class ValuedUnaryMinusNode extends ValuedUnaryNode<UnaryMinusNode> {

	public ValuedUnaryMinusNode(UnaryMinusNode node, int value, ValuedNode<? extends Node> child) {
		super(node, value, child);
	}

	@Override
	public void buildValueString(StringBuilder stringBuilder) {
		stringBuilder.append(getNode().getMinus().srcString());
		getChild().buildValueString(stringBuilder);
	}
}

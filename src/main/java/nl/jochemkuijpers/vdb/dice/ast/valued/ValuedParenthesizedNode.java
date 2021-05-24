package nl.jochemkuijpers.vdb.dice.ast.valued;

import nl.jochemkuijpers.vdb.dice.ast.Node;
import nl.jochemkuijpers.vdb.dice.ast.ParenthesizedNode;

public class ValuedParenthesizedNode extends ValuedUnaryNode<ParenthesizedNode> {

	public ValuedParenthesizedNode(ParenthesizedNode node, int value, ValuedNode<? extends Node> child) {
		super(node, value, child);
	}

	@Override
	public void buildValueString(StringBuilder stringBuilder) {
		getChild().buildValueString(stringBuilder);
	}
}

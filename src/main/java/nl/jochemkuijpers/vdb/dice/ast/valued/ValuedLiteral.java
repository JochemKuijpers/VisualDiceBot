package nl.jochemkuijpers.vdb.dice.ast.valued;

import nl.jochemkuijpers.vdb.dice.ast.Literal;

/** Represents a literal value. */
public class ValuedLiteral<T extends Literal> extends ValuedNode<T> {
	public ValuedLiteral(T node, int value) {
		super(node, value);
	}

	@Override
	public void buildValueString(StringBuilder stringBuilder) {
		stringBuilder.append(getValue());
	}
}

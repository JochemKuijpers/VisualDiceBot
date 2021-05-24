package nl.jochemkuijpers.vdb.dice.ast.valued;

import nl.jochemkuijpers.vdb.dice.ast.DiceLiteral;

public class ValuedDiceLiteral extends ValuedLiteral<DiceLiteral> {
	private final int[] values;

	public ValuedDiceLiteral(DiceLiteral node, int value, int[] values) {
		super(node, value);
		this.values = values;
	}

	@Override
	public void buildValueString(StringBuilder stringBuilder) {
		stringBuilder.append('[');
		for (int i = 0; i < values.length; i++) {
			if (i > 0) stringBuilder.append(' ');
			stringBuilder.append(values[i]);
		}
		stringBuilder.append(']');
	}
}

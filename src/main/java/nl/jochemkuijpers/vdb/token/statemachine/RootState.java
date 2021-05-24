package nl.jochemkuijpers.vdb.token.statemachine;

import nl.jochemkuijpers.vdb.token.TokenType;

public final class RootState<T extends TokenType> extends MultiState<T> {

	public RootState() {
		super(null, null);
	}

	@Override
	public boolean isValid(int[] codepoints, int start, int end) {
		return true;
	}
}

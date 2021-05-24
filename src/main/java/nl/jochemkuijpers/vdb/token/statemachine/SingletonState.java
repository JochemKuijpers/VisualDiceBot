package nl.jochemkuijpers.vdb.token.statemachine;

import nl.jochemkuijpers.vdb.token.TokenType;

/**
 * This state is used to emit tokens that consist of a single codepoint, has no outgoing transitions.
 */
public final class SingletonState<T extends TokenType> extends State<T> {
	private final int codepoint;

	public SingletonState(T type, int codepoint) {
		super(type);
		this.codepoint = codepoint;
	}

	@Override
	public State<T> transition(int codepoint) {
		return null;
	}

	@Override
	public boolean isValid(int[] codepoints, int start, int end) {
		return start == end - 1 && codepoints[start] == codepoint;
	}
}

package nl.jochemkuijpers.vdb.token.statemachine;

import nl.jochemkuijpers.vdb.token.TokenType;

public abstract class State<T extends TokenType> {
	/** The token type this state emits. */
	private final T type;

	protected State(T type) {
		this.type = type;
	}

	public T getType() {
		return type;
	}

	/** Give the next state, given the codepoint received, or null if no next state exists. */
	public abstract State<T> transition(int codepoint);

	/**
	 * Checks whether the codepoints in the start (inclusive) and stop (exclusive) interval is a valid codepoint.
	 *
	 * @param codepoints	the codepoint array
	 * @param start			the start position
	 * @param end			the end position
	 * @return				whether the codepoints make a valid token
	 */
	public abstract boolean isValid(int[] codepoints, int start, int end);
}

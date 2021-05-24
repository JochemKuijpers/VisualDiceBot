package nl.jochemkuijpers.vdb.token.statemachine;

import nl.jochemkuijpers.vdb.token.Token;
import nl.jochemkuijpers.vdb.token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class StateMachine<T extends TokenType> {
	private final RootState<T> rootState;
	private final T eof;
	private final T error;

	public List<Token<T>> run(int[] codepoints) {
		List<Token<T>> tokens = new ArrayList<>(32);
		State<T> currentState = rootState;

		int col = 0;
		int row = 0;

		int startCol = 0;
		int startRow = 0;

		int startPos = 0;
		int prev = -1;

		for (int currentPos = 0; currentPos < codepoints.length; currentPos++) {
			int current = codepoints[currentPos];

			if (currentState == null) currentState = rootState;

			// if we're in the root state, this position might start a new token
			if (currentState == rootState) {
				startPos = currentPos;
				startCol = col;
				startRow = row;
			}

			// attempt a transition, emit a token if we reset to the root state
			State<T> nextState = currentState.transition(current);
			if (nextState == null) {
				if (currentState != rootState) {
					// emit a token
					if (currentState.isValid(codepoints, startPos, currentPos)) {
						tokens.add(new Token<>(currentState.getType(), codepoints, startPos, currentPos, startRow, startCol));
					} else {
						tokens.add(new Token<>(error, codepoints, startPos, currentPos, startRow, startCol));
						return tokens;
					}

					// attempt the same transition from the root state and reset the start positions
					nextState = rootState.transition(current);
					startPos = currentPos;
					startCol = col;
					startRow = row;
				} else {
					nextState = rootState;
					if (!Character.isWhitespace(current)) {
						tokens.add(new Token<>(error, codepoints, startPos, currentPos, startRow, startCol));
						return tokens;
					}
				}
			}

			// determine whether we're transitioning to a new line
			boolean isNewline = false;
			switch (current) {
				case 0x0A, 0x0B, 0x0C, 0x0D, 0x85, 0x2028, 0x2029 -> {
					if (prev == '\r' && current == '\n') {
						col -= 1;
					}
					isNewline = true;
				}
			}

			// count col and row
			col += 1;
			if (isNewline) {
				col = 0;
				row += 1;
			}

			// apply new state
			if (nextState == null) {
				nextState = rootState;
			}
			prev = current;
			currentState = nextState;
		}

		if (currentState != rootState) {
			if (currentState.isValid(codepoints, startPos, codepoints.length)) {
				tokens.add(new Token<>(currentState.getType(), codepoints, startPos, codepoints.length, startRow, startCol));
			} else {
				tokens.add(new Token<>(error, codepoints, startPos, codepoints.length, startRow, startCol));
				return tokens;
			}
		}

		// emit EOF
		tokens.add(new Token<>(eof, codepoints, codepoints.length, codepoints.length, startRow, startCol));
		return tokens;
	}

	StateMachine(RootState<T> rootState, T eof, T error) {
		this.rootState = rootState;
		this.eof = eof;
		this.error = error;
	}

}

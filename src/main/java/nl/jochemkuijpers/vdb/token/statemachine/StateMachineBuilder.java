package nl.jochemkuijpers.vdb.token.statemachine;

import nl.jochemkuijpers.vdb.token.TokenType;

public class StateMachineBuilder<T extends TokenType> {
	private final RootState<T> rootState = new RootState<>();
	private T eof;
	private T error;

	public StateMachineBuilder<T> withSingleton(int codepoint, T tokenType) {
		rootState.addTransition(codepoint, new SingletonState<>(tokenType, codepoint));
		return this;
	}

	public StateMachineBuilder<T> withState(int codepoint, State<T> state) {
		rootState.addTransition(codepoint, state);
		return this;
	}

	public StateMachineBuilder<T> withEof(T eof) {
		this.eof = eof;
		return this;
	}

	public StateMachineBuilder<T> withError(T error) {
		this.error = error;
		return this;
	}

	public StateMachine<T> build() {
		return new StateMachine<>(rootState, eof, error);
	}
}

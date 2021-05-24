package nl.jochemkuijpers.vdb.dice;

import nl.jochemkuijpers.vdb.token.Token;
import nl.jochemkuijpers.vdb.token.Tokenizer;
import nl.jochemkuijpers.vdb.token.statemachine.MultiState;
import nl.jochemkuijpers.vdb.token.statemachine.StateMachine;
import nl.jochemkuijpers.vdb.token.statemachine.StateMachineBuilder;

import java.util.List;

public class DiceTokenizer implements Tokenizer<DiceTokenType> {

	/** The tokenizing state machine. */
	private static final StateMachine<DiceTokenType> stateMachine;

	static {
		MultiState<DiceTokenType> diceState = new MultiState<>(DiceTokenType.DICE
//				// a dice value should contain a 'd' anywhere but the last value
//				(codepoints, start, end) -> {
//					for (int i = start; i < end - 1; i++) {
//						if (codepoints[i] == 'd' || codepoints[i] == 'D') return true;
//					}
//					return false;
//				}
		);
		diceState.addTransition('0', diceState);
		diceState.addTransition('1', diceState);
		diceState.addTransition('2', diceState);
		diceState.addTransition('3', diceState);
		diceState.addTransition('4', diceState);
		diceState.addTransition('5', diceState);
		diceState.addTransition('6', diceState);
		diceState.addTransition('7', diceState);
		diceState.addTransition('8', diceState);
		diceState.addTransition('9', diceState);

		MultiState<DiceTokenType> numberState = new MultiState<>(DiceTokenType.NUMBER);
		numberState.addTransition('0', numberState);
		numberState.addTransition('1', numberState);
		numberState.addTransition('2', numberState);
		numberState.addTransition('3', numberState);
		numberState.addTransition('4', numberState);
		numberState.addTransition('5', numberState);
		numberState.addTransition('6', numberState);
		numberState.addTransition('7', numberState);
		numberState.addTransition('8', numberState);
		numberState.addTransition('9', numberState);
		numberState.addTransition('d', diceState);
		numberState.addTransition('D', diceState);

		stateMachine = new StateMachineBuilder<DiceTokenType>()
				.withEof		(DiceTokenType.EOF)
				.withError		(DiceTokenType.ERROR)
				// single character tokens
				.withSingleton('(', 	DiceTokenType.PAREN_OPEN)
				.withSingleton(')', 	DiceTokenType.PAREN_CLOSE)
				.withSingleton('+', 	DiceTokenType.PLUS)		// plus
				.withSingleton('-', 	DiceTokenType.MINUS)		// dash
				.withSingleton(0x2013, 	DiceTokenType.MINUS)		// en-dash
				.withSingleton(0x2014, 	DiceTokenType.MINUS)		// em-dash
				.withSingleton('*', 	DiceTokenType.MULT)		// asterisk
				.withSingleton(0x00D7, 	DiceTokenType.MULT)		// multiplication symbol
				.withSingleton('x', 	DiceTokenType.MULT)		// lowercase X
				.withSingleton('X', 	DiceTokenType.MULT)		// uppercase X
				.withSingleton('/', 	DiceTokenType.DIV)			// slash
				.withSingleton(':', 	DiceTokenType.DIV)			// colon
				.withSingleton(0x00F7, 	DiceTokenType.DIV)			// division symbol
				// numbers
				.withState('0', numberState)
				.withState('1', numberState)
				.withState('2', numberState)
				.withState('3', numberState)
				.withState('4', numberState)
				.withState('5', numberState)
				.withState('6', numberState)
				.withState('7', numberState)
				.withState('8', numberState)
				.withState('9', numberState)
				// dice
				.withState('d', diceState)
				.withState('D', diceState)
				// get state machine
				.build();
	}

	/** The input to parse. */
	private final String input;

	/** The tokens, or null if not tokenized yet. */
	private List<Token<DiceTokenType>> result;

	public DiceTokenizer(String input) {
		this.input = input;
	}

	@Override
	public List<Token<DiceTokenType>> tokenize() {
		if (result == null) {
			result = stateMachine.run(input.codePoints().toArray());
		}
		return result;
	}
}

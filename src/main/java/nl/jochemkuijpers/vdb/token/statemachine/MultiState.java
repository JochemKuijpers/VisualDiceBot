package nl.jochemkuijpers.vdb.token.statemachine;

import nl.jochemkuijpers.vdb.token.TokenType;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntObjectHashMap;

public class MultiState<T extends TokenType> extends State<T> {
	public interface ValidatorFunction {
		boolean validate(int[] codepoints, int start, int end);
	}

	private final MutableIntObjectMap<State<T>> transitions = new IntObjectHashMap<>();
	private final ValidatorFunction validator;

	public MultiState(T token) {
		this(token, null);
	}

	public MultiState(T token, ValidatorFunction validator) {
		super(token);
		this.validator = validator;
	}

	public void addTransition(int codepoint, State<T> destination) {
		if (transitions.containsKey(codepoint))
			throw new IllegalArgumentException("codepoint already registered as a transition");

		transitions.put(codepoint, destination);
	}

	@Override
	public State<T> transition(int codepoint) {
		return transitions.get(codepoint);
	}

	@Override
	public boolean isValid(int[] codepoints, int start, int end) {
		return validator == null || validator.validate(codepoints, start, end);
	}
}

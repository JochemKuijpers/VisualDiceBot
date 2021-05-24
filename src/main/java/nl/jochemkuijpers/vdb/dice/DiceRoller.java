package nl.jochemkuijpers.vdb.dice;

/**
 * This class implements the random number generation of rolling dice.
 * <p>
 * If multiple threads are rolling dice, every thread gets its own DiceRoller.
 * As such the implementation does not need to be thread-safe.
 */
public interface DiceRoller {
	/** Returns a random number between 1 and {@code numFaces}. */
	int roll(int numFaces);
}

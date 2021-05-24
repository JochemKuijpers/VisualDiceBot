package nl.jochemkuijpers.vdb.dice;

import java.util.Random;

public class RandomDiceRoller implements DiceRoller {
	Random random = new Random(System.nanoTime());

	@Override
	public int roll(int numFaces) {
		return random.nextInt(numFaces) + 1;
	}
}

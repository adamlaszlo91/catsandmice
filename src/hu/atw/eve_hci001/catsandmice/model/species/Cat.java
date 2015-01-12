package hu.atw.eve_hci001.catsandmice.model.species;

import java.util.BitSet;

/**
 * This class represents a Cat.
 * 
 * @author László Ádám
 *
 */
public class Cat extends Species {
	private int miceConsumed;

	public Cat(int generation, BitSet geneSequence) {
		super(generation, geneSequence);
	}

	@Override
	public String toString() {
		String s = "";
		s += "Cat\ngeneration: old rusty 1\nspeed: " + phenomeValue
				+ " (constant)\nmice consumed: " + miceConsumed;
		return s;
	}

	public void miceConsumed() {
		miceConsumed++;
	}

	/**
	 * 
	 * @return The number of mice has been eaten by this cat.
	 */
	public int getMiceConsumed() {
		return miceConsumed;
	}

}

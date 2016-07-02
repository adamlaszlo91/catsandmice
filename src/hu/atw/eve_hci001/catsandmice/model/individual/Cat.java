package hu.atw.eve_hci001.catsandmice.model.individual;

import java.util.BitSet;

/**
 * This class represents cats.
 * 
 * @author László Ádám
 *
 */
public class Cat extends Individual {
	private int mMiceConsumed;

	public Cat(int generation, BitSet genome, int geneMultiplier) {
		super(generation, genome, geneMultiplier);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cat\n");
		builder.append("generation:\t0\t(constant)\n");
		builder.append("speed:\t");
		builder.append(mGenomeValue);
		builder.append("\t(constant)\n");
		builder.append("genome:\t");
		builder.append(getGenomeString());
		builder.append("\nmice eaten:\t");
		builder.append(mMiceConsumed);
		return builder.toString();
	}

	/**
	 * Indicates that the cat has eaten a mouse.
	 */
	public void onMouseConsumed() {
		mMiceConsumed++;
	}

	public int getMiceConsumed() {
		return mMiceConsumed;
	}

	public int compareTo(Individual compareIndividual) {
		int compareValue = compareIndividual.getGenomeValue();
		if (compareValue < mGenomeValue) {
			return -1;
		} else if (compareValue > mGenomeValue) {
			return 1;
		} else {
			return 0;
		}
	}

}

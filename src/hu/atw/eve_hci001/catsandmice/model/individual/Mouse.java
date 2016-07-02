package hu.atw.eve_hci001.catsandmice.model.individual;

import java.util.BitSet;

/**
 * This class represents mice.
 * 
 * @author László Ádám
 *
 */
public class Mouse extends Individual {


	public Mouse(int generation, BitSet genome, int geneMultiplier) {
		super(generation, genome, geneMultiplier);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Mouse\n");
		builder.append("generation:\t");
		builder.append(mGeneration);
		builder.append("\nspeed:\t");
		builder.append(mGenomeValue);
		builder.append("\ngenome:\t");
		builder.append(getGenomeString());
		builder.append("\nequation:\t");
		builder.append(getCalculationString());
		return builder.toString();
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

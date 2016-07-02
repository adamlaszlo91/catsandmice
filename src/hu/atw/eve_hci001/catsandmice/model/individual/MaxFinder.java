package hu.atw.eve_hci001.catsandmice.model.individual;

import java.util.BitSet;

/**
 * This class represents max finder individuals.
 * 
 * @author László Ádám
 *
 */
public class MaxFinder extends Individual {

	public MaxFinder(int generation, BitSet genome, int geneMultiplier) {
		super(generation, genome, geneMultiplier);
		// TODO Auto-generated constructor stub
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

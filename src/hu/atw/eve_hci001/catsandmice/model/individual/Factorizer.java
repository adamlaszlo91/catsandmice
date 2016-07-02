package hu.atw.eve_hci001.catsandmice.model.individual;

import java.util.BitSet;

import hu.atw.eve_hci001.catsandmice.control.populationrunner.FactorizerPopulationRunnable;

/**
 * This class represents a factorizer individual.
 * 
 * @author László Ádám
 *
 */
public class Factorizer extends Individual {

	public Factorizer(int generation, BitSet genome, int geneMultiplier) {
		super(generation, genome, geneMultiplier);
	}

	public int compareTo(Individual compareIndividual) {
		int compareDistance = Math
				.abs(FactorizerPopulationRunnable.DEFAULT_PRODUCT_TARGET - compareIndividual.getGenomeValue());
		int distance = Math.abs(FactorizerPopulationRunnable.DEFAULT_PRODUCT_TARGET - mGenomeValue);
		if (compareDistance > distance) {
			return -1;
		} else if (compareDistance < distance) {
			return 1;
		} else {
			return 0;
		}
	}

}

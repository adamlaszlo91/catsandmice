package hu.atw.eve_hci001.catsandmice.model.individual;

import java.util.BitSet;

/**
 * General abstract class to represent an individual of a species.
 * 
 * @author László Ádám
 *
 */
public abstract class Individual implements Comparable<Individual> {
	protected BitSet mGenome;
	protected int mGeneration;
	protected int mGenomeValue;
	protected String mGenomeString;
	protected String mCalculationString;
	protected int mGeneMultiplier;

	/**
	 * @param generation
	 *            Generation of the individual.
	 * @param genome
	 *            Genome of the individual.
	 */
	public Individual(int generation, BitSet genome, int geneMultiplier) {
		mGeneration = generation;
		mGenome = genome;
		mGeneMultiplier = geneMultiplier;
	}
	
	

	public int getGeneMultiplier() {
		return mGeneMultiplier;
	}



	public void setGeneMultiplier(int geneMultiplier) {
		mGeneMultiplier = geneMultiplier;
	}



	public BitSet getGenome() {
		return mGenome;
	}

	public void setGenome(BitSet genome) {
		mGenome = genome;
	}

	public int getGeneration() {
		return mGeneration;
	}

	public void setGeneration(int generation) {
		mGeneration = generation;
	}

	public int getGenomeValue() {
		return mGenomeValue;
	}

	public void setGenomeValue(int genomeValue) {
		mGenomeValue = genomeValue;
	}

	public String getGenomeString() {
		return mGenomeString;
	}

	public void setGenomeString(String genomeString) {
		mGenomeString = genomeString;
	}

	public String getCalculationString() {
		return mCalculationString;
	}

	public void setCalculationString(String calculationString) {
		mCalculationString = calculationString;
	}

}

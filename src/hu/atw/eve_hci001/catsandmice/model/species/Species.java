package hu.atw.eve_hci001.catsandmice.model.species;

import java.util.BitSet;

/**
 * General class to represent an individual of a species.
 * 
 * @author ÁdámLaci
 *
 */
public class Species implements Comparable<Species> {
	protected BitSet genome;
	protected int generation;
	protected int phenomeValue;
	protected String stringValueOfGenome;
	protected String stringValueOfCaluclation;

	/**
	 * Constructor.
	 * 
	 * @param generation
	 *            Generation of the individual.
	 * @param genome
	 *            Genome of the individual.
	 */
	public Species(int generation, BitSet genome) {
		this.generation = generation;
		this.genome = genome;
	}

	/**
	 * 
	 * @return the int of the genome.
	 */
	public int getPhenomeValue() {
		return phenomeValue;
	}

	public void setPhenomeValue(int phenomeValue) {
		this.phenomeValue = phenomeValue;
	}

	/**
	 * 
	 * @return The genome of the individual.
	 */
	public BitSet getGenome() {
		return genome;
	}

	/**
	 * 
	 * @return The generation of the individual.
	 */
	public int getGeneration() {
		return generation;
	}

	/**
	 * 
	 * @return The string value of its genome.
	 */
	public String getStringValueOfGenome() {
		return stringValueOfGenome;
	}

	public void setStringValueOfGenome(String stringValueOfGenome) {
		this.stringValueOfGenome = stringValueOfGenome;
	}

	/**
	 * 
	 * @return The string representation of the decoding of the genome.
	 */
	public String getstringValueOfCaluclation() {
		return stringValueOfCaluclation;
	}

	public void setPhenomeValueAndStringValueOfCaluclation(
			String stringValueOfCaluclation) {
		this.stringValueOfCaluclation = stringValueOfCaluclation;
	}

	public int compareTo(Species compareSpecies) {
		int compareValue = ((Species) compareSpecies).getPhenomeValue();
		if (compareValue < phenomeValue) {
			return -1;
		} else if (compareValue > phenomeValue) {
			return 1;
		} else {
			return 0;
		}
	}

}

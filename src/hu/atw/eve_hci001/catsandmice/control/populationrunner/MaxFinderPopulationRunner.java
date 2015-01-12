package hu.atw.eve_hci001.catsandmice.control.populationrunner;

import hu.atw.eve_hci001.catsandmice.control.CatsAndMiceController;
import hu.atw.eve_hci001.catsandmice.model.species.Species;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;

/**
 * Class for the Max finder population.
 * 
 * László Ádám
 *
 */
public class MaxFinderPopulationRunner extends PopulationRunner {
	private CatsAndMiceController catsAndMiceController;
	private Species bestSpecies = population.get(0);
	private boolean finalState = false;

	/**
	 * Constructor.
	 * 
	 * @param individualNum
	 *            Number of individuals in the initial population.
	 * @param geneLength
	 *            Length of a gene.
	 * @param geneMultipler
	 *            Determines how many genes are in the genome.
	 * @param phenomeValue
	 *            The value of the full genome. (eg. speed of a species)
	 * @param catsAndMiceController
	 *            The controller object.
	 */
	public MaxFinderPopulationRunner(int individualNum, int geneLength,
			int geneMultipler, int phenomeValue,
			CatsAndMiceController catsAndMiceController) {
		super(individualNum, geneLength, geneMultipler, phenomeValue);
		this.catsAndMiceController = catsAndMiceController;
	}

	protected void fillPopulation(int indivNum, BitSet defaultGeneSequence) {
		for (int i = 1; i <= indivNum; i++) {
			Species s = new Species(1, (BitSet) defaultGeneSequence.clone());
			setStringValueOfGenome(s);
			setPhenomeValueAndStringValueOfCaluclation(s);
			population.add(s);
		}
	}

	protected boolean isFinalState() {
		if (bestSpecies.getPhenomeValue() == maxValueOfGenome) {
			if (!finalState) {
				catsAndMiceController
						.printMaxFinderDetails("Max value reached.");
				finalState = true;
			}
			return true;
		} else {
			return false;
		}
	}

	protected void addToNewPopulation(int generation, BitSet newGenome) {
		Species s = new Species(generation, (BitSet) newGenome.clone());
		setStringValueOfGenome(s);
		setPhenomeValueAndStringValueOfCaluclation(s);
		newPopulation.add(s);
	}

	void selection() {
		/* keep the best half */
		Collections.sort(population);
		ArrayList<Species> bestPopulation = new ArrayList<Species>();
		for (int i = 0; i <= (population.size() / 2) - 1; i++) {
			bestPopulation.add(population.get(i));
		}
		population = bestPopulation;
		/* shuffle after the sorting, to keep the recombination random */
		Collections.shuffle(population);
		recombination();
	}

	void print() {
		bestSpecies = population.get(0);
		for (Species s : population) {
			if (s.getPhenomeValue() > bestSpecies.getPhenomeValue())
				bestSpecies = s;
		}
		catsAndMiceController.printMaxFinderDetails(bestSpecies
				.getStringValueOfGenome()
				+ "\t\t  "
				+ bestSpecies.getPhenomeValue()
				+ "\t"
				+ bestSpecies.getstringValueOfCaluclation() + "\n");
	}

}

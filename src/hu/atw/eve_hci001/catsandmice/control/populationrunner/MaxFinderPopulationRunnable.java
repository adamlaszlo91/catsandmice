package hu.atw.eve_hci001.catsandmice.control.populationrunner;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;

import hu.atw.eve_hci001.catsandmice.control.CatsAndMiceController;
import hu.atw.eve_hci001.catsandmice.model.individual.Individual;
import hu.atw.eve_hci001.catsandmice.model.individual.MaxFinder;

/**
 * Class for the Max finder population.
 * 
 * László Ádám
 *
 */
public class MaxFinderPopulationRunnable extends PopulationRunnable {
	private CatsAndMiceController mController;
	private Individual mBestIndividual;

	/**
	 * 
	 * @param individualNum
	 *            Number of individuals in the initial population.
	 * @param geneLength
	 *            Length of a gene.
	 * @param geneMultipler
	 *            Determines how many genes are in the genome.
	 * @param defaultGenomeValue
	 *            The value of the genome. (eg. speed)
	 * @param controller
	 *            The controller object.
	 */
	public MaxFinderPopulationRunnable(int individualNum, int geneLength, int geneMultipler, int defaultGenomeValue,
			CatsAndMiceController controller) {
		super(individualNum, geneLength, geneMultipler, defaultGenomeValue);
		mController = controller;
		chooseBestIndividual();
	}

	protected void fillPopulation(int indivNum, BitSet defaultGeneSequence) {
		for (int i = 1; i <= indivNum; i++) {
			MaxFinder individual = new MaxFinder(0, (BitSet) defaultGeneSequence.clone(), mGeneMultiplier);
			setStringValueOfGenome(individual);
			setGenomeValueAndCalculationStringFor(individual);
			mPopulation.add(individual);
		}
	}

	public boolean isFinalState() {
		chooseBestIndividual();
		if (mBestIndividual.getGenomeValue() == mGenomeValueMax) {
			mController.printText(CatsAndMiceController.TEXTFIELD_MAX_FINDER, "Max value reached.\n");
			if (mAutoStep) {
				mController.onAutoStepProgressEnd(CatsAndMiceController.RUNNABLE_MAX_FINDER);
			}
			if (mFreeRun) {
				mController.onFreeRunProgressEnd(CatsAndMiceController.RUNNABLE_MAX_FINDER);
			}
			return true;
		} else {
			return false;
		}
	}

	protected void addToNewPopulation(int generation, BitSet newGenome) {
		MaxFinder individual = new MaxFinder(generation, (BitSet) newGenome.clone(), mGeneMultiplier);
		setStringValueOfGenome(individual);
		setGenomeValueAndCalculationStringFor(individual);
		mNewPopulation.add(individual);
	}

	void selection() {
		/* keep the best half */
		Collections.sort(mPopulation);
		ArrayList<Individual> bestPopulation = new ArrayList<Individual>();
		for (int i = 0; i <= (mPopulation.size() / 2) - 1; i++) {
			bestPopulation.add(mPopulation.get(i));
		}
		mPopulation = bestPopulation;
		/* shuffle after the sorting, to keep the recombination random */
		Collections.shuffle(mPopulation);
		recombination();
	}

	void print() {
		chooseBestIndividual();
		mController.printText(CatsAndMiceController.TEXTFIELD_MAX_FINDER, mBestIndividual.getGenomeString() + "\t\t  "
				+ mBestIndividual.getGenomeValue() + "\t" + mBestIndividual.getCalculationString() + "\n");
	}
	
	private void chooseBestIndividual(){
		mBestIndividual = mPopulation.get(0);
		for (Individual individual : mPopulation) {
			if (individual.getGenomeValue() > mBestIndividual.getGenomeValue())
				mBestIndividual = individual;
		}
	}

}

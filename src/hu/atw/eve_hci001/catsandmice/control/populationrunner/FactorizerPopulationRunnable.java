package hu.atw.eve_hci001.catsandmice.control.populationrunner;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;

import hu.atw.eve_hci001.catsandmice.control.CatsAndMiceController;
import hu.atw.eve_hci001.catsandmice.model.individual.Factorizer;
import hu.atw.eve_hci001.catsandmice.model.individual.Individual;

/**
 * Class for the Max finder population.
 * 
 * László Ádám
 *
 */
public class FactorizerPopulationRunnable extends PopulationRunnable {
	public static final int DEFAULT_PRODUCT_TARGET = 36;

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
	 * @param genotype
	 *            The value of the full genome. (eg. speed of a species)
	 * @param catsAndMiceController
	 *            The controller object.
	 */
	public FactorizerPopulationRunnable(int individualNum, int geneLength, int geneMultipler, int defaultPhenomeValue,
			CatsAndMiceController catsAndMiceController) {
		super(individualNum, geneLength, geneMultipler, defaultPhenomeValue);
		this.mController = catsAndMiceController;
		chooseBestIndividual();
	}

	protected void fillPopulation(int indivNum, BitSet defaultGenome) {
		for (int i = 1; i <= indivNum; i++) {
			Factorizer individual = new Factorizer(1, (BitSet) defaultGenome.clone(), mGeneMultiplier);
			setStringValueOfGenome(individual);
			setGenomeValueAndCalculationStringFor(individual);
			mPopulation.add(individual);
		}
	}

	public boolean isFinalState() {
		chooseBestIndividual();
		if (mBestIndividual.getGenomeValue() == DEFAULT_PRODUCT_TARGET) {
			mController.printText(CatsAndMiceController.TEXTFIELD_FACTORIZER, "Target value reached.\n");
			if (mAutoStep) {
				mController.onAutoStepProgressEnd(CatsAndMiceController.RUNNABLE_FACTORIZER);
			}
			if (mFreeRun) {
				mController.onFreeRunProgressEnd(CatsAndMiceController.RUNNABLE_FACTORIZER);
			}
			return true;
		} else {
			return false;
		}
	}

	protected void addToNewPopulation(int generation, BitSet newGenome) {
		Factorizer individual = new Factorizer(generation, (BitSet) newGenome.clone(), mGeneMultiplier);
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
		mController.printText(CatsAndMiceController.TEXTFIELD_FACTORIZER, mBestIndividual.getGenomeString() + "\t\t  "
				+ mBestIndividual.getGenomeValue() + "\t" + mBestIndividual.getCalculationString() + "\n");
	}

	protected void setGenomeValueAndCalculationStringFor(Individual individual) {
		// never allow to multiply by 0, set every 0 value to 1

		for (int i = 0; i < mGenomeLength; i += mGeneLength) {
			if (0 == bitFragmentToInt(individual.getGenome(), i)) {
				individual.getGenome().set(i, true);
				individual.getGenome().set(i + mGeneLength - 1, true);
			}
		}

		BitSet genome = individual.getGenome();
		StringBuilder stringValueOfCaluculation = new StringBuilder();
		stringValueOfCaluculation.append("(");
		int phenomeValue = bitFragmentToInt(genome, 0);
		stringValueOfCaluculation.append(phenomeValue);
		stringValueOfCaluculation.append(")");
		for (int i = mGeneLength; i < mGenomeLength; i += mGeneLength) {
			int addend = bitFragmentToInt(genome, i);
			stringValueOfCaluculation.append("*(");
			stringValueOfCaluculation.append(addend);
			stringValueOfCaluculation.append(")");
			phenomeValue *= addend;
		}
		individual.setGenomeValue(phenomeValue);
		individual.setCalculationString(stringValueOfCaluculation.toString());
	}

	private void chooseBestIndividual() {
		mBestIndividual = mPopulation.get(0);
		for (Individual individual : mPopulation) {
			if (((Factorizer) individual).compareTo(mBestIndividual) == -1) {
				mBestIndividual = individual;
			}
		}
	}

}

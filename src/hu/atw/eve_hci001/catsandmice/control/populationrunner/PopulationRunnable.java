package hu.atw.eve_hci001.catsandmice.control.populationrunner;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

import hu.atw.eve_hci001.catsandmice.model.individual.Individual;

/**
 * This abstract class handles a population of individuals and performs
 * operations on them, such as evaluation, selection, recombination, genome
 * decoding, etc. A genotype can be negative, as the bit sequence is signed.
 * 
 * @author László Ádám
 *
 */
public abstract class PopulationRunnable implements Runnable {
	private Thread mThread;
	protected ArrayList<Individual> mPopulation;
	protected ArrayList<Individual> mNewPopulation;
	protected int mGenomeLength;
	protected int mGeneLength;
	protected int mAlleleNum; // ?
	protected int mGeneMultiplier;
	protected int mGenotypeMax;
	protected int mGenotypeMin;
	protected int mGenomeValueMax;
	protected int mGenomeValueMin;
	protected long mStepDelay;
	protected boolean mAutoStep;
	protected boolean mFreeRun;

	/**
	 * 
	 * @param individualNum
	 *            Number of individuals in the initial population.
	 * @param geneLength
	 *            Length of a gene.
	 * @param geneMultipler
	 *            Determines how many genes are in the genome.
	 * @param defaultGenomeValue
	 *            The default value of the genome. (eg. speed of an individual)
	 */
	public PopulationRunnable(int individualNum, int geneLength, int geneMultipler, int defaultGenomeValue) {
		mPopulation = new ArrayList<Individual>();
		mNewPopulation = new ArrayList<Individual>();
		mGeneLength = geneLength;
		mGeneMultiplier = geneMultipler;
		mGenomeLength = geneLength * geneMultipler;
		mAlleleNum = (int) Math.pow(2, geneLength);
		mGenotypeMax = (mAlleleNum / 2) - 1;
		mGenotypeMin = -(mAlleleNum / 2);
		mGenomeValueMax = mGenotypeMax * geneMultipler;
		mGenomeValueMin = mGenotypeMin * geneMultipler;
		BitSet defaultGenome = generateGenome(defaultGenomeValue);
		fillPopulation(individualNum, defaultGenome);
		mStepDelay = 500;
	}

	/**
	 * Abstract method for filling the default population with individuals
	 * 
	 * 
	 * @param individualNum
	 *            Number of individuals in the population.
	 * @param defaultGenome
	 *            Default genome for the individuals.
	 */
	abstract void fillPopulation(int individualNum, BitSet defaultGenome);

	public void start() {
		mThread = new Thread(this);
		mThread.start();
	}

	public void stop() {
		mThread = null;
	}

	/**
	 * Do not call it directly, use start() instead.
	 */
	public void run() {
		Thread thisThread = Thread.currentThread();
		// initial calculations
		for (Individual individual : mPopulation) {
			setStringValueOfGenome(individual);
			setGenomeValueAndCalculationStringFor(individual);
		}
		// print the initial state
		print();
		while (mThread == thisThread) {
			synchronized (mThread) {
				try {
					if (mFreeRun) {
						if (isFinalState()) {
							mThread.wait();
						}
						evaluation();
						continue;
					}
					if (mAutoStep && !isFinalState()) {
						mThread.wait(mStepDelay);
					} else {
						mThread.wait();
					}
					evaluation();
				} catch (InterruptedException ie) {
					/* idc */
				}
			}
		}
	}

	/**
	 * Generates a genome (as a BitSet) corresponding to the given genome value.
	 * The BitSet will be filled from left to right. This will be calculated by
	 * adding all the genotypes together.
	 * 
	 * @param phenomeVlue
	 *            Value of the whole genome.
	 * @return The genome corresponding to the given phenome value.
	 */
	protected BitSet generateGenome(int genomeValue) {
		/* restrict the phenomeValue */
		if (genomeValue > mGenomeValueMax)
			genomeValue = mGenomeValueMax;
		if (genomeValue < mGenomeValueMin)
			genomeValue = mGenomeValueMin;
		BitSet genome = new BitSet(mGenomeLength);
		/*
		 * subtract the minimum value of a gene, so we can use the binary form
		 * instead
		 */
		genomeValue = genomeValue - mGenotypeMin;
		int offset;
		// fill the genome
		for (offset = 0; offset < mGenomeLength; offset += mGeneLength) {
			// every bit will be set in this gene
			if (genomeValue >= (mAlleleNum - 1)) {
				genomeValue -= (mAlleleNum - 1);
				for (int k = offset; k < mGeneLength + offset; k++) {
					genome.set(k);
				}
				genomeValue = genomeValue - mGenotypeMin;
			} else {
				break;
			}
		}
		// mod
		for (int i = mGeneLength - 1; i >= 0; i--) {
			int pow = (int) Math.pow(2, i);
			if (genomeValue >= pow) {
				genomeValue -= pow;
				genome.set(offset + (mGeneLength - i - 1));
			}
		}
		// fill the rest with zero
		for (offset += mGeneLength; offset < mGenomeLength; offset += mGeneLength) {
			genome.set(offset);
		}
		return genome;
	}

	/**
	 * Performs the evaluation on the members of the population. Sets their
	 * genomeValue and string equivalents.
	 */
	private void evaluation() {
		if (isFinalState())
			return;
		for (Individual individual : mPopulation) {
			setStringValueOfGenome(individual);
			setGenomeValueAndCalculationStringFor(individual);
		}
		selection();
	}

	/**
	 * Abstract method for specifying a custom stop condition in the subclasses.
	 * 
	 * @return The state is final or not.
	 */
	public abstract boolean isFinalState();

	/**
	 * Converts the genome into a string of 1s and 0s and dividers.
	 * 
	 * @param s
	 *            The Species object which string must be set.
	 */
	protected void setStringValueOfGenome(Individual individual) {
		BitSet genome = individual.getGenome();
		StringBuilder stringValueOfGenome = new StringBuilder();
		for (int i = 0; i < mGenomeLength; i++) {
			stringValueOfGenome.append((genome.get(i)) ? "1" : "0");
			if ((i + 1) % mGeneLength == 0 && i != mGenomeLength - 1) {
				stringValueOfGenome.append("|");
			}
		}
		individual.setGenomeString(stringValueOfGenome.toString());
	}

	/**
	 * Sets the geome value and the string of decoding of the given individual
	 * object.
	 * 
	 * @param s
	 *            The Individual object.
	 */
	protected void setGenomeValueAndCalculationStringFor(Individual individual) {
		BitSet genome = individual.getGenome();
		StringBuilder stringValueOfCaluclation = new StringBuilder();
		stringValueOfCaluclation.append("(");
		int phenomeValue = bitFragmentToInt(genome, 0);
		stringValueOfCaluclation.append(phenomeValue);
		stringValueOfCaluclation.append(")");
		for (int i = mGeneLength; i < mGenomeLength; i += mGeneLength) {
			int addend = bitFragmentToInt(genome, i);
			stringValueOfCaluclation.append("+(");
			stringValueOfCaluclation.append(addend);
			stringValueOfCaluclation.append(")");
			phenomeValue += addend;
		}
		individual.setGenomeValue(phenomeValue);
		individual.setCalculationString(stringValueOfCaluclation.toString());
	}

	/**
	 * Converts a fragment of genome (BitSet) into an int. This can be negative:
	 * e.g. for a 4 length gene it's between -8 and 7. The decoding ends when
	 * the length of decoded fragment reaches the length of genes.
	 * 
	 * @param genome
	 *            The genome.
	 * @param offset
	 *            The start of the decoding.
	 * @return The int value of the fragment.
	 */
	protected int bitFragmentToInt(BitSet genome, int offset) {
		int value = 0;
		for (int i = 0; i < mGeneLength; i++) {
			value += (genome.get(offset + i)) ? (int) Math.pow(2, mGeneLength - i - 1) : 0;
		}
		return (value + mGenotypeMin);
	}

	/**
	 * Abstract method for specifying a custom selection method in the
	 * subclasses.
	 */
	abstract void selection();

	/**
	 * Performs the genome recombination on the population.
	 */
	protected void recombination() {
		for (int i = 1; i < mPopulation.size(); i++) {
			if (i % 2 == 1) {
				/* split them here */
				int decompositeIndex = new Random().nextInt(mGenomeLength - 1);
				BitSet newGenome1 = new BitSet(mGenomeLength);
				BitSet newGenome2 = new BitSet(mGenomeLength);
				/*
				 * the successor's generation will be max(parents' generations)
				 */
				int generation = (mPopulation.get(i).getGeneration() > mPopulation.get(i - 1).getGeneration())
						? mPopulation.get(i).getGeneration() : mPopulation.get(i - 1).getGeneration();
				for (int k = 0; k < mGenomeLength; k++) {
					if (k <= decompositeIndex) {
						newGenome1.set(k, mPopulation.get(i).getGenome().get(k));
						newGenome2.set(k, mPopulation.get(i - 1).getGenome().get(k));
					} else {
						newGenome1.set(k, mPopulation.get(i - 1).getGenome().get(k));
						newGenome2.set(k, mPopulation.get(i).getGenome().get(k));
					}
				}
				addToNewPopulation(generation + 1, newGenome1);
				addToNewPopulation(generation + 1, newGenome2);
			}
		}
		mutation();
	}

	/**
	 * Abstract method for adding a custom individuals to the new generation.
	 * 
	 * @param generation
	 *            The generation of the new individual.
	 * @param newGenome
	 *            The genome of the new individual.
	 */
	abstract void addToNewPopulation(int generation, BitSet newGenome);

	/**
	 * Performs the mutation.
	 */
	protected void mutation() {
		for (Individual individual : mNewPopulation) {
			int mutations = new Random().nextInt(individual.getGeneMultiplier());

			for (int i = 1; i < mutations; i++) {
				if (new Random().nextInt(2) > 0) {
					mutateIndividual(individual);
				}
			}
			/* update the individual */
			setStringValueOfGenome(individual);
			setGenomeValueAndCalculationStringFor(individual);
		}
		// put them back to the default list
		mPopulation.addAll(mNewPopulation);
		mNewPopulation.clear();
		print();
	}
	
	protected void mutateIndividual(Individual individual){
		individual.getGenome().flip(new Random().nextInt(mGenomeLength));
	}

	/**
	 * Performs the next step of evolution.
	 */
	public void nextStep() {
		synchronized (mThread) {
			mThread.notify();
		}
	}
	
	public boolean isAutoStep() {
		return mAutoStep;
	}

	public void setAutoStep(boolean autoStep) {
		mAutoStep = autoStep;
	}

	public boolean isFreeRun() {
		return mFreeRun;
	}

	public void setFreeRun(boolean freeRun) {
		mFreeRun = freeRun;
	}

	/**
	 * Sets the auto step setting to it's opposite.
	 */
	public void changeAutoStepSetting() {
		synchronized (mThread) {
			mAutoStep = !mAutoStep;
			mThread.notify();
		}
	}

	/**
	 * Sets the instant result setting to it's opposite.
	 */
	public void changeFreeRunSetting() {
		synchronized (mThread) {
			mFreeRun = !mFreeRun;
			mThread.notify();
		}
	}

	/**
	 * Abstract method for specifying a custom print() method in the subclasses.
	 */
	abstract void print();
}

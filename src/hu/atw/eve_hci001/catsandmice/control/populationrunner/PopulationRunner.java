package hu.atw.eve_hci001.catsandmice.control.populationrunner;

import hu.atw.eve_hci001.catsandmice.model.species.Species;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

/**
 * This abstract class contains a population of Species, and performs operations
 * on them, such as evaluation, selection, recombination, genome decoding, etc.
 * The full genome will be decoded into one number (sum of the genes' phenome
 * value). Each gene has the same length, and decoded to a number which can be
 * negative: e.g. for a 4 length gene it's between -8 and 7.
 * 
 * @author László Ádám
 *
 */
public abstract class PopulationRunner implements Runnable {
	private Thread t;
	protected ArrayList<Species> population;
	protected ArrayList<Species> newPopulation;
	protected int genomeLength;
	protected int geneLength;
	protected int alleleVariations;
	protected int geneMultiplier;
	protected int maxValueOfGene;
	protected int minValueOfGene;
	protected int maxValueOfGenome;
	protected int minValueOfGenome;
	protected Random rand;
	protected long delay;
	private boolean autoStep;

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
	 */
	public PopulationRunner(int individualNum, int geneLength,
			int geneMultipler, int phenomeValue) {
		population = new ArrayList<Species>();
		newPopulation = new ArrayList<Species>();
		this.geneLength = geneLength;
		this.geneMultiplier = geneMultipler;
		genomeLength = geneLength * geneMultipler;
		alleleVariations = (int) Math.pow(2, geneLength);
		maxValueOfGene = (alleleVariations / 2) - 1;
		minValueOfGene = -(alleleVariations / 2);
		maxValueOfGenome = maxValueOfGene * geneMultipler;
		minValueOfGenome = minValueOfGene * geneMultipler;
		BitSet defaultGenome = generateGenome(phenomeValue);
		fillPopulation(individualNum, defaultGenome);
		rand = new Random();
		delay = 500;
		autoStep = false;
	}

	/**
	 * Abstract class for filling the default population in the it's subclasses
	 * with Species objects.
	 * 
	 * @param individualNum
	 *            Number of individuals in the population.
	 * @param defaultGenome
	 *            Default genome for the individuals.
	 */
	abstract void fillPopulation(int individualNum, BitSet defaultGenome);

	/**
	 * Start method of the thread.
	 */
	public void start() {
		t = new Thread(this);
		t.start();
	}

	/**
	 * Stop method of the thread.
	 */
	public void stop() {
		t = null;
	}

	/**
	 * Performs the operations. Do not call directly.
	 */
	public void run() {
		Thread thisThread = Thread.currentThread();
		/* initial precalculations */
		for (Species s : population) {
			setStringValueOfGenome(s);
			setPhenomeValueAndStringValueOfCaluclation(s);
		}
		print();
		while (t == thisThread) {
			synchronized (t) {
				try {
					if (autoStep) {
						t.wait(delay);
					} else {
						t.wait();
					}
					/* each cycle starts with evaluation */
					evaluation();
				} catch (InterruptedException ie) {
					/* idc */
				}
			}
		}
	}

	/**
	 * Generates a genome (as a BitSet) corresponding to the given phenome
	 * value. The BitSet will be filled from left to right.
	 * 
	 * @param phenomeVlue
	 *            Value of the whole genome.
	 * @return The genome corresponding to the given phenome value.
	 */
	protected BitSet generateGenome(int phenomeValue) {
		/* restrict the phenomeValue */
		if (phenomeValue > maxValueOfGenome)
			phenomeValue = maxValueOfGenome;
		if (phenomeValue < minValueOfGenome)
			phenomeValue = minValueOfGenome;
		BitSet geneSequence = new BitSet(genomeLength);
		/*
		 * subtract the minimum value of a gene, so we can use the binary form
		 * instead
		 */
		phenomeValue = phenomeValue - minValueOfGene;
		int offset;
		/* fill the genome */
		for (offset = 0; offset < genomeLength; offset += geneLength) {
			/* every bit will be set in this gene */
			if (phenomeValue >= (alleleVariations - 1)) {
				phenomeValue -= (alleleVariations - 1);
				for (int k = offset; k < geneLength + offset; k++) {
					geneSequence.set(k);
				}
				phenomeValue = phenomeValue - minValueOfGene;
			} else {
				break;
			}
		}
		/* mod */
		for (int i = geneLength - 1; i >= 0; i--) {
			int pow = (int) Math.pow(2, i);
			if (phenomeValue >= pow) {
				phenomeValue -= pow;
				geneSequence.set(offset + (geneLength - i - 1));
			}
		}
		/* fill the rest with zero */
		for (offset += geneLength; offset < genomeLength; offset += geneLength) {
			geneSequence.set(offset);
		}
		return geneSequence;
	}

	/**
	 * Performs the evaluation on the members of the population. Sets their
	 * phenomeValue and String stuffs.
	 */
	private void evaluation() {
		if (isFinalState())
			return;
		for (Species s : population) {
			setStringValueOfGenome(s);
			setPhenomeValueAndStringValueOfCaluclation(s);
		}
		selection();
	}

	/**
	 * Abstract method for specifying a custom stop condition in the subclasses.
	 * 
	 * @return The state is final or not.
	 */
	abstract boolean isFinalState();

	/**
	 * Converts the genome into a string of 1s and 0s and dividers.
	 * 
	 * @param s
	 *            The Species object which string must be set.
	 */
	protected void setStringValueOfGenome(Species s) {
		BitSet genome = s.getGenome();
		String stringValueOfGenome = "";
		for (int i = 0; i < genomeLength; i++) {
			stringValueOfGenome += (genome.get(i)) ? "1" : "0";
			if ((i + 1) % geneLength == 0 && i != genomeLength - 1) {
				stringValueOfGenome += "|";
			}
		}
		s.setStringValueOfGenome(stringValueOfGenome);
	}

	/**
	 * Sets the phenome value and the string of decoding of the given Species
	 * object.
	 * 
	 * @param s
	 *            The Species object.
	 */
	protected void setPhenomeValueAndStringValueOfCaluclation(Species s) {
		BitSet genome = s.getGenome();
		String stringValueOfCaluclation = "";
		int phenomeValue = bitFragmentToInt(genome, 0);
		stringValueOfCaluclation = "" + phenomeValue;
		for (int i = geneLength; i < genomeLength; i += geneLength) {
			int addend = bitFragmentToInt(genome, i);
			stringValueOfCaluclation += "+(" + addend + ")";
			phenomeValue += addend;
		}
		s.setPhenomeValue(phenomeValue);
		s.setPhenomeValueAndStringValueOfCaluclation(stringValueOfCaluclation);
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
	private int bitFragmentToInt(BitSet genome, int offset) {
		int value = 0;
		for (int i = 0; i < geneLength; i++) {
			value += (genome.get(offset + i)) ? (int) Math.pow(2, geneLength
					- i - 1) : 0;
		}
		return (value + minValueOfGene);
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
		for (int i = 1; i < population.size(); i++) {
			if (i % 2 == 1) {
				/* split them here */
				int decompositeIndex = rand.nextInt(genomeLength - 1);
				BitSet newGenome1 = new BitSet(genomeLength);
				BitSet newGenome2 = new BitSet(genomeLength);
				/* the successor's generation will be max(parents' generations) */
				int generation = (population.get(i).getGeneration() > population
						.get(i - 1).getGeneration()) ? population.get(i)
						.getGeneration() : population.get(i - 1)
						.getGeneration();
				for (int k = 0; k < genomeLength; k++) {
					if (k <= decompositeIndex) {
						newGenome1.set(k, population.get(i).getGenome().get(k));
						newGenome2.set(k, population.get(i - 1).getGenome()
								.get(k));
					} else {
						newGenome1.set(k, population.get(i - 1).getGenome()
								.get(k));
						newGenome2.set(k, population.get(i).getGenome().get(k));
					}
				}
				addToNewPopulation(generation + 1, newGenome1);
				addToNewPopulation(generation + 1, newGenome2);
			}
		}
		mutation();
	}

	/**
	 * Abstract method for adding a custom child object of Species object to the
	 * new generation.
	 * 
	 * @param generation
	 *            The generation of the new individual.
	 * @param newGenome
	 *            The genome of the new individual.
	 */
	abstract void addToNewPopulation(int generation, BitSet newGenome);

	/**
	 * Performs the mutation. the mutation has 50% chance to flip a bit in the
	 * genome.
	 */
	private void mutation() {
		for (Species s : newPopulation) {
			if (rand.nextInt(2) > 0) {
				s.getGenome().flip(rand.nextInt(genomeLength));
				/* update the individual */
				setStringValueOfGenome(s);
				setPhenomeValueAndStringValueOfCaluclation(s);
			}
		}
		population.addAll(newPopulation);
		newPopulation.clear();
		print();
	}

	/**
	 * Performs the next step of evolution.
	 */
	public void nextStep() {
		synchronized (t) {
			t.notify();
		}
	}

	/**
	 * Sets the auto step setting to it's opposite.
	 */
	public void changeAutoStep() {
		synchronized (t) {
			autoStep = !autoStep;
			t.notify();
		}
	}

	/**
	 * Abstract method for specifying a custom print() method in the subclasses.
	 */
	abstract void print();
}

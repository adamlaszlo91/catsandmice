package hu.atw.eve_hci001.catsandmice.control.populationrunner;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

import hu.atw.eve_hci001.catsandmice.control.CatsAndMiceController;
import hu.atw.eve_hci001.catsandmice.model.individual.Cat;
import hu.atw.eve_hci001.catsandmice.model.individual.Individual;
import hu.atw.eve_hci001.catsandmice.model.individual.Mouse;

/**
 * This class handles a mouse population and a static cat population.
 * 
 * @author László Ádám
 *
 */

public class MousePopulationRunnable extends PopulationRunnable {
	private CatsAndMiceController mController;
	private ArrayList<Cat> mCats;

	/**
	 * 
	 * @param catNum
	 *            Number of cats.
	 * @param catGenomeValue
	 *            The value of the genome of cats. (eg. speed)
	 * @param individualNum
	 *            Number of individuals in the initial population.
	 * @param geneLength
	 *            Length of a gene.
	 * @param geneMultipler
	 *            Determines how many genes are in the genome.
	 * @param defaultGenomeValue
	 *            The default value of genome of mice. (eg. speed)
	 * @param controller
	 *            The controller object.
	 */
	public MousePopulationRunnable(int catNum, int catGenomeValue, int individualNum, int geneLength, int geneMultipler,
			int defaultGenomeValue, CatsAndMiceController controller) {
		super(individualNum, geneLength, geneMultipler, defaultGenomeValue);
		mController = controller;
		mCats = new ArrayList<Cat>();
		// generate cats
		BitSet catGenome = generateGenome(catGenomeValue);
		for (int i = 1; i <= catNum; i++) {
			Cat cat = new Cat(0, (BitSet) catGenome.clone(), mGeneMultiplier);
			setStringValueOfGenome(cat);
			setGenomeValueAndCalculationStringFor(cat);
			mCats.add(cat);
		}
	}

	protected void fillPopulation(int indivNum, BitSet defaultGeneSequence) {
		for (int i = 1; i <= indivNum; i++) {
			Mouse m = new Mouse(0, (BitSet) defaultGeneSequence.clone(), mGeneMultiplier);
			setStringValueOfGenome(m);
			setGenomeValueAndCalculationStringFor(m);
			mPopulation.add(m);
		}
	}

	public boolean isFinalState() {
		if (mPopulation.size() == 0) {
			/* no more mice */
			mController.clearText(CatsAndMiceController.TEXTFIELD_MICE);
			mController.printText(CatsAndMiceController.TEXTFIELD_MICE, "Mice are extinct, so the cats.\n");
			if (mAutoStep) {
				mController.onAutoStepProgressEnd(CatsAndMiceController.RUNNABLE_CATS_AND_MICE);
			}
			if (mFreeRun) {
				mController.onFreeRunProgressEnd(CatsAndMiceController.RUNNABLE_CATS_AND_MICE);
			}
			return true;
		} else if (mPopulation.size() > 1000) {
			/* too many mice */
			mController.clearText(CatsAndMiceController.TEXTFIELD_CATS);
			mController.printText(CatsAndMiceController.TEXTFIELD_CATS, "Cats are enormous(e)ly outnumbered.\n");
			if (mAutoStep) {
				mController.onAutoStepProgressEnd(CatsAndMiceController.RUNNABLE_CATS_AND_MICE);
			}
			if (mFreeRun) {
				mController.onFreeRunProgressEnd(CatsAndMiceController.RUNNABLE_CATS_AND_MICE);
			}
			return true;
		} else {
			return false;
		}
	}

	protected void addToNewPopulation(int generation, BitSet geneSequence) {
		Mouse m = new Mouse(generation, (BitSet) geneSequence.clone(), mGeneMultiplier);
		setStringValueOfGenome(m);
		setGenomeValueAndCalculationStringFor(m);
		mNewPopulation.add(m);
	}

	void selection() {
		for (Cat cat : mCats) {
			/* two attempts for every cat to eat a mouse */
			for (int i = 1; i <= 2; i++) {
				if (mPopulation.size() == 0) break;
				int mouseIndex = new Random().nextInt(mPopulation.size());
				if (cat.getGenomeValue() >= mPopulation.get(mouseIndex).getGenomeValue()) {
					cat.onMouseConsumed();
					mPopulation.remove(mouseIndex);
				}
			}
		}
		recombination();
	}

	void print() {
		mController.clearText(CatsAndMiceController.TEXTFIELD_MICE);
		mController.clearText(CatsAndMiceController.TEXTFIELD_CATS);

		mController.printText(CatsAndMiceController.TEXTFIELD_CATS,
				"Cat number: " + mCats.size() + "\nMax speed: " + mCats.get(0).getGenomeValue() + "\n\n");
		for (Cat c : mCats) {
			mController.printText(CatsAndMiceController.TEXTFIELD_CATS, c.toString() + "\n\n");
		}
		int maxSpeed = mGenomeValueMin;
		for (Individual s : mPopulation) {
			if (s.getGenomeValue() > maxSpeed) {
				maxSpeed = s.getGenomeValue();
			}
		}
		mController.printText(CatsAndMiceController.TEXTFIELD_MICE,
				"Mouse number: " + mPopulation.size() + "\nMax speed: " + maxSpeed + "\n\n");
		String mouseList = "";
		for (Individual s : mPopulation) {
			mouseList += ((Mouse) s).toString() + "\n\n";
		}
		mController.printText(CatsAndMiceController.TEXTFIELD_MICE, mouseList);
	}
}

package hu.atw.eve_hci001.catsandmice.control.populationrunner;

import hu.atw.eve_hci001.catsandmice.control.CatsAndMiceController;
import hu.atw.eve_hci001.catsandmice.model.species.Cat;
import hu.atw.eve_hci001.catsandmice.model.species.Mouse;
import hu.atw.eve_hci001.catsandmice.model.species.Species;

import java.util.ArrayList;
import java.util.BitSet;

/**
 * This class handles a mouse population and a static cat population.
 * 
 * @author László Ádám
 *
 */

public class MousePopulationRunner extends PopulationRunner {
	private CatsAndMiceController catsAndMiceController;
	private ArrayList<Cat> catPopulation;

	/**
	 * Constructor.
	 * 
	 * @param catNum
	 *            Number of cats.
	 * @param catPhenomeValue
	 *            The value of the full genome of cats. (eg. speed of a species)
	 * @param individualNum
	 *            Number of individuals in the initial population.
	 * @param geneLength
	 *            Length of a gene.
	 * @param geneMultipler
	 *            Determines how many genes are in the genome.
	 * @param phenomeValue
	 *            The value of the full genome of mice. (eg. speed of a species)
	 * @param catsAndMiceController
	 *            The controller object.
	 */
	public MousePopulationRunner(int catNum, int catPhenomeValue,
			int individualNum, int geneLength, int geneMultipler,
			int phenomeValue, CatsAndMiceController catsAndMiceController) {
		super(individualNum, geneLength, geneMultipler, phenomeValue);
		this.catsAndMiceController = catsAndMiceController;
		catPopulation = new ArrayList<Cat>();
		BitSet catGenome = generateGenome(catPhenomeValue);
		for (int i = 1; i <= catNum; i++) {
			Cat cat = new Cat(1, (BitSet) catGenome.clone());
			setStringValueOfGenome(cat);
			setPhenomeValueAndStringValueOfCaluclation(cat);
			catPopulation.add(cat);
		}
		this.catsAndMiceController = catsAndMiceController;
	}

	protected void fillPopulation(int indivNum, BitSet defaultGeneSequence) {
		for (int i = 1; i <= indivNum; i++) {
			Mouse m = new Mouse(1, (BitSet) defaultGeneSequence.clone());
			setStringValueOfGenome(m);
			setPhenomeValueAndStringValueOfCaluclation(m);
			population.add(m);
		}
	}

	protected boolean isFinalState() {
		if (population.size() == 0) {
			/* no more mice */
			catsAndMiceController.clearMouseDetails();
			catsAndMiceController
					.printMouseDetails("Mice are extinct - so the cats.");
			return true;
		} else if (population.size() > 1000) {
			/* too many mice */
			catsAndMiceController.clearCatDetails();
			catsAndMiceController
					.printCatDetails("Cats are enormous(e)ly overnumbered.");
			return true;
		} else {
			return false;
		}
	}

	protected void addToNewPopulation(int generation, BitSet geneSequence) {
		Mouse m = new Mouse(generation, (BitSet) geneSequence.clone());
		setStringValueOfGenome(m);
		setPhenomeValueAndStringValueOfCaluclation(m);
		newPopulation.add(m);
	}

	void selection() {
		for (Cat c : catPopulation) {
			/* two attempts to eat a mouse for every cat */
			int mouseIndex = rand.nextInt(population.size());
			if (c.getPhenomeValue() >= population.get(mouseIndex)
					.getPhenomeValue()) {
				c.miceConsumed();
				population.remove(mouseIndex);
			}
			mouseIndex = rand.nextInt(population.size());
			if (c.getPhenomeValue() >= population.get(mouseIndex)
					.getPhenomeValue()) {
				c.miceConsumed();
				population.remove(mouseIndex);
			}
		}
		recombination();
	}

	void print() {
		catsAndMiceController.clearMouseDetails();
		catsAndMiceController.clearCatDetails();

		catsAndMiceController.printCatDetails("Cat number: "
				+ catPopulation.size() + "\nSpeed:"
				+ catPopulation.get(0).getPhenomeValue() + "\n\n");
		for (Cat c : catPopulation) {
			catsAndMiceController.printCatDetails(c.toString() + "\n\n");
		}
		int maxSpeed = minValueOfGenome;
		for (Species s : population) {
			if (s.getPhenomeValue() > maxSpeed) {
				maxSpeed = s.getPhenomeValue();
			}
		}
		catsAndMiceController.printMouseDetails("Mouse number: "
				+ population.size() + "\nMax speed:" + maxSpeed + "\n\n");
		String mouseList = "";
		for (Species s : population) {
			mouseList += ((Mouse) s).toString() + "\n\n";
		}
		catsAndMiceController.printMouseDetails(mouseList);
	}
}

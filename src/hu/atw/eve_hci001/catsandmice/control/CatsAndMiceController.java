package hu.atw.eve_hci001.catsandmice.control;

import hu.atw.eve_hci001.catsandmice.control.populationrunner.MaxFinderPopulationRunner;
import hu.atw.eve_hci001.catsandmice.control.populationrunner.MousePopulationRunner;
import hu.atw.eve_hci001.catsandmice.control.populationrunner.PopulationRunner;
import hu.atw.eve_hci001.catsandmice.view.CatsAndMiceGUI;

/**
 * Controller class for the Cats and Mice.
 * 
 * @author László Ádám
 *
 */

public class CatsAndMiceController {
	private CatsAndMiceGUI catsAndMiceGUI;
	private PopulationRunner mousePopulationRunner;
	private PopulationRunner maxFinderPopulationRunner;

	/**
	 * Constructor.
	 */
	public CatsAndMiceController() {
		catsAndMiceGUI = new CatsAndMiceGUI(this);
		resetMaxPopulationHandler();
		resetMousePopulationHandler(3, 12);
	}

	/**
	 * Creates a new maxPopulationHandler and uses it.
	 */
	public void resetMaxPopulationHandler() {
		if (maxFinderPopulationRunner != null)
			maxFinderPopulationRunner.stop();
		maxFinderPopulationRunner = new MaxFinderPopulationRunner(12, 5, 4, 0,
				this);
		maxFinderPopulationRunner.start();
	}

	/**
	 * Creates a new mousePopulationHandler and uses it.
	 * 
	 * @param catNum
	 *            Number of cats.
	 * @param mouseNum
	 *            Number of mice.
	 */
	public void resetMousePopulationHandler(int catNum, int mouseNum) {
		if (mousePopulationRunner != null)
			mousePopulationRunner.stop();
		mousePopulationRunner = new MousePopulationRunner(catNum, 10, mouseNum,
				4, 3, 5, this);
		mousePopulationRunner.start();
	}

	/**
	 * Prints text to the GUI's max finder textfield.
	 * 
	 * @param text
	 *            The text to be printed.
	 */
	public void printMaxFinderDetails(String text) {
		catsAndMiceGUI.printMaxFinderDetails(text);
	}

	/**
	 * Prints text to the GUI's cat textarea.
	 * 
	 * @param text
	 *            The text to be printed.
	 */
	public void printCatDetails(String text) {
		catsAndMiceGUI.printCatDetails(text);
	}

	/**
	 * Prints text to the GUI's mouse textarea.
	 * 
	 * @param text
	 *            The text to be printed.
	 */
	public void printMouseDetails(String text) {
		catsAndMiceGUI.printMouseDetails(text);
	}

	/**
	 * Performs the next step of the max fnder's evolution.
	 */
	public void maxFinderNextStep() {
		maxFinderPopulationRunner.nextStep();
	}

	/**
	 * Performs the next step of the cats and mice game's evolution.
	 */
	public void mouseNextStep() {
		mousePopulationRunner.nextStep();
	}

	/**
	 * Changes the autostep setting of the max finder.
	 */
	public void maxChangeAutoStep() {
		maxFinderPopulationRunner.changeAutoStep();
	}

	/**
	 * Changes the autostep setting of the cats and mice game.
	 */
	public void mouseChangeAutoStep() {
		mousePopulationRunner.changeAutoStep();
	}

	/**
	 * Clears the GUI's mice textarea.
	 */
	public void clearMouseDetails() {
		catsAndMiceGUI.clearMouseDetails();
	}

	/**
	 * Clears the GUI's cats textarea.
	 */
	public void clearCatDetails() {
		catsAndMiceGUI.clearCatDetails();
	}

	/**
	 * Terminates the program.
	 */
	public void exit() {
		if (mousePopulationRunner != null) {
			mousePopulationRunner.stop();
		}
		if (maxFinderPopulationRunner != null) {
			maxFinderPopulationRunner.stop();
		}
		System.exit(0);
	}

}

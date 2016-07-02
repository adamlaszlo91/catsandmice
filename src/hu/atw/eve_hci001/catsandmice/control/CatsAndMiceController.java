package hu.atw.eve_hci001.catsandmice.control;

import hu.atw.eve_hci001.catsandmice.control.populationrunner.FactorizerPopulationRunnable;
import hu.atw.eve_hci001.catsandmice.control.populationrunner.MaxFinderPopulationRunnable;
import hu.atw.eve_hci001.catsandmice.control.populationrunner.MousePopulationRunnable;
import hu.atw.eve_hci001.catsandmice.control.populationrunner.PopulationRunnable;
import hu.atw.eve_hci001.catsandmice.view.CatsAndMiceGUI;

/**
 * Controller class for the Cats and Mice.
 * 
 * @author László Ádám
 *
 */

public class CatsAndMiceController {
	public static final int DEFAULT_CAT_NUM = 3;
	public static final int DEFAULT_MOUSE_NUM = 12;
	public static final int DEFAULT_MOUSE_PEHOTYPE = 5;
	public static final int DEFAULT_MOUSE_GENOME_LENGTH = 4;
	public static final int DEFAULT_MOUSE_GENOME_MULTIPLIER = 3;
	public static final int DEFAULT_CAT_PEHOTYPE = 10;

	public static final int DEFAULT_MAX_FINDER_INDIVIDUAL_NUM = 12;
	public static final int DEFAULT_MAX_FINDER_GENOME_LENGTH = 5;
	public static final int DEFAULT_MAX_FINDER_GENOME_MULTIPLIER = 4;
	public static final int DEFAULT_MAX_FINDER_PHENOTYPE = 0;

	public static final String RUNNABLE_CATS_AND_MICE = "RUNNER_CATS_AND_MICE";
	public static final String RUNNABLE_MAX_FINDER = "RUNNER_MAX_FINDER";
	public static final String RUNNABLE_FACTORIZER = "RUNNER_FACTORIZER";

	public static final String TEXTFIELD_CATS = "TEXTFIELD_CATS";
	public static final String TEXTFIELD_MICE = "TEXTFIELD_MICE";
	public static final String TEXTFIELD_MAX_FINDER = "TEXTFIELD_MAX_FINDER";
	public static final String TEXTFIELD_FACTORIZER = "TEXTFIELD_FACTORIZER";

	private CatsAndMiceGUI mGUI;
	private PopulationRunnable mMousePopulationRunnable;
	private PopulationRunnable mMaxFinderPopulationRunnable;
	private PopulationRunnable mFactorizerPopulationRunnable;

	public CatsAndMiceController() {
		mGUI = new CatsAndMiceGUI(this);
		reset(RUNNABLE_MAX_FINDER, 0, 0);
		reset(RUNNABLE_FACTORIZER, 0, 0);
		reset(RUNNABLE_CATS_AND_MICE, DEFAULT_CAT_NUM, DEFAULT_MOUSE_NUM);
	}

	/**
	 * Creates a new runnable, stops the previous one, and uses the new one.
	 * 
	 * @param type
	 *            Type of runnable
	 * @param catNum
	 *            Number of cats.
	 * @param mouseNum
	 *            Number of mice.
	 */
	public void reset(String type, int catNum, int mouseNum) {
		switch (type) {
		case RUNNABLE_CATS_AND_MICE:
			if (mMousePopulationRunnable != null) {
				mMousePopulationRunnable.stop();
			}
			mMousePopulationRunnable = new MousePopulationRunnable(catNum, DEFAULT_CAT_PEHOTYPE, mouseNum,
					DEFAULT_MOUSE_GENOME_LENGTH, DEFAULT_MOUSE_GENOME_MULTIPLIER, DEFAULT_MOUSE_PEHOTYPE, this);
			mMousePopulationRunnable.start();
			break;
		case RUNNABLE_MAX_FINDER:
			if (mMaxFinderPopulationRunnable != null) {
				mMaxFinderPopulationRunnable.stop();
			}
			mMaxFinderPopulationRunnable = new MaxFinderPopulationRunnable(DEFAULT_MAX_FINDER_INDIVIDUAL_NUM,
					DEFAULT_MAX_FINDER_GENOME_LENGTH, DEFAULT_MAX_FINDER_GENOME_MULTIPLIER,
					DEFAULT_MAX_FINDER_PHENOTYPE, this);
			mMaxFinderPopulationRunnable.start();
			break;
		case RUNNABLE_FACTORIZER:
			if (mFactorizerPopulationRunnable != null) {
				mFactorizerPopulationRunnable.stop();
			}
			mFactorizerPopulationRunnable = new FactorizerPopulationRunnable(DEFAULT_MAX_FINDER_INDIVIDUAL_NUM,
					DEFAULT_MAX_FINDER_GENOME_LENGTH, 5, 0, this);
			mFactorizerPopulationRunnable.start();
			break;
		}
	}

	/**
	 * Prints text to the GUI's corresponding textfield.
	 * 
	 * @param target
	 *            Type of target textfield
	 * @param text
	 *            The text to be printed.
	 */
	public void printText(String target, String text) {
		mGUI.printText(target, text);
	}

	/**
	 * Clears the details of the GUI's corresponding textfield.
	 * 
	 * @param type
	 *            Type of details
	 */
	public void clearText(String target) {
		mGUI.clearText(target);
	}

	/**
	 * Performs the next step of the corresponding game's evolution.
	 * 
	 * @param type
	 *            Type of runnable
	 */
	public void step(String type) {
		switch (type) {
		case RUNNABLE_CATS_AND_MICE:
			mMousePopulationRunnable.nextStep();
			break;
		case RUNNABLE_MAX_FINDER:
			mMaxFinderPopulationRunnable.nextStep();
			break;
		case RUNNABLE_FACTORIZER:
			mFactorizerPopulationRunnable.nextStep();
			break;
		}
	}

	/**
	 * Changes the auto step setting of the corresponding runnable
	 * 
	 * @param type
	 *            Type of runnable
	 */
	public void changeAutoStepSetting(String type) {
		switch (type) {
		case RUNNABLE_CATS_AND_MICE:
			mMousePopulationRunnable.changeAutoStepSetting();
			break;
		case RUNNABLE_MAX_FINDER:
			mMaxFinderPopulationRunnable.changeAutoStepSetting();
			break;
		case RUNNABLE_FACTORIZER:
			mFactorizerPopulationRunnable.changeAutoStepSetting();
			break;
		}
	}

	/**
	 * Changes the free run setting of the corresponding runnable
	 * 
	 * @param type
	 *            Type of runnable
	 */
	public void changeInstantResultSetting(String type) {
		switch (type) {
		case RUNNABLE_CATS_AND_MICE:
			mMousePopulationRunnable.changeFreeRunSetting();
			break;
		case RUNNABLE_MAX_FINDER:
			mMaxFinderPopulationRunnable.changeFreeRunSetting();
			break;
		case RUNNABLE_FACTORIZER:
			mFactorizerPopulationRunnable.changeFreeRunSetting();
			break;
		}
	}

	/**
	 * Terminates the program.
	 */
	public void exit() {
		if (mMousePopulationRunnable != null) {
			mMousePopulationRunnable.stop();
		}
		if (mMaxFinderPopulationRunnable != null) {
			mMaxFinderPopulationRunnable.stop();
		}
		if (mFactorizerPopulationRunnable != null) {
			mFactorizerPopulationRunnable.stop();
		}
		System.exit(0);
	}

	/**
	 * Tells if the auto step is in progress
	 * 
	 * @param type
	 *            Type of runner.
	 * 
	 * @return
	 */
	public boolean isAutoStepInProgress(String type) {
		switch (type) {
		case RUNNABLE_CATS_AND_MICE:
			return mMousePopulationRunnable.isAutoStep() && !mMousePopulationRunnable.isFinalState();
		case RUNNABLE_MAX_FINDER:
			return mMaxFinderPopulationRunnable.isAutoStep() && !mMaxFinderPopulationRunnable.isFinalState();
		case RUNNABLE_FACTORIZER:
			return mFactorizerPopulationRunnable.isAutoStep() && !mFactorizerPopulationRunnable.isFinalState();
		}
		return true;
	}

	/**
	 * Tells if the instant result is in progress
	 * 
	 * @param type
	 *            Type of runner.
	 * 
	 * @return
	 */
	public boolean isFreeRunInProgress(String type) {
		switch (type) {
		case RUNNABLE_CATS_AND_MICE:
			return mMousePopulationRunnable.isFreeRun() && !mMousePopulationRunnable.isFinalState();
		case RUNNABLE_MAX_FINDER:
			return mMaxFinderPopulationRunnable.isFreeRun() && !mMaxFinderPopulationRunnable.isFinalState();
		case RUNNABLE_FACTORIZER:
			return mFactorizerPopulationRunnable.isFreeRun() && !mFactorizerPopulationRunnable.isFinalState();
		}
		return true;
	}

	/**
	 * Indicates that the autostep progress has ended
	 * 
	 * @param type
	 *            Type of runner.
	 * 
	 * @return
	 */
	public void onAutoStepProgressEnd(String type) {
		mGUI.onAutoStepProgressEnd(type);
	}

	/**
	 * Indicates that the instant result progress has ended
	 * 
	 * @param type
	 *            Type of runner.
	 * 
	 * @return
	 */
	public void onFreeRunProgressEnd(String type) {
		mGUI.onInstantResultProgressEnd(type);
	}

}

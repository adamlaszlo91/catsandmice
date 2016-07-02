package hu.atw.eve_hci001.catsandmice.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;

import hu.atw.eve_hci001.catsandmice.control.CatsAndMiceController;
import hu.atw.eve_hci001.catsandmice.control.populationrunner.FactorizerPopulationRunnable;

/**
 * GUI for the Cats and Mice program.
 * 
 * @author László Ádám
 *
 */
public class CatsAndMiceGUI {
	private JFrame mFrame;
	private CatsAndMiceController mController;
	private JTextArea mFactorizerTextArea;
	private JTextArea mMaxFinderTextArea;
	private JTextArea mCatTextArea;
	private JTextArea mMouseTextArea;
	private JButton mMouseAutoButton;
	private JButton mMouseNextButton;
	private JButton mMouseNewButton;
	private JButton mMouseFreeRunButton;
	private JSpinner mCatSpinner;
	private JSpinner mMouseSpinner;
	private JButton mMaxAutoButton;
	private JButton mMaxNextButton;
	private JButton mMaxNewButton;
	private JButton mMaxFreeRunButton;
	private JButton mFactorizerAutoButton;
	private JButton mFactorizerNextButton;
	private JButton mFactorizerNewButton;
	private JButton mFactorizerFreeRunButton;

	/**
	 * 
	 * @param controller
	 *            The controller object.
	 */
	public CatsAndMiceGUI(CatsAndMiceController controller) {
		mController = controller;
		initViews();
		initActions();
	}

	/**
	 * Initializes the views
	 */
	private void initViews() {
		// main frame
		mFrame = new JFrame("Cats and Mice 1.0.0");
		mFrame.setResizable(false);

		JTabbedPane tabs = new JTabbedPane();

		// Cats and Mice panel -----------------------------------------------
		JPanel catsAndMiceTab = new JPanel();
		catsAndMiceTab.setLayout(new BorderLayout());

		JPanel evolutionControlPanel = new JPanel();
		evolutionControlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

		// cat spinner
		JPanel evolutionLeftAdjustPanel = new JPanel();
		evolutionLeftAdjustPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		evolutionLeftAdjustPanel.add(new JLabel("Number of cats: "));
		mCatSpinner = new JSpinner();
		mCatSpinner.setPreferredSize(new JButton("   ").getPreferredSize());
		mCatSpinner.setModel(new SpinnerNumberModel(CatsAndMiceController.DEFAULT_CAT_NUM, 1, 100, 1));
		mCatSpinner.setEditor(new JSpinner.DefaultEditor(mCatSpinner));
		evolutionLeftAdjustPanel.add(mCatSpinner);
		evolutionControlPanel.add(evolutionLeftAdjustPanel);

		// buttons
		JPanel evolutionButtonPanel = new JPanel();
		evolutionButtonPanel.setLayout(new FlowLayout());
		mMouseNewButton = new JButton("New");
		evolutionButtonPanel.add(mMouseNewButton);
		mMouseNextButton = new JButton("Next");
		evolutionButtonPanel.add(mMouseNextButton);
		mMouseAutoButton = new JButton("Auto step");
		evolutionButtonPanel.add(mMouseAutoButton);
		mMouseFreeRunButton = new JButton("Free run");
		evolutionButtonPanel.add(mMouseFreeRunButton);

		evolutionControlPanel.add(evolutionButtonPanel);

		// mouse spinner
		JPanel evolutionRightAdjustPanel = new JPanel();
		evolutionRightAdjustPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		evolutionRightAdjustPanel.add(new JLabel("Number of mice: "));
		mMouseSpinner = new JSpinner();
		mMouseSpinner.setPreferredSize(new JButton("   ").getPreferredSize());
		mMouseSpinner.setModel(new SpinnerNumberModel(12, 1, 100, 1));
		mMouseSpinner.setEditor(new JSpinner.DefaultEditor(mMouseSpinner));
		evolutionRightAdjustPanel.add(mMouseSpinner);
		evolutionControlPanel.add(evolutionRightAdjustPanel);

		catsAndMiceTab.add(evolutionControlPanel, BorderLayout.PAGE_START);

		JPanel evolutionOutputPanel = new JPanel();
		evolutionOutputPanel.setLayout(new FlowLayout());

		// text areas
		mCatTextArea = new JTextArea();
		JScrollPane catScrollPane = new JScrollPane(mCatTextArea);
		mCatTextArea.setEditable(false);
		catScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		catScrollPane.setPreferredSize(new Dimension(350, 400));
		evolutionOutputPanel.add(catScrollPane);
		mMouseTextArea = new JTextArea();
		JScrollPane mouseScrollPane = new JScrollPane(mMouseTextArea);
		mMouseTextArea.setEditable(false);
		mouseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mouseScrollPane.setPreferredSize(new Dimension(300, 400));
		evolutionOutputPanel.add(mouseScrollPane);

		catsAndMiceTab.add(evolutionOutputPanel, BorderLayout.CENTER);
		tabs.addTab("Cats and Mice", catsAndMiceTab);

		// Max finder ---------------------------------------------------------
		JPanel maxFinderPanel = new JPanel();
		maxFinderPanel.setLayout(new BorderLayout());

		// text area
		mMaxFinderTextArea = new JTextArea();
		mMaxFinderTextArea.setEditable(false);
		DefaultCaret caret = (DefaultCaret) mMaxFinderTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane maxFieldScrollPane = new JScrollPane(mMaxFinderTextArea);
		maxFieldScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		maxFinderPanel.add(maxFieldScrollPane, BorderLayout.CENTER);

		// buttons
		JPanel maxButtonPanel = new JPanel();
		maxButtonPanel.setLayout(new FlowLayout());
		mMaxNewButton = new JButton("New");
		maxButtonPanel.add(mMaxNewButton);
		mMaxNextButton = new JButton("Next");
		maxButtonPanel.add(mMaxNextButton);
		mMaxAutoButton = new JButton("Auto step");
		maxButtonPanel.add(mMaxAutoButton);
		mMaxFreeRunButton = new JButton("Free run");
		maxButtonPanel.add(mMaxFreeRunButton);

		maxFinderPanel.add(maxButtonPanel, BorderLayout.PAGE_START);
		tabs.addTab("Max Finder", maxFinderPanel);

		// Factorizer
		// -----------------------------------------------------------
		JPanel factorizerPanel = new JPanel();
		factorizerPanel.setLayout(new BorderLayout());

		// text ares
		mFactorizerTextArea = new JTextArea();
		mFactorizerTextArea.setEditable(false);
		DefaultCaret fcaret = (DefaultCaret) mFactorizerTextArea.getCaret();
		fcaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane factorizerFieldScrollPane = new JScrollPane(mFactorizerTextArea);
		factorizerFieldScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		factorizerPanel.add(factorizerFieldScrollPane, BorderLayout.CENTER);
		JPanel factorizerButtonPanel = new JPanel();
		factorizerButtonPanel.setLayout(new FlowLayout());

		// buttons
		mFactorizerNewButton = new JButton("New");
		factorizerButtonPanel.add(mFactorizerNewButton);
		mFactorizerNextButton = new JButton("Next");
		factorizerButtonPanel.add(mFactorizerNextButton);
		mFactorizerAutoButton = new JButton("Auto step");
		factorizerButtonPanel.add(mFactorizerAutoButton);
		mFactorizerFreeRunButton = new JButton("Free run");
		mFactorizerFreeRunButton.setEnabled(false);
		factorizerButtonPanel.add(mFactorizerFreeRunButton);

		factorizerPanel.add(factorizerButtonPanel, BorderLayout.PAGE_START);
		tabs.addTab("Factorizer", factorizerPanel);

		// Help ----------------------------------------------------------------
		JPanel helpPanel = new JPanel();
		helpPanel.setLayout(new BorderLayout());
		JTextArea helpTextArea = new JTextArea();
		helpTextArea.setEditable(false);
		helpTextArea.setLineWrap(true);
		helpTextArea.setWrapStyleWord(true);
		helpTextArea.setText(getHelpText());
		helpPanel.add(helpTextArea, BorderLayout.CENTER);
		tabs.addTab("Help", helpPanel);

		mFrame.add(tabs);
		mFrame.pack();
		mFrame.setLocationRelativeTo(null);
		
		// clear text areas
		clearText(CatsAndMiceController.TEXTFIELD_CATS);
		clearText(CatsAndMiceController.TEXTFIELD_MICE);
		clearText(CatsAndMiceController.TEXTFIELD_MAX_FINDER);
		clearText(CatsAndMiceController.TEXTFIELD_FACTORIZER);
		
		mFrame.setVisible(true);
	}

	/**
	 * Initializes the actions, view listeners, etc.
	 */
	private void initActions() {
		mFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				mFrame.dispose();
				CatsAndMiceGUI.this.mController.exit();
			}
		});

		// Cats and Mice
		// --------------------------------------------------------
		mCatSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mMouseNewButton.doClick();
			}
		});
		mMouseSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mMouseNewButton.doClick();
			}
		});
		mMouseNextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mController.step(CatsAndMiceController.RUNNABLE_CATS_AND_MICE);
			}
		});
		mMouseAutoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (mController.isAutoStepInProgress(CatsAndMiceController.RUNNABLE_CATS_AND_MICE)) {
					mMouseAutoButton.setText("Auto step");
					mMouseNewButton.setEnabled(true);
					mCatSpinner.setEnabled(true);
					mMouseSpinner.setEnabled(true);
					mMouseNextButton.setEnabled(true);
					mMouseFreeRunButton.setEnabled(true);
				} else {
					mMouseNewButton.setEnabled(false);
					mCatSpinner.setEnabled(false);
					mMouseSpinner.setEnabled(false);
					mMouseNextButton.setEnabled(false);
					mMouseFreeRunButton.setEnabled(false);
					mMouseAutoButton.setText("Stop");
				}
				mController.changeAutoStepSetting(CatsAndMiceController.RUNNABLE_CATS_AND_MICE);
			}
		});
		mMouseFreeRunButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (mController.isFreeRunInProgress(CatsAndMiceController.RUNNABLE_CATS_AND_MICE)) {
					mMouseFreeRunButton.setText("Free run");
					mMouseNewButton.setEnabled(true);
					mCatSpinner.setEnabled(true);
					mMouseSpinner.setEnabled(true);
					mMouseNextButton.setEnabled(true);
					mMouseAutoButton.setEnabled(true);
				} else {
					mMouseNewButton.setEnabled(false);
					mCatSpinner.setEnabled(false);
					mMouseSpinner.setEnabled(false);
					mMouseNextButton.setEnabled(false);
					mMouseAutoButton.setEnabled(false);
					mMouseFreeRunButton.setText("Stop");
				}
				mController.changeInstantResultSetting(CatsAndMiceController.RUNNABLE_CATS_AND_MICE);
			}
		});
		mMouseNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearText(CatsAndMiceController.TEXTFIELD_CATS);
				clearText(CatsAndMiceController.TEXTFIELD_MICE);
				mController.reset(CatsAndMiceController.RUNNABLE_CATS_AND_MICE, (Integer) mCatSpinner.getValue(),
						(Integer) mMouseSpinner.getValue());
				mMouseAutoButton.setEnabled(true);
				mMouseNewButton.setEnabled(true);
				mCatSpinner.setEnabled(true);
				mMouseSpinner.setEnabled(true);
				mMouseNextButton.setEnabled(true);
				mMouseFreeRunButton.setEnabled(true);
			}
		});

		// Max finder ---------------------------------------------------------
		mMaxNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearText(CatsAndMiceController.TEXTFIELD_MAX_FINDER);
				mController.reset(CatsAndMiceController.RUNNABLE_MAX_FINDER, 0, 0);
				mMaxAutoButton.setEnabled(true);
				mMaxNewButton.setEnabled(true);
				mMaxNextButton.setEnabled(true);
				mMaxFreeRunButton.setEnabled(true);
			}
		});

		mMaxNextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mController.step(CatsAndMiceController.RUNNABLE_MAX_FINDER);
			}
		});

		mMaxAutoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (mController.isAutoStepInProgress(CatsAndMiceController.RUNNABLE_MAX_FINDER)) {
					mMaxAutoButton.setText("Auto step");
					mMaxNewButton.setEnabled(true);
					mMaxNextButton.setEnabled(true);
					mMaxFreeRunButton.setEnabled(true);
				} else {
					mMaxNewButton.setEnabled(false);
					mMaxNextButton.setEnabled(false);
					mMaxFreeRunButton.setEnabled(false);
					mMaxAutoButton.setText("Stop");
				}
				mController.changeAutoStepSetting(CatsAndMiceController.RUNNABLE_MAX_FINDER);
			}
		});

		mMaxFreeRunButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (mController.isFreeRunInProgress(CatsAndMiceController.RUNNABLE_MAX_FINDER)) {
					mMaxFreeRunButton.setText("Free run");
					mMaxNewButton.setEnabled(true);
					mMaxNextButton.setEnabled(true);
					mMaxAutoButton.setEnabled(true);
				} else {
					mMaxNewButton.setEnabled(false);
					mMaxNextButton.setEnabled(false);
					mMaxAutoButton.setEnabled(false);
					mMaxFreeRunButton.setText("Stop");
				}
				mController.changeInstantResultSetting(CatsAndMiceController.RUNNABLE_MAX_FINDER);
			}
		});

		// Factorizer ----------------------------------------------------------
		mFactorizerNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearText(CatsAndMiceController.TEXTFIELD_FACTORIZER);
				mController.reset(CatsAndMiceController.RUNNABLE_FACTORIZER, (Integer) mCatSpinner.getValue(),
						(Integer) mMouseSpinner.getValue());
				mFactorizerAutoButton.setEnabled(true);
				mFactorizerNewButton.setEnabled(true);
				mFactorizerNextButton.setEnabled(true);
				//mFactorizerFreeRunButton.setEnabled(true);
			}
		});

		mFactorizerNextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mController.step(CatsAndMiceController.RUNNABLE_FACTORIZER);
			}
		});

		mFactorizerAutoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (mController.isAutoStepInProgress(CatsAndMiceController.RUNNABLE_FACTORIZER)) {
					mFactorizerAutoButton.setText("Auto step");
					mFactorizerNewButton.setEnabled(true);
					mFactorizerNextButton.setEnabled(true);
					// factorizerInstantButton.setEnabled(true);
				} else {
					mFactorizerNewButton.setEnabled(false);
					mFactorizerNextButton.setEnabled(false);
					// factorizerInstantButton.setEnabled(false);
					mFactorizerAutoButton.setText("Stop");
				}
				mController.changeAutoStepSetting(CatsAndMiceController.RUNNABLE_FACTORIZER);
			}
		});

		mFactorizerFreeRunButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (mController.isFreeRunInProgress(CatsAndMiceController.RUNNABLE_FACTORIZER)) {
					// factorizerInstantButton.setText("Free run");
					mFactorizerNewButton.setEnabled(true);
					mFactorizerNextButton.setEnabled(true);
					mFactorizerAutoButton.setEnabled(true);
				} else {
					mFactorizerNewButton.setEnabled(false);
					mFactorizerNextButton.setEnabled(false);
					mFactorizerAutoButton.setEnabled(false);
					// factorizerInstantButton.setText("Stop");
				}
				mController.changeInstantResultSetting(CatsAndMiceController.RUNNABLE_FACTORIZER);
			}
		});
	}

	private String getHelpText() {
		StringBuilder help = new StringBuilder();
		help.append("Cats and Mice 1.0.0\n\n");
		help.append("http:eve-hci001.atw.hu\n\n");
		help.append("The program contains 3 sub-programs.\n\n");
		help.append(">> Cats and Mice\n");
		help.append(
				"There is a war between cats and mice. In this program, there are cats with constant 10 speed, and mice with 5 initial speed. The cats are faster, however, they don't evolve. They stay the same at every cycle. On the other hand, mice DO evolve and have children. The speed of the children is calculated with evolutionary algorithm, and can be negative (wow, suicider mice). At every cycle, each cat attempts to consume 2 random mice. If the cat is faster or their speed equals, it can eat the mouse. The cats win, if they eat every mice; the mice win if they enormous(e)ly outnumber the cats.\n\n");
		help.append(">> MaxFinder\n");
		help.append(
				"It has 12 individuals in the population. At every cycle, the population doubles and the best half is kept. The textArea only shows the best individual (which has the largest value). The game ends when the maximum reached.\n\n");
		help.append(">> Factorizer\n");
		help.append(
				"The program attepts to factorize a constant number. Because its desire to run the algorithm forever, 'Auto step' option is disabled here.");
		return help.toString();
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
		switch (target) {
		case CatsAndMiceController.TEXTFIELD_CATS:
			mCatTextArea.setText(mCatTextArea.getText() + text);
			break;
		case CatsAndMiceController.TEXTFIELD_MICE:
			mMouseTextArea.setText(mMouseTextArea.getText() + text);
			break;
		case CatsAndMiceController.TEXTFIELD_MAX_FINDER:
			mMaxFinderTextArea.setText(mMaxFinderTextArea.getText() + text);
			break;
		case CatsAndMiceController.TEXTFIELD_FACTORIZER:
			mFactorizerTextArea.setText(mFactorizerTextArea.getText() + text);
			break;
		}
	}

	/**
	 * Clears the data of the GUI's corresponding textfield.
	 * 
	 * @param target
	 *            Type of target textfield
	 */
	public void clearText(String target) {
		switch (target) {
		case CatsAndMiceController.TEXTFIELD_CATS:
			mCatTextArea.setText("");
			break;
		case CatsAndMiceController.TEXTFIELD_MICE:
			mMouseTextArea.setText("");
			break;
		case CatsAndMiceController.TEXTFIELD_MAX_FINDER:
			mMaxFinderTextArea.setText("Best genome\t\t\tSpeed\tEquation\n\n");
			break;
		case CatsAndMiceController.TEXTFIELD_FACTORIZER:
			mFactorizerTextArea.setText("Best genome\t\t\tProduct (aim="
					+ FactorizerPopulationRunnable.DEFAULT_PRODUCT_TARGET + ")\tEquation\n\n");
			break;
		}
	}

	/**
	 * Indicates that the instant result progress has ended
	 * 
	 * @param type
	 *            Type of runner.
	 * 
	 * @return
	 */
	public void onInstantResultProgressEnd(String type) {
		switch (type) {
		case CatsAndMiceController.RUNNABLE_CATS_AND_MICE:
			mMouseAutoButton.setEnabled(false);
			mMouseNewButton.setEnabled(true);
			mCatSpinner.setEnabled(true);
			mMouseSpinner.setEnabled(true);
			mMouseNextButton.setEnabled(false);
			mMouseFreeRunButton.setText("Free run");
			mMouseFreeRunButton.setEnabled(false);
			break;
		case CatsAndMiceController.RUNNABLE_MAX_FINDER:
			mMaxAutoButton.setEnabled(false);
			mMaxNewButton.setEnabled(true);
			mMaxNextButton.setEnabled(false);
			mMaxFreeRunButton.setText("Free run");
			mMaxFreeRunButton.setEnabled(false);
			break;
		case CatsAndMiceController.RUNNABLE_FACTORIZER:
			mFactorizerAutoButton.setEnabled(false);
			mFactorizerNewButton.setEnabled(true);
			mFactorizerNextButton.setEnabled(false);
			mFactorizerFreeRunButton.setText("Free run");
			//mFactorizerFreeRunButton.setEnabled(false);
			break;
		}
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
		switch (type) {
		case CatsAndMiceController.RUNNABLE_CATS_AND_MICE:
			mMouseAutoButton.setText("Auto step");
			mMouseAutoButton.setEnabled(false);
			mMouseNewButton.setEnabled(true);
			mCatSpinner.setEnabled(true);
			mMouseSpinner.setEnabled(true);
			mMouseNextButton.setEnabled(false);
			mMouseFreeRunButton.setEnabled(false);
			break;
		case CatsAndMiceController.RUNNABLE_MAX_FINDER:
			mMaxAutoButton.setText("Auto step");
			mMaxAutoButton.setEnabled(false);
			mMaxNewButton.setEnabled(true);
			mMaxNextButton.setEnabled(false);
			mMaxFreeRunButton.setEnabled(false);
			break;
		case CatsAndMiceController.RUNNABLE_FACTORIZER:
			mFactorizerAutoButton.setText("Auto step");
			mFactorizerAutoButton.setEnabled(false);
			mFactorizerNewButton.setEnabled(true);
			mFactorizerNextButton.setEnabled(false);
			//mFactorizerFreeRunButton.setEnabled(false);
			break;
		}
	}

}

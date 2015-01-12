package hu.atw.eve_hci001.catsandmice.view;

import hu.atw.eve_hci001.catsandmice.control.CatsAndMiceController;

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
import javax.swing.text.DefaultCaret;

/**
 * GUI for the Cats and Mice program.
 * 
 * @author László Ádám
 *
 */
public class CatsAndMiceGUI {
	@SuppressWarnings("unused")
	private CatsAndMiceController catsAndMiceController;
	private JTextArea maxFinderTextArea;
	private JTextArea catTextArea;
	private JTextArea mouseTextArea;

	/**
	 * Constructor.
	 * 
	 * @param catsAndMiceController
	 *            The controller object.
	 */
	public CatsAndMiceGUI(CatsAndMiceController catsAndMiceController) {
		this.catsAndMiceController = catsAndMiceController;
		/* frame */
		JFrame frame = new JFrame("Cats and Mice 0.1");
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				frame.dispose();
				catsAndMiceController.exit();
			}
		});

		JTabbedPane tabs = new JTabbedPane();

		/*
		 * CaM panel
		 * ************************************************************
		 * ******************************
		 */
		JPanel evolutionTab = new JPanel();
		evolutionTab.setLayout(new BorderLayout());

		JPanel evolutionControlPanel = new JPanel();
		evolutionControlPanel
				.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

		JPanel evolutionLeftAdjustPanel = new JPanel();
		evolutionLeftAdjustPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		evolutionLeftAdjustPanel.add(new JLabel("Number of cats: "));
		JSpinner catSpinner = new JSpinner();
		catSpinner.setPreferredSize(new JButton("   ").getPreferredSize());
		catSpinner.setModel(new SpinnerNumberModel(3, 1, 100, 1));
		catSpinner.setEditor(new JSpinner.DefaultEditor(catSpinner));

		evolutionLeftAdjustPanel.add(catSpinner);
		evolutionControlPanel.add(evolutionLeftAdjustPanel);

		JPanel evolutionButtonPanel = new JPanel();
		evolutionButtonPanel.setLayout(new FlowLayout());
		JButton mouseNewButton = new JButton("New");

		evolutionButtonPanel.add(mouseNewButton);
		JButton mouseNextButton = new JButton("Next");
		mouseNextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				catTextArea.setText("");
				mouseTextArea.setText("");
				catsAndMiceController.mouseNextStep();
			}
		});
		evolutionButtonPanel.add(mouseNextButton);
		JButton mouseAutoButton = new JButton("Auto step");
		mouseAutoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JButton) e.getSource()).getText().startsWith("A")) {
					mouseNewButton.setEnabled(false);
					((JButton) e.getSource()).setText("Stop");
				} else {
					((JButton) e.getSource()).setText("Auto step");
					mouseNewButton.setEnabled(true);
				}
				catsAndMiceController.mouseChangeAutoStep();
			}
		});
		evolutionButtonPanel.add(mouseAutoButton);
		evolutionControlPanel.add(evolutionButtonPanel);

		JPanel evolutionRightAdjustPanel = new JPanel();
		evolutionRightAdjustPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		evolutionRightAdjustPanel.add(new JLabel("Number of mice: "));
		JSpinner mouseSpinner = new JSpinner();
		mouseSpinner.setPreferredSize(new JButton("   ").getPreferredSize());
		mouseSpinner.setModel(new SpinnerNumberModel(12, 1, 100, 1));
		mouseSpinner.setEditor(new JSpinner.DefaultEditor(mouseSpinner));
		evolutionRightAdjustPanel.add(mouseSpinner);
		evolutionControlPanel.add(evolutionRightAdjustPanel);

		mouseNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				catTextArea.setText("");
				mouseTextArea.setText("");
				catsAndMiceController.resetMousePopulationHandler(
						(Integer) catSpinner.getValue(),
						(Integer) mouseSpinner.getValue());
			}
		});

		evolutionTab.add(evolutionControlPanel, BorderLayout.PAGE_START);

		JPanel evolutionOutputPanel = new JPanel();
		evolutionOutputPanel.setLayout(new FlowLayout());

		catTextArea = new JTextArea();
		JScrollPane catScrollPane = new JScrollPane(catTextArea);
		catTextArea.setEditable(false);
		catScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		catScrollPane.setPreferredSize(new Dimension(300, 400));
		evolutionOutputPanel.add(catScrollPane);
		mouseTextArea = new JTextArea();
		JScrollPane mouseScrollPane = new JScrollPane(mouseTextArea);
		mouseTextArea.setEditable(false);
		mouseScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mouseScrollPane.setPreferredSize(new Dimension(300, 400));
		evolutionOutputPanel.add(mouseScrollPane);

		evolutionTab.add(evolutionOutputPanel, BorderLayout.CENTER);

		tabs.addTab("Cats and Mice", evolutionTab);

		/*
		 * MaxFinder tab
		 * ********************************************************
		 * ****************************************
		 */
		JPanel maxPanel = new JPanel();
		maxPanel.setLayout(new BorderLayout());

		maxFinderTextArea = new JTextArea("Genotype\t\t\tSpeed\tEquation\n\n");
		maxFinderTextArea.setEditable(false);
		DefaultCaret caret = (DefaultCaret) maxFinderTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane maxFieldScrollPane = new JScrollPane(maxFinderTextArea);
		maxFieldScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		maxPanel.add(maxFieldScrollPane, BorderLayout.CENTER);

		JPanel maxButtonPanel = new JPanel();
		maxButtonPanel.setLayout(new FlowLayout());
		JButton maxNewButton = new JButton("New");
		maxNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				maxFinderTextArea.setText("Genotype\t\t\tSpeed\tEquation\n\n");
				catsAndMiceController.resetMaxPopulationHandler();

			}
		});
		maxButtonPanel.add(maxNewButton);
		JButton maxNextButton = new JButton("Next");
		maxNextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				catsAndMiceController.maxFinderNextStep();
			}
		});
		maxButtonPanel.add(maxNextButton);
		JButton maxAutoButton = new JButton("Auto step");
		maxAutoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JButton) e.getSource()).getText().startsWith("A")) {
					maxNewButton.setEnabled(false);
					((JButton) e.getSource()).setText("Stop");
				} else {
					((JButton) e.getSource()).setText("Auto step");
					maxNewButton.setEnabled(true);
				}
				catsAndMiceController.maxChangeAutoStep();
			}
		});
		maxButtonPanel.add(maxAutoButton);
		maxPanel.add(maxButtonPanel, BorderLayout.PAGE_START);
		tabs.addTab("Max Finder", maxPanel);
		/*
		 * Help
		 * *****************************************************************
		 * *********
		 */
		String helpText = "Cats and Mice 0.1\n\n";
		helpText += "http:eve-hci001.atw.hu\n\n";
		helpText += "The program contains 2 sub-programs.\n";
		helpText += ">> Cats and Mice\n";
		helpText += "There is a war between cats and mice. In this program, there are cats with 10 speed, and mice with 5 initial speed. The cats are faster, however they don't evolve. They stay the same at every cycle. In the other hand, mice DO evolve and have children. The speed of the children is calculated with genetic algorithm, and can be negative (wow, suicider mice). At every cycle, each cat attempts to consume 2 random mice. If the cat is faster or their speed equals, it can eat the mouse. The cats win, if they eat every mice; the mice win if they enormously outnumber the cats.\n\n";
		helpText += ">> MaxFinder\n";
		helpText += "It has 12 individuals in the population. At every cycle, the population doubles and the best half is kept. The textArea only shows the best individual (which has the largest value). The game ends when the maximum reached.";

		JPanel helpPanel = new JPanel();
		helpPanel.setLayout(new BorderLayout());
		JTextArea helpTextArea = new JTextArea();
		helpTextArea.setEditable(false);
		helpTextArea.setLineWrap(true);
		helpTextArea.setWrapStyleWord(true);
		helpTextArea.setText(helpText);
		helpPanel.add(helpTextArea, BorderLayout.CENTER);
		tabs.addTab("Help", helpPanel);

		frame.add(tabs);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Prints information into the max finder textarea.
	 * 
	 * @param text
	 *            The string to be printed.
	 */
	public void printMaxFinderDetails(String text) {
		maxFinderTextArea.setText(maxFinderTextArea.getText() + text);
	}

	/**
	 * Prints information to the cat textarea.
	 * 
	 * @param text
	 *            The string to be printed.
	 */
	public void printCatDetails(String text) {
		catTextArea.setText(catTextArea.getText() + text);
	}

	/**
	 * Prints information to the mouse textarea.
	 * 
	 * @param text
	 *            The string to be printed.
	 */
	public void printMouseDetails(String text) {
		mouseTextArea.setText(mouseTextArea.getText() + text);
	}

	/**
	 * Clears the mouse textarea.
	 */
	public void clearMouseDetails() {
		mouseTextArea.setText("");
	}

	/**
	 * Clears the cat textarea.
	 */
	public void clearCatDetails() {
		catTextArea.setText("");
	}

}

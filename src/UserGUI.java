import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 	Ian Moreno
 * @date		11/13/2017
 * @purpose 	The UserGUI Class builds and displays the user interface for the Royalty Parser application.
 */ 

/**
 * @purpose	The UserGUI class extends JFrame and is the main container/window of the GUI.
 *			Defines, builds, and lays out each of the GUI components. 				
 */
public class UserGUI extends JFrame {
	// Global class variables that need to be shared among individual components

	// initialize and JFileChooser object to allow file selection as input
	private final JFileChooser FILE_CHOOSER = new JFileChooser();
	// provides a mapping between the Author's name and corresponding ASIN/book IDs
	private Map<String, ArrayList> authorToASINMap = new HashMap<String, ArrayList>();
	// running list of authors contained in the authors list panel
	private ArrayList<Author> authorsList = new ArrayList<>();
	// provides a mapping between the String abbreviations of a currency and its 
	// corresponding JTextField
	private Map<String, JTextField> currencyTextFields = new HashMap<String, JTextField>();
	// use HashSet to maintain a list of unique files; no duplicates.
	private HashSet<File> savedReportFiles = new HashSet<>();
	// Array of File objects taken in as multiple selection input 
	// (user chose more than one file in browse window)
	private File[] inputReportFiles = new File[100]; 
	// GUI buttons for each panel/component
	private JButton browseButton, 
			addReportButton, 
			removeReportButton, 
			removeAllReportsButton,
			saveCurrencyConversionButton,
			clearCurrencyConversionButton,
			addAuthorButton, 
			removeAuthorButton, 
			addASINButton, 
			removeASINButton,
			removeAllASINsButton,
			createButton,
			cancelButton;
	// GUI panels
	private JPanel reportsPanel,
			currencyPanel,
			authorsPanel,
			ASINsPanel,
			buttonsPanel;
	// GUI labels for each panel/component
	private JLabel findReportLabel,
			CADLabel, GBPLabel, BRLLabel,
			INRLabel, MXNLabel, EURLabel,
			JPYLabel, AUDLabel,
			enterAuthorNameLabel,
			enterASINLabel;

	// GUI JTextFields for each panel/component
	private JTextField findReportField,
			CADField, GBPField, BRLField,
			INRField, MXNField, EURField,
			JPYField, AUDField,
			enterAuthorNameField,
			enterASINField;
	// Lists to hold the current reports, authors, and ASINs to be presented to user
	private JList<String> reportList,
			authorNameList,
			ASINsList;
	// JScrollPanes to hold corresponding lists to be presented to user
	private JScrollPane reportListScrollPane,
			authorNameListScrollPane,
			ASINsListScrollPane,
			buttonsPanelScrollPane;
	// JTextArea to display status messages to user
	private JTextArea logTextBox;

	/**
	 * @purpose Constructor for UserGUI that accepts one argument of ArrayList<Author>
	 * @param 	presavedAuthorsList an ArrayList<Author> containing any previously saved authors
	 * 			to load into the GUI for the user to select or modify
	 */
	public UserGUI(ArrayList<Author> presavedAuthorsList) {
		//Create the window to hold all components of the GUI
		JFrame frame = new JFrame("Royalty Parser");
		frame.setLayout(new GridLayout(0,1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// allow user to select multiple files at once
		FILE_CHOOSER.setMultiSelectionEnabled(true);
		
		// load pre-saved authors
		authorsList = presavedAuthorsList;
		
		// build each component panel
		buildReportsPanel();
		buildCurrencyPanel();
		buildAuthorProfilesPanel();
		buildASINsPanel();
		buildButtonsPanel();
 
		// Add each panel to the JFrame window.
		frame.add(reportsPanel);
		frame.add(currencyPanel);
		frame.add(authorsPanel);
		frame.add(ASINsPanel);
		frame.add(buttonsPanel);
 
		// pack JFrame
		frame.pack();
		// Display the JFrame window to the user.
		frame.setVisible(true);
	}

	/**
	 * @purpose	private helper method builds the reports panel
	 */
	private void buildReportsPanel() {
		// initialize BorderLayout component panels
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel eastPanel = new JPanel(new GridLayout(3,1)); // 3 rows, 1 column
		
		// initialize reportsPanel
		reportsPanel = new JPanel();
		// set border properties
		reportsPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10), 
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Reports")
				));
		// set layout to BorderLayout
		reportsPanel.setLayout(new BorderLayout());

		// create northPanel's fields and buttons
		findReportLabel = new JLabel("Find Reports");
		findReportField = new JTextField(40);
		browseButton = new JButton("Browse");
		browseButton.addActionListener(new BrowseButtonListener());
		addReportButton = new JButton("Add Report");
		addReportButton.addActionListener(new AddReportButtonListener());
		addReportButton.setEnabled(false);

		// add fields and buttons to northPanel
		northPanel.add(findReportLabel);
		northPanel.add(findReportField);
		northPanel.add(browseButton);
		northPanel.add(addReportButton);

		// create centerPanels fields and JScrollPane 
		reportList = new JList<String>(new DefaultListModel<String>());
		reportList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reportList.addListSelectionListener(new ReportListSelectionListener());
		reportList.setVisibleRowCount(5);
		reportListScrollPane = new JScrollPane(reportList);
		
		// add fields and JScrollPane to centerPanel
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(reportListScrollPane, BorderLayout.CENTER);

		// create buttons for eastPanel
		removeReportButton = new JButton("Remove Report");
		removeReportButton.addActionListener(new RemoveReportButtonListener());
		removeReportButton.setEnabled(false);
		removeAllReportsButton = new JButton("Remove All Reports");
		removeAllReportsButton.addActionListener(new RemoveAllReportsButtonListener());
		removeAllReportsButton.setEnabled(false);
		
		// add buttons to eastPanel
		eastPanel.add(removeReportButton);
		eastPanel.add(removeAllReportsButton);
		
		// add northPanel, centerPanel, and eastPanel to the reportsPanel
		reportsPanel.add(northPanel, BorderLayout.NORTH);
		reportsPanel.add(centerPanel, BorderLayout.CENTER);
		reportsPanel.add(eastPanel, BorderLayout.EAST);
	}
	
	/**
	 * @purpose	Private inner class implements the ListSelectionListener for the ReportList
	 * @author  	Ian Moreno
	 */
	private class ReportListSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			// do nothing, method override required for event listener 
		}
	}

	/**
	 * @purpose	Private inner class implements the ActionListener for the BrowsButtonListener 
	 * @author  	Ian Moreno
	 */
	private class BrowseButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			if (e.getSource() == browseButton) {
				// only allow KDP Excel reports to be selected by user
				FILE_CHOOSER.setFileFilter(new ExcelFileFilter());
				int returnVal = FILE_CHOOSER.showOpenDialog(UserGUI.this);
	 
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//Handle opening files.
					
					// save all selected input files in an array
					inputReportFiles = FILE_CHOOSER.getSelectedFiles();
					// update the browse text field with selected file names
					findReportField.setText(buildInputTextString(inputReportFiles));

					// enable to add report button
					addReportButton.setEnabled(true);
					
				} else {
					// output status message to user
					logTextBox.append("Open command cancelled by user.\n");
				}
			}
		}
		
		/**
		 * @purpose Private inner class ExcelFileFilter extends FileFilter and allows
		 * 			overloaded accept() method to only allow user to select files ending in ".xlsx"
		 * @author Ian Moreno 
		 */
		private class ExcelFileFilter extends FileFilter {
			  public String getDescription() {
				  // Present description to user
				  return "Excel Documents (.xlsx)";
			  }

			  public boolean accept(File file) {
				  // allow user to select a directory
				  if (file.isDirectory()) {
					  return true;
				  } else {
					  // user's file can only be selected if it ends in .xlsx
					  String fileName = file.getName().toLowerCase();
					  return fileName.endsWith(".xlsx");
				  }
			  }
		}
		
		/**
		 * @purpose 	Private helper method buildInputTextString takes an array of files and 
		 * 			returns a String to populate the input textBox for user to see 
		 * @param 	files is a File[] containing all of the files selected by user
		 * @return	String representing the text string of the chosen files and their paths
		 */
		private String buildInputTextString(File[] files) {
			// use StringBuilder to allow for easier concatenation
			StringBuilder filesString = new StringBuilder();

			// for each selected file
			for (File file : files) {
				// append file name to the fileString
				filesString.append(file.getName() + " ");
			}

			return filesString.toString();
		}
	}

	/**
	 * @purpose 	private class AddReportButtonlistener implements ActionListener for 'Add Report' button
	 * 			and handles actionPerformed events.
	 * @author 	Ian Moreno
	 */
	private class AddReportButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle 'Add Report' button click event
			
			// for each inputReportFile
			for (File inputReportFile : inputReportFiles) {
				// save report file path String
				String reportPath = inputReportFile.getPath();
				// get report file name
				String reportFileName = inputReportFile.getName();
				// save the file name prefix that all valid KDP report file names should contain
				String prefixKDP = "KDP Prior Month Royalties";
				// grab a reference to the ListModel that maintains the current list of reports shown in reportList
				DefaultListModel reportListModel = (DefaultListModel)reportList.getModel();

				// ensure reportPath is not null or empty and ensure the fileName begins with "KDP Prior Month Royalties"
				// this is done in order to minimize importing of invalid spreadsheets
				if (reportPath != null && !reportPath.equals("") && reportFileName.regionMatches(0, prefixKDP, 0, prefixKDP.length()-1)) {
					// if report is not already present, add its path string to reportList
					if (!reportListModel.contains(reportPath)) {
						// ad the report file path String to the reportList and display to user
						reportListModel.addElement(reportPath);
						// add the report file to the savedReportFiles set
						savedReportFiles.add(inputReportFile);

						// enable 'Remove Report', 'Remove All Reports', and 'Create Reports' buttons
						removeReportButton.setEnabled(true);
						removeAllReportsButton.setEnabled(true);
						createButton.setEnabled(true);

						// report added successfully, output status message to user
						logTextBox.append("Added Report: " + reportPath + "\n");
					} else {
						// report is already contained/present in the reportListModel, output status message to user
						logTextBox.append("ERROR Report Already Added\n");
					}
				} else {
					// there was an error when attempting to add report, output status message to user
					logTextBox.append("ERROR Not a valid KDP Report\n");
				}

				addReportButton.setEnabled(false); // disable the add report button until a new file is selected
				findReportField.setText(""); // clear the findReportField text box
			}
		}
	}

	/**
	 * @purpose 	private class RemoveAllReportsButtonListener implements ActionListener for 'Remove All Reports' button
	 * 			and handles actionPerformed events.
	 * @author 	Ian Moreno
	 */
	private class RemoveAllReportsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle 'Remove All Reports' button click event

			// grab a reference to the ListModel that maintains the current list of reports shown in reportList
			DefaultListModel reportListModel = (DefaultListModel)reportList.getModel();

			// if the reportList contains reports
			if (!reportListModel.isEmpty()) {
				// remove all the reports from the report list
				reportListModel.removeAllElements();
				// remove all the reports from the savedReportFiles set
				savedReportFiles.clear();

				// disable the 'Remove All Reports', 'Remove Report', and 'Create Reports' buttons
				removeAllReportsButton.setEnabled(false);
				removeReportButton.setEnabled(false);
				createButton.setEnabled(false);

				// successfully removed all the reports, output status message to user
				logTextBox.append("Removed All Reports\n");
			} else {
				// report list was empty, output status message to user
				logTextBox.append("Report List Empty\n");
			}
		}
	}

	/**
	 * @purpose 	private class RemoveReportButtonListener implements ActionListener for 'Remove Report' button
	 * 			and handles actionPerformed events.
	 * @author 	Ian Moreno
	 */	
	private class RemoveReportButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle 'Remove Report' button click event
			
			// initialize String variable to hold the report path
			String reportPath = "";
			// if a report is selected by user
			if (!reportList.isSelectionEmpty()) {

				// grab a reference to the ListModel that maintains the current list of reports shown in reportList
				DefaultListModel reportListModel = (DefaultListModel)reportList.getModel();
				// save the index of the selected report
				int selectedReportIndex = reportList.getSelectedIndex();
				// ensure the report list model contains reports
				if (!reportListModel.isEmpty()) {
					// save the report path String of the selected report
					reportPath = reportListModel.getElementAt(selectedReportIndex).toString();
					// remove the selected report from the savedReportFiles set
					savedReportFiles.remove(reportListModel.getElementAt(selectedReportIndex));
					// remove the selected report from the report list model, and from the report list JScrollPane
					reportListModel.removeElementAt(selectedReportIndex);

					// if after the selected report is removed the report list is now empty,
					// then disable the 'Remove Report', 'Remove All Reports', and 'Create Reports' buttons
					if (reportListModel.isEmpty()) {
						removeReportButton.setEnabled(false);
						removeAllReportsButton.setEnabled(false);
						createButton.setEnabled(false);
					}

					// removed report successfully, output status to user
					logTextBox.append("Removed Report: " + reportPath + "\n");
				}
			} else {
				// user did not select a report, output message to user
				logTextBox.append("No Report Selected\n");
			}
		}
	} // END REPORT PANEL
	

	/**
	 * @purpose	Private helper method builds the currency conversion panel.
	 */
	private void buildCurrencyPanel() {
		// initialize conversionMap with default values
		CurrencyConverter.resetConversionMap();

		currencyPanel = new JPanel();
		currencyPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10), 
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Currency Conversions")
				));
		currencyPanel.setLayout(new GridLayout(3,4));

		// initialize currency labels
		CADLabel = new JLabel("Candian Dollar (CAD)");
		GBPLabel = new JLabel("British Pound (GBP)");
		BRLLabel = new JLabel("Brazilian Real (BRL)");
		INRLabel = new JLabel("Indian Rupee (INR)");
		MXNLabel = new JLabel("Mexican Peso (MXN)");
		EURLabel = new JLabel("Euro (EUR)");
		JPYLabel = new JLabel("Japanese Yen (JPY)");
		AUDLabel = new JLabel("Australian Dollar (AUD)");
		// create corresponding currency text fields and their event listeners
		CADField = new JTextField(3);
		addCurrencyButtonListeners(CADField);
		currencyTextFields.put("CAD", CADField);
		GBPField = new JTextField(3);
		addCurrencyButtonListeners(GBPField);
		currencyTextFields.put("GBP", GBPField);
		BRLField = new JTextField(3);
		addCurrencyButtonListeners(BRLField);
		currencyTextFields.put("BRL", BRLField);
		INRField = new JTextField(3);
		addCurrencyButtonListeners(INRField);
		currencyTextFields.put("INR", INRField);
		MXNField = new JTextField(3);
		addCurrencyButtonListeners(MXNField);
		currencyTextFields.put("MXN", MXNField);
		EURField = new JTextField(3);
		addCurrencyButtonListeners(EURField);
		currencyTextFields.put("EUR", EURField);
		JPYField = new JTextField(3);
		addCurrencyButtonListeners(JPYField);
		currencyTextFields.put("JPY", JPYField);
		AUDField = new JTextField(3);
		addCurrencyButtonListeners(AUDField);
		currencyTextFields.put("AUD", AUDField);

		// save and clear buttons
		saveCurrencyConversionButton = new JButton("Save Conversions");
		saveCurrencyConversionButton.addActionListener(new SaveCurrencyConversionButtonListener());
		saveCurrencyConversionButton.setEnabled(false);
		clearCurrencyConversionButton = new JButton("Clear All");
		clearCurrencyConversionButton.addActionListener(new ClearCurrencyConversionButtonListener());
		clearCurrencyConversionButton.setEnabled(false);
		
		// create individual panels for each currency type
		JPanel cadPanel = buildCurrencyPanel(CADLabel, CADField);
		JPanel gbpPanel = buildCurrencyPanel(GBPLabel, GBPField);
		JPanel brlPanel = buildCurrencyPanel(BRLLabel, BRLField);
		JPanel incPanel = buildCurrencyPanel(INRLabel, INRField);
		JPanel mxnPanel = buildCurrencyPanel(MXNLabel, MXNField);
		JPanel eurPanel = buildCurrencyPanel(EURLabel, EURField);
		JPanel jpyPanel = buildCurrencyPanel(JPYLabel, JPYField);
		JPanel audPanel = buildCurrencyPanel(AUDLabel, AUDField);
		JPanel saveCurrencyButtonPanel = new JPanel();
		saveCurrencyButtonPanel.setSize(new Dimension(10, 5));
		saveCurrencyButtonPanel.add(saveCurrencyConversionButton);
		JPanel clearCurrencyButtonPanel = new JPanel();
		clearCurrencyButtonPanel.setSize(new Dimension(10, 5));
		clearCurrencyButtonPanel.add(clearCurrencyConversionButton);

		// add individual component currency panels and buttons to the main currencyPanel
		currencyPanel.add(cadPanel);
		currencyPanel.add(gbpPanel);
		currencyPanel.add(brlPanel);
		currencyPanel.add(incPanel);
		currencyPanel.add(mxnPanel);
		currencyPanel.add(eurPanel);
		currencyPanel.add(jpyPanel);
		currencyPanel.add(audPanel);
		currencyPanel.add(clearCurrencyButtonPanel);
		currencyPanel.add(new JPanel()); // empty cell
		currencyPanel.add(new JPanel()); // empty cell
		currencyPanel.add(saveCurrencyButtonPanel);
	}

	/**
	 * @purpose private helper method addCurrencyButtonListeners adds a DocumentListener
	 * 			event listener to the text field
	 * @param field JTextField component object to add event listener to.
	 */
	private void addCurrencyButtonListeners(JTextField field) {
		field.getDocument().addDocumentListener(new DocumentListener() {
			// override DocumentListener methods
			public void changedUpdate(DocumentEvent e) {
				// when JTextField object registers a change
				enableCurrencyPanelButtons();
			}
			public void removeUpdate(DocumentEvent e) {
				// do nothing
			}
			public void insertUpdate(DocumentEvent e) {
				// when JTextField object registers characters typed into field.
				enableCurrencyPanelButtons();
			}
			// helper method that enables the save and clear buttons on the currency conversion panel
			public void enableCurrencyPanelButtons() {
				saveCurrencyConversionButton.setEnabled(true);
				clearCurrencyConversionButton.setEnabled(true);
			}
		});
	}

	/**
	 * @puropse 	private helper method buildCurrencyPanel constructs a JTextField component panel
	 * @param label JLabel for currency conversion object
	 * @param field JTextField for currency conversion object
	 * @return JPanel panel containing the label and field the currency conversion component
	 */
	private JPanel buildCurrencyPanel(JLabel label, JTextField field) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(label);
		panel.add(field);
		return panel;
	}
	
	/**
	 * @purpose	private inner class that implements ActionListener for the clear button on the currency conversion panel
	 * @author Ian Moreno
	 *
	 */
	private class ClearCurrencyConversionButtonListener implements ActionListener {
		@Override
		// handle clear currency button click event
		public void actionPerformed(ActionEvent e) {
			// zero-out the currency conversion map values
			CurrencyConverter.resetConversionMap();
			JTextField field = new JTextField();
			
			// build iterator to cycle through each of the 'values' contained in the 
			// currencyTextFields map. Each 'value' is a JTextField belonging to a currency conversion component
			Iterator<JTextField> currencyFieldsIterator = currencyTextFields.values().iterator();
			while (currencyFieldsIterator.hasNext()) {
				// get next JTextField from iterator
				field = currencyFieldsIterator.next();
				// set the text field to empty
				field.setText("");
			}
			// disable the save and clear buttons on the currency conversion panel
			saveCurrencyConversionButton.setEnabled(false);
			clearCurrencyConversionButton.setEnabled(false);
		}
	} 

	/**
	 * @purpose 	private inner class SaveCurrencyConversionButtonListern implements ActionListener for the save button
	 * @author Ian Moreno
	 *
	 */
	private class SaveCurrencyConversionButtonListener implements ActionListener {
		@Override
		// handle save currency button click event
		public void actionPerformed(ActionEvent e) {
			CurrencyConverter.resetConversionMap(); // clear conversionMap
			String currencyType = "";
			Double conversionFactor = 0.0;
			// create iterator for each currency field component in currencyTextFields map
			Iterator currencyFieldsIterator = currencyTextFields.entrySet().iterator();
			// initialize currencyField variable outside the while loop
			JTextField currencyField = new JTextField();
			
			while (currencyFieldsIterator.hasNext()) {
				// handle next entry in the map
				Map.Entry fieldEntry = (Map.Entry)currencyFieldsIterator.next();
				// assign currencyField to the currency JTextField being iterated over
				currencyField = (JTextField)fieldEntry.getValue();

				// if currencyField isn't null AND it isn't empty
				if (currencyField != null && !currencyField.getText().trim().equals("")) {
					// save the currency type String value
					currencyType = fieldEntry.getKey().toString();
					// save the conversion factor
					conversionFactor = Double.valueOf(currencyField.getText());
					// add key, value pair to conversionMap
					CurrencyConverter.conversionMap.put(currencyType, conversionFactor);
					// output result status to user
					logTextBox.append("Added Currency Type: " + currencyType + ": Conversion Factor: " + conversionFactor + "\n");
				}
			}
		}
	} // END CURRENCY PANEL

 
	/**
	 * @purpose	private helper method builds the author profiles panel
	 */
	private void buildAuthorProfilesPanel() {
		// initialize the component panels
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel eastPanel = new JPanel(new GridLayout(3,1));
		
		// initialize main authors Panel and set the border and layout properties
		authorsPanel = new JPanel();
		authorsPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10), 
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Author Profiles")
				));
		authorsPanel.setLayout(new BorderLayout());

		// create JLabel and JTextField and action listener for the author name field
		enterAuthorNameLabel = new JLabel("Enter New Author");
		enterAuthorNameField = new JTextField(40);
		addAuthorPanelButtonListener(enterAuthorNameField);

		// create the add author button, it's action listener, and set button to enabled
		addAuthorButton = new JButton("Add Author");
		addAuthorButton.addActionListener(new AddAuthorButtonListener());
		addAuthorButton.setEnabled(false);

		// add the author name field and add author button to the north panel
		northPanel.add(enterAuthorNameLabel);
		northPanel.add(enterAuthorNameField);
		northPanel.add(addAuthorButton);

		// create a JList named authorNameList that holds String objects.
		// initialize authorNameList with a DefaultListModel of type String
		authorNameList = new JList<String>(new DefaultListModel<String>());
		// grab a reference to the DefaultListModel being used to control the authorNameList JList.
		DefaultListModel authorNameListModel = (DefaultListModel)authorNameList.getModel();

		// populate authorList with presaved author names
		String authorName = "";
		// initialize ArrayList, outside of loop, that will hold author's corresponding ASINs
		ArrayList<String> authorsASINs = new ArrayList<>();
		for (Author a : authorsList) {
			// save author's name
			authorName = a.getName();
			// grab their list of ASINs
			authorsASINs = (ArrayList)a.getASINList();
			// add the new author's name to the JList that displays the list of author names
			authorNameListModel.addElement(a.getName());
			// add the author's name and list of ASINs to the authorToASINMap
			authorToASINMap.put(authorName, authorsASINs);
		}

		// add an event listener to authorNameList to capture whenever an author's name is selected
		authorNameList.addListSelectionListener(new AuthorNameListSelectionListener());
		// allow user to select only one author at a time
		authorNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// allow only 5 author names at a time to appear visible within the JList 
		authorNameList.setVisibleRowCount(5);
		// add authorNameList JList to a JScrollPane 
		authorNameListScrollPane = new JScrollPane(authorNameList);
		centerPanel.setLayout(new BorderLayout());
		// add the scroll pane to the center panel 
		centerPanel.add(authorNameListScrollPane, BorderLayout.CENTER);

		// create remove author button and it's event listener, and set to disabled initially
		removeAuthorButton = new JButton("Remove Author");
		removeAuthorButton.addActionListener(new RemoveAuthorButtonListener());
		removeAuthorButton.setEnabled(false);

		// add the remove author button to the east panel
		eastPanel.add(removeAuthorButton);
		
		// add north, center, and east component panels to the main author's panel
		authorsPanel.add(northPanel, BorderLayout.NORTH);
		authorsPanel.add(centerPanel, BorderLayout.CENTER);
		authorsPanel.add(eastPanel, BorderLayout.EAST);
	}
	
	/**
	 * @purpose 	Private helper method adds a Document Event Listener for the author entry JTextField
	 * @param field JTextField object where user enters a new author name
	 */
	private void addAuthorPanelButtonListener(JTextField field) {
		field.getDocument().addDocumentListener(new DocumentListener() {
			// handle when user makes a change to the JTextField
			public void changedUpdate(DocumentEvent e) {
				enableAuthorPanelButtons();
			}

			public void removeUpdate(DocumentEvent e) {
				// do nothing
			}

			// handle when user inserts a character into the JTextField
			public void insertUpdate(DocumentEvent e) {
				enableAuthorPanelButtons();
			}

			// enable the add author and remove author buttons
			public void enableAuthorPanelButtons() {
				addAuthorButton.setEnabled(true);
				removeAuthorButton.setEnabled(true);
			}
		});
	}

	/**
	 * @purpose	Private inner class implmenets the List Selection Action Listener for the authorNameList
	 * @author Ian Moreno
	 *
	 */
	private class AuthorNameListSelectionListener implements ListSelectionListener {
		@Override
		// handle list selection event
		public void valueChanged(ListSelectionEvent e) {
			// grab a reference to the list model controlling the authorNameList JList entries
			DefaultListModel authorListModel = (DefaultListModel)authorNameList.getModel();
			// grab a reference to the list model controlling the ASINsList JList entries
			DefaultListModel ASINsListModel = (DefaultListModel)ASINsList.getModel();
			int selectedIndex = authorNameList.getSelectedIndex();
			
			// if a valid author is chosen from the list
			if (selectedIndex >= 0) {
				// grab the selected author's name
				String authorName = authorListModel.getElementAt(authorNameList.getSelectedIndex()).toString();
				// clear out the ASINsList that may be previously displayed for another author
				ASINsListModel.clear();
				// enable the remove author button 
				removeAuthorButton.setEnabled(true);

				// check to see if author has any saved ASINs
				if (authorName != null) { // check to make sure author isn't already previously deleted
					if (authorToASINMap.containsKey(authorName)) {
						// get the list of saved ASINs
						ArrayList<String> asins = authorToASINMap.get(authorName);
						for (String aName : asins) {
							// add each ASIN to the authorListModel
							ASINsListModel.addElement(aName);
						}
						// enable the remove all ASINs button now that there are ASINs to remove
						removeAllASINsButton.setEnabled(true);
					}
				}
			} else {
				// output error message to user
				logTextBox.append("ERROR: No Author Selected\n");
			}
		}
	}
	
	/**
	 * @purpose Private inner class implements ActionListener for the add author button
	 * @author Ian Moreno
	 *
	 */
	private class AddAuthorButtonListener implements ActionListener {
		@Override
		// handle add author button click event
		public void actionPerformed(ActionEvent e) {
			// save author's name from the enterAuthorNameField
			String authorName = enterAuthorNameField.getText();
			// grab a reference to the DefaultListModel handling the contents of authorList 
			DefaultListModel authorListModel = (DefaultListModel)authorNameList.getModel();
			
			// if authorName isn't null AND the authorName isn't empty
			if (authorName != null && !authorName.trim().equals("")) {
				// if author name is not already present, add it to authorNamesList
				if (!authorListModel.contains(authorName)) {
					// add to the authorNameList via the authorListModel controller
					authorListModel.addElement(authorName);
					// add the author name and an empty ArrayList pair to authorToASINMap
					authorToASINMap.put(authorName, new ArrayList<String>());
					// output resulting status to user
					logTextBox.append("Added Author: " + authorName + "\n");
				} else {
					// output error message to user
					logTextBox.append("ERROR Author Name Already Added\n");
				}
				enterAuthorNameField.setText(""); // clear the enterAuthorNameField text box
			} else {
				// output error message to user
				logTextBox.append("Enter an Author Name\n");
			}
			// disable the add author button until user adds content to the enterAuthorNameField
			addAuthorButton.setEnabled(false);
		}
	}

	/**
	 * @purpose 	Private inner class implements ActionListener for the remove author button
	 * @author Ian Moreno
	 *
	 */
	private class RemoveAuthorButtonListener implements ActionListener {
		@Override
		// handle remove author button click event
		public void actionPerformed(ActionEvent e) {
			// if there is a valid author selected in the authorNameList
			if (!authorNameList.isSelectionEmpty()) {
				// grab a reference to the DefaultListModel controlling the authorNameList JList
				DefaultListModel authorListModel = (DefaultListModel)authorNameList.getModel();
				// grab a reference to the DefaultListModel controlling the ASINsList JList
				DefaultListModel ASINsListModel = (DefaultListModel)ASINsList.getModel();
				int selectedAuthorIndex = authorNameList.getSelectedIndex();
				// save selected author's name
				String authorName = authorListModel.getElementAt(selectedAuthorIndex).toString();
				// if the list is not empty and there is an author name selected
				if (!authorListModel.isEmpty() && selectedAuthorIndex >= 0) {
					// remove author and ASIN list from the authorToASINMap
					authorToASINMap.remove(authorName);
					// remove author name from authorNameList
					authorListModel.removeElementAt(selectedAuthorIndex);
					// clear ASINs from the ASINsList
					ASINsListModel.clear(); 
					// log status result to user
					logTextBox.append("Removed Author: " + authorName + "\n");
					// disable the remove author button until another author is selected
					removeAuthorButton.setEnabled(false);
				} else {
					// log error to user
					logTextBox.append("No Author Selected\n");
				}
			} else {
				// log error to user
				logTextBox.append("No Author Selected\n");
			}
		}
	} // END AUTHOR PROFILES


	/**
	 * @purpose	private helper method builds the ASINs panel
	 */
	private void buildASINsPanel() {
		// initialize the north, center, and east panels
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel eastPanel = new JPanel(new GridLayout(3,1)); // 3 rows 1 column
		
		// create the main ASINsPanel, set the border and layout properties
		ASINsPanel = new JPanel();
		ASINsPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10), 
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Author's ASINs")
				));
		ASINsPanel.setLayout(new BorderLayout());

		// create the ASINLabel and corresponding JTextField and add it's event listener
		enterASINLabel = new JLabel("Enter New ASINs");
		enterASINField = new JTextField(40);
		addASINPanelButtonListener(enterASINField);

		// create add ASIN button, add it's event listener, and set disabled initially
		addASINButton = new JButton("Add ASIN");
		addASINButton.addActionListener(new AddASINButtonListener());
		addASINButton.setEnabled(false);

		// add the ASINLabel, ASINField, and ASIN button to the north panel
		northPanel.add(enterASINLabel);
		northPanel.add(enterASINField);
		northPanel.add(addASINButton);

		// create a JList to hold the list of ASINs, initializing with a DefaultListMode of type String
		ASINsList = new JList<String>(new DefaultListModel<String>());
		// allow user to select only one ASIN at a time
		ASINsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// add a list selection event listener to the ASINsList
		ASINsList.addListSelectionListener(new ASINsListSelectionListener());
		// 5 rows visible in window
		ASINsList.setVisibleRowCount(5);
		// add ASINsList to a scroll pane
		ASINsListScrollPane = new JScrollPane(ASINsList);
		// add the scroll pane to the center panel
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(ASINsListScrollPane, BorderLayout.CENTER);

		// create remove ASIN button and remove all ASINs button, and corresponding event listeners
		// set both to disable initially
		removeASINButton = new JButton("Remove ASIN");
		removeASINButton.addActionListener(new RemoveASINButtonListener());
		removeASINButton.setEnabled(false);
		removeAllASINsButton = new JButton("Remove All ASINs");
		removeAllASINsButton.addActionListener(new RemoveAllASINsButtonListener());
		removeAllASINsButton.setEnabled(false);

		// add both buttons to the east panel
		eastPanel.add(removeASINButton);
		eastPanel.add(removeAllASINsButton);
		
		// add the north, center, and east panels to the main ASINsPanel
		ASINsPanel.add(northPanel, BorderLayout.NORTH);
		ASINsPanel.add(centerPanel, BorderLayout.CENTER);
		ASINsPanel.add(eastPanel, BorderLayout.EAST);
	}

	/**
	 * @purpose private helper method adds a DocumentListener event listener to the enterASINField JTextField
	 * @param field JTextField representing the enterASINField to add a listener to
	 */
	private void addASINPanelButtonListener(JTextField field) {
		field.getDocument().addDocumentListener(new DocumentListener() {
			// handle the add ASIN field entry
			public void changedUpdate(DocumentEvent e) {
				enableASINPanelButtons();
			}

			public void removeUpdate(DocumentEvent e) {
				// do nothing
			}

			// handle when a character is entered into the JTextField
			public void insertUpdate(DocumentEvent e) {
				enableASINPanelButtons();
			}

			// enable the addASIN button
			public void enableASINPanelButtons() {
				addASINButton.setEnabled(true);
			}
		});
	}
	
	/**
	 * @purpose 	Private inner class implements List Selection Action Listener for the ASINs List
	 * @author Ian Moreno
	 *
	 */
	private class ASINsListSelectionListener implements ListSelectionListener {
		@Override
		// handle list selection event
		public void valueChanged(ListSelectionEvent e) {
			// set remove ASIN and remove all ASINs buttons both to enabled
			removeASINButton.setEnabled(true);
			removeAllASINsButton.setEnabled(true);
		}
	}
	
	/**
	 * @purpose 	Private inner class implements ActionListener for the add ASIN button
	 */
	private class AddASINButtonListener implements ActionListener {
		@Override
		// handle Add ASIN button click event
		public void actionPerformed(ActionEvent e) {
			// if an author name is selected in the authorNameList
			if (!authorNameList.isSelectionEmpty()) {
				// grab a reference to the authorNameList's DefaulListModel that controls the entries in authorNameList
				DefaultListModel authorListModel = (DefaultListModel)authorNameList.getModel();
				// grab a reference to the ASINsList's DefaulListModel that controls the entries in ASINsList
				DefaultListModel ASINListModel = (DefaultListModel)ASINsList.getModel();
				int selectedAuthorIndex = authorNameList.getSelectedIndex();
				// save the selected author's name
				String authorName = authorListModel.getElementAt(selectedAuthorIndex).toString();

				// first replace duplicate commas and leading spaces from enterASINField String
				// the split the String wherever a comma or space occurs and place each entry into String[] ASINs
				String[] ASINs = enterASINField.getText().replaceAll("^[,\\s]+", "").split("[,\\s]+");
				// grab a reference to the ArrayList of all the author's saved ASINs
				ArrayList<String> asins = authorToASINMap.get(authorName);
				
				// if the list of author's ASINs isn't null or empty
				if (asins != null && ASINs != null) {
					// for each new ASIN to be added
					for (int i = 0; i < ASINs.length; i++) {
						if (ASINs[i] != null && !ASINs[i].isEmpty()) {
							// if the new ASIN isn't already present in the author's ASINsList
							if (!asins.contains(ASINs[i])) {
								// add new unique ASIN to the author's  ASINsList
								asins.add(ASINs[i]);
								// add the new ASIN to the ASINListModel for display in the JList
								ASINListModel.addElement(ASINs[i]);

								// output status message to user
								logTextBox.append("Added ASIN: " + ASINs[i] + "\n");
							} else {
								// output error message to user
								logTextBox.append("ERROR ASIN Already Present\n");
							}
							// clear the ASIN entry field
							enterASINField.setText("");
						} else {
							// output error message to user
							logTextBox.append("Please Enter an ASIN\n");
						}
					}
				}
				// enable the add ASIN button
				addASINButton.setEnabled(false);
			} else {
				// output error message to user
				logTextBox.append("Please Select an Author\n");
			}
		}
	}

	/**
	 * @purpose 	Private inner class implements action listener for remove all ASINs button
	 * @author Ian Moreno
	 *
	 */
	private class RemoveAllASINsButtonListener implements ActionListener {
		@Override
		// handle remove all ASINs button click event
		public void actionPerformed(ActionEvent e) {
			// if an author is selected
			if (!authorNameList.isSelectionEmpty()) {
				// grab a reference to the authorListModel controlling authorNameList JList entries
				DefaultListModel authorListModel = (DefaultListModel)authorNameList.getModel();
				// grab a reference to the ASINListModel controlling ASINsList JList entries
				DefaultListModel ASINListModel = (DefaultListModel)ASINsList.getModel();
				int selectedAuthorIndex = authorNameList.getSelectedIndex();
				int selectedASINIndex = ASINsList.getSelectedIndex();
				// save author name
				String authorName = authorListModel.getElementAt(selectedAuthorIndex).toString();
				// grab a reference to the ArrayList of author's saved ASINs
				ArrayList<String> asins = authorToASINMap.get(authorName); // get authors ASIN list
				
				asins.clear(); // remove all ASINs from list
				//authorToASINMap.put(authorName, asins); // add updated list back to authorToASINMap
				ASINListModel.clear();
				logTextBox.append("Removed all ASINs from Author: " + authorName + "\n");
				removeAllASINsButton.setEnabled(false);
				removeASINButton.setEnabled(false);
			}
		}
	}

	/**
	 * @purpose 	Private inner class implements ActionListener for the remove ASIN button
	 * @author Ian Moreno
	 */
	private class RemoveASINButtonListener implements ActionListener {
		@Override
		// handle remove ASIN button click event
		public void actionPerformed(ActionEvent e) {
			// if an ASIN is selected, and author is selected
			if (!ASINsList.isSelectionEmpty() && !authorNameList.isSelectionEmpty()) {
				DefaultListModel authorListModel = (DefaultListModel)authorNameList.getModel();
				DefaultListModel ASINListModel = (DefaultListModel)ASINsList.getModel();
				int selectedAuthorIndex = authorNameList.getSelectedIndex();
				int selectedASINIndex = ASINsList.getSelectedIndex();
				String authorName = authorListModel.getElementAt(selectedAuthorIndex).toString();
				String ASIN = ASINListModel.getElementAt(selectedASINIndex).toString();
				ArrayList<String> asins = authorToASINMap.get(authorName); // get authors ASIN list
				
				asins.remove(ASIN); // remove ASIN from list
				//authorToASINMap.put(authorName, asins); // add updated list back to authorToASINMap
				ASINListModel.removeElementAt(selectedASINIndex);
				logTextBox.append("Removed ASIN: " + ASIN + "\n");
				removeASINButton.setEnabled(false);
				
				if (ASINListModel.isEmpty()) {
					removeAllASINsButton.setEnabled(false);
				}
			}
		}
	} //END ASINs 
	
	
	/**
	 * @purpose 	private helper method builds the buttons panel
	 */
	private void buildButtonsPanel() {
		// create the main button panel and set it's border properties
		buttonsPanel = new JPanel(new BorderLayout());
		buttonsPanel.setBackground(Color.LIGHT_GRAY);
		buttonsPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10), 
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Status Messages")
				));
		// create buttons container and set its layout properties
		JPanel buttonsContainer = new JPanel(new FlowLayout());
		buttonsContainer.setBackground(Color.LIGHT_GRAY);

		// create the log container and set its background and layout properties
		JPanel logContainer = new JPanel(new FlowLayout());
		logContainer.setBackground(Color.LIGHT_GRAY);
		logTextBox = new JTextArea(5, 60); // 5 rows 60 charactes wide
		buttonsPanelScrollPane = new JScrollPane(logTextBox);
		logContainer.add(buttonsPanelScrollPane);

		createButton = new JButton("Create Reports");
		createButton.addActionListener(new CreateButtonListener());
		createButton.setEnabled(false);
		buttonsContainer.add(createButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelButtonListener());
		buttonsContainer.add(cancelButton);
		
		// add log container and buttons container to the main buttonsPanel
		buttonsPanel.add(logContainer, BorderLayout.CENTER);
		buttonsPanel.add(buttonsContainer, BorderLayout.SOUTH);
	}
	
	/**
	 * @purpose 	Private helper class implement Action Listener for the create button 
	 * @author Ian Moreno
	 *
	 */
	private class CreateButtonListener implements ActionListener {
		@Override
		// handle create button click event
		public void actionPerformed(ActionEvent e) {
			boolean success = false;
			// if the authorToASINMap isn't empty and savedReportFiles isn't empty 
			if (!authorToASINMap.isEmpty() && !savedReportFiles.isEmpty()) {
				// create reports for every saved author, given each saved report
				success = ReportGenerator.createReport(authorToASINMap, savedReportFiles);
			} else {
				// output error message to user
				logTextBox.append("ERROR CANNOT CREATE REPORTS\n");
			}
			if (success) logTextBox.append("Reports Generated Successfully.\n");
		}
	}

	/**
	 * @purpose	Private inner class implement action listen for the cancel button
	 * @author Ian Moreno
	 *
	 */
	private class CancelButtonListener implements ActionListener {
		@Override
		// handle cancel button click event
		public void actionPerformed(ActionEvent e) {
			// exit
			System.exit(0);
		}
	} //END ButtonsPanel
	
}

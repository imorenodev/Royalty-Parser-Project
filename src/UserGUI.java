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
	private final JFileChooser fileChooser = new JFileChooser();
	// provides a mapping between the Author's name and corresponding ASIN/book IDs
	private Map<String, ArrayList> authorToASINMap = new HashMap<String, ArrayList>();
	// running list of authors contained in the authors list panel
	private ArrayList<Author> authorsList = new ArrayList<>();
	// provides a mapping between the String abbreviations of a currency and its 
	// corresponding JTextField
	private Map<String, JTextField> currencyTextFields = new HashMap<String, JTextField>();
	// use HashSet to maintain a list of unique files, no duplicates.
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
		fileChooser.setMultiSelectionEnabled(true);
		
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
	 * @purpose	Implement the ListSelectionListener for the ReportList
	 * @author  	Ian Moreno
	 */
	private class ReportListSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			// do nothing, method override required for event listener 
		}
	}

	/**
	 * @purpose	Implement the ActionListener for the BrowsButtonListener 
	 * @author  	Ian Moreno
	 */
	private class BrowseButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			if (e.getSource() == browseButton) {
				// only allow KDP Excel reports to be selected by user
				fileChooser.setFileFilter(new ExcelFileFilter());
				int returnVal = fileChooser.showOpenDialog(UserGUI.this);
	 
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//Handle opening files.
					
					// save all selected input files in an array
					inputReportFiles = fileChooser.getSelectedFiles();
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
		 * 			overloaded accept method to only allow user to select files ending in ".xlsx"
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
	 * @purpose 	private class AddReportButtonlistener implements ActionListener 
	 * 			and handles actionPerformed events.
	 * @author 	Ian Moreno
	 */
	private class AddReportButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			
			// for each inputReportFile
			for (File inputReportFile : inputReportFiles) {
				// save report file path String
				String reportPath = inputReportFile.getPath();
				// get report file name
				String reportFileName = inputReportFile.getName();
				String prefixKDP = "KDP Prior Month Royalties";
				DefaultListModel reportListModel = (DefaultListModel)reportList.getModel();

				// ensure reportPath is not null or empty and ensure the fileName begins with "KDP Prior Month Royalties"
				// to minimize importing of invalid spreadsheets
				if (reportPath != null && !reportPath.equals("") && reportFileName.regionMatches(0, prefixKDP, 0, prefixKDP.length()-1)) {
					// if report is not already present, add its path string to reportList
					if (!reportListModel.contains(reportPath)) {
						reportListModel.addElement(reportPath);
						// add the report file to the savedReportFiles set
						savedReportFiles.add(inputReportFile);

						removeReportButton.setEnabled(true);
						removeAllReportsButton.setEnabled(true);
						createButton.setEnabled(true);

						logTextBox.append("Added Report: " + reportPath + "\n");
					} else {
						logTextBox.append("ERROR Report Already Added\n");
					}
				} else {
					logTextBox.append("ERROR Not a valid KDP Report\n");
				}

				addReportButton.setEnabled(false); // disable the add report button until a new file is selected
				findReportField.setText(""); // clear the findReportField text box
			}
		}
	}

	private class RemoveAllReportsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			DefaultListModel reportListModel = (DefaultListModel)reportList.getModel();

			if (!reportListModel.isEmpty()) {
				reportListModel.removeAllElements();
				savedReportFiles.clear();

				removeAllReportsButton.setEnabled(false);
				removeReportButton.setEnabled(false);
				createButton.setEnabled(false);

				logTextBox.append("Removed All Reports\n");
			} else {
				logTextBox.append("Report List Empty\n");
			}
		}
	}

	private class RemoveReportButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			String reportPath = "";
			if (!reportList.isSelectionEmpty()) {
				DefaultListModel reportListModel = (DefaultListModel)reportList.getModel();
				int selectedReportIndex = reportList.getSelectedIndex();
				if (!reportListModel.isEmpty()) {
					reportPath = reportListModel.getElementAt(selectedReportIndex).toString();
					savedReportFiles.remove(reportListModel.getElementAt(selectedReportIndex));
					reportListModel.removeElementAt(selectedReportIndex);

					if (reportListModel.isEmpty()) {
						removeReportButton.setEnabled(false);
						removeAllReportsButton.setEnabled(false);
						createButton.setEnabled(false);
					}

					logTextBox.append("Removed Report: " + reportPath + "\n");
				}
			} else {
				logTextBox.append("No Report Selected\n");
			}
		}
	} // END REPORT PANEL
	

	private void buildCurrencyPanel() {
		// initialize conversionMap with default values
		CurrencyConverter.resetConversionMap();

		currencyPanel = new JPanel();
		currencyPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10), 
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Currency Conversions")
				));
		currencyPanel.setLayout(new GridLayout(3,4));

		CADLabel = new JLabel("Candian Dollar (CAD)");
		GBPLabel = new JLabel("British Pound (GBP)");
		BRLLabel = new JLabel("Brazilian Real (BRL)");
		INRLabel = new JLabel("Indian Rupee (INR)");
		MXNLabel = new JLabel("Mexican Peso (MXN)");
		EURLabel = new JLabel("Euro (EUR)");
		JPYLabel = new JLabel("Japanese Yen (JPY)");
		AUDLabel = new JLabel("Australian Dollar (AUD)");
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

		saveCurrencyConversionButton = new JButton("Save Conversions");
		saveCurrencyConversionButton.addActionListener(new SaveCurrencyConversionButtonListener());
		saveCurrencyConversionButton.setEnabled(false);
		clearCurrencyConversionButton = new JButton("Clear All");
		clearCurrencyConversionButton.addActionListener(new ClearCurrencyConversionButtonListener());
		clearCurrencyConversionButton.setEnabled(false);
		
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

	private void addCurrencyButtonListeners(JTextField field) {
		field.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
					enableCurrencyPanelButtons();
				  }
				  public void removeUpdate(DocumentEvent e) {
					  // do nothing
				  }
				  public void insertUpdate(DocumentEvent e) {
					enableCurrencyPanelButtons();
				  }

				  public void enableCurrencyPanelButtons() {
					  saveCurrencyConversionButton.setEnabled(true);
					  clearCurrencyConversionButton.setEnabled(true);
				  }
			});
	}

	private JPanel buildCurrencyPanel(JLabel label, JTextField field) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(label);
		panel.add(field);
		return panel;
	}
	
	private class ClearCurrencyConversionButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			CurrencyConverter.resetConversionMap();
			JTextField field = new JTextField();
			
			Iterator<JTextField> currencyFieldsIterator = currencyTextFields.values().iterator();
			while (currencyFieldsIterator.hasNext()) {
				field = currencyFieldsIterator.next();
				field.setText("");
			}
			saveCurrencyConversionButton.setEnabled(false);
			clearCurrencyConversionButton.setEnabled(false);
		}
	} 

	private class SaveCurrencyConversionButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			CurrencyConverter.resetConversionMap(); // clear conversionMap
			String currencyType = "";
			Double conversionFactor = 0.0;
			Iterator currencyFieldsIterator = currencyTextFields.entrySet().iterator();
			JTextField currencyField = new JTextField();
			
			while (currencyFieldsIterator.hasNext()) {
				Map.Entry fieldEntry = (Map.Entry)currencyFieldsIterator.next();
				currencyField = (JTextField)fieldEntry.getValue();

				if (currencyField != null && !currencyField.getText().equals("")) {
					currencyType = fieldEntry.getKey().toString();
					conversionFactor = Double.valueOf(currencyField.getText());
					CurrencyConverter.conversionMap.put(currencyType, conversionFactor);
					logTextBox.append("Added Currency Type: " + currencyType + ": Conversion Factor: " + conversionFactor + "\n");
				}
			}
		}
	} // END CURRENCY PANEL

 
	private void buildAuthorProfilesPanel() {
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel eastPanel = new JPanel(new GridLayout(3,1));
		
		authorsPanel = new JPanel();
		authorsPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10), 
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Author Profiles")
				));
		authorsPanel.setLayout(new BorderLayout());

		enterAuthorNameLabel = new JLabel("Enter New Author");
		enterAuthorNameField = new JTextField(40);
		addAuthorPanelButtonListener(enterAuthorNameField);

		addAuthorButton = new JButton("Add Author");
		addAuthorButton.addActionListener(new AddAuthorButtonListener());
		addAuthorButton.setEnabled(false);

		northPanel.add(enterAuthorNameLabel);
		northPanel.add(enterAuthorNameField);
		northPanel.add(addAuthorButton);

		authorNameList = new JList<String>(new DefaultListModel<String>());
		DefaultListModel authorNameListModel = (DefaultListModel)authorNameList.getModel();

		// populate authorList with presaved author names
		String authorName = "";
		ArrayList<String> authorsASINs = new ArrayList<>();
		for (Author a : authorsList) {
			authorName = a.getName();
			authorsASINs = (ArrayList)a.getASINList();
			authorNameListModel.addElement(a.getName());
			authorToASINMap.put(authorName, authorsASINs);
		}

		authorNameList.addListSelectionListener(new AuthorNameListSelectionListener());
		authorNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		authorNameList.setVisibleRowCount(5);
		authorNameListScrollPane = new JScrollPane(authorNameList);
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(authorNameListScrollPane, BorderLayout.CENTER);

		removeAuthorButton = new JButton("Remove Author");
		removeAuthorButton.addActionListener(new RemoveAuthorButtonListener());
		removeAuthorButton.setEnabled(false);

		eastPanel.add(removeAuthorButton);
		
		authorsPanel.add(northPanel, BorderLayout.NORTH);
		authorsPanel.add(centerPanel, BorderLayout.CENTER);
		authorsPanel.add(eastPanel, BorderLayout.EAST);
	}
	
	private void addAuthorPanelButtonListener(JTextField field) {
		field.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
					enableAuthorPanelButtons();
				  }
				  public void removeUpdate(DocumentEvent e) {
					  // do nothing
				  }
				  public void insertUpdate(DocumentEvent e) {
					enableAuthorPanelButtons();
				  }

				  public void enableAuthorPanelButtons() {
					  addAuthorButton.setEnabled(true);
					  removeAuthorButton.setEnabled(true);
				  }
			});
	}

	private class AuthorNameListSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			// handle list selection event
			DefaultListModel authorListModel = (DefaultListModel)authorNameList.getModel();
			DefaultListModel ASINsListModel = (DefaultListModel)ASINsList.getModel();
			int selectedIndex = authorNameList.getSelectedIndex();
			
			if (selectedIndex >= 0) {
				String authorName = authorListModel.getElementAt(authorNameList.getSelectedIndex()).toString();
				// check to see if author has any saved ASINs
				ASINsListModel.clear();
				removeAuthorButton.setEnabled(true);

				if (authorName != null) { // check to make sure author isn't already previously deleted
					if (authorToASINMap.containsKey(authorName)) {
						// get the list of saved ASINs
						ArrayList<String> asins = authorToASINMap.get(authorName);
						for (String aName : asins) {
							// add each ASIN to the authorListModel
							ASINsListModel.addElement(aName);
						}
						removeAllASINsButton.setEnabled(true);
					}
				}
			} else {
				logTextBox.append("ERROR: No Author Selected\n");
			}
		}
	}
	
	private class AddAuthorButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			String authorName = enterAuthorNameField.getText();
			DefaultListModel authorListModel = (DefaultListModel)authorNameList.getModel();
			
			if (authorName != null && !authorName.equals("")) {
				// if author name is not already present, add it to authorNamesList
				if (!authorListModel.contains(authorName)) {
					authorListModel.addElement(authorName);
					authorToASINMap.put(authorName, new ArrayList<String>());
					logTextBox.append("Added Author: " + authorName + "\n");
				} else {
					logTextBox.append("ERROR Author Name Already Added\n");
				}
				enterAuthorNameField.setText(""); // clear the enterAuthorNameField text box
			} else {
				logTextBox.append("Enter an Author Name\n");
			}
			addAuthorButton.setEnabled(false);
		}
	}

	private class RemoveAuthorButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			if (!authorNameList.isSelectionEmpty()) {
				DefaultListModel authorListModel = (DefaultListModel)authorNameList.getModel();
				DefaultListModel ASINsListModel = (DefaultListModel)ASINsList.getModel();
				int selectedAuthorIndex = authorNameList.getSelectedIndex();
				String authorName = authorListModel.getElementAt(selectedAuthorIndex).toString();
				// if the list is not empty and there is an author name selected
				if (!authorListModel.isEmpty() && selectedAuthorIndex >= 0) {
					// remove author and ASIN list from the authorToASINMap
					authorToASINMap.remove(authorName);
					// remove author name from authorNameList
					authorListModel.removeElementAt(selectedAuthorIndex);
					// clear ASINs from the ASINsList
					ASINsListModel.clear(); 
					logTextBox.append("Removed Author: " + authorName + "\n");
					removeAuthorButton.setEnabled(false);
				} else {
					logTextBox.append("No Author Selected\n");
				}
			} else {
				logTextBox.append("No Author Selected\n");
			}
		}
	} // END AUTHOR PROFILES


	private void buildASINsPanel() {
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel eastPanel = new JPanel(new GridLayout(3,1));
		
		ASINsPanel = new JPanel();
		ASINsPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10), 
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Author's ASINs")
				));
		ASINsPanel.setLayout(new BorderLayout());

		enterASINLabel = new JLabel("Enter New ASINs");
		enterASINField = new JTextField(40);
		addASINPanelButtonListener(enterASINField);

		addASINButton = new JButton("Add ASIN");
		addASINButton.addActionListener(new AddASINButtonListener());
		addASINButton.setEnabled(false);

		northPanel.add(enterASINLabel);
		northPanel.add(enterASINField);
		northPanel.add(addASINButton);

		ASINsList = new JList<String>(new DefaultListModel<String>());
		ASINsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ASINsList.addListSelectionListener(new ASINsListSelectionListener());
		ASINsList.setVisibleRowCount(5);
		ASINsListScrollPane = new JScrollPane(ASINsList);
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(ASINsListScrollPane, BorderLayout.CENTER);

		removeASINButton = new JButton("Remove ASIN");
		removeASINButton.addActionListener(new RemoveASINButtonListener());
		removeASINButton.setEnabled(false);
		removeAllASINsButton = new JButton("Remove All ASINs");
		removeAllASINsButton.addActionListener(new RemoveAllASINsButtonListener());
		removeAllASINsButton.setEnabled(false);

		eastPanel.add(removeASINButton);
		eastPanel.add(removeAllASINsButton);
		
		ASINsPanel.add(northPanel, BorderLayout.NORTH);
		ASINsPanel.add(centerPanel, BorderLayout.CENTER);
		ASINsPanel.add(eastPanel, BorderLayout.EAST);
	}

	private void addASINPanelButtonListener(JTextField field) {
		field.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
					enableASINPanelButtons();
				  }
				  public void removeUpdate(DocumentEvent e) {
					  // do nothing
				  }
				  public void insertUpdate(DocumentEvent e) {
					enableASINPanelButtons();
				  }

				  public void enableASINPanelButtons() {
					  //removeASINButton.setEnabled(true);
					  //removeAllASINsButton.setEnabled(true);
					  addASINButton.setEnabled(true);
				  }
			});
	}
	
	private class ASINsListSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			// handle list selection event
			removeASINButton.setEnabled(true);
			removeAllASINsButton.setEnabled(true);
		}
	}
	
	private class AddASINButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			if (!authorNameList.isSelectionEmpty()) {
				DefaultListModel authorListModel = (DefaultListModel)authorNameList.getModel();
				DefaultListModel ASINListModel = (DefaultListModel)ASINsList.getModel();
				int selectedAuthorIndex = authorNameList.getSelectedIndex();
				String authorName = authorListModel.getElementAt(selectedAuthorIndex).toString();

				// first replace duplicate commas and leading spaces from enterASINField String
				// the split the String wherever a comma or space occurs and place each entry into String[] ASINs
				String[] ASINs = enterASINField.getText().replaceAll("^[,\\s]+", "").split("[,\\s]+");
				ArrayList<String> asins = authorToASINMap.get(authorName);
				
				if (asins != null && ASINs != null) {
					for (int i = 0; i < ASINs.length; i++) {
						if (ASINs[i] != null && !ASINs[i].isEmpty()) {
							if (!asins.contains(ASINs[i])) {
								asins.add(ASINs[i]);
								ASINListModel.addElement(ASINs[i]);

								logTextBox.append("Added ASIN: " + ASINs[i] + "\n");
							} else {
								logTextBox.append("ERROR ASIN Already Present\n");
							}
							enterASINField.setText("");
						} else {
							logTextBox.append("Please Enter an ASIN\n");
						}
					}
				}
				addASINButton.setEnabled(false);
			} else {
				logTextBox.append("Please Select an Author\n");
			}
		}
	}

	private class RemoveAllASINsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			
			// if an author is selected
			if (!authorNameList.isSelectionEmpty()) {
				DefaultListModel authorListModel = (DefaultListModel)authorNameList.getModel();
				DefaultListModel ASINListModel = (DefaultListModel)ASINsList.getModel();
				int selectedAuthorIndex = authorNameList.getSelectedIndex();
				int selectedASINIndex = ASINsList.getSelectedIndex();
				String authorName = authorListModel.getElementAt(selectedAuthorIndex).toString();
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

	private class RemoveASINButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			
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
	
	
	private void buildButtonsPanel() {
		buttonsPanel = new JPanel(new BorderLayout());
		buttonsPanel.setBackground(Color.LIGHT_GRAY);
		buttonsPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10), 
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Status Messages")
				));
		JPanel buttonsContainer = new JPanel(new FlowLayout());
		buttonsContainer.setBackground(Color.LIGHT_GRAY);
		JPanel logContainer = new JPanel(new FlowLayout());
		logContainer.setBackground(Color.LIGHT_GRAY);
		logTextBox = new JTextArea(5, 60);
		buttonsPanelScrollPane = new JScrollPane(logTextBox);
		logContainer.add(buttonsPanelScrollPane);

		createButton = new JButton("Create Reports");
		createButton.addActionListener(new CreateButtonListener());
		createButton.setEnabled(false);
		buttonsContainer.add(createButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelButtonListener());
		buttonsContainer.add(cancelButton);
		
		buttonsPanel.add(logContainer, BorderLayout.CENTER);
		buttonsPanel.add(buttonsContainer, BorderLayout.SOUTH);
	}
	
	private class CreateButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			if (!authorToASINMap.isEmpty() && !savedReportFiles.isEmpty()) {
				ReportGenerator.createReport(authorToASINMap, savedReportFiles);
			} else {
				logTextBox.append("ERROR CANNOT CREATE REPORTS\n");
			}
		}
	}

	private class CancelButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			System.exit(0);
		}
	} //END ButtonsPanel
	
}

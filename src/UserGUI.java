import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class UserGUI extends JFrame {
	private final JFileChooser fileChooser = new JFileChooser();
	private Map<String, ArrayList> authorToASINMap = new HashMap<String, ArrayList>();
	private ArrayList<Author> authorsList = new ArrayList<>();
	private HashSet<File> reportFiles = new HashSet<>(); // use HashSet to maintain list of unique files
	private JButton browseButton, 
			addReportButton, 
			removeReportButton, 
			saveCurrencyConversionButton,
			clearCurrencyConversionButton,
			addAuthorButton, 
			removeAuthorButton, 
			addASINButton, 
			removeASINButton,
			createButton,
			cancelButton;
	private JPanel reportsPanel,
			currencyPanel,
			authorsPanel,
			ASINsPanel,
			buttonsPanel;
	private JLabel findReportLabel,
			CADLabel, GBPLabel, BRLLabel,
			INRLabel, MXNLabel, EURLabel,
			JPYLabel, AUDLabel,
			enterAuthorNameLabel,
			enterASINLabel;
	private JTextField findReportField,
			CADField, GBPField, BRLField,
			INRField, MXNField, EURField,
			JPYField, AUDField,
			enterAuthorNameField,
			enterASINField;
	private JList<String> reportList,
			authorNameList,
			ASINsList;
	private JScrollPane reportListScrollPane,
			authorNameListScrollPane,
			ASINsListScrollPane,
			buttonsPanelScrollPane;
	private JTextArea logTextBox;

	/**
	 * Constructor for UserGUI
	 */
	public UserGUI(ArrayList<Author> presavedAuthorsList) {
		//Create the window.
		JFrame frame = new JFrame("Royalty Parser");
		frame.setLayout(new GridLayout(0,1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// load pre-saved authors
		authorsList = presavedAuthorsList;
		
		buildReportsPanel();
		buildCurrencyPanel();
		buildAuthorProfilesPanel();
		buildASINsPanel();
		buildButtonsPanel();
 
		//Add content to the window.
		frame.add(reportsPanel);
		frame.add(currencyPanel);
		frame.add(authorsPanel);
		frame.add(ASINsPanel);
		frame.add(buttonsPanel);
 
		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	

	private void buildReportsPanel() {
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel eastPanel = new JPanel(new GridLayout(3,1));
		
		reportsPanel = new JPanel();
		reportsPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10), 
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Reports")
				));
		reportsPanel.setLayout(new BorderLayout());

		findReportLabel = new JLabel("Find Report");
		findReportField = new JTextField(40);
		browseButton = new JButton("Browse");
		browseButton.addActionListener(new BrowseButtonListener());
		addReportButton = new JButton("Add Report");
		addReportButton.addActionListener(new AddReportButtonListener());

		northPanel.add(findReportLabel);
		northPanel.add(findReportField);
		northPanel.add(browseButton);
		northPanel.add(addReportButton);

		reportList = new JList<String>(new DefaultListModel<String>());
		reportList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reportList.addListSelectionListener(new ReportListSelectionListener());
		reportList.setVisibleRowCount(5);
		reportListScrollPane = new JScrollPane(reportList);
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(reportListScrollPane, BorderLayout.CENTER);

		removeReportButton = new JButton("Remove Report");
		removeReportButton.addActionListener(new RemoveReportButtonListener());
		eastPanel.add(removeReportButton);
		
		reportsPanel.add(northPanel, BorderLayout.NORTH);
		reportsPanel.add(centerPanel, BorderLayout.CENTER);
		reportsPanel.add(eastPanel, BorderLayout.EAST);
	}
	
	private class ReportListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			// handle list selection event
			//logTextBox.append("Selected Report: " + e.getFirstIndex() + "\n");
		}
	}
	
	private class BrowseButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			//Handle open button action.
			if (e.getSource() == browseButton) {
				int returnVal = fileChooser.showOpenDialog(UserGUI.this);
	 
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					//Handle opening file.
					findReportField.setText(file.getPath());
				} else {
					logTextBox.append("Open command cancelled by user.\n");
				}
			}
		}
	}

	private class AddReportButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			String reportPath = findReportField.getText();
			DefaultListModel reportListModel = (DefaultListModel)reportList.getModel();

			if (reportPath != null && !reportPath.equals("")) {
				// if report is not already present, add its path string to reportList
				if (!reportListModel.contains(reportPath)) {
					reportListModel.addElement(reportPath);
					// add the report file to the reportFiles set
					reportFiles.add(fileChooser.getSelectedFile());
					logTextBox.append("Added Report: " + reportPath + "\n");
				} else {
					logTextBox.append("ERROR Report Already Added\n");
				}
				findReportField.setText(""); // clear the findReportField text box
			}
		}
	}

	private class RemoveReportButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			String reportPath = "";
			if (!reportList.isSelectionEmpty()) {
				DefaultListModel reportListModel = (DefaultListModel)reportList.getModel();
				int selectedReportIndex = reportList.getSelectedIndex();
				if (!reportListModel.isEmpty()) {
					reportPath = reportListModel.getElementAt(selectedReportIndex).toString();
					reportListModel.removeElementAt(selectedReportIndex);
					logTextBox.append("Removed Report: " + reportPath + "\n");
				}
			} else {
				logTextBox.append("No Report Selected\n");
			}
		}
	} // END REPORT PANEL
	

	private void buildCurrencyPanel() {
		// initialize conversionMap with default values
		CurrencyConverter.conversionMap.put("USD", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("CAD", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("GBP", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("EUR", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("BRL", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("INR", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("JPY", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("AUD", Double.valueOf(1.0));
		CurrencyConverter.conversionMap.put("MXN", Double.valueOf(1.0));

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
		GBPField = new JTextField(3);
		BRLField = new JTextField(3);
		INRField = new JTextField(3);
		MXNField = new JTextField(3);
		EURField = new JTextField(3);
		JPYField = new JTextField(3);
		AUDField = new JTextField(3);
		saveCurrencyConversionButton = new JButton("Save Conversions");
		saveCurrencyConversionButton.addActionListener(new SaveCurrencyConversionButtonListener());
		clearCurrencyConversionButton = new JButton("Clear All");
		clearCurrencyConversionButton.addActionListener(new ClearCurrencyConversionButtonListener());
		
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
	
	private JPanel buildCurrencyPanel(JLabel label, JTextField field) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(label);
		panel.add(field);
		return panel;
	}
	
	private class ClearCurrencyConversionButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
		}
	} 

	private class SaveCurrencyConversionButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
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
		addAuthorButton = new JButton("Add Author");
		addAuthorButton.addActionListener(new AddAuthorButtonListener());
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

		authorNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		authorNameList.addListSelectionListener(new AuthorNameListSelectionListener());
		authorNameList.setVisibleRowCount(5);
		authorNameListScrollPane = new JScrollPane(authorNameList);
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(authorNameListScrollPane, BorderLayout.CENTER);

		removeAuthorButton = new JButton("Remove Author");
		removeAuthorButton.addActionListener(new RemoveAuthorButtonListener());
		eastPanel.add(removeAuthorButton);
		
		authorsPanel.add(northPanel, BorderLayout.NORTH);
		authorsPanel.add(centerPanel, BorderLayout.CENTER);
		authorsPanel.add(eastPanel, BorderLayout.EAST);
	}
	
	private class AuthorNameListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			// handle list selection event
			DefaultListModel authorListModel = (DefaultListModel)authorNameList.getModel();
			DefaultListModel ASINsListModel = (DefaultListModel)ASINsList.getModel();
			int selectedIndex = authorNameList.getSelectedIndex();
			
			if (selectedIndex >= 0) {
				String authorName = authorListModel.getElementAt(authorNameList.getSelectedIndex()).toString();
				// check to see if author has any saved ASINs
				ASINsListModel.clear();

				if (authorName != null) { // check to make sure author isn't already previously deleted
					if (authorToASINMap.containsKey(authorName)) {
						// get the list of saved ASINs
						ArrayList<String> asins = authorToASINMap.get(authorName);
						for (String aName : asins) {
							// add each ASIN to the authorListModel
							ASINsListModel.addElement(aName);
						}
					}
				}
			} else {
				logTextBox.append("ERROR: No Author Selected\n");
			}
		}
	}
	
	private class AddAuthorButtonListener implements ActionListener {
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
		}
	}

	private class RemoveAuthorButtonListener implements ActionListener {
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

		enterASINLabel = new JLabel("Enter New ASIN");
		enterASINField = new JTextField(40);
		addASINButton = new JButton("Add ASIN");
		addASINButton.addActionListener(new AddASINButtonListener());
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
		eastPanel.add(removeASINButton);
		
		ASINsPanel.add(northPanel, BorderLayout.NORTH);
		ASINsPanel.add(centerPanel, BorderLayout.CENTER);
		ASINsPanel.add(eastPanel, BorderLayout.EAST);
	}
	
	private class ASINsListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			// handle list selection event
		}
	}
	
	private class AddASINButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			if (!authorNameList.isSelectionEmpty()) {
				DefaultListModel authorListModel = (DefaultListModel)authorNameList.getModel();
				DefaultListModel ASINListModel = (DefaultListModel)ASINsList.getModel();
				int selectedAuthorIndex = authorNameList.getSelectedIndex();
				String authorName = authorListModel.getElementAt(selectedAuthorIndex).toString();
				String ASIN = enterASINField.getText();
				ArrayList<String> asins = authorToASINMap.get(authorName);
				
				if (asins != null && ASIN != null && !ASIN.equals("")) {
					if (!asins.contains(ASIN)) {
						asins.add(ASIN);
						//authorToASINMap.put(authorName, asins);
						ASINListModel.addElement(ASIN);
						logTextBox.append("Added ASIN: " + ASIN + "\n");
					} else {
						logTextBox.append("ERROR ASIN Already Present\n");
					}
					enterASINField.setText("");
				} else {
					logTextBox.append("Please Enter an ASIN\n");
				}
			} else {
				logTextBox.append("Please Select an Author\n");
			}
		}
	}

	private class RemoveASINButtonListener implements ActionListener {
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
		buttonsContainer.add(createButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelButtonListener());
		buttonsContainer.add(cancelButton);
		
		buttonsPanel.add(logContainer, BorderLayout.CENTER);
		buttonsPanel.add(buttonsContainer, BorderLayout.SOUTH);
	}
	
	private class CreateButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
			if (!authorToASINMap.isEmpty() && !reportFiles.isEmpty()) {
				ReportGenerator.createReport(authorToASINMap, reportFiles);
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

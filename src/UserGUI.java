import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UserGUI extends JFrame {
	static private final String newline = "\n";
	private JFileChooser fileChooser;
	private JButton browseButton, 
				addReportButton, 
				removeReportButton, 
				addAuthorButton, 
				removeAuthorButton, 
				addASINButton, 
				removeASINButton,
				createButton,
				cancelButton;
	private JPanel reportsPanel,
			authorsPanel,
			ASINsPanel,
			buttonsPanel;
	private JLabel findReportLabel,
			enterAuthorNameLabel,
			enterASINLabel;
	private JTextField findReportField,
			enterAuthorNameField,
			enterASINField;
	private JList reportList,
			authorNameList,
			ASINsList;
	private JScrollPane reportListScrollPane,
			authorNameListScrollPane,
			ASINsListScrollPane;

	/**
	 * Constructor for UserGUI
	 */
	public UserGUI() {
		//Create the window.
		JFrame frame = new JFrame("Royalty Parser");
		frame.setLayout(new GridLayout(0,1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buildReportsPanel();
		buildAuthorProfilesPanel();
		buildASINsPanel();
		buildButtonsPanel();
 
		//Add content to the window.
		frame.add(reportsPanel);
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

		reportList = new JList(new String[] {"Report 1", "Report 2", "Report 3", "Report 4", "Report 5", "Report 6", "Report 7", "Report 8"});
		reportList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
		}
	}
	
	private class BrowseButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
		}
	}

	private class AddReportButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
		}
	}

	private class RemoveReportButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
		}
	} // END REPORT PANEL
	
 
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

		authorNameList = new JList(new String[] {"Ian", "Joey", "Kushal", "Andrea", "Yusef", "One", "Two", "Three"});
		authorNameList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
		}
	}
	
	private class AddAuthorButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
		}
	}

	private class RemoveAuthorButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
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

		ASINsList = new JList(new String[] {"AS1234", "AS5678", "AS91012123", "AS123142", "AS9876", "AS65432", "AS4313123", "AS990002"});
		ASINsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
		}
	}

	private class RemoveASINButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
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
		JTextArea logTextBox = new JTextArea(5, 60);
		logContainer.add(logTextBox);

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
		}
	}

	private class CancelButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// handle browse button click event
		}
	} //END ASINs 
/**	
 
	public void actionPerformed(ActionEvent e) {
 
		//Handle open button action.
		if (e.getSource() == openButton) {
			int returnVal = fileChooser.showOpenDialog(UserGUI.this);
 
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				//Handle opening file.
			} else {
				JOptionPane.showMessageDialog(this, "Open command cancelled by user." + newline);
			}
 
		//Handle save button action.
		} else if (e.getSource() == saveButton) {
			int returnVal = fileChooser.showSaveDialog(UserGUI.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				//handle saving the file.
				JOptionPane.showMessageDialog(this, "Saving: " + file.getName() + "." + newline);
			} else {
				JOptionPane.showMessageDialog(this, "Save command cancelled by user." + newline);
			}
		}
	}
*/ 
}

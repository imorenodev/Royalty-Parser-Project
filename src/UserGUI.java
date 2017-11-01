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
			confirmButton,
			cancelButton;
	private JPanel reportsPanel;
	private JPanel authorsPanel;
	private JPanel ASINsPanel;
	private JLabel findReportLabel;
	private JLabel enterAuthorNameLabel;
	private JLabel enterASINLabel;
	private JTextField findReportField;
	private JTextField enterAuthorNameField;
	private JTextField enterASINField;
	private JList reportList;

	private void buildReportsPanel() {
		reportsPanel = new JPanel();
		findReportLabel = new JLabel("Find Report");
		findReportField = new JTextField(40);
		browseButton = new JButton("Browse");
		browseButton.addActionListener(new BrowseButtonListener());
		reportList = new JList(new String[] {"Ian", "Joey", "Kushal", "Andrea", "Yusef"});
		reportList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		reportList.addListSelectionListener(new ReportListSelectionListener());
		reportList.setVisibleRowCount(5);
		addReportButton = new JButton("Add Report");
		addReportButton.addActionListener(new AddReportButtonListener());
		removeReportButton = new JButton("Remove Report");
		removeReportButton.addActionListener(new RemoveReportButtonListener());

		GroupLayout layout = new GroupLayout(reportsPanel);
		reportsPanel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		reportsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Reports"));
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addComponent(findReportLabel)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
							.addComponent(findReportField, 475, 475, 475)
							.addComponent(reportList, 475, 475, 475))
					.addGroup(layout.createParallelGroup()
							.addComponent(browseButton)
							.addComponent(addReportButton)
							.addComponent(removeReportButton))
				);
				layout.setVerticalGroup(
						layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup()
									.addComponent(findReportLabel)
									.addComponent(findReportField)
									.addComponent(browseButton))
							.addGroup(layout.createParallelGroup()
									.addComponent(reportList)
									.addComponent(addReportButton)
									.addComponent(removeReportButton))
				);
		layout.linkSize(SwingConstants.HORIZONTAL, browseButton, addReportButton, removeReportButton);
		
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
	}
	
	/**
	 * Constructor for UserGUI
	 */
	public UserGUI() {
		//Create the window.
		JFrame frame = new JFrame("Royalty Parser");
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buildReportsPanel();
 
		//Add content to the window.
		frame.add(reportsPanel);
 
		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
 
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

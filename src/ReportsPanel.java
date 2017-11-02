import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ReportsPanel {
	static private final String newline = "\n";
	private JFileChooser fileChooser;
	private JButton browseButton, 
				addReportButton, 
				removeReportButton;
	private JPanel reportsPanel;
	private JLabel findReportLabel;
	private JTextField findReportField;
	private JList reportList;
	private JScrollPane reportListScrollPane;

	public ReportsPanel() {
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
	
	public JPanel getReportsPanel() {
		return reportsPanel;
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
}

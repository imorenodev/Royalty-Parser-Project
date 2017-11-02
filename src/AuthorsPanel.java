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

public class AuthorsPanel {
	private JFileChooser fileChooser;
	private JButton addAuthorButton, 
					removeAuthorButton;
	private JPanel authorsPanel;
	private JLabel enterAuthorNameLabel;
	private JTextField enterAuthorNameField;
	private JList authorNameList;
	private JScrollPane authorNameListScrollPane;

	public AuthorsPanel() {
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
	
	public JPanel getAuthorsPanel() {
		return authorsPanel;
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
}

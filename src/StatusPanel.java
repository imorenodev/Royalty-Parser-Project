import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class StatusPanel {
	private JFileChooser fileChooser;
	private JButton createButton,
			cancelButton;
	private JPanel statusPanel,
			buttonsContainer,
			logContainer;
	private JLabel findReportLabel,
			enterAuthorNameLabel,
			enterASINLabel;
	private JTextArea logTextBox;

	public StatusPanel() {
		statusPanel = new JPanel(new BorderLayout());
		statusPanel.setBackground(Color.LIGHT_GRAY);
		statusPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10), 
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 1), "Status Messages")
				));
		buttonsContainer = new JPanel(new FlowLayout());
		buttonsContainer.setBackground(Color.LIGHT_GRAY);
		logContainer = new JPanel(new FlowLayout());
		logContainer.setBackground(Color.LIGHT_GRAY);
		logTextBox = new JTextArea(5, 60);
		logContainer.add(logTextBox);

		createButton = new JButton("Create Reports");
		createButton.addActionListener(new CreateButtonListener());
		buttonsContainer.add(createButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelButtonListener());
		buttonsContainer.add(cancelButton);
		
		statusPanel.add(logContainer, BorderLayout.CENTER);
		statusPanel.add(buttonsContainer, BorderLayout.SOUTH);
	}
	
	public JPanel getStatusPanel() {
		return statusPanel;
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
}

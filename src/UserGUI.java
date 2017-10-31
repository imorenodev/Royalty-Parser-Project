import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UserGUI extends JPanel implements ActionListener {
	static private final String newline = "\n";
	JButton openButton, saveButton;
	JFileChooser fileChooser;

	/**
	 * Create the GUI and show it.
	 */
	public static void createAndShowGUI() {
		//Create the window.
		JFrame frame = new JFrame("Royalty Parser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
		//Add content to the window.
		frame.add(new UserGUI());
 
		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
 
	private UserGUI() {
		super(new BorderLayout());
 
		//Create a file chooser
		fileChooser = new JFileChooser();

		openButton = new JButton("Open a File...");
								 
		openButton.addActionListener(this);
 
		saveButton = new JButton("Save a File...");
		saveButton.addActionListener(this);
 
		JPanel buttonPanel = new JPanel(); //use FlowLayout
		buttonPanel.add(openButton);
		buttonPanel.add(saveButton);
 
		//Add the buttons to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
	}
 
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
 
}

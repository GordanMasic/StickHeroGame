package game;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {

	private static final long serialVersionUID = -1270370868129612418L;

	private JPanel mainPanel = new MyPanel();
	private String ocean = "/ocean-waves-1.wav";

	public Window() {
		// Loading sound from file
		// ocean = ResourceLoader.load("ocean-waves-1.wav");

		// Making and adding panel to widow
		add(mainPanel);

		/*
		 * Window default settings
		 */
		setTitle("Pengin escape");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		while (true) {
			MyPanel.playSound(ocean);
		}
	}

}

package game;


import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {

	private static final long serialVersionUID = -1270370868129612418L;

	private JPanel mainPanel = new MyPanel();
	InputStream ocean;

	public Window() {
		//Loading sound from file
		ocean = ResourceLoader.load("ocean-waves-1.wav");
		
		
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
		while(true){
		ocean = ResourceLoader.load("ocean-waves-1.wav");
		playSound(ocean);
		}
	}

	/**
	 * Method for playing sound
	 * 
	 * @param sound
	 *            sound loaded from file
	 */
	public static void playSound(InputStream sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();

			Thread.sleep(clip.getMicrosecondLength());
		} catch (Exception e) {

		}

	}
}

package game;

import java.io.InputStream;

public class Main {

	public static void main(String[] args) {
		InputStream ocean = ResourceLoader.load("ocean-waves-1.wav");
		
		new Window();
		while(true){
		Window.playSound(ocean);
		}
	}

}

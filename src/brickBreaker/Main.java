package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		/**
		 * setting the properties of the window
		 */
		JFrame obj = new JFrame();
		gamePlay game = new gamePlay(); // creating an object of the class
		obj.setBounds(10, 10,700, 600);// setting the background
		obj.setTitle("Breakout Ball"); // naming the game
		obj.setResizable(false);// want to keep the window fixed
		obj.setVisible(true);//want to make the window visible
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//there should be a cross to close
		obj.add(game);
	}

}

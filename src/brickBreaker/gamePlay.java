package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class gamePlay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false; // the game shouldnt play by itself
	private int score = 0; // initially the game will be 0

	private int totalBricks = 21;

	private Timer timer;
	private int delay = 8; // speed of the ball

	private int playerx = 310; // starting position of the slider

	private int ballposx = 120; // x position of the ball
	private int ballposy = 350; // y position of the ball

	private int ballxdir = -1; // horizontal direction of the ball
	private int ballydir = -2; // vertical direction of the ball

	
	private MapGenerator map;
	
	public gamePlay() {
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();

	}

	/**
	 * will draw the ball, slider etc here
	 */
	public void paint(Graphics g) {
		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);

		//map
		map.draw((Graphics2D)g);
		
		// borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//scores
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score, 590, 30);

		// paddle
		g.setColor(Color.green);
		g.fillRect(playerx, 550, 100, 8);

		// ball
		g.setColor(Color.yellow);
		g.fillOval(ballposx, ballposy, 20, 20);
		
		if(ballposy> 570)
		{
			play = false;
			ballxdir = 0;
			ballydir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over !", 190, 300);
			
			
		}
		g.dispose(); // what does that do?

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		if(play) {
			
			//used to intersect with the slider
			if(new Rectangle(ballposx, ballposy, 20, 20).intersects(new Rectangle(playerx, 550, 100, 8)))
			{
				ballydir = -ballydir;
			}
			
			A: for(int i=0; i<map.map.length; i++)
			{
				for(int j = 0; j<map.map[0].length; j++)
				{
					if(map.map[i][j] > 0)
					{
						int brickx = j*map.brickWidth +80;
						int bricky = i*map.brickHeight +50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickx, bricky, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposx, ballposy, 20, 20);	
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) 
						{
							map.setBrickValue(0, i, j);
							score += 5;
							
							if(ballposx +19 <= brickRect.x || ballposx +1 >= brickRect.x + brickRect.width)
							{
								ballxdir = -ballxdir;
							}
							else
							{
								ballydir = - ballydir;
							}
							
							break A;
						}
					}
				}
			}
			ballposx += ballxdir; // ball's x position increments by x direction
			ballposy += ballydir; // ball's y position increments by y direction
			
			// the ball's x position is less that 0 then it has hit the left border
			// therefore it changes its direction to opposite of that in x axis
			if(ballposx <0) //left border
			{
				ballxdir = -ballxdir;
			}
			

			// the ball's y position is less that 0 then it has hit the top border
			// therefore it changes its direction to opposite of that in y axis
			if(ballposy <0) //top border
			{
				ballydir = -ballydir;
			}
			

			// the ball's x position is greater than 670 then it has hit the right border
			// therefore it changes its direction to opposite of that in x axis
			if(ballposx > 670) //right border
			{
				ballxdir = -ballxdir;
			}
		}
		
		
		repaint(); // it calls the whole paint method and redo everything
				   // we basically do this to update the positon of the paddle 

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			// we want to increment the position of the slidebar
			// but dont want to go beyond the border
			if (playerx >= 600) // 600 is the window's height

			{
				playerx = 600;

			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerx < 10) // 10 is the window's y position/x position
			// confused abt the bounds
			{
				playerx = 10;

			} else {
				moveLeft();
			}
		}

	}

	public void moveRight() {
		play = true;
		playerx += 20;
	}

	public void moveLeft() {
		play = true;
		playerx -= 20;
	}

}

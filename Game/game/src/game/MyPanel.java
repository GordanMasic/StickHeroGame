package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MyPanel extends JPanel {

	private static final long serialVersionUID = 7081826329768097269L;

	// >>>>>>Default settings<<<<<<<<
	boolean isClicked = false;
	boolean raise = true;
	private boolean isPerfect = false;

	// Stick first time position
	private int x1 = 85;
	private int y1 = 400;
	private int x2 = 85;
	private int y2 = 400;

	// Timers
	private Timer timer;
	private Timer timer1;
	private Timer timer2;
	private Timer timer3;
	private Timer timer4;
	private Timer timer5;

	// Angle and length of stick drop down arc
	private int angle = 0;
	private double l = 0;

	// Rectangle1
	private int rectX1 = 20;
	private int rectY1 = 380;
	private int width1 = 100;
	private int height1 = 100;

	// Rectangle2

	private int rectX2 = (int) (Math.random() * 240 + 150);
	private int rectY2 = 380;
	private int width2 = (int) (Math.random() * 70 + 30);
	private int height2 = 100;

	// Rectangle3

	private int rectX3 = 50;
	private int rectY3 = 362;
	private int width3 = 30;
	private int height3 = 38;

	// Score counter
	private int counter = 0;

	// Pictures
	private BufferedImage img;
	private BufferedImage img1;
	private BufferedImage img2;

	public MyPanel() {

		// Adding images from the file
		try {
			img1 = ImageIO.read(new File("images\\player1.png"));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		try {
			img = ImageIO.read(new File("images\\Sunset Landscape.jpg"));

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			img2 = ImageIO.read(new File("images\\wood1.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Initializing timer 0
		timer = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (y2 == 400) {

					// Stopping other timers
					timer1.stop();
					timer2.stop();
					timer3.stop();

					repaint();
				}

			}
		});
		timer.start();

		// Initializing timer 1
		timer1 = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Stopping other timers
				timer.stop();
				timer2.stop();
				timer.stop();

				// Stick is rising
				y2--;
				repaint();

			}
		});

		// Initializing timer2
		timer2 = new Timer(20, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Stopping other timers
				timer.stop();
				timer1.stop();
				timer3.stop();

				// Stick is falling on the other wood
				++angle;
				l = ((400 - y2) * Math.PI * angle) / 180;
				if (angle <= 4) {
					x2 += (int) l;
					y2--;
				} else {
					x2 += (int) l;
					y2 += (int) l;
				}

				// If stick falls on other side's center it is perfect and you
				// get additional score
				if (y2 == 400) {
					if (x2 >= rectX2 + width2 / 2 - 3
							&& x2 <= rectX2 + width2 / 2 + 3) {
						counter++;
						isPerfect = true;
					} else {
						isPerfect = false;
					}
					timer2.stop();
					timer3.start();
				}

				repaint();

			}
		});

		// Initializing timer 3
		timer3 = new Timer(5, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Stopping other timers
				timer.stop();
				timer1.stop();
				timer2.stop();

				// Moving player
				rectX3++;

				// Player moves to the end of stick
				if (rectX3 == x2) {
					timer3.stop();

					// If player doesn't get on the other side starts timer 4
					if (rectX3 < rectX2 || rectX3 > (rectX2 + width2)) {
						timer4.start();

						// If player gets to the other side stick is
						// disappearing and timer 5 starts
					} else {
						rectX3 = rectX2 + width2 / 2 - 30;
						x1 = rectX2 + width2 - 30;
						x2 = x1;
						timer5.start();
					}

				}

			}
		});

		// Initializing timer 4
		timer4 = new Timer(5, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Player falls down
				rectY3++;

				// When player disappears from window dialog message shows
				if (rectY3 == 500) {
					timer4.stop();
					int choise = JOptionPane.showOptionDialog(
							null,
							String.format(
									"Your score is: %d\nDo You want to play again? ",
									counter), "", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, null, null);
					if (choise == JOptionPane.YES_OPTION) {
						restart();
					} else {
						System.exit(0);
					}
				}

			}
		});

		// Initializing timer 5
		timer5 = new Timer(5, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Woods and player move to the left and game and scene resets
				rectX1--;
				rectX2--;
				rectX3--;
				x1--;
				x2--;
				if (rectX2 == 10) {
					timer5.stop();
					counter++;
					reset();
				}

			}
		});

		// Adding mouse listener
		MouseListener mouse = new Mouse();
		addMouseListener(mouse);

	}

	/**
	 * Drawings on panel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (isPerfect) {
			g.drawImage(img, 0, 0, null);
			g.drawImage(img2, rectX1, rectY1, width1, height1, null, null);
			g.drawImage(img2, rectX2, rectY2, width2, height2, null, null);
			g.drawImage(img1, rectX3, rectY3, width3, height3, null, null);


			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(new Color(139, 90, 0));
			g2d.setStroke(new BasicStroke(5));
			g2d.drawLine(x1, y1, x2, y2);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("" + counter, 245, 30);
			g.drawString("Perfect!", 205, 80);
			repaint();
		} else {
			g.drawImage(img, 0, 0, null);
			g.drawImage(img2, rectX1, rectY1, width1, height1, null, null);
			g.drawImage(img2, rectX2, rectY2, width2, height2, null, null);
			g.drawImage(img1, rectX3, rectY3, width3, height3, null, null);

			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(new Color(139, 90, 0));
			g2d.setStroke(new BasicStroke(5));
			g2d.drawLine(x1, y1, x2, y2);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("" + counter, 245, 30);

			repaint();
		}

	}

	/**
	 * Used for reseting positions after one cycle of the game
	 */
	public void reset() {
		rectX1 = rectX2;
		rectY1 = rectY2;
		width1 = width2;
		height1 = height2;
		angle = 0;
		rectX2 = (int) (Math.random() * 240 + 150);
		width2 = (int) (Math.random() * 70 + 30);
		repaint();
		timer.start();
		isPerfect = false;

	}

	/**
	 * Used for restarting game
	 */
	public void restart() {

		x1 = 100;
		y1 = 400;
		x2 = 100;
		y2 = 400;

		isClicked = false;
		raise = true;

		angle = 0;
		l = 0;

		rectX1 = 20;
		rectY1 = 380;
		width1 = 100;
		height1 = 100;

		rectX2 = (int) (Math.random() * 240 + 150);
		rectY2 = 380;
		width2 = (int) (Math.random() * 70 + 30);
		height2 = 100;

		rectX3 = 50;
		rectY3 = 362;
		width3 = 30;
		height3 = 38;

		counter = 0;

		isPerfect = false;
		timer.start();
	}

	/**
	 * Class that represents MouseListener
	 *
	 */
	private class Mouse extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			((Component) e.getSource()).requestFocus();
			raise = false;
			isClicked = false;
			timer1.stop();
			if (!timer.isRunning() && !timer1.isRunning()) {
				timer2.start();

			}

		}

		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			((Component) e.getSource()).requestFocus();
			if (timer2.isRunning() || timer3.isRunning() || timer4.isRunning()
					|| timer5.isRunning()) {
				timer1.stop();
			} else {
				y2 -= 10;
				isClicked = true;
				timer.stop();

				timer1.start();
			}

		}
	}

}

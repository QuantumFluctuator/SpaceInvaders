package com.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	private final static int WIDTH = 1200;
	private final static int HEIGHT = 800;
	
	static boolean running = true;
	static double fps;
	
	static object[] objects = new object[1000];
	
	static class object {
		int id, x, y, height, width;
		Color colour;
		
		public void setID(int id) {
			this.id = id;
		}
		
		public int getID() {
			return id;
		}
		
		public void setX(int x) {
			this.x = x;
		}
		
		public int getX() {
			return x;
		}
		
		public void setY(int y) {
			this.y = y;
		}
		
		public int getY() {
			return y;
		}
		
		public int getWidth() {
			return width;
		}
		
		public void setWidth(int width) {
			this.width = width;
		}
		
		public int getHeight() {
			return height;
		}
		
		public void setHeight(int height) {
			this.height = height;
		}
		
		public Color getColour() {
			return colour;
		}
		
		public void setColour(Color colour) {
			this.colour = colour;
		}
	}
	
	static class player extends object {
		player() {
			width = 64;
			height = 16;
			x = (WIDTH/2)-(getWidth()/2);
			y = HEIGHT-(128-getHeight()/2);
			colour = Color.WHITE;
			id = 0;
		}
	}
	
	static class enemy extends object {
		enemy() {
			width = 32;
			height = 32;
		}
	}
	
	static class enemy1 extends enemy {
		enemy1() {
			super();
			colour = Color.GREEN;
		}
	}
	
	static class enemy2 extends enemy {
		enemy2() {
			super();
			colour = Color.BLUE;
		}
	}
	
	static class enemy3 extends enemy {
		enemy3() {
			super();
			colour = Color.CYAN;
		}
	}
	
	public static void main(String[] args) {
		JFrame window;
		JPanel panel;
		Graphics g;
		
		window = new JFrame("Evan's Gay Wank Shitty Space Invaders");
		panel = new JPanel();
		
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		window.setContentPane(panel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		g = panel.getGraphics();
		
		player p = new player();
		
		objects[0] = p;
				
		while (running) {
			long lastTime = System.nanoTime();
			
			update(g);
			
			System.out.println();
			
			fps = 1000000000.0 / (System.nanoTime() - lastTime);
            lastTime = System.nanoTime();
		}
		System.exit(0);
	}
	
	private static void update(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageGraphics = image.createGraphics();
		
		imageGraphics.setColor(Color.BLACK);
		imageGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		
		for (object o : objects) {
			if (o != null) {
				imageGraphics.setColor(o.getColour());
				imageGraphics.fillRect(o.getX(), o.getY(), o.getWidth(), o.getHeight());
			}
		}
		
		imageGraphics.setColor(Color.RED);
		imageGraphics.drawString("FPS: " + String.valueOf((int)fps), 16, 16);
		
		g2d.drawImage(image, 0, 0, null);
	}
}
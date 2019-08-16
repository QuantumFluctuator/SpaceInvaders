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
	private final static int OBJECTS = 100;
	private final static int PROJECTILES = 25;
	
	private static double baseSpeed = 0.15;
	
	private static boolean running = true;
	private static double fps;
	
	private static object[] objects = new object[OBJECTS];
	private static projectile[] projectiles = new projectile[PROJECTILES];
	private static boolean[] availability = new boolean[PROJECTILES];
	
	static class object {
		int id, height, width;
		double x, y, xsp, ysp;
		Color colour;
		
		public void setID(int id) {
			this.id = id;
		}
		
		public int getID() {
			return id;
		}
		
		public void setX(double x) {
			this.x = x;
		}
		
		public double getX() {
			return x;
		}
		
		public void setY(double y) {
			this.y = y;
		}
		
		public double getY() {
			return y;
		}
		
		public void setXsp(double xsp) {
			this.xsp = xsp;
		}
		
		public double getXsp() {
			return xsp;
		}
		
		public void setYsp(double ysp) {
			this.ysp = ysp;
		}
		
		public double getYsp() {
			return ysp;
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
			colour = Color.CYAN;
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
			colour = Color.GREEN;
		}
	}
	static class projectile extends object{}
	static class barrier extends object{
		barrier() {
			width = 64;
			height = 32;
			colour = Color.RED;
		}
	}
	
	public static void main(String[] args) {
		JFrame window;
		JPanel panel;
		Graphics g;
		
		window = new JFrame("Space Invaders");
		panel = new JPanel();
		
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		window.setContentPane(panel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		g = panel.getGraphics();
		
		setup();
				
		while (running) {
			long lastTime = System.nanoTime();
			
			updateGame();
			updateGraphics(g);
			
			System.out.println();
			
			fps = 1000000000.0 / (System.nanoTime() - lastTime);
            lastTime = System.nanoTime();
		}
		System.exit(0);
	}
	
	private static void updateGame() {
		for (object o : objects) {
			if (o != null) {
				o.setX(o.getX()+o.getXsp());
			}
		}
	}
	private static void updateGraphics(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageGraphics = image.createGraphics();
		
		imageGraphics.setColor(Color.BLACK);
		imageGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		
		for (object o : objects) {
			if (o != null) {
				imageGraphics.setColor(o.getColour());
				imageGraphics.fillRect((int)o.getX(), (int)o.getY(), o.getWidth(), o.getHeight());
			}
		}
		
		imageGraphics.setColor(Color.RED);
		imageGraphics.drawString("FPS: " + String.valueOf((int)fps), 16, 16);
		
		g2d.drawImage(image, 0, 0, null);
	}
	private static void setup() {
		for (int i = 0; i < PROJECTILES; i++) {
			availability[i] = true;
		}
		
		player p = new player();
		objects[0] = p;
		
		barrier b = new barrier();
		b.setX((WIDTH/5)-(b.getWidth()/2));
		b.setY(HEIGHT-200);
		objects[1] = b;
		
		b = new barrier();
		b.setX((2*WIDTH/5)-(b.getWidth()/2));
		b.setY(HEIGHT-200);
		objects[2] = b;
		
		b = new barrier();
		b.setX((3*WIDTH/5)-(b.getWidth()/2));
		b.setY(HEIGHT-200);
		objects[3] = b;
		
		b = new barrier();
		b.setX((4*WIDTH/5)-(b.getWidth()/2));
		b.setY(HEIGHT-200);
		objects[4] = b;
		
		int count = 5;
		
		for (int j = 1; j < 3; j++) {
			for (int i = 0; i < 12; i++) {
				enemy e = new enemy1();
				e.setX(((i+1)*WIDTH/13)-(e.getWidth()/2));
				e.setY((j+1)*64);
				e.setXsp(baseSpeed);
				count++;
				objects[count] = e;
			}
		}
		
		for (int j = 3; j < 5; j++) {
			for (int i = 0; i < 12; i++) {
				enemy e = new enemy2();
				e.setX(((i+1)*WIDTH/13)-(e.getWidth()/2));
				e.setY((j+1)*64);
				e.setXsp(baseSpeed);
				count++;
				objects[count] = e;
			}
		}
		
		for (int i = 0; i < 12; i++) {
			enemy e = new enemy3();
			e.setX(((i+1)*WIDTH/13)-(e.getWidth()/2));
			e.setY(64);
			e.setXsp(baseSpeed);
			count++;
			objects[count] = e;
		}
	}
	private static void createProjectile(boolean player, int x, int y) {
		int count = 0;
		for (boolean b : availability) {
			if (b) {
				projectiles[count] = new projectile();
			}
			count++;
		}
	}
}
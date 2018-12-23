package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import Algorithms.ShortestPathAlgo;
import Coords.Pixel;
import GIS.Fruit;
import GIS.Packman;
import Game.Game;
import Geom.Path;
import Geom.Point3D;
import Game.Map;


public class GameFrame extends JPanel implements MouseListener, ActionListener, ComponentListener{

	private static final long serialVersionUID = 1L;
	private Map map; 
	private BufferedImage myImage;
	public int Image_initial_Width; 
	public int Image_initial_Height;

	private final  String imagePackman = "C:\\Users\\owner\\Desktop\\forExe3\\Packman.png";
	private final String imageFruit = "C:\\Users\\owner\\Desktop\\forExe3\\Fruit.png";
	private final String imageMap;
	private Game game;
	private ArrayList<Path> pathes = new ArrayList<Path>();
	private int counter;

	private String status = "";

	private int x = -1;
	private int y = -1;
	private double ratioY;
	private double ratioX;

	private ShortestPathAlgo simulation;
	private int TIME = 0;
	int DELAY = 1000;
	Timer timer ;

	/**
	 * Create the frame.
	 */
	public GameFrame(Map map) {
		createGui();
		game = new Game();
		imageMap =  map.getFilePath();
		myImage = map.getMyImage();
		Image_initial_Width = myImage.getWidth();
		Image_initial_Height = myImage.getHeight();
		ratioY = 1;
		ratioX = 1;
		this.setSize(Image_initial_Width, Image_initial_Height);
	}


	// create the GUI
	private void createGui() {
		timer = new Timer(DELAY, this);
		addMouseListener(this); 
		addComponentListener(this);
	}



	//              Actions performed from the menu              //

	/**
	 * Saves the game to a CSV file
	 */
	public void save() {
		String name = JOptionPane.showInputDialog(this, "Save As:", null);
		this.game.Game2CSV(name);
	}



	/**
	 * Changes the status for the paint function
	 * Called by MyFrame 
	 */
	public void addPackman(){
		status = "DRAW_PACK";
	}


	/**
	 * Changes the status for the paint function
	 * Called by MyFrame 
	 */	
	public void addFruit(){
		status = "DRAW_FRUIT";
	}


	/**
	 * Changes the status for the paint function and call to 
	 * Called by MyFrame 
	 */	
	public void simulation() {
		if(game.getFruits().size() == 0) status = "EXCEPTION";
		else{
			status = "SIMULATION";
			simulation = new ShortestPathAlgo(this.game);
			pathes = simulation.getSolution();
			repaint();
		}
	}



	/**
	 * Invoke for the action listener in the when runItem button is pressed
	 * Called by MyFrame 
	 */
	public void runSimulation() {
		if(pathes.size() == 0) simulation();
		status = "RUN"; 
		for(Packman o : game.getPackmans()) {
			System.out.println("pacman " + o.getId() + " first location " + o.getLocation());
		}
	}

	/**
	 * Stop the timer
	 */
	public void Stop() {
		status = "STOP";
		timer.stop();
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		step();
		repaint();
	}


	//Move the packman and the fruits in RUN status
	private void step() {
		status = "RUN";
		TIME = TIME + DELAY;
		System.out.println(TIME/1000);
		for (Packman pac : game.getPackmans()) {
			Path path = pac.getPath();
			pac.setLocation(path.pointAt(TIME/1000));
		}
		for(int i = 0; i < game.getFruits().size(); i++){
			if(game.getFruits().get(i).getTime() < (TIME/1000)) {
				game.getFruits().remove(i);
			}
		}
	}


	/**
	 * This function active after the user click on one of the Draw Item
	 * and then you click on the board
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (status.equals("DRAW_FRUIT")) { 
			x = arg0.getX();
			y = arg0.getY();
			counter++;
			Fruit newFruit = new Fruit(new Pixel(x,y), counter);
			game.getFruits().add(newFruit);
			repaint();
		}
		else if(status.equals("DRAW_PACK")) {	
			x = arg0.getX();
			y = arg0.getY();
			counter++;
			Packman newPackman = new Packman(new Pixel(x,y),counter); 
			game.getPackmans().add(newPackman);
			repaint();
		}
	}


	/**
	 * Panel painting function
	 * Refers to the given status according to which the board is painted
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			myImage = ImageIO.read(new File (imageMap));
			g.drawImage(myImage, 0,0,getWidth(),getHeight(),this);
		}catch (IOException e) {
			e.printStackTrace();
		}
		//exception
		if(status.equals("EXCEPTION")) {
			String ex = "EXCEPTION";
			g.setColor(Color.red);
			g.drawString(ex ,40 ,40);
		}
		//simulation
		if(status.equals("SIMULATION")) {
			Iterator<Path> p = pathes.iterator();
			while(p.hasNext()) {
				Path line = p.next();
				Point3D prevLocation = line.getPackman().getLocation();
				ArrayList<Fruit> way = line.getPath();
				Iterator<Fruit> f = way.iterator();
				while(f.hasNext()) {
					Fruit nextFruit = f.next();
					Point3D nextLocation = nextFruit.getLocation();
					g.setColor(Color.red);
					int xPrev = (int)(prevLocation.x()*ratioX);
					int yPrev = (int)( prevLocation.y()*ratioY);
					int xNext = (int)(nextLocation.x()*ratioX);
					int yNext = (int)(nextLocation.y()*ratioY);
					g.drawLine(xPrev, yPrev, xNext, yNext);
					prevLocation = nextLocation;
				}
			}
			String time = "The longest time path: " + simulation.getTime();
			g.drawString(time, 40, 40);
		}
		//run
		if(status.equals("RUN")) {
			timer.start();
		}
		//stop the run
		if(status.equals("STOP")) {
			timer.stop();
		}

		//paint the component
		for (Packman p : game.getPackmans()) {
			if(Map.is_Valid_Point(p.getLocation())) {
				x = (int)p.getLocation().x();
				y = (int)p.getLocation().y();
				try {
					myImage = ImageIO.read(new File (imagePackman));
					g.drawImage(myImage,(int)(x*ratioX) ,(int)(y*ratioY) ,50 , 50,this);
				}catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
		for (Fruit f : game.getFruits()) {
			if( Map.is_Valid_Point(f.getLocation())) {
				x = (int)f.getLocation().x();
				y = (int)f.getLocation().y();
				try {
					myImage = ImageIO.read(new File (imageFruit));
					g.drawImage(myImage, (int)(x*ratioX) ,(int)(y*ratioY) ,35 , 35 ,this);
				}catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
	}




	public void setGame(Game gm){
		simulation = new ShortestPathAlgo(this.game);
		game.Game2KML(simulation);
		game = gm;
	}

	/**
	 * Adjusts the Packmas and fruits in the screen according to its size
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		ratioX = (double)this.getWidth()/(double)Image_initial_Width;
		ratioY = (double)this.getHeight()/(double)Image_initial_Height;
		repaint();
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}


}

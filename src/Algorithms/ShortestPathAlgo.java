package Algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

import Coords.Pixel;
import GIS.Fruit;
import GIS.GIS_element;
import GIS.Packman;
import GIS.Packman_comperator;
import Geom.Path;
import Geom.Point3D;
import Game.Game;
import Game.Map;

/**
 * The central algorithmic class, extends Thread Class
 * Constructor receives a Game object and calculates the optimal path (the shortest) 
 * so that all the fruits are "eaten" as quickly as possible.
 * The algorithm updates the "fruit tracks" for each of the pacmans.
 * Game object should be in sync with the GUI, therefore run() method has a synchronized block. 
 * @author Chen
 *
 */
public class ShortestPathAlgo extends Thread{
	private Thread t;
	private Game game;
 
	/**
	 * Constructor
	 * @param currentGame Game
	 */
	public ShortestPathAlgo(Game currentGame) {
		game = currentGame;
	}

	/*
	 * Run method to execute the algorithm 
	 * As long as there are fruits that are not yet eaten
	 * A greedy algorithm: look for the closest pacman for each fruit
	 */
	public void run() {
		while(!game.getFruits().isEmpty()) { 
			// GameFrame and this class share access for the fruits and pacmans lists in Game object,
			// therefore, one class should not update a list while another class is working on it.
			synchronized(game) {
				for (Fruit fru : game.getFruits()) {
					int closerPacID = 0;
					int minTime = Integer.MAX_VALUE;
					int time = 0;
					for(Packman pac : game.getPackmans()) {
						time = calculateTime(pac, fru); 
						if(time < minTime) { 
							minTime = time; 
							fru.setTime(time);
							closerPacID = pac.getId();
						}
					}
					pacmanByID(closerPacID, game.getPackmans()).getPath().add(fru , time);
				}
			} // end synchronized section
		}
	}


	/**
	 * A static function
	 * calculates the time between the Packman and the Fruit on the input
	 * @param pac Packman
	 * @param fru Fruit
	 * @return time int
	 */
	public static int calculateTime(Packman pac, Fruit fru) {
		double way = pac.getLocation().distance3D(fru.getLocation()); 
		double radius = pac.getRadiusEat().get_radius();
		double speed = pac.getSpeed(); 
		int time =(int)((way-radius)/speed);
		return time;		
	}


	/*
	 * Finds the Packman in the list according to its id number
	 */
	private Packman pacmanByID(int id, ArrayList<Packman> demo) {
		for(Packman temp : demo) {
			if(temp.getId() == id) return temp;
		}
		return null;
	}


	public void getSolution(){
		this.start();
	}


}

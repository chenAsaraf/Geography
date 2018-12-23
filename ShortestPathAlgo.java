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
 * A class that receives a Game object and calculates the optimal path(the shortest) 
 * so that all the fruits are "eaten" as quickly as possible.
 * This is the central algorithmic class
 * includes the calculation of "fruit tracks" for each of the packmans.
 * @author Inna and Chen
 *
 */
public class ShortestPathAlgo {

	private ArrayList<Fruit> fruits;
	private ArrayList<Packman> pacmans;
	private ArrayList<Path> solution;
	private int timeForAll = 0;
	private final Packman_comperator comp = new Packman_comperator();


	/**
	 * Constructor
	 * Initializes the fields and executes the calculation
	 * @param game
	 */
	public ShortestPathAlgo(Game game) {
		this.fruits = game.getFruits();
		this.pacmans = game.getPackmans();
		this.solution = new ArrayList<>(pacmans.size());
		execute();
	}






	/*
	 * Private void function to execute the algorithm
	 * A greedy algorithm
	 * search for each Fruit the closest Packman and add adds it to the list of the Packman's path
	 */
	private void execute() {

		ArrayList<Packman> packmanDemo = new ArrayList<Packman>(pacmans.size());
		for(Packman p : pacmans) {
			Packman d = new Packman(p);
			packmanDemo.add(d);
		}


		pacmans.sort(comp); //sort by speed from bigger to smaller

		Iterator<Fruit> f = fruits.iterator();
		while(f.hasNext()) { 
			Fruit fru = f.next();
			Iterator<Packman> p = packmanDemo.iterator();
			int closerPacID = 0;
			int minTime = Integer.MAX_VALUE;
			int time = 0;
			while (p.hasNext()) { 
				Packman pac = p.next();
				time = calculateTime(pac, fru); 
				if(time < minTime) { 
					minTime = time; 
					fru.setTime(time);
					closerPacID = pac.getId();
				}
			}
			pacmanByID(closerPacID, pacmans).getPath().add(fru , time);
			pacmanByID(closerPacID, packmanDemo).setLocation(fru.getLocation());
			timeForAll += time;
		}
		for(Packman temp : pacmans) {
			solution.add(temp.getPath());
		}
		System.out.println(solution.toString());
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
	 * Finds the Fruit in the list according to its id number
	 */
	private Fruit fruitByID(int id,  ArrayList<Fruit> demo) {
		for(Fruit temp : demo) {
			if(temp.getId() == id) return temp;
		}
		return null;
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




	/*
	 * A private function to calculate the time until all fruits are eaten
	 * according to the result of the algorithm
	 */
	private void setTime() {
		int maxTime = 0; 
		for(Path p : solution) {
			if(maxTime < p.getTime()) maxTime = p.getTime();
		}
		timeForAll = maxTime;
	}




	public ArrayList<Path> getSolution(){
		return solution;
	}



	public int getTime() {
		setTime();
		return timeForAll;
	}



}
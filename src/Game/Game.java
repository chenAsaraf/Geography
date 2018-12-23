package Game;

import java.util.ArrayList;

import Algorithms.ShortestPathAlgo;
import File_format.CSV_Reader;
import File_format.CSV_Writer;
import File_format.Path2Kml;
import GIS.Fruit;
import GIS.GIS_element;
import GIS.Packman;

/**
 * 
 * @author Inna and Chen
 *
 */
public class Game {
	private ArrayList<Packman> packmans;
	private ArrayList<Fruit> fruits;
	private String fileName ="";

	/**
	 * Constructor
	 */
	public Game() {
		packmans = new ArrayList<>();
		fruits = new ArrayList<>();
	}

	/**
	 * Constructor
	 * the class has the ability to build from a CSV file
	 * @param path
	 */
	public Game(String path) {
		CSV_Reader cr = new CSV_Reader();
		ArrayList<GIS_element> theElements = cr.readCsvFilePackmans(path);
		packmans = new ArrayList<>();
		fruits = new ArrayList<>();
		for(GIS_element element: theElements) {
			if(element instanceof Fruit) fruits.add((Fruit) element);
			else if(element instanceof Packman) packmans.add((Packman)element);
		}
		System.out.println("csv read successfully");
	}


	/**
	 * The class has the ability to save its information to a KML file
	 */
	public void Game2KML(ShortestPathAlgo simulation) {
		Path2Kml pk = new Path2Kml(simulation, packmans, fruits);
	}

	
	/**
	 * The class has the ability to save its information to a csv file
	 */
	public void Game2CSV(String name) {
		CSV_Writer cw = new CSV_Writer(name, packmans, fruits);
	}


	public ArrayList<Packman> getPackmans(){
		return packmans;
	}
	

	public ArrayList<Fruit> getFruits() {
		return fruits;
	}


}

package File_format;

import java.util.ArrayList;

import Algorithms.ShortestPathAlgo;
import GIS.Fruit;
import GIS.Packman;
import GIS.GameLayer;

public class Path2Kml {

	private GameLayer p_layer = new GameLayer();


	public Path2Kml(ShortestPathAlgo simulation, ArrayList<Packman> packmans,ArrayList<Fruit> fruits){
		for (int i = 0; i < packmans.size(); i++) {
			p_layer.add(packmans.get(i));
		}
		for (int i = 0; i < fruits.size(); i++) {
			p_layer.add(fruits.get(i));
		}
		
		KML_Writer kw = new KML_Writer("C:\\Users\\owner\\Desktop\\forExe3", p_layer, simulation);
		System.out.println("kml created successfully");
	}
}

package Geom;

import java.util.ArrayList;
import java.util.Iterator;
import GIS.Fruit;
import GIS.Packman;
import Geom.Point3D;

/**
 * A route made up of a GPS point collection
 * with methods to find its length as well as additional geographic information
 * @author Chen
 */
public class Path {
	private ArrayList<Fruit> path;
	private Packman packman;
	private int time; 



	/**
	 * Constructor
	 * initialize for each Packman it's own Path
	 * @param p
	 */
	public Path(Packman p) {
		this.packman = p;
		time = 0;
		path = new ArrayList<Fruit>();
	}


	/**
	 * Add the fruit if it does not exist
	 * updating the time of the fruit itself
	 * updating the time of the path
	 * @param fruit
	 * @param fruitTime
	 * @return
	 */
	public boolean add(Fruit fruit, int fruitTime) {
		if(path.contains(fruit))
			return false;
		else{ 
			path.add(fruit);
			this.time += fruitTime; 
			fruit.setTime(this.time);
			return true;
		}
	}


	
	/**
	 * This method returns the point that this Packman was at that time
	 * @param TIME seconds 
	 * @return
	 */
	public Point3D pointAt(int TIME) {
		Point3D pointAt = packman.getLocation();
		if(path.size() == 0) return pointAt;


		Iterator<Fruit> f = path.iterator();
		Fruit fru = f.next();
		if(fru.getTime() == TIME) return fru.getLocation();
		else if(fru.getTime() > TIME) {
			pointAt = position(packman.getLocation() , fru.getLocation());
			return pointAt;
		}
		else if(fru.getTime() < TIME) {
			Iterator<Fruit> p = path.iterator();
			Fruit prev = p.next();
			while(f.hasNext()) {
				fru = f.next();
				if(fru.getTime() == TIME) return fru.getLocation();
				else if(fru.getTime() > TIME) {
					pointAt = position(prev.getLocation() , fru.getLocation());
					return pointAt;
				}
				prev = p.next();
			}
		}
		return pointAt;
	}



	/*
	 * private function
	 * Finds the position of the Packman according to the rate of progress and direction 
	 */
	private Point3D position(Point3D prev ,Point3D next) {
		Point3D next_position; 
		double deltaX =  next.x() - prev.x();
		double deltaY =  next.y() - prev.y();
		double direction = Math.atan2(deltaY, deltaX);
		int speed = packman.getSpeed();
		double x =  prev.x() + (speed * Math.cos(direction));	
		double y =  prev.y() + (speed * Math.sin(direction));
		next_position = new Point3D(x,y,0);
		return next_position;
	}


	public ArrayList<Fruit> getPath() {
		return path;
	}
	
	public Fruit get(int index){
		return path.get(index);
	}
	
	public void remove(int index) {
		path.remove(index);
	}

	public void setPath(ArrayList<Fruit> path) {
		this.path = path;
	}

	public int getTime() {
		return time;
	}

	public Packman getPackman() {
		return packman;
	}

	public int size() {
		return path.size();
	}


	public String toString() {
		String s = "";
		for(Fruit f : path) {
			s += "Packman id: " + packman.getId() +" " + f.toString() + " \n ";
		}
		s += " timeForAll: " +time + " \n" ;
		return s;
	}



}
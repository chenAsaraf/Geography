package GIS;


import Game.Map;
import Geom.Circle;
import Geom.Geom_element;
import Geom.Path;
import Geom.Point3D;

/**
 * A class that represents a "robot" with a location,
 * orientation and ability to move (at a defined man speed)
 * Each packman has a speed of movement (in meters per second)
 * and an eating radius (in meters) that defines the minimum proximity
 * of packman to the fruit to eat it.
 * @author Inna and Chen
 */
public class Packman implements GIS_element{
	private static final String COMMA_DELIMITER = ",";

	public int time;	
	private Path path; 
	private int id;
	private Point3D location; 
	private int speed;
	private Circle radiusEat;
	private Point3D pointGoogle;




	/**
	 * Constructor 
	 * for mode where the user builds the game
	 * @param firstLocation
	 */
	public Packman(Point3D firstLocation, int id){
		this.id = id;
		location = firstLocation;
		speed = 1;
		radiusEat = new Circle(location, 1);
		path = new Path(this); 
	}

	/**
	 * Constructor copy
	 * @param demo
	 */
	public Packman(Packman demo){
		id = demo.getId();
		location = new Point3D(demo.getLocation());
		speed = demo.getSpeed();
		radiusEat = new Circle(demo.getRadiusEat());
		path = new Path(this); 
	}


	/**
	 * Constructor
	 * for mode where the user loads the game from a CSV File
	 * @param row
	 */
	public Packman(String row){
		int CSV_ID = 1;
		int CSV_LON = 2;
		int CSV_LAT = 3;
		int CSV_ALT = 4;
		int CSV_SPEED = 5;
		int CSV_RADIUS = 6;

		String [] tokens = row.split(COMMA_DELIMITER);	
		if (tokens.length > 0) {
			id = Integer.parseInt(tokens[CSV_ID]);
			String packmanPoint = tokens[CSV_LAT] + "," + tokens[CSV_LON] + "," + tokens[CSV_ALT];
			String pointToGoogle = tokens[CSV_LON] + "," + tokens[CSV_LAT] + "," + tokens[CSV_ALT];
			pointGoogle = new Point3D(pointToGoogle);
			Point3D polar = new Point3D(packmanPoint);
			location = Map.convertToPixel(polar);
			speed = Integer.parseInt(tokens[CSV_SPEED]);
			double radius =  Double.parseDouble(tokens[CSV_RADIUS]);
			radiusEat = (new Circle(getLocation(), radius));
			path =  new Path(this);
		}
	}


	/**
	 * A function that returns a row for the CSV file
	 * @return
	 */
	public String output(){
		Point3D p = Map.convertToPolar(this.getLocation());
		String ans = "";
		ans = "P," + this.getId() + "," + p.y() + "," + p.x() + "," + p.z() + "," + this.getSpeed()
		+ "," + this.getRadiusEat().get_radius();
		return ans;
	}

	
	public Point3D getGoogle(){
		return pointGoogle;
	}


	public Point3D getPoint() {
		return location;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Point3D getLocation() {
		return location;
	}

	public void setLocation(Point3D location) {
		this.location = location;
	}

	public void setLocation(double x, double y, double z) {
		Point3D newLocation= new Point3D(x,y,z);
		this.location = newLocation;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Circle getRadiusEat() {
		return radiusEat;
	}

	public void setRadiusEat(Circle radiusEat) {
		this.radiusEat = radiusEat;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}




	@Override
	public Geom_element getGeom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meta_data getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void translate(Point3D vec) {
		// TODO Auto-generated method stub

	}

	public String toString() { 
		return "P id: " + id + " location: " + location.toString();
	}



}
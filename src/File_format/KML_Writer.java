package File_format;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import Algorithms.ShortestPathAlgo;
import Geom.GeomElement;
import Geom.Point3D;
import GIS.GISlayer;
import GIS.Packman;
import GIS.GameLayer;
import Game.Map;
import GIS.Fruit;
import GIS.GIS_element;


/**
 * This Class writes a KML file from the CSV file it gets
 * @author Inna and Chen
 *
 */
public class KML_Writer {

	private ShortestPathAlgo shortPath;
	private GISlayer layer = new GISlayer();
	private GameLayer Player = new GameLayer();
	private String path;
	private int time;
	private Collection<GIS_element> points = new HashSet<>();
	
	
	/**
	 * Constructor 
	 * @param path
	 * @param toCSV
	 */
	public KML_Writer(String path, GISlayer toCSV){
		this.layer = toCSV;
		this.path = path;
		for (GIS_element element : layer) {
			points.add(element);
		}
		try {
			writeKml();
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 
	 * try to read from the gis_layer file and turns it into kml with timestamp
	 * @param fileName
	 * @throws ParseException 
	 */
	public void writeKml() throws ParseException{
		PrintWriter writer;
		try {
			writer = new PrintWriter(new File(path + "/kml.kml"));

			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.println("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
			writer.println("<Document>");
			writer.println("<Folder>");
			writer.println("<name/>");
			Iterator<GIS_element> i= layer.iterator();
			GIS_element g = i.next();
			GeomElement geom = (GeomElement) g.getGeom();
			for (GIS_element ge : points) {
				writer.println("<Placemark>");
				writer.println("<name/>");
				Date date = new Date(ge.getData().getUTC());
				geom = (GeomElement) ge.getGeom();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				writer.println("<description><![CDATA[Timestamp: <b>" + ge.getData().getUTC() + "</b><br/>Date: <b>" + df.format(date) + "</b>]]></description>");

				//creates timestamp according to GoogleEarth
				String time = df.format(date);
				time = time.replace(' ','T');
				time = time+'Z';
				writer.println("<TimeStamp>" + time + "</TimeStamp>");

				writer.println("<Point>");
				writer.println("<coordinates>" + geom.getPoint3D().y() + "," + geom.getPoint3D().x() + "," + geom.getPoint3D().z() + "</coordinates>");
				writer.println("</Point>");
				writer.println("</Placemark>");
			}
			writer.println("</Folder>");
			writer.println("</Document>");
			writer.println("</kml>");
			writer.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor 
	 * @param path
	 * @param toCSV
	 */
	public KML_Writer(String path, GameLayer pCSV, ShortestPathAlgo simulation){
		this.Player = pCSV;
		this.path = path;
		this.shortPath = simulation;
		for (GIS_element element : Player) {
			points.add(element);
		}

		try {
			writeKmlPac();
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * try to read from the gis_layer file of the packmasn and turns it into kml with timestamp
	 * @param fileName
	 * @throws ParseException 
	 */
	public void writeKmlPac() throws ParseException{
		PrintWriter writer;
		try {
			writer = new PrintWriter(new File(path + "/kml.kml"));

			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.print("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
			writer.print("<Document>");
			writer.print("<Style id='pacman'>");
			writer.print("<IconStyle><Icon><href>C:/Users/InnaPC/Documents/OOP_3/Ex3/Packman.png</href></Icon></IconStyle></Style>");
			writer.print("<Style id='fruit'>");
			writer.print("<IconStyle><Icon><href>C:/Users/InnaPC/Documents/OOP_3/Ex3/Fruit.png</href></Icon></IconStyle></Style>");
			writer.println("<Folder>");
			writer.println("<name> Pacman Game </name>");
			
			Iterator<GIS_element> i= Player.iterator();
			GIS_element check;
			Packman pa ;
			Fruit fr ;
			time = 0;

			long firstTimeStamp = 0;
			long lastTimeStamp = 0;
			int totalTime = this.shortPath.getTime();

			while(i.hasNext()){
				check =  i.next();
				if(check instanceof Packman){
					pa = (Packman) check;
					writer.println("<Placemark>");
					writer.println("<styleUrl>#pacman</styleUrl>");

					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					int second = 1;
					if (totalTime > second) {
						Date date = new Date(firstTimeStamp + (second-1)*1000);
						Date date1 = new Date(lastTimeStamp + (second)*10000);
						String timeS = df.format(date);
						writer.println("<description><![CDATA[Timestamp: <b>" + df.format(date) + "</b></b>]]></description>");
						timeS = timeS.replace(' ','T');
						timeS = timeS+'Z';
						time = (int) (lastTimeStamp + (second)*10000);
						writer.println("<TimeSpan>\n" + "<begin>" + timeS + "</begin>");
						timeS = df.format(date1);
						timeS = timeS.replace(' ','T');
						timeS = timeS+'Z';
						writer.println("<end>" + timeS + "</end>");
						writer.println("</TimeSpan>");
						second++;
					}

					Point3D pointP = pa.getPath().pointAt( time);
					pointP = Map.convertToPolar(pointP);
					writer.println("<Point>");
					writer.println("<coordinates>" + pointP.x() + "," + pointP.y() + "," + pointP.z() + "</coordinates>");
					writer.println("</Point>");
					writer.println("</Placemark>");
				}
				else if(check instanceof Fruit){
					fr = (Fruit) check;
					writer.println("<Placemark>");
					writer.println("<styleUrl>#fruit</styleUrl>");
					Date date = new Date((long) fr.getTime());
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					writer.println("<description><![CDATA[Timestamp: <b>" + df.format(date) + "</b><br/>]]></description>");

					//creates timestamp according to GoogleEarth
					String time = df.format(date);
					time = time.replace(' ','T');
					time = time+'Z';
					writer.println("<TimeStamp>" + time + "</TimeStamp>");

					writer.println("<Point>");
					writer.println("<coordinates>" + fr.getGoogle().y() + "," + fr.getGoogle().x() + "," + fr.getGoogle().z() + "</coordinates>");
					writer.println("</Point>");
					writer.println("</Placemark>");
				}
			}
			writer.println("</Folder>");
			writer.println("</Document>");
			writer.println("</kml>");
			writer.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
package File_format;

import java.util.Scanner;

import GIS.GISlayer;

/**
 * this class sends a csv file in the url we ask for, to read and then sends it to create a kml for it
 * @author Inna and Chen
 *
 */
public class Csv2Kml {

	private GISlayer g_layer = new GISlayer();
	private String path = "";
	private String fileName ="";

	public Csv2Kml(){
		CSV_Reader cr = new CSV_Reader();
		Scanner reader = new Scanner(System.in);
		System.out.println("please enter the path of your csv file");
		path = reader.nextLine();
		System.out.println("please enter the name of your csv file");
		fileName = reader.nextLine();
		cr.readCsvFile(g_layer, (path + "/" + fileName));
		System.out.println("csv read successfully");
		KML_Writer kw = new KML_Writer(path, g_layer);
		System.out.println("kml created successfully");
	}
}
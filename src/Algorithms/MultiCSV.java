package Algorithms;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import File_format.CSV_Reader;
import File_format.KML_Writer;
import GIS.GISlayer;
import GIS.GISproject;

/**
 * this class reads form the folder all the csv files and sends them to GIS element then to GIS layer then to GIS project 
 * the difference between this class from Csv2Kml is that here we make a merged kml to all the csv files
 * unlike Csv2Kml that makes a kml to each csv file sepparatly 
 * @author Inna and Chen
 *
 */
public class MultiCSV {

	private GISproject g_project = new GISproject();

	private List<Path> paths = new ArrayList<>();

	private GISlayer g_layer = new GISlayer();
	private String path = "";
	private String fileName ="";

	public MultiCSV(){
		CSV_Reader cr = new CSV_Reader();
		Scanner reader = new Scanner(System.in);
		System.out.println("please enter the path of your csv file");
		path = reader.nextLine();
		readFolder(path);
		while(!paths.isEmpty()){
			fileName = ""+paths.get(0);
			cr.readCsvFile(g_layer, (path + "/" + fileName.substring(path.length()+1)));
			System.out.println("csv read successfully");
			g_project.add(g_layer);
			paths.remove(0);
		} 
		KML_Writer kw = new KML_Writer(path, g_layer);
		System.out.println("kml created successfully");
	}

	/**
	 * reads only the csv files the folder contains and saves its' paths in a parameter
	 * @param directory 
	 */
	public void readFolder(String directory){
		File folder=new File(directory);
		File[] files = folder.listFiles();
		for(int i=0; i<files.length; i++){
			if(files[i].isDirectory())
				readFolder(files[i].getPath());
			else{
				directory = files[i].getPath();
				if(directory.contains(".csv"))	{
					paths.add(Paths.get(directory));
				}
				continue;
			}
		}
	}
}
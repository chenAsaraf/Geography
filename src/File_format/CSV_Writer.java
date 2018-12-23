package File_format;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import GIS.Fruit;
import GIS.GIS_element;
import GIS.Packman;
import GIS.GameLayer;


/**
 * This Class writes a new CSV file  from a
 * @author Inna and Chen
 *
 */
public class CSV_Writer {
	private GameLayer Game = new GameLayer();


	public CSV_Writer(String name, ArrayList<Packman> packmans, ArrayList<Fruit> fruits) {
		for (int i = 0; i < packmans.size(); i++) {
			Game.add(packmans.get(i));
		}
		for (int i = 0; i < fruits.size(); i++) {
			Game.add(fruits.get(i));
		}
		try {
			write("C:\\Desktop\\" + name + ".csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * writes a new csv file
	 * @param filename
	 * @param pCSV
	 * @throws IOException
	 */
	public void write(String filename) throws IOException{ 
		Iterator<GIS_element> i= Game.iterator();
		GIS_element check;
		Packman pa ;
		Fruit fr ;
		FileWriter writer = new FileWriter(filename);
		//write header line
		writer.write("Type,Id,Lat,Lon,Alt,Speed/Weight,Radius");
		writer.write("\n");

		while(i.hasNext()){
			check =  i.next();
			if(check instanceof Packman){
				pa = (Packman) check;
				writer.write(pa.output());
				writer.write("\n");
			}
			else if(check instanceof Fruit){
				fr = (Fruit) check;
				writer.write(fr.output());
				writer.write("\n");
			}
		}

		writer.close();
		System.out.println("new csv was created on C:\\Desktop");
	}

}

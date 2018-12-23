package File_format;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;


class Path2KmlTest {

	@Test
	public void test() {
		Scanner reader = new Scanner(System.in);
		System.out.println("please enter the path of the kml folder you want to check");//C:\Users\InnaPC\Documents\OOP_3\Ex3\csv_files
		String input_path = reader.nextLine();
		reader.close();
		Path files = Paths.get(input_path);
		List<String> fileNameArray = new ArrayList<String>();
		try {
			Files.newDirectoryStream(files,"*.kml").forEach(s -> fileNameArray.add(s.toString()));	
		}
		catch(IOException e) {
			e.printStackTrace();	
		}
		assertTrue((fileNameArray.toString().contains(".kml")));
	}

}

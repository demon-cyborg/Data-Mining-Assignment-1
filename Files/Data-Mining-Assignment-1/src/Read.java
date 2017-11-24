import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Read {

	public static void main(String[] args) {

		try {
			// Reads the data from the file specified
			BufferedReader fileIn = new BufferedReader(new FileReader("adult.test.csv"));

			String line;
			// Array List will store the data read from the file
			ArrayList<String> importData = new ArrayList<String>();
			int importRowNum = 0;
			int importColNum = 0;

			// While loop to add each line read to the arraylist & get the number of rows
			// and columns in the file
			while ((line = fileIn.readLine()) != null) {
				if (!line.equals("")) {
					importData.add(line);

					String[] rowSplit1 = line.split(",");
					importColNum = rowSplit1.length;
					importRowNum++;
				}
			}

			// Array List to store rows that need to be removed
			ArrayList<String> removeList = new ArrayList<String>();

			// For loop to go through rows with empty fields and add them to the removeList
			for (int i = 0; i < importRowNum; i++) {
				String[] rowSplit = importData.get(i).split(",");
				for (int j = 0; j < importColNum; j++) {
					// System.out.println(rowSplit[1]);
					if (rowSplit[j].contains("?")) {
						removeList.add(importData.get(i));
					}
				}
			}
			// Remove all the rows marked for removal in the removeList
			importData.removeAll(removeList);

			// System.out.println(importData.get(8));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

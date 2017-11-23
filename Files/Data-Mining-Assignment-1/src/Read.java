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

			BufferedReader fileIn = new BufferedReader(new FileReader("adult.test.csv"));

			String line;
			String[][] data = new String[16282][15];
			int row = 0;

			while ((line = fileIn.readLine()) != null) {
				if (!line.equals("")) {
					String[] rowSplit = line.split(",");

					for (int col = 0; col < 15; col++) {
						data[row][col] = rowSplit[col];
						// System.out.println(cols[i]);
					}
					row++;
				}
			}

			System.out.println(data[3][3]);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

import java.io.*;
import java.util.ArrayList;

public class ProcessData {

	public static ArrayList readFile(BufferedReader fileIn) {
		String line;
		// Array List will store the data read from the file
		ArrayList<String> importData = new ArrayList<String>();
		int importRowNum = 0;
		int importColNum = 0;

		// While loop to add each line read to the arraylist & get the number of rows
		// and columns in the file

		try {
			while ((line = fileIn.readLine()) != null) {
				if (!line.equals("")) {
					importData.add(line);

					String[] rowSplit1 = line.split(",");
					importColNum = rowSplit1.length;
					importRowNum++;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return importData;
	}

	public static void writeFile(ArrayList importData, String fileName) {
		int genderNum;
		int earnsNum;

		BufferedWriter br;
		try {
			br = new BufferedWriter(new FileWriter(fileName));

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < importData.size(); i++) {

				String[] columnSplit = ((String) importData.get(i)).split(",");

				// System.out.println(rowSplit[6]);
				// if(!educationList.contains(rowSplit[4])) {
				// educationList.add(columnSplit[4]);
				if (i == 0) {
					sb.append(columnSplit[4] + ",");
					sb.append(columnSplit[9] + ",");
					if (columnSplit.length == 16) {
						sb.append(columnSplit[14] + ",");
						sb.append(columnSplit[15] + "\n");
					} else {
						sb.append(columnSplit[14] + "\n");
					}
				} else {

					if (columnSplit[9].contains("Male")) {
						genderNum = 0;
					} else {
						genderNum = 1;
					}

					if (columnSplit[14].contains("<=50K")) {
						earnsNum = 0;
					} else {
						earnsNum = 1;
					}
					// }
					// String edu = columnSplit[4].replaceAll("\\.0", "");
					sb.append(columnSplit[4].replaceAll("\\.0", "") + ",");
					sb.append(genderNum + ",");
					if (columnSplit.length == 16) {
						sb.append(earnsNum + ",");
						sb.append(columnSplit[15] + "\n");
					} else {
						sb.append(earnsNum + "\n");
					}
				}

			}
			// sb.append(educationList+"\n");
			br.write(sb.toString());
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		try {
			// Reads the data from the file specified

			/*
			 * // Array List to store rows that need to be removed ArrayList<String>
			 * removeList = new ArrayList<String>();
			 * 
			 * // For loop to go through rows with empty fields and add them to the
			 * removeList for (int i = 0; i < importRowNum; i++) { String[] rowSplit =
			 * importData.get(i).split(","); for (int j = 0; j < importColNum; j++) { //
			 * System.out.println(rowSplit[1]); if (rowSplit[j].contains("?")) {
			 * removeList.add(importData.get(i)); } } } // Remove all the rows marked for
			 * removal in the removeList importData.removeAll(removeList);
			 */

			/*
			 * for(int i=0;i<importData.size();i++) { System.out.println(importData.get(i));
			 * }
			 */

			// ArrayList<String> educationList = new ArrayList<String>();
			// ArrayList<Integer> genderList = new ArrayList<Integer>();
			// ArrayList<Integer> earnsList = new ArrayList<Integer>();
			// int educationNum;

			// System.out.println(readFile(testFileIn));

			BufferedReader trainFileIn = new BufferedReader(new FileReader("adult.train.5fold.csv"));
			writeFile(readFile(trainFileIn), "train.csv");

			BufferedReader testFileIn = new BufferedReader(new FileReader("adult.test.csv"));
			writeFile(readFile(testFileIn), "test.csv");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

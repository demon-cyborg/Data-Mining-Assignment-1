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
		int workClass;
		int capitalGain;
		int capitalLoss;

		BufferedWriter br;
		try {
			br = new BufferedWriter(new FileWriter(fileName));

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < importData.size(); i++) {

				String[] columnSplit = ((String) importData.get(i)).split(",");

				// Writes column headers to file
				if (i == 0) {
					sb.append(columnSplit[4] + ",");
					sb.append(columnSplit[9] + ",");
					sb.append(columnSplit[14] + ",");
					sb.append(columnSplit[1] + ",");
					sb.append(columnSplit[10] + ",");
					if (columnSplit.length == 16) {
						sb.append(columnSplit[11] + ",");
						sb.append(columnSplit[15] + "\n");
					} else {
						sb.append(columnSplit[11] + "\n");
					}
					// Writes encoded attribute value to file
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

					if (columnSplit[1].contains("Private") || columnSplit[1].contains("Self-emp-not-inc")) {
						workClass = 0;
					} else {
						workClass = 1;
					}

					if (columnSplit[10].startsWith("0")) {
						capitalGain = 0;
					} else {
						capitalGain = 1;
					}

					if (columnSplit[11].startsWith("0")) {
						capitalLoss = 1;
					} else {
						capitalLoss = 0;
					}

					sb.append(columnSplit[4].replaceAll("\\.0", "") + ",");
					sb.append(genderNum + ",");
					sb.append(earnsNum + ",");
					sb.append(workClass + ",");
					sb.append(capitalGain + ",");
					if (columnSplit.length == 16) {
						sb.append(capitalLoss + ",");
						sb.append(columnSplit[15] + "\n");
					} else {
						sb.append(capitalLoss + "\n");
					}
				}

			}
			br.write(sb.toString());
			br.close();
		} catch (IOException e)

		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		try {
			// Reads the data from the file specified
			BufferedReader trainFileIn = new BufferedReader(new FileReader("adult.train.5fold.csv"));
			// Writes the data to the file specified
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

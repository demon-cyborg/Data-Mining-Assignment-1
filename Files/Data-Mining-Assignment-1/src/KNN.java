import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.pow;

public class KNN {

	public static void report(String name, int num, ArrayList<String> att1, ArrayList<String> att2,
			ArrayList<String> att3) {
		// Print Test of Training and Testing data, only displays 3 attributes - to test
		// if ProcessData Works
		System.out.println(name);
		for (int i = 0; i < att1.size() && i < num; i++) {
			System.out.println(i + ".\t\t" + att1.get(i) + "\t" + att2.get(i) + "\t" + att3.get(i));
		}
		System.out.println();
	}

	public static double euclideanDist(int x1, int x2, int y1, int y2) {
		double euclidean;

		return euclidean = Math.sqrt(pow((x1 - x2), 2) + pow((y1 - y2), 2));
	}

	public static void main(String[] args) {

		try {
			BufferedReader readTrainData = new BufferedReader(new FileReader("train.csv"));
			BufferedReader readTestData = new BufferedReader(new FileReader("test.csv"));

			try {

				// TRAINING DATA
				String line;
				// readTrainData.readLine();
				// ArrayList<String> trainEducation = new ArrayList<String>();
				// ArrayList<String> trainGender = new ArrayList<String>();
				// ArrayList<String> trainEarns = new ArrayList<String>();
				ArrayList<Integer> trainEducation = new ArrayList<Integer>();
				ArrayList<Integer> trainGender = new ArrayList<Integer>();
				ArrayList<Integer> trainEarns = new ArrayList<Integer>();

				readTrainData.readLine();
				while ((line = readTrainData.readLine()) != null) {
					String[] columnSplit = line.split(",");
					trainEducation.add(Integer.parseInt(columnSplit[0].trim()));
					trainGender.add(Integer.parseInt(columnSplit[1].trim()));
					trainEarns.add(Integer.parseInt(columnSplit[2].trim()));
					// trainEducation.add((columnSplit[0].trim()));
					// trainGender.add((columnSplit[1].trim()));
					// trainEarns.add((columnSplit[2].trim()));

				}
				// report("TrainData", 20, trainEducation, trainGender, trainEarns);

				String testLine;
				// readTestData.readLine();
				ArrayList<Integer> testEducation = new ArrayList<Integer>();
				ArrayList<Integer> testGender = new ArrayList<Integer>();
				ArrayList<Integer> testEarns = new ArrayList<Integer>();

				readTestData.readLine();
				while ((testLine = readTestData.readLine()) != null) {
					String[] columnSplit = testLine.split(",");
					testEducation.add(Integer.parseInt((columnSplit[0].trim())));
					testGender.add(Integer.parseInt((columnSplit[1].trim())));
					testEarns.add(Integer.parseInt((columnSplit[2].trim())));
				}
				// report("TestData", 20, testEducation, testGender, testEarns);

				for (int i = 0; i < 10; i++) {
					System.out.println(euclideanDist(trainEducation.get(i), testEducation.get(i), trainGender.get(i),
							testGender.get(i)));
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		} catch (FileNotFoundException e)

		{

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
}
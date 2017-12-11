import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

public class KNN {

	// Calculates euclidian distance
	public static double euclideanDist(int v1, int v2, int w1, int w2, int x1, int x2, int y1, int y2, int z1, int z2) {
		double euclidean;

		return euclidean = Math.sqrt(
				pow((v1 - v2), 2) + pow((w1 - w2), 2) + pow((x1 - x2), 2) + pow((y1 - y2), 2) + pow((z1 - z2), 2));
	}
	
	//Writes k-accuracy results to text file
	public static void writeResults(ArrayList Attr1, ArrayList Attr2, int fold) throws IOException {
		BufferedWriter bw;

		bw = new BufferedWriter(new FileWriter("grid.results.txt", true));

		bw.append("Fold " + fold + ":");
		bw.newLine();
		String entry = ("K, Accuracy");
		bw.append(entry);
		bw.newLine();

		for (int i = 0; i < Attr1.size() && i < Attr2.size(); i++) {
			entry = (Attr1.get(i) + ", " + Attr2.get(i));

			bw.append(entry);
			bw.newLine();

		}
		bw.newLine();
		bw.close();
	}

	public static void main(String[] args) {

		try {
			// Reads the files created in ProcessData
			BufferedReader readTrainData = new BufferedReader(new FileReader("train.csv"));
			BufferedReader readTestData = new BufferedReader(new FileReader("test.csv"));

			try {

				// TRAINING DATA
				String line;
				ArrayList<Integer> trainEducation = new ArrayList<Integer>();
				ArrayList<Integer> trainGender = new ArrayList<Integer>();
				ArrayList<Integer> trainEarns = new ArrayList<Integer>();
				ArrayList<Integer> trainWorkClass = new ArrayList<Integer>();
				ArrayList<Integer> trainCapGain = new ArrayList<Integer>();
				ArrayList<Integer> trainCapLoss = new ArrayList<Integer>();
				ArrayList<Integer> fold = new ArrayList<Integer>();
				
				//Adds encoded attribute data to arraylists
				readTrainData.readLine();
				while ((line = readTrainData.readLine()) != null) {
					String[] columnSplit = line.split(",");
					trainEducation.add(Integer.parseInt(columnSplit[0].trim()));
					trainGender.add(Integer.parseInt(columnSplit[1].trim()));
					trainEarns.add(Integer.parseInt(columnSplit[2].trim()));
					trainWorkClass.add(Integer.parseInt(columnSplit[3].trim()));
					trainCapGain.add(Integer.parseInt(columnSplit[4].trim()));
					trainCapLoss.add(Integer.parseInt(columnSplit[5].trim()));
					fold.add(Integer.parseInt(columnSplit[6].trim()));
				}
				// report("TrainData", 20, trainEducation, trainGender, trainEarns);

				// Testing data
				String testLine;
				ArrayList<Integer> testEducation = new ArrayList<Integer>();
				ArrayList<Integer> testGender = new ArrayList<Integer>();
				ArrayList<Integer> testEarns = new ArrayList<Integer>();
				ArrayList<Integer> testWorkClass = new ArrayList<Integer>();
				
				//Adds encoded attribute data to arraylists
				int testingSize = 0;
				readTestData.readLine();
				while ((testLine = readTestData.readLine()) != null) {
					String[] columnSplit = testLine.split(",");
					testEducation.add(Integer.parseInt((columnSplit[0].trim())));
					testGender.add(Integer.parseInt((columnSplit[1].trim())));
					testEarns.add(Integer.parseInt((columnSplit[2].trim())));
					testWorkClass.add(Integer.parseInt((columnSplit[3].trim())));
					testingSize++;
				}
				// report("TestData", 20, testEducation, testGender, testEarns);

				int bestNeighbours;
				double bestAccuracy;

				// For each fold
				for (int i = 1; i < 6; i++) {
					ArrayList<Integer> valFoldIndex = new ArrayList<Integer>();
					ArrayList<Integer> trainFoldIndex = new ArrayList<Integer>();
					ArrayList<Integer> testFoldIndex = new ArrayList<Integer>();
					
					//Gets indexes for the validation and training data for each fold
					for (int j = 0; j < fold.size(); j++) {

						if (fold.get(j) == i) {
							valFoldIndex.add(j);
						} else {
							trainFoldIndex.add(j);
						}

					}
					//Splits the testing data into 5 folds
					for (int j = (testingSize / 5) * i - 1; j < (testingSize / 5) * i; j++) {
						testFoldIndex.add(j);
					}
					// System.out.println(valFoldIndex);

					// Adds euclidean distances to arraylist
					ArrayList<Integer> earnsPredictionList = new ArrayList<Integer>();
					ArrayList<Integer> earnsActualList = new ArrayList<Integer>();

					ArrayList<Double> accuracyList = new ArrayList<Double>();
					ArrayList<Integer> kList = new ArrayList<Integer>();
					// Goes through different number of neighbours from 1 to 39
					for (int k = 1; k < 40; k += 2) {
						for (int x = 0; x < valFoldIndex.size(); x++) {
							HashMap<Integer, Double> euclideanHMap = new HashMap<Integer, Double>();
							int neighbours = 1;
							//Gets the euclidean distance between each validation data and all the training data in the fold
							for (int y = 0; y < trainFoldIndex.size(); y++) {
								euclideanHMap.put(y, euclideanDist(trainEducation.get(valFoldIndex.get(x)),
										trainEducation.get(trainFoldIndex.get(y)), trainGender.get(valFoldIndex.get(x)),
										trainGender.get(trainFoldIndex.get(y)), trainWorkClass.get(valFoldIndex.get(x)),
										trainWorkClass.get(trainFoldIndex.get(y)),
										trainCapGain.get(valFoldIndex.get(x)), trainCapGain.get(trainFoldIndex.get(y)),
										trainCapLoss.get(valFoldIndex.get(x)),
										trainCapLoss.get(trainFoldIndex.get(y))));
							}

							// System.out.println(x+" "+euclideanHMap);

							// https://stackoverflow.com/questions/8119366/sorting-hashmap-by-values
							// Sorts data from smallest distances to largest to get the indices
							Map<Integer, Double> sortedMap = euclideanHMap.entrySet().stream()
									.sorted(Entry.comparingByValue()).collect(Collectors.toMap(Entry::getKey,
											Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

							ArrayList<Integer> smallestDistIndex = new ArrayList<Integer>();
							for (Entry<Integer, Double> entry : sortedMap.entrySet()) {
								smallestDistIndex.add(entry.getKey());
							}
							// System.out.println("Smallest indexes"+smallestDistIndex);
							// System.out.println("validation:"+valFoldIndex.get(x));

							// Gets earn values of closest neighbours
							ArrayList<Integer> earnValue = new ArrayList<Integer>();
							for (int a = 0; a < k; a++) {
								earnValue.add(trainEarns.get(smallestDistIndex.get(a)));
							}
							
							//Makes a prediction on earn value based on the most common occurence in its neighbours
							int earnsPrediction;
							if (Collections.frequency(earnValue, 0) >= (earnValue.size() / 2)) {
								earnsPrediction = 0;
							} else {
								earnsPrediction = 1;
							}

							earnsPredictionList.add(earnsPrediction);
							earnsActualList.add(trainEarns.get(valFoldIndex.get(x)));

						} 

						// Calculates accuracy
						double correct = 0;
						for (int x = 0; x < earnsPredictionList.size(); x++) {
							if (earnsPredictionList.get(x) == earnsActualList.get(x)) {
								correct++;
							}
						}
						double accuracy = correct / earnsPredictionList.size();

						accuracyList.add(accuracy);
						kList.add(k);

						// System.out.println("Fold "+i);
						// System.out.println("Earns predictions: "+earnsPredictionList);
						// System.out.println("Really earns: "+earnsActualList);
						// System.out.println("Accuracy: " + accuracyList.get(accuracyList.size()-1));
						// System.out.println("");
					} // num of neighbours
					
					//Writes results to grid.results file
					writeResults(kList, accuracyList, i);

					// Best neighbours & accuracy per fold
					int bestNumNeighbours = kList.get(accuracyList.indexOf(Collections.max(accuracyList)));
					System.out.println("Fold" + i + "-  Best neighbours: " + bestNumNeighbours + "	"
							+ "Best accuracy: " + Collections.max(accuracyList));

					// TESTING DATA

				} // end of run

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
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

	
    public static void writeResults(ArrayList Attr1, ArrayList Attr2, int fold ) throws IOException {
        BufferedWriter bw;

        bw = new BufferedWriter(new FileWriter("grid.results.txt", true));
        
        bw.append("Fold "+fold+":");
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
			//Reads the files created in ProcessData
			BufferedReader readTrainData = new BufferedReader(new FileReader("train.csv"));
			BufferedReader readTestData = new BufferedReader(new FileReader("test.csv"));

			try {

				// TRAINING DATA
				String line;
				ArrayList<Integer> trainEducation = new ArrayList<Integer>();
				ArrayList<Integer> trainGender = new ArrayList<Integer>();
				ArrayList<Integer> trainEarns = new ArrayList<Integer>();
				ArrayList<Integer> fold = new ArrayList<Integer>();

				readTrainData.readLine();
				while ((line = readTrainData.readLine()) != null) {
					String[] columnSplit = line.split(",");
					trainEducation.add(Integer.parseInt(columnSplit[0].trim()));
					trainGender.add(Integer.parseInt(columnSplit[1].trim()));
					trainEarns.add(Integer.parseInt(columnSplit[2].trim()));
					fold.add(Integer.parseInt(columnSplit[3].trim()));
				}
				// report("TrainData", 20, trainEducation, trainGender, trainEarns);
				
				//Testing data
				String testLine;
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

				int bestNeighbours;
				double bestAccuracy;
				
				//For each run
				for (int i = 1; i < 6; i++) {
					ArrayList<Integer> valFoldIndex = new ArrayList<Integer>();
					ArrayList<Integer> trainFoldIndex = new ArrayList<Integer>();
					ArrayList<Integer> testFoldIndex = new ArrayList<Integer>();
					
					for (int j = 0; j < fold.size(); j++) {

						if (fold.get(j) == i) {
							valFoldIndex.add(j);
						} else {
							trainFoldIndex.add(j);
						}

					}
					//System.out.println(valFoldIndex);

					
					
					
					// Adds euclidean distances to arraylist
					ArrayList<Integer> earnsPredictionList = new ArrayList<Integer>();
					ArrayList<Integer> earnsActualList = new ArrayList<Integer>();
					
					ArrayList<Double> accuracyList = new ArrayList<Double>();
					ArrayList<Integer> kList = new ArrayList<Integer>();
					//Number of neighbours
					for(int k=1;k<40;k+=2) {
					for (int x = 0; x < valFoldIndex.size(); x++) {
						//ArrayList<Double> euclidean = new ArrayList<Double>();
						HashMap<Integer, Double> euclideanHMap = new HashMap<Integer, Double>();
						int neighbours = 1;
						for (int y = 0; y < trainFoldIndex.size(); y++) {
							euclideanHMap.put(y,euclideanDist(trainEducation.get(valFoldIndex.get(x)),
									trainEducation.get(trainFoldIndex.get(y)), trainGender.get(valFoldIndex.get(x)),
									trainGender.get(trainFoldIndex.get(y))));
						}
						
						//System.out.println(x+" "+euclideanHMap);
						
						//https://stackoverflow.com/questions/8119366/sorting-hashmap-by-values
						//Gets index of data with smallest distance
						Map<Integer, Double> sortedMap = 
								euclideanHMap.entrySet().stream()
							    .sorted(Entry.comparingByValue())
							    .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
							                              (e1, e2) -> e1, LinkedHashMap::new));
						
						ArrayList<Integer> smallestDistIndex = new ArrayList<Integer>();
						for (Entry<Integer, Double> entry : sortedMap.entrySet()){
							smallestDistIndex.add(entry.getKey());
						}
						//System.out.println("Smallest indexes"+smallestDistIndex);
						//System.out.println("validation:"+valFoldIndex.get(x));
						
						//check earns
						ArrayList<Integer> earnValue = new ArrayList<Integer>();
						
						for(int a=0;a<k;a++) {
							earnValue.add(trainEarns.get(smallestDistIndex.get(a)));
						}
						
						int earnsPrediction;
						if(Collections.frequency(earnValue, 0)>=(earnValue.size()/2)) {
							earnsPrediction=0;
						}
						else {
							earnsPrediction=1;
						}
						
						
						earnsPredictionList.add(earnsPrediction);
						earnsActualList.add(trainEarns.get(valFoldIndex.get(x)));
						
					}//each validation data
					
					//Accuracy
					double correct=0;
					for(int x=0;x<earnsPredictionList.size();x++) {
						if(earnsPredictionList.get(x)==earnsActualList.get(x)) {
							correct++;
						}
					}
					double accuracy = correct/earnsPredictionList.size();

					accuracyList.add(accuracy);
					kList.add(k);
					
					//System.out.println("Fold "+i);
					//System.out.println("Earns predictions: "+earnsPredictionList);
					//System.out.println("Really earns: "+earnsActualList);
					//System.out.println("Accuracy: " + accuracyList.get(accuracyList.size()-1));
					//System.out.println("");
					}//num of neighbours
					
					writeResults(kList,accuracyList,i);
					
					//Best neighbours
					int bestNumNeighbours=kList.get(accuracyList.indexOf(Collections.max(accuracyList)));
					System.out.println("Best neighbours: " + bestNumNeighbours);
					
					
					//TESTING DATA
					
					
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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class KNN {


    public static double euclideanDist(int x1, int x2, int y1, int y2) {
        double euclidean = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));

        return euclidean;
    }

    public static void main(String[] args) {

        try {
            BufferedReader readTrainData = new BufferedReader(new FileReader("train.csv"));
            BufferedReader readTestData = new BufferedReader(new FileReader("test.csv"));

            try {
                // readTrainData.readLine();
                ArrayList<String> trainEducation = new ArrayList<String>();
                ArrayList<String> trainGender = new ArrayList<String>();
                ArrayList<String> trainEarns = new ArrayList<String>();

                String line;

                readTrainData.readLine();
                while ((line = readTrainData.readLine()) != null) {
                    String[] columnSplit = line.split(",");
                    trainEducation.add((columnSplit[0]));
                    trainGender.add((columnSplit[1]));
                    trainEarns.add((columnSplit[2]));

                }


                ArrayList<Integer> testEducation = new ArrayList<Integer>();
                ArrayList<Integer> testGender = new ArrayList<Integer>();
                ArrayList<Integer> testEarns = new ArrayList<Integer>();

                //String line;
                readTestData.readLine();
                while ((line = readTestData.readLine()) != null) {
                    String[] columnSplit = line.split(",");
                    testEducation.add(Integer.parseInt(columnSplit[0].trim()));
                    testGender.add(Integer.parseInt(columnSplit[1].trim()));
                    testEarns.add(Integer.parseInt(columnSplit[2].trim()));

                }

                //euclideanDist(trainEducation.get(0), testEducation.get(0), trainGender.get(0), testGender.get(0));

                for (int i = 0; i < /*testEducation.size()*/ 40; i++) {
                    System.out.println(
                            testEducation.get(i) +
                                    "\t" +
                                    testGender.get(i) +
                                    "\t" +
                                    testEarns.get(i)
                    );
                }


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (
                FileNotFoundException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

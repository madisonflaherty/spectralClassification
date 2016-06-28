import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by madison on 4/29/16.
 */
public class CSVReader {
    /** The name of the file used for training */
    //String fileName = "/Users/madison/Documents/dataMining/project_proj/stellarClassification/combinedData500_PCA.csv";
    double[][] rawData;
    /** number of attributes in the classifier */
    static final int NUM_ATTRIBUTES = 2;

    /**
     * Reads the CSV file and generates a 2D array of floats that represent
     * data points.
     * @return 2D array representation of data points.
     */
     public double[][] readCSVFile(String fileName) {
        File f = new File(fileName);
        String line = "";
        try {
            long lineCount = Files.lines(Paths.get(fileName)).count();
            this.rawData = new double[(int) (lineCount - 1)][NUM_ATTRIBUTES + 1];
            BufferedReader fileReader = new BufferedReader(new FileReader(f));
            //skip first line in file with headers
            fileReader.readLine();
            int currLine = 0;
            // read all the lines and convert them into a double[][]
            while ((line = fileReader.readLine()) != null) {
                int attribute = 0;
                String[] tokens = line.split(",");
                for(String token : tokens) {
                    if(attribute == NUM_ATTRIBUTES) {
                        int temp = -1;
                        switch(token) {
                            case "O": temp = 0; break;
                            case "B": temp = 1; break;
                            case "A": temp = 2; break;
                            case "F": temp = 3; break;
                            case "G": temp = 4; break;
                            case "K": temp = 5; break;
                            case "M": temp = 6; break;
                        }
                        this.rawData[currLine][attribute] = temp;
                        attribute++;
                    }
                    else {
                        this.rawData[currLine][attribute] = Double.parseDouble(token);
                        attribute++;
                    }
                }
                currLine++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rawData;
    }
}

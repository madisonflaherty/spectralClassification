/**
 * Created by madison on 4/28/16.
// */
import weka.attributeSelection.PrincipalComponents;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class wekaPCA {
    public wekaPCA(Instances data1) throws Exception {
        PrincipalComponents pc = new PrincipalComponents();
        pc.buildEvaluator(data1);
        Instances transformedData1 = pc.transformedData(data1);
        generateTransformedDataFile(transformedData1, "transformedPCAData_TEST.arff");
    }

    public void generateTransformedDataFile(Instances data, String filename) throws FileNotFoundException {
        String datafileContents= data.toString();
        PrintWriter pw = new PrintWriter(filename);
        pw.write(datafileContents);
        pw.flush();
        pw.close();

    }

    /**
     * Takes in a test file name and performs PCA on it to generate appropriate
     * transformed data.
     * @param args
     */
    public static void main(String[] args) {
        String testDataFile = "/Users/madison/Documents/dataMining/project_proj/stellarClassification/combinedData.arff";
        DataSource source = null;
        try {
            source = new DataSource(testDataFile);
            Instances instances = source.getDataSet();
            instances.setClassIndex(instances.numAttributes() - 1);
            new wekaPCA(instances);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


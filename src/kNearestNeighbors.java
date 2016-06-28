import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.AbstractPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import com.panayotis.gnuplot.terminal.PostscriptTerminal;

import java.util.ArrayList;

/**
 * Created by madison on 4/30/16.
 */
public class kNearestNeighbors implements Classifier {
    /** The raw data that will be used in training */
    private double[][] trainingData;
    /** The set of training data in node form */
    private ArrayList<kNNNode> trainingNodes;
    /** The data that will be classified */
    private double[][] testingData;
    /** The number of attributes to be examined */
    private static final int NUM_ATTRIBUTES = 2;
    /** The number of classes in the classifier */
    private static final int NUM_CLASSES = 7;
    /** The value of k to be searched for */
    private final int k;
    /** The value of p to use for the general minkowski formula */
    private final int p;

    /**
     * Initializes the k nearest neighbors algorithm
     * @param testingData the data to be tested
     */
    public kNearestNeighbors(double[][] trainingData, double[][] testingData, int k, int p){
        this.trainingData = trainingData;
        this.trainingNodes = convertToNodes(trainingData);
        this.testingData = testingData;
        this.k = k;
        this.p = p;
    }

    private ArrayList<kNNNode> convertToNodes(double[][] data){
        ArrayList<kNNNode> temp = new ArrayList<>();
        for(int i=0; i < data.length; i++) {
            temp.add(new kNNNode(data[i]));
        }
        return temp;
    }

    @Override
    public double[] classifyAll() {
        double[] classifications = new double[testingData.length];
        ArrayList<kNNNode> testingNodes = convertToNodes(testingData);
        //for each node, find the closest k nodes
        Integer[] dict = new Integer[NUM_CLASSES];
        for(int j = 0; j<testingData.length;j++) {
            kNNNode[] temp = testingNodes.get(j).findNearestK(k, trainingNodes, p);
            // initialize dictionary to determine "best" class
            for(int i=0; i<NUM_CLASSES;i++) {
                dict[i] = 0;
            }
            //determine the most "likely" class based off nearest neighbors
            for(int i=0; i<k; i++) {
                dict[(int) temp[i].getData()[NUM_ATTRIBUTES]] += 1;
            }
            int max = 0;
            for(int i=0; i<NUM_CLASSES; i++) {
                if(dict[i] > max) {
                    max = i;
                }
            }
            classifications[j] = max;
        }
        return classifications;
    }

    public double eval(double[] classifications) {
        double total = 0;
        double right = 0;
        double wrong = 0;
        for( int i = 0; i < testingData.length; i++) {
            int clssGuess = (int) classifications[i];
            int clssActual = (int) testingData[i][NUM_ATTRIBUTES];
            //System.out.println(attribute);
            if (clssGuess == clssActual) {
                right += 1;
            } else {
                wrong += 1;
            }
            total += 1;
        }
        return right/total;
    }
    @Override
    public void evaluate(double[] classifications) {
        double rightPer = eval(classifications);
        System.out.println("\nk Nearest Neighbors: k=" + this.k + ", p=" + this.p);
        System.out.println("---------------------------------------");
        System.out.println("Right: " + rightPer);
        System.out.println("Wrong: " + (1 - rightPer));
    }
    @Override
    public void confusionMatrix(double[] classifications) {
        int[][] cm = new int[NUM_CLASSES][NUM_CLASSES];
        // initialize the confusion matrix
        for(int i=0;i<NUM_CLASSES;i++) {
            for(int j=0;j<NUM_CLASSES;j++) {
                cm[i][j] = 0;
            }
        }

        // add everything up
        for(int i=0;i<testingData.length;i++){
            cm[(int) testingData[i][NUM_ATTRIBUTES]][(int) classifications[i]] += 1;
        }

        System.out.println("\nk Nearest Neighbors Confusion Matrix: k=" + this.k + ", p=" + this.p);
        System.out.println("-------------------------------------------------");
        System.out.println("                       Predicted");
        System.out.print("Actual   ");
        for(int i=0; i<NUM_CLASSES; i++) {
            System.out.printf("%5d", i);
        }
        System.out.println("\n");
        for(int i = 0; i<NUM_CLASSES; i++) {
            System.out.print(" " + i + "       ");
            for(int j = 0; j<NUM_CLASSES; j++) {
                System.out.printf("%5d", cm[i][j]);
            }
            System.out.println();
        }

    }



}

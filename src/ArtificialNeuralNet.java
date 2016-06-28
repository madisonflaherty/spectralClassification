import java.util.ArrayList;

/**
 * This more complicated neural net was never completed due to a bug in which
 * it almost always said "yes". More reseach into how neural nets work is needed.
 */
public class ArtificialNeuralNet {
    static final int NUM_ATTRIBUTES = 2;
    static final int NUM_CLASSES = 7;

    ArrayList<Perceptron> inputPS;
    Perceptron outputPS;


    public ArtificialNeuralNet(double[][] trainingData, double[][] testingData, int classVal){
        trainAll(trainingData, classVal);
        double[] classifications = testAll(testingData);
        evaluate(classifications, testingData, classVal);
    }

    private void trainAll(double[][] trainingData, int classVal) {
        this.inputPS = new ArrayList<>();
        for(int i=0; i < NUM_CLASSES; i++) {
            inputPS.add(new Perceptron(trainingData[0].length, NUM_ATTRIBUTES));
        }
        this.outputPS = new Perceptron(NUM_CLASSES, 1);
        //double[] tempClassifications = testAll(trainingData);
        double[] tempClassifications;
        double error = 1.0; //(1 - evaluate(tempClassifications, trainingData, classVal));
        while(error > 0.1) {
            //System.out.println("INIT: " + outputPS.weights[0]);
            double[] tempOutputs = new double[NUM_CLASSES];
            for (int i = 0; i < trainingData.length; i++) {
                double classTemp = trainingData[i][NUM_ATTRIBUTES];
                System.out.println(classTemp);
                // train input nodes
                for (int j = 0; j < NUM_CLASSES; j++) {
                    inputPS.get(j).train(trainingData[i], j);
                    tempOutputs[j] = inputPS.get(j).feedForward(trainingData[i]);
                }
                // train output node
                outputPS.train(tempOutputs, classVal);
                int temp = outputPS.feedForward(tempOutputs);
                System.out.println(temp);
            }
            //System.out.println("EXIT: " + outputPS.weights[0]);
            tempClassifications = testAll(trainingData);
            error = (1 - evaluate(tempClassifications, trainingData, classVal));
            System.out.println("ERROR: " + error);
        }
    }

    /**
     * Taking the entire structure (input and output layers), classify all
     * records in the testing data file.
     * @param testingData the testing data file values
     * @return the classifications determined for each value record (ordered)
     */
    private double[] testAll(double[][] testingData) {
        double[] classifications = new double[testingData.length];
        for(int i = 0; i < testingData.length; i++) {
            double[] tempOutputs = new double[NUM_CLASSES];
            // train input nodes
            for (int j = 0; j < NUM_CLASSES; j++) {
                tempOutputs[j] = inputPS.get(j).feedForward(testingData[i]);
            }
            // train output node
            classifications[i] = outputPS.feedForward(tempOutputs);
            //System.out.println(outputPS.weights[0]);
        }
        return classifications;
    }

    public double evaluate(double[] classifications, double[][] data, int classVal) {
        double total = 0;
        double right = 0;
        double wrong = 0;
        for( int i = 0; i < data.length; i++ ){
            int clssGuess = (int) classifications[i];
            int clssActual = (classVal == data[i][NUM_ATTRIBUTES]) ? 1 : -1;
            //System.out.println(attribute);
            if(clssGuess == clssActual) {
                right += 1;
            } else {
                wrong += 1;
            }
            total += 1;
        }
        return (right/total);
    }

    private void prettyPrint(double[] classifications, double[][] data, int classVal) {
        double percentRight = evaluate(classifications, data, classVal);
        System.out.println("Right: " + percentRight);
        System.out.println("Wrong: " + (1-percentRight));
    }

    /**

     * @param trainingData
     * @return
     */
    private Perceptron train(double[][] trainingData, int classVal) {
        Perceptron p = new Perceptron(trainingData[0].length, NUM_ATTRIBUTES);
            //runs a single epoch
            for (int i = 0; i < trainingData.length; i++) {
                // Because a single perceptron can only output 1 or -1, we need to
                // convert the data such that the "matching" class is set to 1 and
                // all other classes are set to -1 for "wrong"
                int temp = (classVal == trainingData[i][NUM_ATTRIBUTES]) ? 1 : -1;
                p.train(trainingData[i], temp);
            }
        return p;
    }

    private double testSingle(Perceptron p, double[] testingData) {
        return p.feedForward(testingData);
    }

    private double[] test(Perceptron p, double[][] testingData) {
        double[] classifications = new double[testingData.length];
        for(int i=0; i<testingData.length; i++) {
            int answer = p.feedForward(testingData[i]);
            classifications[i] = answer;
        }
        return classifications;
    }



}


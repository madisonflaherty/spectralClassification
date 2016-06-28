/**
 * A very simple artificial neural "net" in which only a single perceptron is used.
 * Based on example found at [10]("Implementing")
 */
public class SimpleArtificialNeuralNet implements Classifier{
    private static final int NUM_ATTRIBUTES = 2;
    private static final int NUM_CLASSES = 2; // in ann, we can only say yes or no!
    private final int classVal;
    private double[][] trainingData;
    private double[][] testingData;
    private Perceptron p;



    public SimpleArtificialNeuralNet(double[][] trainingData, double[][] testingData, int classVal){
        this.trainingData = trainingData;
        this.testingData = testingData;
        this.classVal = classVal;
    }

    /**
     * Evaluates accuracy of the classifier, returns the percent of correct
     * classifications
     * @param classifications the classifications to be compared
     * @return the percent of correct classifications
     */
    public double eval(double[] classifications) {
        double total = 0;
        double right = 0;
        for( int i = 0; i < testingData.length; i++ ){
            int clssGuess = (int) classifications[i];
            int clssActual = (classVal == testingData[i][NUM_ATTRIBUTES]) ? 1 : -1;
            if(clssGuess == clssActual) {
                right += 1;
            }
            total += 1;
        }
        return (right/total);
    }
    @Override
    public void evaluate(double[] classifications) {
        double rightPer = eval(classifications);
        System.out.println("\nANN: Single Perceptron Implementation: " + classVal);
        System.out.println("---------------------------------------");
        System.out.println("Right: " + rightPer);
        System.out.println("Wrong: " + (1-rightPer));
    }

    /**
     * Trains the single perceptron
     */
    public void train() {
        Perceptron p = new Perceptron(trainingData[0].length, NUM_ATTRIBUTES);
        int count = 0;
        while(count < 10) {
            //runs a single epoch
            for (int i = 0; i < trainingData.length; i++) {
                // Because a single perceptron can only output 1 or -1, we need to
                // convert the data such that the "matching" class is set to 1 and
                // all other classes are set to -1 for "wrong"
                int temp = (classVal == trainingData[i][NUM_ATTRIBUTES]) ? 1 : -1;
                p.train(trainingData[i], temp);
            }
            count += 1;
        }
         this.p = p;
    }

    @Override
    public double[] classifyAll() {
        double[] classifications = new double[testingData.length];
        for(int i=0; i<testingData.length; i++) {
            int answer = p.feedForward(testingData[i]);
            classifications[i] = answer;
        }
        return classifications;
    }
    @Override
    public void confusionMatrix(double[] classifications) {
    }

}


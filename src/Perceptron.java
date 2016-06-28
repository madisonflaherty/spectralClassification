/**
 * A single perceptron which takes in n inputs and keeps track of n weights.
 * Has a single output
 */
import java.util.Random;
public class Perceptron {
    public double[] weights;
    double c = 0.01;
    static int NUM_ATTRIBUTES;
    static final double MIN = -1.0;
    static final double MAX = 1.0;

    public Perceptron(int n, int numAttributes){
        NUM_ATTRIBUTES = numAttributes;
        Random rn = new Random();
        weights = new double[n];
        for(int i=0; i<n; i++) {
            weights[i] = MIN + (MAX - MIN) * rn.nextDouble();

        }
    }

    /**
     * Feed the output onword after determing it
     * @param inputs
     * @return 1 if "yes"; -1 if no
     */
    int feedForward(double[] inputs){
        double sum = 0;
        for(int i=0; i<this.weights.length; i++) {
            if(i == NUM_ATTRIBUTES - 1) {
                //instead of counting class (which would obviously be bad)
                // implement bias term (see slide 42 of Lecture 21b)
                sum += weights[i] * 1;
            } else {
                sum += weights[i]
                        * inputs[i];
            }
        }
        return (sum>0) ? 1 : -1;
    }

    /**
     * Train the single perceptron with the given inputs for the desired class
     * @param inputs the inputs to train on
     * @param desired the desired stellar class being classified
     */
    void train(double[] inputs, double desired) {
        int guess = feedForward(inputs);
        double error = desired - guess;
        for(int i=0; i<inputs.length; i++) {
            weights[i] += c * error * inputs[i];
        }
    }

}

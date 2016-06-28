/**
 * Created by madison on 4/30/16.
 */
public interface Classifier {
    /**
     * Classify the previously stored data according to the particular algorithm
     * @return array of each classification
     */
    double[] classifyAll();

    /**
     * Evaluates the given classifications according to the actual. Prints out
     * to the screen the accuracy and all other relevant data.
     * @param classifiers list of classifications to be compared to actual
     */
    void evaluate(double[] classifiers);

    /**
     * Generates a confusion matrix for output
     * @param classifiers the classifications to compare
     */
    void confusionMatrix(double[] classifiers);

}

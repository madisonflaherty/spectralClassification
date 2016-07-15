
/**
 * Simple PCA implementation to simply help document how it works. A more robust
 * linear algebra library will be used to more efficiently calculate the values
 * to likely greatly improve time complexity with large datasets.
 */
public class PrincipalComponetsAnalysis {
    private double[][] data;
    private static int NUM_ATTRIBUTES = 0;
    private double[] means;
    private double[][] covarianceMatrix;

    public PrincipalComponetsAnalysis(double[][] data) {
        if(data.length > 0){
            this.data = data;
            NUM_ATTRIBUTES = data[0].length;
            this.means = calculateMeans(this.data);
            subtractMean();
            this.covarianceMatrix = calculateCovarianceMatrix();

        }
    }


    /**
     * Calculates the means of each attribute in the given dataset
     * @param data dataset being assessed. not mutated
     * @return the list of means for each attribute in order of original set
     */
    private double[] calculateMeans(double[][] data) {
        double[] means = new double[NUM_ATTRIBUTES];

        for (int i = 0; i < NUM_ATTRIBUTES; i++) {
            //iterate through each attribute
            double attributeSum = 0;
            for (int j = 0; j < NUM_ATTRIBUTES; j++) {
                //for each star
                attributeSum += data[i][j];
            }
            means[i] = attributeSum / (double) NUM_ATTRIBUTES;
        }
        return means;


    }

    /**
     * PCA requires that the dataset be "column-wise zero empirical mean".
     * What this means is that the average of each column is shifted to 0.
     */
    private void subtractMean() {
        for (int i = 0; i < NUM_ATTRIBUTES; i++) {
            for (int j = 0; j < data.length; j++ ) {
                data[i][j] = data[i][j] - means[i];
            }
        }
    }

    /**
     * calculates the covariance of two given sets of data
     * @param attributeX the index of first set
     * @param attributeY the index of second set
     * @return double value representing the covariance of those two sets
     */
    private double calculateCovariance(int attributeX, int attributeY) {
       double sum = 0;
        for( int i = 0; i<NUM_ATTRIBUTES; i++) {
            sum += (data[i][attributeX] - means[attributeX])*(data[i][attributeY] - means[attributeY]);
        }

        return sum/(NUM_ATTRIBUTES - 1);
    }

    /**
     * Calculates the covariance matrix of the given dataset
     * @return the covariance matrix representation
     */
    private double[][] calculateCovarianceMatrix() {
        double[][] covarianceMatrix = new double[NUM_ATTRIBUTES][];
        for (int i = 0; i < NUM_ATTRIBUTES; i++) {  // i  == attribute
            for (int j = 0; j < data.length; j++) {  // j == star
                covarianceMatrix[i][j] = calculateCovariance(i, j);
            }
        }
        return covarianceMatrix;
    }
}

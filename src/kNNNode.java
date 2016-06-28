import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by madison on 4/30/16.
 */
public class kNNNode {
    /** The data represented in this node*/
    private double[] data;
    /** the number of attributes per record */
    private int NUM_ATTRIBUTES = 2;
    /** the ID of this node to prevent duplication */
    private int id;
    /** The current number of nodes */
    static private int count = 0;


    /**
     * Constructor to generate a node
     * @param data the data this node represents
     */
    public kNNNode(double[] data){
        this.data = data;
        this.id = count;
        count += 1;
    }

    public double[] getData(){
        return data;
    }

    public kNNNode[] findNearestK(int k, ArrayList<kNNNode> nodes, int p){
        Collections.sort(nodes, (k1, k2) -> Double.compare(calculateDistance(k1, p), calculateDistance(k2, p)));
        kNNNode[] temp = new kNNNode[k];
        for(int i=0;i<k;i++){
            temp[i] = nodes.get(i);
        }
        return temp;
    }

    /**
     * Calculates the euclidean distance between this cluster and the other
     * cluster
     * @param oNode the record being compared against
     *
     * @return the euclidean distance in the form of a decimal
     */
    private double calculateMinkowski(kNNNode oNode, int p){
        double sum = 0.0;
        // Using the center of mass, perform the summation of (Xk - Yk) where k
        // is a given attribute index.
        double[] temp = oNode.getData();
        for( int i = 0; i < NUM_ATTRIBUTES; i++) {
            sum += Math.pow(this.data[i] - temp[i], p);
        }
        // raise the summation to the power of 1/p to finish the euclidean
        // calculation.
        return (Math.pow(sum, 1/(double) p));
    }

    /**
     * Wrapper method to easily swap distance metrics
     * @return
     */
    public double calculateDistance(kNNNode oNode, int p) {
        return calculateMinkowski(oNode, p);
    }
}

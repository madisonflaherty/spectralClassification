import java.io.*;

/**
 * The main Trainer module for taking in data and generating a classifier.
 * In order to maintain the file formats required by the writeup all training
 * methods were kept in this file instead of the usual OOP procedure of splitting
 * up into multiple files.
 *
 * @author Madison Flaherty (mef4824)
 * 2/24/2016
 */
public class StellarDecisionTreeTrainer {
    /** The name of the file used for training */
    private String fileName = "/Users/madison/Documents/dataMining/project_proj/stellarClassification/combinedData500_PCA.csv";
        //"/Users/madison/Documents/dataMining/project_proj/stellarClassification/transformedPCADataTest.csv";
    private double[][] rawData;
    /** number of attributes in the classifier */
    private static final int NUM_ATTRIBUTES = 2;
    /** The number of classes in the classifier */
    private static final int NUM_CLASSES = 7;
    /** keeps track of necessary information to generate java code for branching structure */
    private static String branchingStructure;


    /**
     * Trainer constructor. Initializes the process for making the branching
     * factor
     */
    public StellarDecisionTreeTrainer(double[][] trainingData){
        rawData = trainingData;
        branchingStructure = "";
        //generateDecisionTree();
        //makeClassifierFile();

    }

    /* CLASSIFIER CODE GENERATION */

    /**
     * Takes in the code string and writes it to a file.
     */
    public void makeClassifierFile() {
        try {
            PrintWriter writer = new PrintWriter("src/StellarDecisionTreeClassifier.java");
            writer.write(genProlog());
            writer.write(generateCode());
            writer.write(genEpilog());
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the constant portions of the file that are always a part of the
     * classifier.
     * @return the string representation of the constant initializer
     */
    private String genProlog() {
        String s = "import java.io.BufferedReader;\n" +
                "import java.io.File;\n" +
                "import java.io.FileReader;\n" +
                "import java.nio.file.Files;\n" +
                "import java.nio.file.Paths;\n" +
                "\n" +
                "/**\n" +
                " * Classifier program that is dynamically generated by the training program.\n" +
                " * @author Madison Flaherty (mef4824)\n" +
                " * 2/24/2016 \n" +
                " */\n" +
                "public class StellarDecisionTreeClassifier implements Classifier{\n" +
                "    /** The raw data that will be classified */\n" +
                "    private double[][] rawData;\n" +
                "    /** The number of attributes to be examined */\n" +
                "    private static final int NUM_ATTRIBUTES = 2;\n" +
                "    /** The number of classes in the classifier */\n" +
                "    private static final int NUM_CLASSES = 7;\n" +
                "    private static String[] attributes;\n" +
                "\n" +
                "    /** Constructor for the classifier */\n" +
                "    public StellarDecisionTreeClassifier(double[][] testingData){\n" +
                "        this.rawData = testingData;\n" +
                "    }\n";
        return s;
    }

    /**
     * Generates the closing portions of the file that are always a part of the
     * classifier.
     * @return the string representation of the constant closer
     */
    private String genEpilog() {
        String s = "\n" +
                "    /**\n" +
                "     * Classifies all of the data points in the the supplied data file\n" +
                "     */\n" +
                "    public double[] classifyAll(){\n" +
                "        double[] classifications = new double[rawData.length];\n" +
                "        for(int i=0; i<rawData.length; i++) {\n" +
                "            classifications[i] = classify(rawData[i]);\n" +
                "        }\n" +
                "        return classifications;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * Evalates the classifications supplied compared to the actual classes\n" +
                "     * @param classifications the classifications generated by this dec. tree\n" +
                "     */\n" +
                "    public void evaluate(double[] classifications){\n" +
                "        double total = 0;\n" +
                "        double right = 0;\n" +
                "        double wrong = 0;\n" +
                "        for( int i = 0; i < rawData.length; i++ ){\n" +
                "            int clssGuess = (int) classifications[i];\n" +
                "            int clssActual = (int) rawData[i][NUM_ATTRIBUTES];\n" +
                "            //System.out.println(attribute);\n" +
                "            if(clssGuess == clssActual) {\n" +
                "                right += 1;\n" +
                "            } else {\n" +
                "                wrong += 1;\n" +
                "            }\n" +
                "            total += 1;\n" +
                "        }\n" +
                "        System.out.println(\"Decision Tree Results: \");\n" +
                "        System.out.println(\"-------------------------\");\n" +
                "        System.out.println(\"Right: \" + right/total);\n" +
                "        System.out.println(\"Wrong: \" + wrong/total);\n" +
                "    }" +
                "    public void confusionMatrix(double[] classifications) {\n" +
                "        int[][] cm = new int[NUM_CLASSES][NUM_CLASSES];\n" +
                "        // initialize the confusion matrix\n" +
                "        for(int i=0;i<NUM_CLASSES;i++) {\n" +
                "            for(int j=0;j<NUM_CLASSES;j++) {\n" +
                "                cm[i][j] = 0;\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        // add everything up\n" +
                "        for(int i=0;i<rawData.length;i++){\n" +
                "            cm[(int) rawData[i][NUM_ATTRIBUTES]][(int) classifications[i]] += 1;\n" +
                "        }\n" +
                "\n" +
                "        System.out.println(\"Decision Tree\");\n" +
                "        System.out.println(\"-------------------------------------------------\");\n" +
                "        System.out.println(\"                       Predicted\");\n" +
                "        System.out.print(\"Actual   \");\n" +
                "        for(int i=0; i<NUM_CLASSES; i++) {\n" +
                "            System.out.printf(\"%5d\", i);\n" +
                "        }\n" +
                "        System.out.println(\"\\n\");\n" +
                "        for(int i = 0; i<NUM_CLASSES; i++) {\n" +
                "            System.out.print(\" \" + i + \"       \");\n" +
                "            for(int j = 0; j<NUM_CLASSES; j++) {\n" +
                "                System.out.printf(\"%5d\",cm[i][j]);\n" +
                "            }\n" +
                "            System.out.println();\n" +
                "        }" +
                "}\n}";

        return s;
    }

    /**
     * Takes in the classifier tree structure and generates the code for the
     * classifier
     * @return string representation of the entire classifier file
     */
    private String generateCode(){
        String s = "    /** Using the derived branching system classifies a given data point */\n";
        s += "    private int classify(double[] attr) {\n";
        s += branchingStructure;
        s += "    }";
        return s;
    }

    /* TRAINER PORTION */
    /**
     * Generates a list of the population count of each class in the given data
     * @param data the data to return the population values of
     * @return list of the amount of each class in the population.
     *      ie: if 4 of c0 and 5 of c1, return [4,5]
     */
    private int[] getClassifierPopulation(double[][] data) {
        int[] count = new int[NUM_CLASSES];
        for(int i = 0; i < NUM_CLASSES; i++) {
            count[i] = 0;
        }
        for(double[] d : data) {
            count[(int)(d[NUM_ATTRIBUTES])] += 1;
        }
        return count;
    }

    /**
     * Generates the gini index of a given data set
     * @return gini index of given data set
     */
    private double calculateGiniIndex(double[][] data){
        int[] population = getClassifierPopulation(data);
        int total = data.length;
        double sum = 0.0;
        if (total > 0){
            for(int clssPop : population) {
                double percent = (double) clssPop / (double) total;
                percent = percent*percent;
                sum += percent;
            }
        }

        return 1-sum;
    }

    /**
     * Generates a mixed GINI value given a node and a threshold
     * @param dataLess - the array of points that are below (or equal) to the
     *                 threshold
     * @param dataMore - the array of points that are above the threshold
     * @param total - the total number of points in the parent node
     * @return the mixed GINI index of the parent
     */
    private double mixedGiniValue(double[][] dataLess, double[][] dataMore, double total) {
        double mixedLess = calculateGiniIndex(dataLess);
        double mixedMore = calculateGiniIndex(dataMore);
        return (dataLess.length/total)*(mixedLess) + (dataMore.length/total)*(mixedMore);
    }

    /**
     * Generates two arrays that actually split up the data
     * @param data the parent data node
     * @param threshold the threshold to split on
     * @param attribute the attribute that is being focused on
     * @return an array of length two that contains two double[][] of the split data
     */
    private double[][][] makeSplit(double[][] data, double threshold, int attribute) {
        // figure out how many are in each array
        int lessCount = 0;
        int moreCount = 0;
        for( double[] d : data ){
            if(d[attribute] <= threshold) {
                lessCount++;
            }
            else {
                moreCount++;
            }
        }
        // Make separate lists of each value in the set.
        double[][] dataLess = new double[lessCount][NUM_ATTRIBUTES + 1];
        double[][] dataMore = new double[moreCount][NUM_ATTRIBUTES + 1];
        lessCount = 0;
        moreCount = 0;
        for (double[] d : data) {
            if(d[attribute] <= threshold) {
                dataLess[lessCount] = d.clone();
                lessCount++;
            }
            else {
                dataMore[moreCount] = d.clone();
                moreCount++;
            }
        }
        return new double[][][] {dataLess, dataMore};
    }

    /**
     * Finds the best binary split given a set of data and a given attribute value
     * @param data the data to be split
     * @param attribute the attribute that is being evaluated
     * @return the best value to split on
     */
    private double[] findBestSplit(double[][] data, int attribute) {
        double bestGini = Integer.MAX_VALUE;
        double bestThreshold = Integer.MAX_VALUE;

        //find range
        double minValue = Integer.MAX_VALUE;
        double maxValue = Integer.MIN_VALUE;
        for(double[] value : data){
            maxValue = Double.max(value[attribute], maxValue);
            minValue = Double.min(value[attribute], minValue);
        }

        //Using range make thresholds to check (20 of them)
        double width = (maxValue - minValue) / (double) 20;

        for( double threshold = minValue; threshold <= maxValue; threshold = threshold + width) {
            double[][][] tmp = makeSplit(data, threshold, attribute);
            double[][] dataLess = tmp[0];
            double[][] dataMore = tmp[1];

            double mixedGiniValue = mixedGiniValue(dataLess, dataMore, (double) data.length);
            if (mixedGiniValue < bestGini) {
                bestGini = mixedGiniValue;
                bestThreshold = threshold;
            }
        }
        return new double[] {bestThreshold, bestGini};

    }

    /**
     * The recursive call for generating the decision tree
     * @param data - the data to be evaluated on
     * @return the class evaluated for this set of data (-1 for if splitting still
     * needs to occur)
     */
    private int decisionTreeRec(double[][] data ){
        int[] population = getClassifierPopulation(data);
        double total = data.length;
        // base case for in case a given node is more than 90% a single class
        for(int i = 0; i < population.length; i++) {
            if((double) (population[i])/total >= .90) {
                branchingStructure += "    return " + i + ";\n";
                return i;
            }
        }

        // stopping criteria has not been met yet
        int bestAttribute = -1;
        double bestThreshold = Integer.MAX_VALUE;
        double bestGoodness = 1;

        // walk through each attribute and find best threshold to split on for
        // each. If GINI index is better make that the best split.
        for(int i = 0; i<NUM_ATTRIBUTES; i++) {
            double[] split = findBestSplit(data, i);
            double threshold = split[0];
            double goodness = split[1];
            //if a tie, we just keep the first "best" split value
            if(goodness < bestGoodness) {
                bestThreshold = threshold;
                bestAttribute = i;
                bestGoodness = goodness;
            }

        }

        // with new best split, actually make the split and recurse on each set
        // of data points.
        double[][][] splitData = makeSplit(data, bestThreshold, bestAttribute);
        double[][] lessData = splitData[0];
        double[][] moreData = splitData[1];

        // recursively build if/then tree for classifier program here
        branchingStructure += "    if(attr[" + bestAttribute + "] <= " + bestThreshold + ") { \n";
        decisionTreeRec(lessData);
        branchingStructure += "    } else {\n";
        decisionTreeRec(moreData);
        branchingStructure += "    }\n";
        return -1;
    }

    /**
     * The main method used for generating the decision tree
     */
    public void generateDecisionTree(){
        decisionTreeRec(rawData);
    }



}

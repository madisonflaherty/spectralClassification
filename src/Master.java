import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.AbstractPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import com.panayotis.gnuplot.terminal.PostscriptTerminal;

/**
 * Effectively the "Main" class to run each of the individual tests.
 */


public class Master {

    /**
     * Runs the main program for testing each classifier
     * @param args unused
     */
    public static void main(String[] args) {
        CSVReader csvR = new CSVReader();

        double[][] trainingData = csvR.readCSVFile("combinedData500_PCA.csv");
        double[][] testingData = csvR.readCSVFile("combinedData1000_PCA.csv");

        /**
         * DECISION TREE RUN
         * Beware! This will not update the DT classifier on this run. Will
         * need to run it twice or run the trainer separately
         */

        StellarDecisionTreeTrainer trainer =
                new StellarDecisionTreeTrainer(trainingData);
        trainer.generateDecisionTree();
        trainer.makeClassifierFile();

        StellarDecisionTreeClassifier classifier =
                new StellarDecisionTreeClassifier(testingData);
        double[] classifications1 = classifier.classifyAll();
        classifier.evaluate(classifications1);
        classifier.confusionMatrix(classifications1);

        /**
         * SIMPLE ARTIFICIAL NEURAL NET RUN
         * (checking for F class stars)
         */

        SimpleArtificialNeuralNet sann = new SimpleArtificialNeuralNet(trainingData, testingData, 0);
        sann.train();
        double[] classifications2 = sann.classifyAll();
        sann.evaluate(classifications2);
        //ArtificialNeuralNet ann = new ArtificialNeuralNet(trainingData, testingData, 3);

        /**
         * Artificial Neural Net (ALL CLASSES) RUN
         */
        /*
        double[][][] graphData = new double[7][100][2];
        for(int i=0; i<7;i++) {
            for(int j=0; j<100; j++) {
                sann = new SimpleArtificialNeuralNet(trainingData, testingData, i);
                sann.train();
                classifications2 = sann.classifyAll();
                double accuracy = sann.eval(classifications2);
                //sann.evaluate(classifications2);
                graphData[i][j][0] = j;
                graphData[i][j][1] = accuracy;
            }
        }
        generateDataVisualANN(graphData);
        for(int i=0;i<7;i++){
            double sum = 0;
            for(int j=0;j<graphData[0].length;j++){
                sum += graphData[i][j][1];
            }
            System.out.println(i + ": " + (sum/(graphData[0].length)) );
        }
        */

        /**
         * SIMPLE KNN RUN
         */
        int k = 9;
        int p = 4;
        kNearestNeighbors knn = new kNearestNeighbors(trainingData, testingData, k, p);
        double[] classifications3 = knn.classifyAll();
        knn.evaluate(classifications3);
        knn.confusionMatrix(classifications3);

        /**
         * KNN run to determine best P/K value. Left commented out after initial use.
         */
        /*
        double[][][] temp = new double[5][14][2];
        int count = 0;
        for(int p=2; p<7; p++) {
            // try all values of k for 1-14
            for(int k=1; k<15; k++) {
                kNearestNeighbors knn = new kNearestNeighbors(trainingData, testingData, k, p);
                double[] classifications3 = knn.classifyAll();
                temp[count][k-1][0] = k;
                temp[count][k-1][1] = knn.eval(classifications3);
                knn.evaluate(classifications3);
            }
            count+= 1;
        }
        generateDataVisual(temp);
        */

    }


    /**
     * Generates the plot for bonus part f using JavaPlot
     * @param data the list of clusters being drawn
     * @return javaPlot object (not used)
     */
    public static JavaPlot generateDataVisual(double[][][] data) {
        JavaPlot p = new JavaPlot();

        // open terminal
        PostscriptTerminal epsf = new PostscriptTerminal("../knnGraphs/graph.eps");
        epsf.setColor(true);
        p.setTerminal(epsf);

        p.setTitle("P-K Classification Accuracy");
        p.getAxis("x").setLabel("K", "Arial", 20);
        p.getAxis("y").setLabel("% Accuracy", "Arial", 20);

        p.newGraph();
        NamedPlotColor[] colors = {NamedPlotColor.RED, NamedPlotColor.GREEN, NamedPlotColor.BLUE, NamedPlotColor.YELLOW,
                NamedPlotColor.BLACK, NamedPlotColor.GRAY, NamedPlotColor.BROWN, NamedPlotColor.ORANGE};

        p.setKey(JavaPlot.Key.OUTSIDE);
        for(int i = 0; i < data.length; i++) {
            // set up plot
            p.addPlot(data[i]);
            PlotStyle stl = ((AbstractPlot) p.getPlots().get(i)).getPlotStyle();
            ((AbstractPlot) p.getPlots().get(i)).setTitle("P = " + (i + 2));
            stl.setPointSize(1);
            stl.setLineType(colors[i]);
            stl.setStyle(Style.LINESPOINTS);
        }
        // plot
        p.plot();
        return p;
    }

    /**
     * Generates the plot for bonus part f using JavaPlot
     * @param data the list of clusters being drawn
     * @return javaPlot object (not used)
     */
    public static JavaPlot generateDataVisualANN(double[][][] data) {
        JavaPlot p = new JavaPlot();

        // open terminal
        PostscriptTerminal epsf = new PostscriptTerminal("../knnGraphs/anngraph.eps");
        epsf.set("size", "10, 10");
        epsf.setColor(true);
        p.setTerminal(epsf);

        p.setTitle("ANN Classification Accuracy");
        p.getAxis("x").setLabel("K", "Arial", 20);
        p.getAxis("y").setLabel("% Accuracy", "Arial", 20);

        p.newGraph();
        NamedPlotColor[] colors = {NamedPlotColor.RED, NamedPlotColor.GREEN, NamedPlotColor.BLUE, NamedPlotColor.YELLOW,
                NamedPlotColor.BLACK, NamedPlotColor.GRAY, NamedPlotColor.BROWN, NamedPlotColor.ORANGE};

        for(int i = 0; i < data.length; i++) {
            // set up plot
            p.addPlot(data[i]);
            PlotStyle stl = ((AbstractPlot) p.getPlots().get(i)).getPlotStyle();
            String temp = "";
            switch(i) {
                case 0: temp= "O"; break;
                case 1: temp = "B"; break;
                case 2: temp = "A"; break;
                case 3: temp = "F"; break;
                case 4: temp = "G"; break;
                case 5: temp = "K"; break;
                case 6: temp = "M"; break;
            }
            ((AbstractPlot) p.getPlots().get(i)).setTitle(temp);
            stl.setPointSize(1);
            stl.setLineType(colors[i]);
            stl.setStyle(Style.LINES);
        }
        // plot
        p.setKey(JavaPlot.Key.OUTSIDE);
        p.set("size", "ratio 0.4");
        p.plot();
        return p;
    }

}

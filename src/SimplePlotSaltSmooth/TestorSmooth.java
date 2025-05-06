package SimplePlotSaltSmooth;

import java.util.List;

public class TestorSmooth {
    public static void main(String[] args) {
        // Step 1: Import the salted data from the CSV file
        List<PlotterGraph> saltedData = Smoother.importFromCSV("SaltedPolynomialGraph.csv");

        // Step 2: Smooth the data using a moving average
        List<PlotterGraph> smoothedData = Smoother.smoothData(saltedData);

        // Step 3: Export the smoothed data to a new CSV file
        Smoother.exportToCSV(smoothedData, "SmoothedCSVFile.csv");
    }
}
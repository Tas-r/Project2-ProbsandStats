package SimplePlotSaltSmooth;

import java.util.List;

public class TestorSalt {
    public static void main(String[] args) {
        // Step 1: Import the data from the CSV file
        List<PlotterGraph> data = SaltData.importFromCSV("PolynomialGraph.csv");

        // Step 2: Apply the SALT algorithm
        List<PlotterGraph> saltedData = SaltData.applySalt(data);

        // Step 3: Export the salted data to a new CSV file
        SaltData.exportToCSV(saltedData, "SaltedPolynomialGraph.csv");
    }
}
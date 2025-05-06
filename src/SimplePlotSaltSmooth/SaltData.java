package SimplePlotSaltSmooth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class SaltData {

    // Method to read data from the CSV file
    public static List<PlotterGraph> importFromCSV(String fileName) {
        List<PlotterGraph> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Skip the header line (x,y)
            reader.readLine();

            // Read each line and parse x and y values
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    int x = Integer.parseInt(values[0].trim());
                    int y = Integer.parseInt(values[1].trim());
                    data.add(new PlotterGraph(x, y));
                }
            }

            System.out.println("Data successfully imported from " + fileName);

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numbers from CSV: " + e.getMessage());
        }

        return data;
    }

    // Method to apply the SALT algorithm
    public static List<PlotterGraph> applySalt(List<PlotterGraph> data) {
        List<PlotterGraph> saltedData = new ArrayList<>();
        // Define the range for the random "SALT" value
        int saltRangeMin = -20;
        int saltRangeMax = 20;

        // Optional: Scale the SALT range based on the magnitude of y
        boolean scaleSalt = false;

        for (PlotterGraph point : data) {
            int x = point.getX();
            int y = point.getY();

            // Determine the SALT range (fixed or scaled)
            int minSalt = saltRangeMin;
            int maxSalt = saltRangeMax;
            if (scaleSalt) {
                // Scale the SALT range to be 20% of the y value
                int saltRange = (int) (Math.abs(y) * 0.2);
                minSalt = -saltRange;
                maxSalt = saltRange;
            }

            // Generate a random "SALT" value between minSalt and maxSalt
            int salt = (int) (Math.random() * (maxSalt - minSalt + 1)) + minSalt;
            // Add the SALT to y
            y += salt;
            PlotterGraph saltedPoint = new PlotterGraph(x, y);
            saltedData.add(saltedPoint);
            System.out.printf("x: %d, original y: %d, salted y: %d (SALT: %d)%n", x, point.getY(), y, salt);
        }

        return saltedData;
    }

    // Method to export the salted data to a new CSV file
    public static void exportToCSV(List<PlotterGraph> data, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("x,y\n");

            for (PlotterGraph p : data) {
                writer.write(String.format("%d,%d%n", p.getX(), p.getY()));
            }

            System.out.println("Data successfully exported to " + fileName);

        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}
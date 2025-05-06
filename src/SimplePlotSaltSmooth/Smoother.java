package SimplePlotSaltSmooth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Smoother {

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

    // Method to smooth the data using a moving average
    public static List<PlotterGraph> smoothData(List<PlotterGraph> data) {
        List<PlotterGraph> smoothedData = new ArrayList<>();
        int windowSize = 11; // 5 points before, current point, 5 points after
        int halfWindow = windowSize / 2; // 5 points on each side

        for (int i = 0; i < data.size(); i++) {
            int x = data.get(i).getX();
            int y = data.get(i).getY();

            // Calculate the start and end indices for the window
            int start = Math.max(0, i - halfWindow); // Don't go below index 0
            int end = Math.min(data.size() - 1, i + halfWindow); // Don't go beyond the last index

            // Sum the y values in the window
            double sum = 0;
            int count = 0;
            for (int j = start; j <= end; j++) {
                sum += data.get(j).getY();
                count++;
            }

            // Calculate the average y value for the window with floating-point precision
            int smoothedY = (int) Math.round(sum / count);
            smoothedData.add(new PlotterGraph(x, smoothedY));
            System.out.printf("x: %d, original y: %d, smoothed y: %d (window size: %d)%n", x, y, smoothedY, count);
        }

        return smoothedData;
    }

    // Method to export the smoothed data to a new CSV file
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
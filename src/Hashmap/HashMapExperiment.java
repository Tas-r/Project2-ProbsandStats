package Hashmap;

import Hashmap.SimpleHashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class HashMapExperiment {

    public static void main(String[] args) {
        SimpleHashMap map = new SimpleHashMap(16);

        String csvFile = "C:\\Users\\tasni\\OneDrive\\Desktop\\SPRING 2025\\PROBS AND STATS\\Report\\cleaned_dataset.csv"; // <<<--- your file
        String line;
        String cvsSplitBy = ",";
        List<String> vinList = new ArrayList<>();


        Map<String, Integer> brandPriceSum = new HashMap<>();
        Map<String, Integer> brandCarCount = new HashMap<>();

        long startTime = System.currentTimeMillis();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                if (data.length >= 10) {
                    String vin = data[8];
                    String brand = data[2];
                    String model = data[3];
                    int price = Integer.parseInt(data[1].trim()); // Price

                    String value = "Brand: " + brand + ", Model: " + model + ", Price: $" + price;
                    map.put(vin, value);
                    vinList.add(vin);

                    // Update brand price data
                    brandPriceSum.put(brand, brandPriceSum.getOrDefault(brand, 0) + price);
                    brandCarCount.put(brand, brandCarCount.getOrDefault(brand, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();

        System.out.println("\n=== Experiment Results ===");
        System.out.println("Inserted " + vinList.size() + " entries.");
        System.out.println("Time taken for insertion: " + (endTime - startTime) + " ms");
        System.out.println("Final Capacity: " + map.capacity);
        System.out.println("Average Load per Bucket: " + map.averageLoad());
        System.out.println("Max Bucket Size: " + map.maxBucketSize());

        // Contains test (optional)
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            String randomVin = vinList.get(random.nextInt(vinList.size()));
            System.out.println("Contains " + randomVin + ": " + map.contains(randomVin));
        }

        // Draw chart: Average Price by Brand
        showAveragePriceByBrand(brandPriceSum, brandCarCount);
    }

    private static void showAveragePriceByBrand(Map<String, Integer> brandPriceSum, Map<String, Integer> brandCarCount) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (String brand : brandPriceSum.keySet()) {
            int total = brandPriceSum.get(brand);
            int count = brandCarCount.get(brand);
            double average = (double) total / count;
            dataset.addValue(average, "Average Price", brand);
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Average Car Price by Brand",
                "Brand",
                "Average Price (USD)",
                dataset,
                org.jfree.chart.plot.PlotOrientation.VERTICAL,
                true, true, false
        );

        JFrame frame = new JFrame("Car Price Analysis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(barChart));
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
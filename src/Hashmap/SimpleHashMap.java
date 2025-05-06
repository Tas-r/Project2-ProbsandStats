package Hashmap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SimpleHashMap {

    static class Node {
        String key;
        String value;

        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<Node>[] table;
    private int size;
    public int capacity;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    public List<Integer> entryCounts = new ArrayList<>();
    public List<Integer> capacities = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public SimpleHashMap(int initialCapacity) {
        capacity = initialCapacity;
        table = new LinkedList[capacity];
        size = 0;
    }

    private int hash(String key) {
        int hash = 0;
        for (char c : key.toCharArray()) {
            hash += c;
        }
        return hash % capacity;
    }

    public void put(String key, String value) {
        if ((double) size / capacity > LOAD_FACTOR_THRESHOLD) {
            resize();
        }

        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }
        for (Node node : table[index]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        table[index].add(new Node(key, value));
        size++;

        // Log every entry for small datasets
        entryCounts.add(size);
        capacities.add(capacity);
    }

    public boolean contains(String key) {
        int index = hash(key);
        if (table[index] != null) {
            for (Node node : table[index]) {
                if (node.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        System.out.println("Resizing from " + capacity + " to " + (capacity * 2));
        LinkedList<Node>[] oldTable = table;
        capacity *= 2;
        table = new LinkedList[capacity];
        size = 0;
        for (LinkedList<Node> bucket : oldTable) {
            if (bucket != null) {
                for (Node node : bucket) {
                    put(node.key, node.value);
                }
            }
        }
    }

    public double averageLoad() {
        int nonEmptyBuckets = 0;
        for (LinkedList<Node> bucket : table) {
            if (bucket != null && !bucket.isEmpty()) {
                nonEmptyBuckets++;
            }
        }
        return (double) size / nonEmptyBuckets;
    }

    public int maxBucketSize() {
        int max = 0;
        for (LinkedList<Node> bucket : table) {
            if (bucket != null) {
                max = Math.max(max, bucket.size());
            }
        }
        return max;
    }

    // Chart drawing is now INSIDE Hashmap.SimpleHashMap
    public void showCapacityGrowthChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < entryCounts.size(); i++) {
            dataset.addValue(capacities.get(i), "Capacity", entryCounts.get(i));
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "HashMap Capacity Growth",
                "Entries Inserted",
                "Capacity",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        JFrame chartFrame = new JFrame("Capacity Growth Chart");
        chartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chartFrame.add(new ChartPanel(lineChart));
        chartFrame.setSize(800, 600);
        chartFrame.setLocationRelativeTo(null);
        chartFrame.setVisible(true);
    }
}


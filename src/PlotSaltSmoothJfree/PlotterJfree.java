package PlotSaltSmoothJfree;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class PlotterJfree {
    private double x;
    private double y;

    public PlotterJfree(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Generate coordinates for main polynomial (y = x^3)
    public static List<PlotterJfree> generateMainCoordinates(double startingX, double endX, double step) {
        List<PlotterJfree> polynomialGraph = new ArrayList<>();
        for (double x = startingX; x <= endX + step / 2; x += step) { // Include endX
            double y = Math.pow(x, 3); // y = x^3
            polynomialGraph.add(new PlotterJfree(x, y));
            System.out.printf("Main - x: %.2f, y: %.2f%n", x, y);
        }
        return polynomialGraph;
    }

    // Generate coordinates with noise (PlotSaltSmoothJfree.SalterJfe)
    public static List<PlotterJfree> generateSaltedCoordinates(double startingX, double endX, double step, SalterJfe salter) {
        List<PlotterJfree> polynomialGraph = new ArrayList<>();
        for (double x = startingX; x <= endX + step / 2; x += step) {
            double baseY = Math.pow(x, 3); // y = x^3
            double y = salter.applyNoise(baseY); // Apply noise
            polynomialGraph.add(new PlotterJfree(x, y));
            System.out.printf("Salted - x: %.2f, y: %.2f%n", x, y);
        }
        return polynomialGraph;
    }

    // Generate coordinates for smoother graph (y = x^3 with smaller step)
    public static List<PlotterJfree> generateSmoothedCoordinates(double startingX, double endX, double step) {
        List<PlotterJfree> polynomialGraph = new ArrayList<>();
        for (double x = startingX; x <= endX + step / 2; x += step) {
            double y = Math.pow(x, 3); // y = x^3
            polynomialGraph.add(new PlotterJfree(x, y));
            System.out.printf("Smoothed - x: %.2f, y: %.2f%n", x, y);
        }
        return polynomialGraph;
    }

    public static JFreeChart createChart(double startX, double endX, double mainStep, double smoothStep, SalterJfe salter) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        // Main polynomial (y = x^3)
        XYSeries mainSeries = new XYSeries("Main Polynomial");
        List<PlotterJfree> mainGraph = generateMainCoordinates(startX, endX, mainStep);
        for (PlotterJfree p : mainGraph) {
            mainSeries.add(p.getX(), p.getY());
        }
        dataset.addSeries(mainSeries);

        // Salted polynomial (y = x^3 + noise)
        XYSeries saltedSeries = new XYSeries("Salted Polynomial");
        List<PlotterJfree> saltedGraph = generateSaltedCoordinates(startX, endX, mainStep, salter);
        for (PlotterJfree p : saltedGraph) {
            saltedSeries.add(p.getX(), p.getY());
        }
        dataset.addSeries(saltedSeries);

        // Smoothed polynomial (y = x^3 with smaller step)
        XYSeries smoothedSeries = new XYSeries("Smoothed Polynomial");
        List<PlotterJfree> smoothedGraph = generateSmoothedCoordinates(startX, endX, smoothStep);
        for (PlotterJfree p : smoothedGraph) {
            smoothedSeries.add(p.getX(), p.getY());
        }
        dataset.addSeries(smoothedSeries);

        return ChartFactory.createXYLineChart(
                "Polynomial Graph (y = x^3 Variants)",
                "X",
                "Y",
                dataset
        );
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Polynomial Plotter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // Initial chart
        double startX = -10.0;
        double endX = 10.0;
        double mainStep = 1.0; // Step size for main and salted graphs
        SmootherJfe smoother = new SmootherJfe(0.1); // Smaller step size for smoothed graph
        SalterJfe salter = new SalterJfe(100.0); // Noise amplitude of ±100
        JFreeChart chart = createChart(startX, endX, mainStep, smoother.getStepSize(), salter);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(700, 500));

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new java.awt.BorderLayout());
        panel.add(chartPanel, java.awt.BorderLayout.CENTER);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
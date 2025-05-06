package SimplePlotSaltSmooth;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;


public class PlotterGraph {

    private int x;
    private int y;

    public PlotterGraph(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static List <PlotterGraph>  generateCoordinates(int startingX, int endX) {
        List<PlotterGraph> PolynomialGraph = new ArrayList<>();

        for (int x = startingX; x <= endX; x += 1) {
            int y = (int) Math.pow(x, 3);
            PolynomialGraph.add(new PlotterGraph(x, y));
            System.out.printf("x: %d, y: %d%n", x, y);
        }

        return PolynomialGraph;
    }

    public static void ExportToCSV (List <PlotterGraph> PolynomialGraph)

    {
        try (FileWriter writer = new FileWriter("PolynomialGraph.csv")) {
            writer.write("x,y\n");

            for (PlotterGraph p : PolynomialGraph) {
                writer.write(String.format("%d,%d%n", p.getX(), p.getY()));
            }

            System.out.println("Data successfully exported to PolynomialGraph.csv");

        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }


    }

}










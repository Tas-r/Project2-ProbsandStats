package SimplePlotSaltSmooth;

import java.util.List;

public class PlotterGraphTester {
    public static void main(String[] args) {
        List<PlotterGraph> PolynomialGraph = PlotterGraph.generateCoordinates(-5, 5);
        PlotterGraph.ExportToCSV(PolynomialGraph);
    }
}

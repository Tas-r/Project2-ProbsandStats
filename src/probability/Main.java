package probability;

public class Main {
    public static void main(String[] args) {
        StatsLibrary stats = new StatsLibrary();

        // Example 1: Poisson Distribution
        stats.setPoissonParameters(2.5); // λ = 2.5
        System.out.println("Poisson P(X = 2): " + stats.getPoissonProbability(2));
        System.out.println("Poisson Mean: " + stats.getPoissonMean());
        System.out.println("Poisson Variance: " + stats.getPoissonVariance());

        // Example 2: Tschebyscheff's Theorem
        stats.setTschebyscheffParameters(stats.getPoissonMean(), stats.getPoissonVariance());
        double k = 2.0;
        System.out.println("Tschebyscheff P(|X - μ| < " + k + "σ): >= " + stats.getTschebyscheffProbabilityWithinK(k));

        // Example 3: Marginal Probability of Y1
        double[][] jointProb = {
            {0.1, 0.2, 0.05}, // P(Y1=0, Y2=0), P(Y1=0, Y2=1), P(Y1=0, Y2=2)
            {0.15, 0.1, 0.1}, // P(Y1=1, Y2=0), P(Y1=1, Y2=1), P(Y1=1, Y2=2)
            {0.05, 0.15, 0.1}  // P(Y1=2, Y2=0), P(Y1=2, Y2=1), P(Y1=2, Y2=2)
        };
        stats.setMarginalProbabilityParameters(jointProb);
        double[] marginalY1 = stats.getMarginalY1();
        System.out.println("Marginal Probability of Y1:");
        for (int i = 0; i < marginalY1.length; i++) {
            System.out.println("P(Y1 = " + i + ") = " + marginalY1[i]);
        }
    }
}
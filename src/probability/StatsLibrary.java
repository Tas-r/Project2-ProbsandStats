package probability;

public class StatsLibrary {
    // Poisson Distribution fields and methods
    private double lambda;

    public StatsLibrary() {
        // Default constructor for general use
    }

    // Poisson constructor equivalent
    public void setPoissonParameters(double lambda) {
        if (lambda <= 0) {
            throw new IllegalArgumentException("Lambda must be positive.");
        }
        this.lambda = lambda;
    }

    // Poisson PMF: P(X = k) = (e^(-λ) * λ^k) / k!
    public double getPoissonProbability(int k) {
        if (k < 0) return 0.0;
        return Math.exp(-lambda) * Math.pow(lambda, k) / factorial(k);
    }

    // Calculate factorial
    private long factorial(int n) {
        if (n == 0) return 1;
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // Get Poisson mean (λ)
    public double getPoissonMean() {
        return lambda;
    }

    // Get Poisson variance (λ)
    public double getPoissonVariance() {
        return lambda;
    }

    // Tschebyscheff's Theorem fields and methods
    private double mean;
    private double variance;

    public void setTschebyscheffParameters(double mean, double variance) {
        if (variance < 0) {
            throw new IllegalArgumentException("Variance cannot be negative.");
        }
        this.mean = mean;
        this.variance = variance;
    }

    // Tschebyscheff's Theorem: P(|X - μ| ≥ kσ) ≤ 1/k^2
    // Returns probability that X lies within k standard deviations of the mean
    public double getTschebyscheffProbabilityWithinK(double k) {
        if (k <= 0) {
            throw new IllegalArgumentException("k must be positive.");
        }
        double standardDeviation = Math.sqrt(variance);
        // P(|X - μ| < kσ) = 1 - P(|X - μ| ≥ kσ) ≥ 1 - 1/k^2
        return 1.0 - (1.0 / (k * k));
    }

    // Marginal Probability fields and methods
    private double[][] jointProb;
    private int y1Size;
    private int y2Size;

    public void setMarginalProbabilityParameters(double[][] jointProb) {
        if (jointProb == null || jointProb.length == 0 || jointProb[0].length == 0) {
            throw new IllegalArgumentException("Invalid joint probability matrix.");
        }
        this.jointProb = jointProb;
        this.y1Size = jointProb.length;
        this.y2Size = jointProb[0].length;
    }

    // Compute marginal probability of Y1: P(Y1 = y1) = Σ P(Y1 = y1, Y2 = y2) over all y2
    public double[] getMarginalY1() {
        double[] marginalY1 = new double[y1Size];
        for (int i = 0; i < y1Size; i++) {
            double sum = 0.0;
            for (int j = 0; j < y2Size; j++) {
                sum += jointProb[i][j];
            }
            marginalY1[i] = sum;
        }
        return marginalY1;
    }
}
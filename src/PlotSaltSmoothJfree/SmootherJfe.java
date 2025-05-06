package PlotSaltSmoothJfree;

public class SmootherJfe {
    private double stepSize;

    public SmootherJfe(double stepSize) {
        if (stepSize <= 0) {
            throw new IllegalArgumentException("Step size must be positive");
        }
        this.stepSize = stepSize;
    }

    public double getStepSize() {
        return stepSize;
    }

    public void setStepSize(double stepSize) {
        if (stepSize <= 0) {
            throw new IllegalArgumentException("Step size must be positive");
        }
        this.stepSize = stepSize;
    }
}
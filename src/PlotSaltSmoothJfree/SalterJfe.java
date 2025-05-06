package PlotSaltSmoothJfree;

import java.util.Random;

public class SalterJfe {
    private double noiseAmplitude;
    private Random random;

    public SalterJfe(double noiseAmplitude) {
        if (noiseAmplitude < 0) {
            throw new IllegalArgumentException("Noise amplitude must be non-negative");
        }
        this.noiseAmplitude = noiseAmplitude;
        this.random = new Random();
    }

    public double applyNoise(double baseValue) {
        // Add random noise in the range [-noiseAmplitude, +noiseAmplitude]
        double noise = (random.nextDouble() * 2 - 1) * noiseAmplitude;
        return baseValue + noise;
    }

    public double getNoiseAmplitude() {
        return noiseAmplitude;
    }

    public void setNoiseAmplitude(double noiseAmplitude) {
        if (noiseAmplitude < 0) {
            throw new IllegalArgumentException("Noise amplitude must be non-negative");
        }
        this.noiseAmplitude = noiseAmplitude;
    }
}
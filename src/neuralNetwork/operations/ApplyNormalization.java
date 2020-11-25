package neuralNetwork.operations;

import org.ejml.simple.SimpleMatrix;

public class ApplyNormalization extends ApplyToMatrix {
    private double minD;
    private double maxD;
    private double minN;
    private double maxN;

    public ApplyNormalization(SimpleMatrix data, double minN, double maxN){
        this.minD = data.elementMinAbs();
        this.maxD = data.elementMaxAbs();
        this.minN = minN;
        this.maxN = maxN;
    }

    @Override
    public double operation(double input) {
        return ((input - minD)*(maxN - minN)/(maxD - minD)) + minN;
    }
}

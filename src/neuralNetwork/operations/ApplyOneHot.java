package neuralNetwork.operations;

import org.ejml.simple.SimpleMatrix;

public class ApplyOneHot extends ApplyToMatrix {
    private double maxValue;

    public ApplyOneHot(SimpleMatrix data){
        this.maxValue = data.elementMaxAbs();
    }

    @Override
    public double operation(double input) {
        if (input < maxValue){
            return 0.0;
        }
        else{
            return 1.0;
        }
    }
}

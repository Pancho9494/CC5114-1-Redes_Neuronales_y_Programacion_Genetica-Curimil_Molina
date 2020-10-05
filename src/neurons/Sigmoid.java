package neurons;

import java.util.ArrayList;
import java.util.Arrays;

public class Sigmoid extends Perceptron {
    double threshold = 0.5;

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public double compute(double x1, double x2){
        if (activation(new ArrayList(Arrays.asList(x1,x2)),getWeights(), getBias()) <= threshold){
            return -1.0;
        }
        else{
            return 1.0;
        }
    }

    public double activation(ArrayList<Double> x, ArrayList<Double> w, double b){
        double linearOut = 0;
        for (double input: x){
            linearOut += input*w.get(x.indexOf(input));
        }
        return 1/(1 + Math.exp(-(linearOut + b)));
    }
}

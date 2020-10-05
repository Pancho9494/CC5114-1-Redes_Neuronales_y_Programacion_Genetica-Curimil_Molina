package operations;

import neurons.Perceptron;

public class BinaryAdder{
    Perceptron perceptron = new Perceptron();
    private double inner;
    private double outer1;
    private double outer2;

    public BinaryAdder(){
        perceptron.NAND();
    }

    private void compute(int x1, int x2){
        this.inner = perceptron.compute(x1,x2);
        this.outer1 = perceptron.compute(x1,inner);
        this.outer2 = perceptron.compute(x2,inner);
    }

    public double sum(int x1, int x2) {
        this.compute(x1,x2);
        return perceptron.compute(outer1,outer2);
    }

    public double cOut(int x1, int x2){
        this.compute(x1,x2);
        return perceptron.compute(inner,inner);
    }
}

package operations;

public class ApplyTanh extends ApplyToMatrix {
    @Override
    public double operation(double input) {
        return Math.tanh(input);
    }
}

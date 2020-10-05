package operations;

public class ApplySigmoid extends ApplyToMatrix{
    @Override
    public double operation(double input) {
        return 1/(1 + Math.exp(-input));
    }
}

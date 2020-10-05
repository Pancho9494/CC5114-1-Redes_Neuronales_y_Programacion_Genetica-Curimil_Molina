package operations;

/**
 * Template for ApplyToMatrix
 * Overrides the operation method
 */
public class ApplyTanh extends ApplyToMatrix {

    /**
     * Applies the hyperbolic tangent to the input
     * @param input input from the matrix
     * @return the new value of that position in the matrix
     */
    @Override
    public double operation(double input) {
        return Math.tanh(input);
    }
}

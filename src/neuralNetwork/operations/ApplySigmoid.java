package neuralNetwork.operations;

/**
 * Template for ApplyToMatrix
 * Overrides the operation method
 */
public class ApplySigmoid extends ApplyToMatrix{

    /**
     * Applies the sigmoid function to the input
     * @param input input from the matrix
     * @return the new value of that position in the matrix
     */
    @Override
    public double operation(double input) {
        return 1/(1 + Math.exp(-input));
    }
}

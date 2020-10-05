package operations;

/**
 * Template for ApplyToMatrix
 * Overrides the operation method
 */
public class ApplyActivation extends ApplyToMatrix{

    /**
     * Applies the activation function to the input
     * @param input input from the matrix
     * @return the new value of that position in the matrix
     */
    @Override
    public double operation(double input) {
        if (input >= 0.5){
            return 1;
        }
        else{
            return 0;
        }
    }
}

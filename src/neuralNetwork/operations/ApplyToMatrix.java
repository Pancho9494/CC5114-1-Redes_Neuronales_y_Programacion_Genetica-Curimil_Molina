package neuralNetwork.operations;

import org.ejml.simple.SimpleMatrix;

/**
 * Template patter design
 * Applies an operation to each element of a matrix
 * If A is the input matrix and B is the output:
 *      bij = operation(aij)
 */
public abstract class ApplyToMatrix {
    /**
     * Abstract method meant to be replaced by inheriting classes
     * @param input input from the matrix
     * @return the new value of that position in the matrix
     */
    public abstract double operation(double input);

    /**
     * Goes through the matrix changing each of its values using the defined operation
     * @param input input from the matrix
     * @return the new value of that position in the matrix
     */
    public SimpleMatrix applyFunction(SimpleMatrix input){
        SimpleMatrix output = input.copy();
        for (int i = 0; i < output.numRows(); i ++){
            for (int j = 0; j < output.numCols(); j ++){
                output.set(i,j,operation(output.get(i,j)));
            }
        }
        return output;
    }
}



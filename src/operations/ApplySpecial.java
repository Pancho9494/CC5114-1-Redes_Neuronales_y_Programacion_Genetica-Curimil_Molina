package operations;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

/**
 * This class is used to store methods that make some operations with matrices
 * Made because I couldn't get this to work with the ejml
 */
public class ApplySpecial extends ApplyToMatrix{

    /**
     * The supposed purpose of this matrix
     * Replaces each value with a 0
     */
    @Override
    public double operation(double input) {
        return 0;
    }

    /**
     * In python using numpy you can add a vector to a matrix
     * Suppose the matrix has dimensions mxn
     * If the vector has dimensions 1xn then the values get added by column
     * If the vector has dimensions mx1 then the values get added by row
     * This method emulates that behavior
     * @param input1 Matrix
     * @param input2 Vector
     * @return The sum of the inputs given the instructions listed above
     */
    public SimpleMatrix applyVector(SimpleMatrix input1, SimpleMatrix input2){
        SimpleMatrix inp1 = input1.copy();
        SimpleMatrix inp2 = input2.copy();
        if (inp2.numCols() == 1){
            applyFunction(input1);
            for (int i = 0; i < inp1.numRows(); i ++){
                for (int j = 0; j < inp1.numCols(); j ++){
                    inp1.set(i,j,inp1.get(i,j) + inp2.get(i,0));
                }
            }
        }
        else if (inp2.numRows() == 1){
            for (int i = 0; i < inp1.numRows(); i ++){
                for (int j = 0; j < inp1.numCols(); j ++){
                    inp1.set(i,j,inp1.get(i,j) + inp2.get(0,j));
                }
            }
        }
        return inp1;
    }

    /**
     * Calculates the mean of a matrix given an axis
     * If mode == 1 then it calculates just the sum
     * Else, it calculates the mean
     * @param input The input matrix
     * @param axis  The axis where the mean will be calculated
     * @param mode  Mode 1 : Sum ; Else: Mean
     * @return the new value of that position in the matrix
     */
    public SimpleMatrix meanAlongRows(SimpleMatrix input, int axis, int mode){
        SimpleMatrix out = new SimpleMatrix(input.numRows(),1);
        double div = input.numCols();
        if (axis == 0){
            input = input.transpose();
            div = input.numCols();
        }
        if (mode == 1){
            div = div/div;
        }
        for (int i = 0; i < input.numRows(); i++) {
            double sum = 0;
            for (int j = 0; j < input.numCols(); j++) {
                sum += input.get(i, j);
            }
            out.set(i,0,sum/div);
        }
        if (axis == 0){
            out = out.transpose();
        }
        return out;
    }
}

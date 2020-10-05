package operations;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

public class ApplySpecial extends ApplyToMatrix{
    @Override
    public double operation(double input) {
        return 0;
    }

    public SimpleMatrix applyVector(SimpleMatrix input1, SimpleMatrix input2){
        SimpleMatrix inp1 = input1.copy();
        SimpleMatrix inp2 = input2.copy();
        if (inp1.numCols() == 1){
            applyFunction(input1);
            for (int i = 0; i < inp1.numRows(); i ++){
                for (int j = 0; j < inp1.numCols(); j ++){
                    inp1.set(i,j,inp1.get(i,j) + inp2.get(i,0));
                }
            }
        }
        else if (inp1.numRows() == 1){
            for (int i = 0; i < inp1.numRows(); i ++){
                for (int j = 0; j < inp1.numCols(); j ++){
                    inp1.set(i,j,inp1.get(i,j) + inp2.get(0,j));
                }
            }
        }
        return inp1;
    }

    /**
     * mode 1: sum
     * else: mean
     * @param input
     * @param axis
     * @param mode
     * @return
     */
    public SimpleMatrix meanAlongRows(SimpleMatrix input, int axis, int mode){
        SimpleMatrix out = new SimpleMatrix(input.numRows(),1);
        double div = input.numCols();
        if (axis == 1){
            out = new SimpleMatrix(input.numCols(),1);
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
        if (axis == 1){
            out.transpose();
        }
        return out;
    }
}

package operations;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

public abstract class ApplyToMatrix {

    public abstract double operation(double input);

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



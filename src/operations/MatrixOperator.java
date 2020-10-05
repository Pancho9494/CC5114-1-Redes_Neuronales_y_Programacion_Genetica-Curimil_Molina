package operations;

import exceptions.WrongVectorSizeException;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class makes operations between matrices represented as ArrayList<ArrayList<>>
 */
public class MatrixOperator {
    Random random = new Random();

    public MatrixOperator(int seed){
        this.random.setSeed(seed);
    }

    /**
     * Applies the selected function to each value of the Matrix A (represented as ArrayList<ArrayList<Double>>)
     * Current selected function: square function (Truncated to 4 decimal values)
     * @param A The original Matrix
     * @return The resulting matrix of applying the function to each value of A
     */
    public ArrayList<ArrayList<Double>> mapSquareInMatrix(ArrayList<ArrayList<Double>> A){
        ArrayList<ArrayList<Double>> C = new ArrayList<>();
        for (int i = 0; i < A.size(); i++){
            ArrayList<Double> tempArray = new ArrayList<>();
            for (int j = 0; j < A.get(0).size(); j++){
                tempArray.add(Math.floor(Math.pow(A.get(i).get(j),2)*10000)/10000);
            }
            C.add(tempArray);
        }
        return C;
    }

    /**
     * Calculates the multiplication between two Matrices
     * Both represented as ArrayList<ArrayList<Double>>
     * @param A Matrix of shape (m,n)
     * @param B Matrix of shape (n,p)
     * @return The product between A and B; it must have shape (m,p)
     * @throws WrongVectorSizeException (If the amount of columns in A doesn't match the amount of rows in B)
     */
    public ArrayList<ArrayList<Double>> matrixMultiplication(ArrayList<ArrayList<Double>> A, ArrayList<ArrayList<Double>> B) throws WrongVectorSizeException {
        if (A.get(0).size() != B.size()){
            throw new WrongVectorSizeException("Input sizes don't match");
        }
        ArrayList<ArrayList<Double>> C = new ArrayList<>();
        if (B.get(0).size() == 1){
            for (int i = 0; i < A.size(); i++){
                ArrayList<Double> tempArray = new ArrayList<>();
                for (int j = 0; j < A.get(0).size(); j++){
                    tempArray.add(A.get(i).get(j)*B.get(0).get(j));
                }
                C.add(tempArray);
            }
        }
        else {
            for (int m = 0; m < A.size(); m++) {
                ArrayList<Double> tempArray = new ArrayList<>();
                for (int p = 0; p < B.get(0).size(); p++) {
                    double sum = 0;
                    for (int n = 0; n < B.size(); n++) {
                        sum += A.get(m).get(n) * B.get(n).get(p);
                    }
                    tempArray.add(sum);
                }
                C.add(tempArray);
            }
        }
        return C;
    }

    /**
     * Applies a multiplication to each value of the Matrix A (represented as ArrayList<ArrayList<Double>>)
     * @param A The original Matrix
     * @param value The value that is multiplicated with each element of A
     * @return The resulting matrix of applying the function to each value of A
     */
    public ArrayList<ArrayList<Double>> mapMultiplicationInMatrix(ArrayList<ArrayList<Double>> A, double value){
        ArrayList<ArrayList<Double>> C = new ArrayList<>();
        for (int i = 0; i < A.size(); i++){
            ArrayList<Double> tempArray = new ArrayList<>();
            for (int j = 0; j < A.get(0).size(); j++){
                tempArray.add(Math.floor(A.get(i).get(j)*value*10000)/10000);
            }
            C.add(tempArray);
        }
        return C;
    }


    /**
     * Sums the values of a vector represented as ArrayList<ArrayList<Double>>
     * @param A The vector of shape (n,1)
     * @return The sum of the n values of A
     */
    public double sumVector(ArrayList<ArrayList<Double>> A){
        double sum = 0;
        for (ArrayList<Double> row : A){
            for (double value: row){
                sum += value;
            }
        }
        return sum;
    }



    /**
     * Calculates the mean of a matrix over the rows axis (1) .i.e sum all the values in each column
     * The matrix is represented as ArrayList<ArrayList<Double>>
     *
     * !!! Can also be used to just sum over each row, using numberOfExamples = 1 !!!
     * @param A The original Matrix of shape (m,n)
     * @return The resulting vector of shape (2,1)
     */
    public ArrayList<ArrayList<Double>> meanAlongRows(ArrayList<ArrayList<Double>> A, double numberOfExamples){
        ArrayList<ArrayList<Double>> C = new ArrayList<>();
        for (ArrayList<Double> row: A){
            double sum = 0;
            ArrayList<Double> tempArray = new ArrayList<>();
            for (double value: row){
                sum += value;
            }
            tempArray.add(sum/numberOfExamples);
            C.add(tempArray);
        }
        return C;
    }


    /**
     * Applies the selected function to each value of the Matrix A (represented as ArrayList<ArrayList<Double>>)
     * Current selected function: sigmoid function (Truncated to 4 decimal values)
     * @param A The original Matrix
     * @return The resulting matrix of applying the function to each value of A
     */
    public ArrayList<ArrayList<Double>> mapSigmoidInMatrix(ArrayList<ArrayList<Double>> A){
        ArrayList<ArrayList<Double>> C = new ArrayList<>();
        for (int i = 0; i < A.size(); i++){
            ArrayList<Double> tempArray = new ArrayList<>();
            for (int j = 0; j < A.get(0).size(); j++){
                tempArray.add(Math.floor(1/(1 + Math.exp(-(A.get(i).get(j))))* 10000) / 10000);
            }
            C.add(tempArray);
        }
        return C;
    }


    /**
     * Applies the selected function to each value of the Matrix A (represented as ArrayList<ArrayList<Double>>)
     * Current selected function: Math.tanh() (Truncated to 4 decimal values)
     * @param A The original Matrix
     * @return The resulting matrix of applying the function to each value of A
     */
    public ArrayList<ArrayList<Double>> mapTanhInMatrix(ArrayList<ArrayList<Double>> A){
        ArrayList<ArrayList<Double>> C = new ArrayList<>();
        for (int i = 0; i < A.size(); i++){
            ArrayList<Double> tempArray = new ArrayList<>();
            for (int j = 0; j < A.get(0).size(); j++){
                tempArray.add(Math.floor(Math.tanh(A.get(i).get(j))* 10000) / 10000);
            }
            C.add(tempArray);
        }
        return C;
    }



    /**
     * Adds two matrices together
     * Both are represented as ArrayList<ArrayList<Double>>
     * If B is a vector of shape (m,1) then each value of B gets added to each row of A
     * If B is a vector of shape (1,m) then each value of B gets added to each column of A
     * If neither A and B have one dimension as 1, then the matrices adds as usual
     *
     * !!! Can be used to subtract using mode = -1 !!!
     * @param A First matrix
     * @param B Second Matrix (could be vector of shape (m,1) or (1,m))
     * @return The sum of A and B
     */
    public ArrayList<ArrayList<Double>> matrixAddition(ArrayList<ArrayList<Double>> A, ArrayList<ArrayList<Double>> B, double mode){
        ArrayList<ArrayList<Double>> C = new ArrayList<>();
        if (B.get(0).size() == 1){
            for (int i = 0; i < A.size(); i++){
                ArrayList<Double> tempArray = new ArrayList<>();
                for (int j = 0; j < A.get(0).size(); j++){
                    tempArray.add(A.get(i).get(j) + mode*B.get(i).get(0));
                }
                C.add(tempArray);
            }
        }
        else if (B.size() == 1){
            for (int i = 0; i < A.size(); i++){
                ArrayList<Double> tempArray = new ArrayList<>();
                for (int j = 0; j < A.get(0).size(); j++){
                    tempArray.add(A.get(i).get(j) + mode*B.get(0).get(j));
                }
                C.add(tempArray);
            }
        }
        else {
            for (int i = 0; i < A.size(); i++){
                ArrayList<Double> tempArray = new ArrayList<>();
                for (int j = 0; j < A.get(0).size(); j++){
                    tempArray.add(A.get(i).get(j) + mode*B.get(i).get(j));
                }
                C.add(tempArray);
            }
        }
        return C;
    }


    public ArrayList<ArrayList<Double>> multiplyMatrix(ArrayList<ArrayList<Double>> A, ArrayList<ArrayList<Double>> B){
        ArrayList<ArrayList<Double>> C = new ArrayList<>();
        for (int i = 0; i < A.size(); i++){
            ArrayList<Double> tempArray = new ArrayList<>();
            for (int j = 0; j < A.get(0).size(); j++){
                tempArray.add(A.get(i).get(j)*B.get(i).get(j));
            }
            C.add(tempArray);
        }
        return C;
    }




    /**
     * Generates a Matrix of shape (layer1,layer2) represented as ArrayList<ArrayList<Double>>
     * @param layer1 The amount of rows in the matrix
     * @param layer2 The amount of columns in the matrix
     * @param targetWeights The empty ArrayList<Double> that will store the generated matrix
     */
    public void arrayListMaker(int layer1, int layer2, ArrayList<ArrayList<Double>> targetWeights) {
        for (int i = 0; i < layer2; i++){
            ArrayList<Double> tempWeights = new ArrayList<>();
            for (int j = 0; j < layer1; j++){
                tempWeights.add(random.nextGaussian());
            }
            targetWeights.add(tempWeights);
        }
    }

    /**
     * Calculates the transpose of a Matrix represented as ArrayList<ArrayList<Double>>
     * @param A The original matrix
     * @return The transposed matrix
     */
    public ArrayList<ArrayList<Double>> transpose(ArrayList<ArrayList<Double>> A){
        ArrayList<ArrayList<Double>> B = new ArrayList<>();
        for (int i = 0; i < A.get(0).size(); i++){
            ArrayList<Double> tempArray = new ArrayList<>();
            for (int j = 0; j < A.size(); j++){
                tempArray.add(A.get(j).get(i));
            }
            B.add(tempArray);
        }
        return B;

    }

}

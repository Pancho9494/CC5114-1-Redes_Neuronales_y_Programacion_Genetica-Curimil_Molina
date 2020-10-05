package test;

import exceptions.WrongVectorSizeException;
import neurons.NeuralNetwork;
import operations.ApplySpecial;
import operations.MatrixOperator;
import org.ejml.simple.SimpleMatrix;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class NeuralNetworkTest {
    NeuralNetwork NN = new NeuralNetwork();
    SimpleMatrix X = new SimpleMatrix(2,4);
    SimpleMatrix Y = new SimpleMatrix(1,4);
    MatrixOperator MO = new MatrixOperator(42);
    SimpleMatrix A;
//    ArrayList<ArrayList<Double>> A = new ArrayList();



    @Test
    public void initializeTest(){
        NN.initializeParameters(2,4,1);
        assertEquals(4,NN.getW1().numRows());
        assertEquals(2,NN.getW1().numCols());

        assertEquals(1,NN.getW2().numRows());
        assertEquals(4,NN.getW2().numCols());

        assertEquals(4,NN.getB1().numRows());
        assertEquals(1,NN.getB1().numCols());

        assertEquals(1,NN.getB2().numRows());
        assertEquals(1,NN.getB2().numCols());
    }

    @Before
    public void setUp(){
        NN.initializeParameters(2,4,1);
        SimpleMatrix X = new SimpleMatrix(2,4);
        X.setRow(0,0,0,0,1,1);
        X.setRow(1,0,0,1,0,1);

        SimpleMatrix Y = new SimpleMatrix(1,4);
        Y.setRow(0,0,0,1,1,0);
    }

    @Test
    public void forwardPropagationTest(){
        SimpleMatrix input = new SimpleMatrix(2,4);
        input.setRow(0,0,0,0,1,1);
        input.setRow(1,0,0,1,0,1);

        SimpleMatrix A2 = NN.forwardPropagation(input);
        assertEquals(1,A2.numRows());
        assertEquals(4,A2.numCols());
    }

    @Test
    public void calculateCostTest() throws WrongVectorSizeException {
        A = new SimpleMatrix(2,3);
        A.setRow(0,0,1.0,2.0,3.0);
        A.setRow(1,0,4.0,5.0,6.0);

        NN.initializeParameters(2,4,1);
        SimpleMatrix B = new SimpleMatrix(2,3);
        B.setRow(0,0,3.0,2.0,1.0);
        B.setRow(1,0,6.0,5.0,4.0);

        assertEquals(A.numCols(),B.numCols());
        assertEquals(A.numRows(),B.numRows());
        assertEquals(0.666,Math.floor(NN.calculateCost(A,B)*1000)/1000);
    }

    @Test
    public void backPropagationTest(){
        NN.forwardPropagation(X);
        assertEquals(4,NN.getActivationValues().get(0).numRows());
        assertEquals(4,NN.getActivationValues().get(0).numCols());

        assertEquals(1,NN.getActivationValues().get(1).numRows());
        assertEquals(4,NN.getActivationValues().get(1).numCols());

        NN.backwardsPropagation(X,Y);
        assertEquals(4,NN.getGradients().get(0).numRows());
        assertEquals(2,NN.getGradients().get(0).numCols());

        assertEquals(4,NN.getGradients().get(1).numRows());
        assertEquals(1,NN.getGradients().get(1).numCols());

        assertEquals(1,NN.getGradients().get(2).numRows());
        assertEquals(4,NN.getGradients().get(2).numCols());

        assertEquals(1,NN.getGradients().get(3).numRows());
        assertEquals(1,NN.getGradients().get(3).numCols());
    }

    @Test
    public void updateParametersTest(){
        double learningRate = 0.01;

        NN.forwardPropagation(X);
        NN.backwardsPropagation(X,Y);

        // Expected Values
        SimpleMatrix eW1 = NN.getW1().copy().minus(NN.getGradients().get(0).scale(learningRate));
        SimpleMatrix eb1 = NN.getB1().copy().minus(NN.getGradients().get(1).scale(learningRate));
        SimpleMatrix eW2 = NN.getW2().copy().minus(NN.getGradients().get(2).scale(learningRate));
        SimpleMatrix eb2 = NN.getB2().copy().minus(NN.getGradients().get(3).scale(learningRate));

        NN.updateParameters(learningRate);

        // New Values
        SimpleMatrix nW1 = NN.getW1();
        SimpleMatrix nb1 = NN.getB1();
        SimpleMatrix nW2 = NN.getW2();
        SimpleMatrix nb2 = NN.getB2();

        // If updateParameters works the new values should be equal to the expected values
        assertEquals(eW1.elementSum(), nW1.elementSum());
        assertEquals(eb1.elementSum(), nb1.elementSum());
        assertEquals(eW2.elementSum(), nW2.elementSum());
        assertEquals(eb2.elementSum(), nb2.elementSum());
    }

    @Test
    public void modelPredictTest(){
        int nIterations = 10000;
        double learningRate = 0.01;
        SimpleMatrix X = new SimpleMatrix(2,4);
        X.setRow(0,0,0,0,1,1);
        X.setRow(1,0,0,1,0,1);

        SimpleMatrix Y = new SimpleMatrix(1,4);
        Y.setRow(0,0,0,1,1,0);

        NN.model(X,Y,nIterations,learningRate);
        SimpleMatrix predictions = NN.predict(X);

        assertEquals(0.0,predictions.get(0,0));
        assertEquals(1.0,predictions.get(0,1));
        assertEquals(1.0,predictions.get(1,0));
        assertEquals(0.0,predictions.get(1,1));
    }

//    @Test
//    public void transposeTest(){
//        ArrayList<ArrayList<Double>> transposed = MO.transpose(A);
//
//        assertEquals(3,transposed.size());
//        assertEquals(2,transposed.get(0).size());
//
//        assertEquals(1.0,transposed.get(0).get(0));
//        assertEquals(4.0,transposed.get(0).get(1));
//
//        assertEquals(2.0,transposed.get(1).get(0));
//        assertEquals(5.0,transposed.get(1).get(1));
//
//        assertEquals(3.0,transposed.get(2).get(0));
//        assertEquals(6.0,transposed.get(2).get(1));
//    }


//    @Test
//    public void matrixMultiplicationTest() throws WrongVectorSizeException {
//        ArrayList<ArrayList<Double>> B = new ArrayList();
//        ArrayList<Double> Brow1 = new ArrayList<>(Arrays.asList(1.0,2.0));
//        ArrayList<Double> Brow2 = new ArrayList<>(Arrays.asList(3.0,4.0));
//        ArrayList<Double> Brow3 = new ArrayList<>(Arrays.asList(5.0,6.0));
//        B.add(Brow1);
//        B.add(Brow2);
//        B.add(Brow3);
//
//        ArrayList<ArrayList<Double>> result = MO.matrixMultiplication(A,B);
//
//        assertEquals(2,result.size());
//        assertEquals(2,result.get(0).size());
//
//        assertEquals(22.0,result.get(0).get(0));
//        assertEquals(28.0,result.get(0).get(1));
//        assertEquals(49.0,result.get(1).get(0));
//        assertEquals(64.0,result.get(1).get(1));
//    }

//    @Test
//    public void matrixAdditionTest(){
//        ArrayList<ArrayList<Double>> A = new ArrayList();
//        ArrayList<Double> Arow1 = new ArrayList<>(Arrays.asList(1.0,2.0));
//        ArrayList<Double> Arow2 = new ArrayList<>(Arrays.asList(3.0,4.0));
//        A.add(Arow1);
//        A.add(Arow2);
//
//        ArrayList<ArrayList<Double>> column = new ArrayList();
//        ArrayList<Double> columnRow1 = new ArrayList<>(Arrays.asList(1.0));
//        ArrayList<Double> columnRow2 = new ArrayList<>(Arrays.asList(3.0));
//        column.add(columnRow1);
//        column.add(columnRow2);
//
//        ArrayList<ArrayList<Double>> row = new ArrayList();
//        ArrayList<Double> rowRow1 = new ArrayList<>(Arrays.asList(1.0,3.0));
//        row.add(rowRow1);
//
//        ArrayList<ArrayList<Double>> addColumn = MO.matrixAddition(A,column,1);
//        assertEquals(2,addColumn.size());
//        assertEquals(2,addColumn.get(0).size());
//        assertEquals(2.0,addColumn.get(0).get(0));
//        assertEquals(3.0,addColumn.get(0).get(1));
//        assertEquals(6.0,addColumn.get(1).get(0));
//        assertEquals(7.0,addColumn.get(1).get(1));
//
//        ArrayList<ArrayList<Double>> addRow = MO.matrixAddition(A,row,1);
//        assertEquals(2,addRow.size());
//        assertEquals(2,addRow.get(0).size());
//        assertEquals(2.0,addRow.get(0).get(0));
//        assertEquals(5.0,addRow.get(0).get(1));
//        assertEquals(4.0,addRow.get(1).get(0));
//        assertEquals(7.0,addRow.get(1).get(1));
//
//        ArrayList<ArrayList<Double>> addMatrix = MO.matrixAddition(A,A,1);
//        assertEquals(2,addMatrix.size());
//        assertEquals(2,addMatrix.get(0).size());
//        assertEquals(2.0,addMatrix.get(0).get(0));
//        assertEquals(4.0,addMatrix.get(0).get(1));
//        assertEquals(6.0,addMatrix.get(1).get(0));
//        assertEquals(8.0,addMatrix.get(1).get(1));
//    }

//    @Test
//    public void mapFunctionTest(){
//        ArrayList<ArrayList<Double>> tanh = MO.mapTanhInMatrix(A);
//        assertEquals(0.7615,tanh.get(0).get(0));
//        assertEquals(0.9640,tanh.get(0).get(1));
//        assertEquals(0.9993,tanh.get(1).get(0));
//        assertEquals(0.9999,tanh.get(1).get(1));
//    }


//    @Test
//    public void meanAlongRowsTest(){
//        ArrayList<ArrayList<Double>> meanRows = MO.meanAlongRows(A,A.get(0).size());
//        assertEquals(2,meanRows.size());
//        assertEquals(1,meanRows.get(0).size());
//        assertEquals(6.0/3,meanRows.get(0).get(0));
//        assertEquals(15.0/3,meanRows.get(1).get(0));
//
//        ArrayList<ArrayList<Double>> sumRows = MO.meanAlongRows(A,1);
//        assertEquals(2,sumRows.size());
//        assertEquals(1,sumRows.get(0).size());
//        assertEquals(6.0,sumRows.get(0).get(0));
//        assertEquals(15.0,sumRows.get(1).get(0));
//    }

//    @Test
//    public void sumVectorTest(){
//        ArrayList<ArrayList<Double>> meanRows = MO.meanAlongRows(A,A.get(0).size());
//        double value = MO.sumVector(meanRows);
//        assertEquals(7.0,value);
//    }




}

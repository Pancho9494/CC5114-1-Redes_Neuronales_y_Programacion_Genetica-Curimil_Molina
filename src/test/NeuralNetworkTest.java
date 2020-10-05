package test;

import exceptions.WrongVectorSizeException;
import neurons.NeuralNetwork;
import operations.ApplyActivation;
import operations.ApplySigmoid;
import operations.ApplySpecial;
import operations.ApplyTanh;
import org.ejml.simple.SimpleMatrix;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class NeuralNetworkTest {
    NeuralNetwork NN = new NeuralNetwork();
    SimpleMatrix X = new SimpleMatrix(2,4);
    SimpleMatrix Y = new SimpleMatrix(1,4);
    SimpleMatrix A;



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

        SimpleMatrix A2 = NN.forwardsPropagation(input);
        assertEquals(1,A2.numRows());
        assertEquals(4,A2.numCols());
    }

    @Test
    public void calculateCostTest() {
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
        NN.forwardsPropagation(X);
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

        NN.forwardsPropagation(X);
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

        // Training Data
        SimpleMatrix X = new SimpleMatrix(2,4);
        X.setRow(0,0,0,0,1,1);
        X.setRow(1,0,0,1,0,1);

        SimpleMatrix Y = new SimpleMatrix(1,4);
        Y.setRow(0,0,0,1,1,0);

        // Train the model
        NN.model(X,Y,nIterations,learningRate);

        // Testing Data
        SimpleMatrix m00 = new SimpleMatrix(2,1);
        m00.setRow(0,0,0);
        m00.setRow(1,0,0);


        SimpleMatrix m01 = new SimpleMatrix(2,1);
        m01.setRow(0,0,0);
        m01.setRow(1,0,1);

        SimpleMatrix m10 = new SimpleMatrix(2,1);
        m10.setRow(0,0,1);
        m10.setRow(1,0,0);

        SimpleMatrix m11 = new SimpleMatrix(2,1);
        m11.setRow(0,0,1);
        m11.setRow(1,0,1);

        // Make predictions
        SimpleMatrix predictions00 = NN.predict(m00);
        SimpleMatrix predictions01 = NN.predict(m01);
        SimpleMatrix predictions10 = NN.predict(m10);
        SimpleMatrix predictions11 = NN.predict(m11);

        assertEquals(0.0,predictions00.get(0,0));
        assertEquals(1.0,predictions01.get(0,0));
        assertEquals(1.0,predictions10.get(0,0));
        assertEquals(0.0,predictions11.get(0,0));
    }

    @Test
    public void applyOperationsTest(){
        ApplySigmoid sigmoid = new ApplySigmoid();
        ApplyTanh tanh = new ApplyTanh();
        ApplyActivation act = new ApplyActivation();
        ApplySpecial special = new ApplySpecial();

        SimpleMatrix test = new SimpleMatrix(2,2);
        test.setRow(0,0,0.1,0.6);
        test.setRow(1,0,0.8,0.3);

        // Apply sigmoid
        SimpleMatrix testSigmoid = test.copy();
        testSigmoid = sigmoid.applyFunction(testSigmoid);
        assertEquals(0.5249,Math.floor(testSigmoid.get(0,0)*10000)/10000);
        assertEquals(0.6456,Math.floor(testSigmoid.get(0,1)*10000)/10000);
        assertEquals(0.6899,Math.floor(testSigmoid.get(1,0)*10000)/10000);
        assertEquals(0.5744,Math.floor(testSigmoid.get(1,1)*10000)/10000);

        // Apply hyperbolic tangent
        SimpleMatrix testTanh = test.copy();
        testTanh = tanh.applyFunction(testTanh);
        assertEquals(0.0996,Math.floor(testTanh.get(0,0)*10000)/10000);
        assertEquals(0.5370,Math.floor(testTanh.get(0,1)*10000)/10000);
        assertEquals(0.6640,Math.floor(testTanh.get(1,0)*10000)/10000);
        assertEquals(0.2913,Math.floor(testTanh.get(1,1)*10000)/10000);

        // Apply activation function
        SimpleMatrix testAct = test.copy();
        testAct = act.applyFunction(testAct);
        assertEquals(0.0,Math.floor(testAct.get(0,0)*10000)/10000);
        assertEquals(1.0,Math.floor(testAct.get(0,1)*10000)/10000);
        assertEquals(1.0,Math.floor(testAct.get(1,0)*10000)/10000);
        assertEquals(0.0,Math.floor(testAct.get(1,1)*10000)/10000);

        // Apply special functions
        SimpleMatrix a = new SimpleMatrix(1,2);
        a.setRow(0,0,2,0);

        SimpleMatrix b = new SimpleMatrix(2,1);
        b.setRow(0,0,0);
        b.setRow(1,0,4);

        SimpleMatrix testApplyVectorHorizontal = test.copy();
        testApplyVectorHorizontal = special.applyVector(testApplyVectorHorizontal,a);
        assertEquals(2.1,testApplyVectorHorizontal.get(0,0));
        assertEquals(0.6,testApplyVectorHorizontal.get(0,1));
        assertEquals(2.8,testApplyVectorHorizontal.get(1,0));
        assertEquals(0.3,testApplyVectorHorizontal.get(1,1));

        SimpleMatrix testApplyVectorVertical = test.copy();
        testApplyVectorVertical = special.applyVector(testApplyVectorVertical,b);
        assertEquals(0.1,testApplyVectorVertical.get(0,0));
        assertEquals(0.6,testApplyVectorVertical.get(0,1));
        assertEquals(4.8,testApplyVectorVertical.get(1,0));
        assertEquals(4.3,testApplyVectorVertical.get(1,1));

        SimpleMatrix testMeanAx0 = test.copy();
        testMeanAx0 = special.meanAlongRows(testMeanAx0,0,0);
        assertEquals(0.45,Math.floor(testMeanAx0.get(0,0)*10000)/10000);
        assertEquals(0.45,Math.floor(testMeanAx0.get(0,1)*10000)/10000);

        SimpleMatrix testMeanAx1 = test.copy();
        testMeanAx1 = special.meanAlongRows(testMeanAx1,1,0);
        assertEquals(0.35,Math.floor(testMeanAx1.get(0,0)*10000)/10000);
        assertEquals(0.55,Math.floor(testMeanAx1.get(1,0)*10000)/10000);

        SimpleMatrix testSum0 = test.copy();
        testSum0 = special.meanAlongRows(testSum0,0,1);
        assertEquals(0.9,Math.floor(testSum0.get(0,0)*10000)/10000);
        assertEquals(0.9,Math.floor(testSum0.get(0,1)*10000)/10000);

        SimpleMatrix testSum1 = test.copy();
        testSum1 = special.meanAlongRows(testSum1,1,1);
        assertEquals(0.7,Math.floor(testSum1.get(0,0)*10000)/10000);
        assertEquals(1.1,Math.floor(testSum1.get(1,0)*10000)/10000);

    }
}
package neuralNetwork.test;

import neuralNetwork.neurons.NeuralNetwork;
import neuralNetwork.operations.*;
import org.ejml.simple.SimpleMatrix;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void modelPredictTest() throws FileNotFoundException {
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

        // Make predictions
        SimpleMatrix test00 = new SimpleMatrix(2,1);
        test00.setRow(0,0,0);
        test00.setRow(1,0,0);
        SimpleMatrix pred00 = NN.predict(test00);

        SimpleMatrix test01 = new SimpleMatrix(2,1);
        test01.setRow(0,0,0);
        test01.setRow(1,0,1);
        SimpleMatrix pred01 = NN.predict(test01);

        SimpleMatrix test10 = new SimpleMatrix(2,1);
        test10.setRow(0,0,1);
        test10.setRow(1,0,0);
        SimpleMatrix pred10 = NN.predict(test10);

        SimpleMatrix test11 = new SimpleMatrix(2,1);
        test11.setRow(0,0,1);
        test11.setRow(1,0,1);
        SimpleMatrix pred11 = NN.predict(test11);

//        String conf = NN.ConfusionMatrix(predictions,Y);

        assertEquals(0.0,pred00.get(0,0));
        assertEquals(1.0,pred01.get(0,0));
        assertEquals(1.0,pred10.get(0,0));
        assertEquals(1.0,pred11.get(0,0));
    }

    /**
     * Apply sigmoid function
     * Apply hyperbolic tangent
     * Apply activation function
     */
    @Test
    public void applyOperationsTest(){
        ApplySigmoid sigmoid = new ApplySigmoid();
        ApplyTanh tanh = new ApplyTanh();
        ApplyActivation act = new ApplyActivation();

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
    }

    /**
     * Apply Vector Addition
     */
    @Test
    public void applyVectorAdition(){
        ApplySpecial special = new ApplySpecial();
        SimpleMatrix test = new SimpleMatrix(2,2);
        test.setRow(0,0,0.1,0.6);
        test.setRow(1,0,0.8,0.3);

        SimpleMatrix a = new SimpleMatrix(1,2);
        a.setRow(0,0,2,0);

        SimpleMatrix b = new SimpleMatrix(2,1);
        b.setRow(0,0,0);
        b.setRow(1,0,4);

        SimpleMatrix testApplyVectorHorizontal = test.copy();
        testApplyVectorHorizontal = special.applyVectorAddition(testApplyVectorHorizontal,a);
        assertEquals(2.1,testApplyVectorHorizontal.get(0,0));
        assertEquals(0.6,testApplyVectorHorizontal.get(0,1));
        assertEquals(2.8,testApplyVectorHorizontal.get(1,0));
        assertEquals(0.3,testApplyVectorHorizontal.get(1,1));

        SimpleMatrix testApplyVectorVertical = test.copy();
        testApplyVectorVertical = special.applyVectorAddition(testApplyVectorVertical,b);
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

    /**
     * Apply VectorMultiplication
     */
    @Test
    public void applyVectorMultiplication(){
        ApplySpecial applyVec = new ApplySpecial();
        SimpleMatrix x1 = new SimpleMatrix(3,3);
        x1.setRow(0,0,0,1,2);
        x1.setRow(1,0,3,4,5);
        x1.setRow(2,0,6,7,8);

        SimpleMatrix x2 = new SimpleMatrix(1,3);
        x2.setRow(0,0,0,1,2);

        SimpleMatrix result = applyVec.applyVectorMultiplication(x1,x2);
        assertEquals(0.0,result.get(0,0));
        assertEquals(1.0,result.get(0,1));
        assertEquals(4.0,result.get(0,2));
        assertEquals(0.0,result.get(1,0));
        assertEquals(4.0,result.get(1,1));
        assertEquals(10.0,result.get(1,2));
        assertEquals(0.0,result.get(2,0));
        assertEquals(7.0,result.get(2,1));
        assertEquals(16.0,result.get(2,2));

        SimpleMatrix x3 = new SimpleMatrix(3,1);
        x3.setRow(0,0,0);
        x3.setRow(1,0,1);
        x3.setRow(2,0,2);
        SimpleMatrix result2 = applyVec.applyVectorMultiplication(x1,x3);
        assertEquals(0.0,result2.get(0,0));
        assertEquals(0.0,result2.get(0,1));
        assertEquals(0.0,result2.get(0,2));
        assertEquals(3.0,result2.get(1,0));
        assertEquals(4.0,result2.get(1,1));
        assertEquals(5.0,result2.get(1,2));
        assertEquals(12.0,result2.get(2,0));
        assertEquals(14.0,result2.get(2,1));
        assertEquals(16.0,result2.get(2,2));
    }

    /**
     * Apply One Hot Encoding
     * Apply Normalization
     */
    @Test
    public void applyMoreOperationsTest(){
        SimpleMatrix a = new SimpleMatrix(1,4);
        a.setRow(0,0,0.4,0.5,0.9,0.8);

        ApplyOneHot oneHot = new ApplyOneHot(a);
        SimpleMatrix aEncoded = oneHot.applyFunction(a);
        assertEquals(0.0,aEncoded.get(0,0));
        assertEquals(0.0,aEncoded.get(0,1));
        assertEquals(1.0,aEncoded.get(0,2));
        assertEquals(0.0,aEncoded.get(0,3));

        SimpleMatrix b = new SimpleMatrix(3,3);
        b.setRow(0,0,39,124,46);
        b.setRow(1,0,45,13,75);
        b.setRow(2,0,69,87,15);

        ApplyNormalization norm = new ApplyNormalization(b,0,1);
        SimpleMatrix bNormal = norm.applyFunction(b);
        for (int i = 0; i < b.numRows(); i ++){
            for (int j = 0; j < b.numCols(); j ++){
                assertTrue(bNormal.get(i,j) >= 0 && bNormal.get(i,j) <= 1);
            }
        }
    }
}
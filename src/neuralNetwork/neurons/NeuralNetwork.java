package neuralNetwork.neurons;

import java.util.ArrayList;
import java.util.Random;

import neuralNetwork.operations.*;
import org.ejml.simple.*;

/**
 * This class represents a neural Network with only one hidden layer
 * The amount of neuralNetwork.datasets.neurons per layer is dictated by nFirst, nHidden and nOut respectively
 */
public class NeuralNetwork {
    private int randomSeed = 42;
    Random random = new Random(randomSeed);
    private int nFirst;
    private int nHidden;
    private int nOut;
    private SimpleMatrix w1;
    private SimpleMatrix w2;
    private SimpleMatrix b1;
    private SimpleMatrix b2;
    private ArrayList<SimpleMatrix> activationValues = new ArrayList<>();
    private ArrayList<SimpleMatrix> gradients = new ArrayList<>();
    private ArrayList<Double> trainingCost = new ArrayList<>();
    private ArrayList<Integer> confusionData = new ArrayList<>();


    /**
     * Sets the initial parameters of the neural network (random weights and zero bias)
     * It also clears the activationValues and gradients ArrayLists
     * @param nFirst    number of neuralNetwork.datasets.neurons in first layer
     * @param nHidden   number of neuralNetwork.datasets.neurons in hidden layer
     * @param nOut      number of neuralNetwork.datasets.neurons in output layer
     */
    public void initializeParameters(int nFirst, int nHidden, int nOut){
        this.nFirst = nFirst;
        this.nHidden = nHidden;
        this.nOut = nOut;
        w1 = SimpleMatrix.random_DDRM(nHidden,nFirst, -2.0, 2.0, random);
        w2 = SimpleMatrix.random_DDRM(nOut,nHidden, -2.0, 2.0, random);
        b1 = new SimpleMatrix(nHidden,1);
        b2 = new SimpleMatrix(nOut,1);
        activationValues.clear();
        gradients.clear();
    }

    /**
     * Propagates the input through the neural network
     * @param input Input vector represented as ArrayList<ArrayList<Double>>
     * @return output vector represented as ArrayList<ArrayList<Double>>
     */
    public SimpleMatrix forwardsPropagation(SimpleMatrix input) {
        ApplyTanh tanh = new ApplyTanh();
        ApplySigmoid sigmoid = new ApplySigmoid();
        ApplySpecial addVector = new ApplySpecial();
        // Z value for Layer 1
        SimpleMatrix Z1 = w1.mult(input);
        Z1 = addVector.applyVectorAddition(Z1,b1);
        // Activation value for Layer 1
        SimpleMatrix a1 = tanh.applyFunction(Z1);
        // Z value for Layer 2
        // In this case b is of dimensions 1x1
        // If this needs to be expanded, add the condition to the ApplyVector operator
        SimpleMatrix Z2 = w2.mult(a1).plus(b2.get(0,0));
        // Activation value for Layer 2
        SimpleMatrix a2 = sigmoid.applyFunction(Z2);

        //Save results
        activationValues.add(a1);
        activationValues.add(a2);
        return a2;
    }


    /**
     * Calculates the cost between prediction and the actual output
     * @param NNOutput The prediction made by the neural network
     * @param actualOutput The actual output
     * @return A decimal number representing the cost (double)
     */
    public double calculateCost(SimpleMatrix NNOutput, SimpleMatrix actualOutput) {
        ApplySpecial meanAlongRows = new ApplySpecial();
        SimpleMatrix one = NNOutput.plus(-1,actualOutput).elementPower(2).scale(0.5); // 0.5*(A2 - y) ** 2
        SimpleMatrix two = meanAlongRows.meanAlongRows(one,1,0); // (0.5*(A2 - y) ** 2).mean(axis=1)
        return two.elementSum()/w1.numRows(); // np.sum((0.5*(A2 - y) ** 2).mean(axis=1))
    }


    /**
     * Propagates the error backwards through the neural network
     * Calculates the new weights and biases of this iteration and
     * stores them in the gradients ArrayList
     * @param input         The input given to the neural network
     * @param actualOutput  The output the neural network should produce, given the input
     */
    public void backwardsPropagation(SimpleMatrix input, SimpleMatrix actualOutput) {
        ApplySpecial special = new ApplySpecial();
        // Get activation values
        SimpleMatrix a1 = activationValues.get(0);
        SimpleMatrix a2 = activationValues.get(1);

        // Compute the difference between the predicted value and the real values
        SimpleMatrix dZ2 = a2.minus(actualOutput);
        SimpleMatrix dW2 = dZ2.mult(a1.transpose()).scale(1.0/w1.numRows());
        SimpleMatrix db2 = special.meanAlongRows(dZ2,1,1).scale(1.0/w1.numRows());

//        SimpleMatrix db2 = new SimpleMatrix(1,1);
//        double db2Content = dZ2.elementSum()/w1.numRows();
//        db2.setRow(0,0,db2Content);

        SimpleMatrix dZ1 = w2.transpose().mult(dZ2).elementMult(a1.elementPower(2).negative().plus(1));
        SimpleMatrix dW1 = dZ1.mult(input.transpose()).scale(1.0/w1.numRows());
        SimpleMatrix db1 = special.meanAlongRows(dZ1,1,1).scale(1.0/w1.numRows()); // mode 1: sum

        // Save Results
        gradients.add(dW1);
        gradients.add(db1);
        gradients.add(dW2);
        gradients.add(db2);
    }

    /**
     * Updates the weights and biases to their new values
     * This shouldn't be called unless backwardsPropagation and forwardsPropagation were called first
     * @param learningRate  The size of the changes made in each iteration
     */
    public void updateParameters(double learningRate){
        SimpleMatrix dw1 = gradients.get(0);
        SimpleMatrix db1 = gradients.get(1);
        SimpleMatrix dw2 = gradients.get(2);
        SimpleMatrix db2 = gradients.get(3);

        w1 = w1.minus(dw1.scale(learningRate));
        b1 = b1.minus(db1.scale(learningRate));
        w2 = w2.minus(dw2.scale(learningRate));
        b2 = b2.minus(db2.scale(learningRate));
    }


    /**
     * Trains the neural network
     * @param input         The input given
     * @param actualOutput  The desired output
     * @param n_iterations  The number of iterations wanted
     * @param learningRate  The size of the changes made in each iteration
     */
    public void model(SimpleMatrix input, SimpleMatrix actualOutput, int n_iterations, double learningRate) {
        initializeParameters(nFirst,nHidden,nOut);
        int k = 0;
        for (int i = 0; i < n_iterations; i++){
            SimpleMatrix A2 = forwardsPropagation(input);
            // Confusion Matrix Data
            ApplyOneHot oneHot = new ApplyOneHot(A2);
            SimpleMatrix coded = oneHot.applyFunction(A2);
            double cost = calculateCost(A2,actualOutput);
            trainingCost.add(cost);
            backwardsPropagation(input,actualOutput);
            updateParameters(learningRate);
        }
    }

    public SimpleMatrix predict(SimpleMatrix input){
        ApplyActivation activation = new ApplyActivation();
        SimpleMatrix A2 = forwardsPropagation(input);
        return activation.applyFunction(A2);
    }

    public String ConfusionMatrix(SimpleMatrix predictions, SimpleMatrix actualOutput){
        ArrayList<Integer> A = new ArrayList<>();
        ArrayList<Integer> B = new ArrayList<>();
        ArrayList<Integer> C = new ArrayList<>();
        int Aa = 0;
        int Ab = 0;
        int Ac = 0;
        int Ba = 0;
        int Bb = 0;
        int Bc = 0;
        int Ca = 0;
        int Cb = 0;
        int Cc = 0;
        // Horrible
        for (int i = 0; i < predictions.numCols(); i++){
            // A
            if (actualOutput.get(0,i) == 1){
                if (predictions.get(0,i) == 1){
                    Aa ++;
                }
                else if (predictions.get(1,i) == 1){
                    Ab ++;
                }
                else if (predictions.get(2,i) == 1){
                    Ac ++;
                }
            }
            // B
            else if (actualOutput.get(1,i) == 1){
                if (predictions.get(0,i) == 1){
                    Ba ++;
                }
                else if (predictions.get(1,i) == 1){
                    Bb ++;
                }
                else if (predictions.get(2,i) == 1){
                    Bc ++;
                }
            }
            // C
            else if (actualOutput.get(2,i) == 1){
                if (predictions.get(0,i) == 1){
                    Ca ++;
                }
                else if (predictions.get(1,i) == 1){
                    Cb ++;
                }
                else if (predictions.get(2,i) == 1){
                    Cc ++;
                }
            }
        }
        String output = String.format("               |  Real Setosa  |  Real Versicolor  |  Real Virginica  \n" +
                        "Pred Setosa    |      %d       |         %d         |        %d        \n" +
                        "Pred Versicolor|      %d       |         %d         |        %d        \n" +
                        "Pred Virginica |      %d       |         %d         |        %d        ",
                        Aa, Ab, Ac, Ba, Bb, Bc, Ca, Cb, Cc);
        return output;
    }

    // Getters and Setters

    public ArrayList<SimpleMatrix> getGradients() {
        return gradients;
    }

    public ArrayList<SimpleMatrix> getActivationValues() {
        return activationValues;
    }

    public SimpleMatrix getW1() {
        return w1;
    }

    public SimpleMatrix getW2() {
        return w2;
    }

    public SimpleMatrix getB1() {
        return b1;
    }

    public SimpleMatrix getB2() { return b2; }

    public ArrayList<Double> getTrainingCost() {
        return trainingCost;
    }

    public void setNumberOfLayers(int nFirst, int nHidden, int nOut){
        this.nFirst = nFirst;
        this.nHidden = nHidden;
        this.nOut = nOut;
    }
}

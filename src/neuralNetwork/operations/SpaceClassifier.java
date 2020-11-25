//package neuralNetwork.datasets.operations;
//
//import neuralNetwork.datasets.neurons.Perceptron;
//import neuralNetwork.datasets.neurons.Sigmoid;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Random;
//
//public class SpaceClassifier {
//    private Perceptron neuron = new Perceptron();
//    private final Random random;
//    public ArrayList<Double> trainingXInputs = new ArrayList<>();
//    public ArrayList<Double> trainingYInputs = new ArrayList<>();
//    public ArrayList<Double> trainingOutputs = new ArrayList<>();
//    public ArrayList<Double> testingXInputs = new ArrayList<>();
//    public ArrayList<Double> testingYInputs = new ArrayList<>();
//    public ArrayList<Double> testingOutputs = new ArrayList<>();
//
//    public SpaceClassifier(){
//        this.random = new Random();
//        random.setSeed(42);
//        neuron.setBias(1);
//        neuron.addWeights(random.nextInt(4) - 2);
//        neuron.addWeights(random.nextInt(4) - 2);
//    }
//
//    public double compute(double x1, double x2){
//        return neuron.compute(x1,x2);
//    }
//
//
//    /**
//     * Arbitrary function the perceptron must learn
//     * @param x input of the x coordinate
//     * @return the corresponding y output
//     */
//    public double function(double x){
//        return 2*x - 13;
//    }
//
//    public void generateTestPoints(int numberOfSamples){
//        ArrayList<Double> xInput = new ArrayList<>();
//        ArrayList<Double> yInput = new ArrayList<>();
//        while (numberOfSamples != 0){
//            double anX = (double) random.nextInt(50) - 25;
//            double anY = (double) random.nextInt(50) - 25;
//            xInput.add(anX);
//            yInput.add(anY);
//            if (function(anX) >= anY){
//                testingOutputs.add(1.0);
//            }
//            else {
//                testingOutputs.add(-1.0);
//            }
//            numberOfSamples -= 1;
//        }
//        this.testingXInputs = xInput;
//        this.testingYInputs = yInput;
//    }
//
//
//    public void generateTrainingSet(int cycles){
//        ArrayList<Double> xInputs = new ArrayList<>();
//        ArrayList<Double> yInputs = new ArrayList<>();
//        ArrayList<Double> expectedOutputs = new ArrayList<>();
//        while(cycles != 0){
//            double anX = (double) random.nextInt(50) - 25;
//            double anY = (double) random.nextInt(50) - 25;
//            double expectedOutput = function(anX);
//
//            xInputs.add(anX);
//            yInputs.add(anY);
//            if (expectedOutput >= anY){
//                expectedOutputs.add(1.0);
//            }
//            else {
//                expectedOutputs.add(-1.0);
//            }
//            cycles -=1;
//        }
//        this.trainingXInputs = xInputs;
//        this.trainingYInputs = yInputs;
//        this.trainingOutputs = expectedOutputs;
//    }
//
//    public void train(double lr, int samples){
//        // Create the training samples
//        generateTrainingSet(samples);
//        neuron.setLr(lr);
//        ArrayList<ArrayList<Double>> exmaples = new ArrayList<>();
//        for (double input: trainingXInputs){
//            exmaples.add(new ArrayList<Double>(Arrays.asList(input, trainingYInputs.get(trainingXInputs.indexOf(input)))));
//        }
//        neuron.train(exmaples,trainingOutputs);
//    }
//
//    public int[] range(int limit) {
//        int[] vector = new int[limit];
//        for (int i = 0; i < limit; ++i) {
//            vector[i] = i;
//        }
//        return vector;
//    }
//
//
//}

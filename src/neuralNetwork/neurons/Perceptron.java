//package neuralNetwork.datasets.neurons;
//
//import java.util.ArrayList;
//
//public class Perceptron {
//    private double bias;
//    private double lr= 0.1;
//    private ArrayList<Double> weights = new ArrayList<>();
//
//    public void setLr(double lr) {
//        this.lr = lr;
//    }
//
//    public void addWeights(double w){
//        this.weights.add(w);
//    }
//
//    public double getBias() {
//        return bias;
//    }
//
//    public void setBias(double bias){
//        this.bias = bias;
//    }
//
//    public ArrayList<Double> getWeights() {
//        return weights;
//    }
//
//    public void setWeights(ArrayList<Double> newWeights){
//        if (this.weights.size() != newWeights.size()){
//            return;
//        }
//        else{
//            this.weights = newWeights;
//        }
//    }
//
//    // Poor implementation
//    // Not extendable
//    // Should have bias and weight parameter in perceptron constructor
//    public void AND(){
//        this.bias = -1.5;
//        weights.add(1.0);
//        weights.add(1.0);
//    }
//
//    public void OR(){
//        this.bias = -0.5;
//        weights.add(1.0);
//        weights.add(1.0);
//    }
//
//    public void NAND(){
//        this.bias = 3;
//        weights.add(-2.0);
//        weights.add(-2.0);
//    }
//
//    public double compute(double x1, double x2){
//        if (x1*weights.get(0) + x2*weights.get(1) + bias <= 0){
//            return -1.0;
//        }
//        else{
//            return 1.0;
//        }
//    }
//
//    public void train(ArrayList<ArrayList<Double>> examples, ArrayList<Double> label){
//        int count = 0;
//        ArrayList<ArrayList<Double>> visitedWeights = new ArrayList<>();
//        visitedWeights.add(this.getWeights());
//        whileloop:
//        while (count < 4) {
//            for (ArrayList<Double> example : examples) {
//                double output = compute(example.get(0), example.get(1));
//                double real = label.get(examples.indexOf(example));
//                if (output != real) {
//                    count = 0;
//                    double diff = real - output;
//                    ArrayList<Double> newWeights = new ArrayList();
//                    for (int weightPointer : range(this.getWeights().size())) {
//                        double newW = this.getWeights().get(weightPointer) + this.lr * example.get(weightPointer) * diff;
//                        newWeights.add(newW);
//                    }
//                    if (visitedWeights.contains(newWeights)) {
//                        break whileloop;
//                    }
//                    visitedWeights.add(newWeights);
//                    this.setWeights(newWeights);
//                    this.setBias(this.getBias() + this.lr * diff);
//                } else {
//                    count++;
//                }
//            }
//        }
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
//}

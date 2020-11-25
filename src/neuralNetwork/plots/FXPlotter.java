package neuralNetwork.plots;

import javafx.application.Application;
import javafx.stage.Stage;
import neuralNetwork.neurons.NeuralNetwork;
import neuralNetwork.operations.ApplyNormalization;
import neuralNetwork.operations.ApplySpecial;
import org.ejml.simple.SimpleMatrix;

import java.io.*;
import java.util.ArrayList;

/**
 * This class makes graphs using JavaFX
 * Each type of graph overwrites the init method
 */
public abstract class FXPlotter extends Application {
    NeuralNetwork NN = new NeuralNetwork();
    SimpleMatrix X;
    SimpleMatrix Y;
    ArrayList<Double> lossData = new ArrayList<>();
    int iterations;

    /**
     * Method that will be overwritten by each type of graph
     *
     * @param stage the initial stage
     */
    protected abstract void init(Stage stage);

    /**
     * Sets up the parameters of the Neural Network and trains it
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        // Load data
        setUpData();
        // Neural Network
        this.iterations = 500;
        NN.setNumberOfLayers(4, 5, 3);
        NN.model(X, Y, iterations, 0.01);
        lossData = NN.getTrainingCost();
        // Confusion Matrix
        SimpleMatrix predictions = NN.predict(X);
        System.out.println(NN.ConfusionMatrix(predictions,Y));
        // Graph
        init(stage);
    }


    /**
     * Loads the data that will go into the Neural Network
     */
    public void setUpData() {
        readData();
        ApplyNormalization norm = new ApplyNormalization(X,0,1);
        ApplySpecial shuffle = new ApplySpecial();
        X = norm.applyFunction(X);
        X = shuffle.MatrixShuffle(X);
        Y = shuffle.MatrixShuffle(Y);
    }

    /**
     * Reads a csv file and creates a SimpleMatrix representation of the inputs and the outputs
     */
    public void readData() {
        String line = "";
        int k = 0;
        X = new SimpleMatrix(4,150);
        Y = new SimpleMatrix(3,150);
        try {
            BufferedReader br = new BufferedReader(new FileReader("D:\\Carpetas\\Documentos\\Java\\CC5114-1\\Tarea1\\src\\neuralNetwork.datasets\\iris.data"));
            while (k < 150){
                line = br.readLine();
                String[] split = line.split(",");
                for (int i = 0; i < split.length - 1; i++){
                    X.set(i,k, Double.parseDouble(split[i]));
                }
                SimpleMatrix encode = encodeCategorical(split[4]);
                for (int j = 0 ; j < encode.numCols(); j++){
                    Y.set(j,k, encode.get(0,j));
                }
                k ++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Turns a categorical variable into a numerical Matrix
     * @param input The String corresponding to the desired output
     * @return A matrix
     */
    public SimpleMatrix encodeCategorical(String input){
        SimpleMatrix out = new SimpleMatrix(1,3);
        if (input.equals("Iris-setosa")){
            out.setRow(0,0,1.0,0.0,0.0);
        }
        else if (input.equals("Iris-versicolor")){
            out.setRow(0,0,0.0,1.0,0.0);
        }
        else if (input.equals("Iris-virginica")){
            out.setRow(0,0,0.0,0.0,1.0);
        }
        return out;
    }
}


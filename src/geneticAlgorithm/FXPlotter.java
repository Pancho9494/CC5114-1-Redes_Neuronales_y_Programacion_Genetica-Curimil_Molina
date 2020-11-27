package geneticAlgorithm;

import geneticAlgorithm.Individuals.Individual;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * This class makes graphs using JavaFX
 * Each type of graph overwrites the init method
 */
public abstract class FXPlotter extends Application {
    protected ArrayList<Integer> dataBest = new ArrayList<>();
    protected ArrayList<Integer> dataWorst = new ArrayList<>();
    protected ArrayList<Integer> dataMean = new ArrayList<>();
    protected Engine engine;
    protected int iterations;
    protected Individual target;

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
        init(stage);
    }


    /**
     * Loads the data that will go into the Neural Network
     */
    public void setUpData(ArrayList<Integer> data1, ArrayList<Integer> data2, ArrayList<Integer> data3) {
        this.dataBest = data1;
        this.dataWorst = data2;
        this.dataMean = data3;
        this.iterations = dataBest.size();
    }

//    public  void setController(Controller controller){
//        this.controller = controller;
//    }

    public void setTarget(Individual target){
        this.target = target;
    }

    public void setEngine(Engine engine){
        this.engine = engine;
    }
}


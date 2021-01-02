package geneticProgramming;

import geneticAlgorithm.GAEngine;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public abstract class GPFXPlotter extends Application {
    protected ArrayList<Double> dataBest = new ArrayList<>();
    protected ArrayList<Double> dataWorst = new ArrayList<>();
    protected ArrayList<Double> dataMean = new ArrayList<>();
    protected int iterations;
    protected GPEngine GPEngine;

    protected abstract void init(Stage stage);

    @Override
    public void start(Stage stage) throws Exception {
        init(stage);
    }

    public void setUpData(ArrayList<Double> data1, ArrayList<Double> data2, ArrayList<Double> data3) {
        this.dataBest = data1;
        this.dataWorst = data2;
        this.dataMean = data3;
        this.iterations = dataBest.size();
    }

    public void setGPEngine(GPEngine GPEngine){
        this.GPEngine = GPEngine;
    }
}

package plots;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import neurons.NeuralNetwork;
import operations.SpaceClassifier;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.Collections;

public abstract class FXPlotter extends Application {
    NeuralNetwork NN = new NeuralNetwork();
    SimpleMatrix training;
    SimpleMatrix trainingLabels;
    ArrayList<Double> lossData = new ArrayList<>();
    int iterations;
//    ArrayList<Double> xData = new ArrayList<>();
//    ArrayList<Double> yData = new ArrayList<>();
//    SpaceClassifier classifier = new SpaceClassifier();

    protected abstract void init(Stage stage);

    @Override
    public void start(Stage stage) throws Exception {
        setUpData();
        this.iterations = 10000;
        NN.setNumberOfLayers(2,4,1);
        NN.model(training,trainingLabels,iterations,0.01);
        lossData = NN.getTrainingCost();
        init(stage);
//        classifier.train(0.1,1540);
//        classifier.generateTestPoints(1400);
//        this.xData = classifier.testingXInputs;
//        this.yData = classifier.testingYInputs;
    }


    public void setUpData(){
        training = new SimpleMatrix(2,4);
        training.setRow(0,0,0,0,1,1);
        training.setRow(1,0,0,1,0,1);

        trainingLabels = new SimpleMatrix(1,4);
        trainingLabels.setRow(0,0,0,1,1,0);
    }






//        // Set graph points (base chart)
//        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
//        scatterChart.setLegendVisible(false);
//        scatterChart.setAnimated(false);
//        XYChart.Series<Number, Number> redData = new XYChart.Series<>();
//        XYChart.Series<Number, Number> blueData = new XYChart.Series<>();
//        for (int pointer: classifier.range(xData.size())){
//            double xPoint = xData.get(pointer);
//            double yPoint = yData.get(pointer);
//            if (classifier.compute(xPoint,yPoint) == -1.0){
//                redData.getData().add(new XYChart.Data<Number, Number>(xPoint, yPoint));
//            }
//            else{
//                blueData.getData().add(new XYChart.Data<Number, Number>(xPoint, yPoint));
//            }
//        }

//        line.getData().add(new XYChart.Data(-27, classifier.function(-27)));
//        line.getData().add(new XYChart.Data(27, classifier.function(27)));

//        // Make background invisible
//        lineChart.setLegendVisible(false);
//        lineChart.setAnimated(false);
//        lineChart.setCreateSymbols(true);
//        lineChart.setAlternativeRowFillVisible(false);
//        lineChart.setAlternativeColumnFillVisible(false);
//        lineChart.setHorizontalGridLinesVisible(false);
//        lineChart.setVerticalGridLinesVisible(false);
//        lineChart.getXAxis().setVisible(false);
//        lineChart.getYAxis().setVisible(false);
//        Object a = getClass().getResource("plots/chart.css").toExternalForm();
//        lineChart.getStylesheets().addAll((String) a);



//        scatterChart.setPrefSize(width, height);



//        scatterChart.getData().add(redData);
//        scatterChart.getData().add(blueData);

}

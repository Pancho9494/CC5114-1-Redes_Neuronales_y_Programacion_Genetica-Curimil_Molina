package geneticProgramming;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Collections;

public class FXGP extends GPFXPlotter {
    public void begin(Stage stage){
        init(stage);
    }

    @Override
    protected void init(Stage stage) {
        int width = 230;
        int height = 200;

        Group root = new Group();
        Scene scene = new Scene(root, width, height);

        int xCenter = 55;
        int yCenter = 20;
        int vSpacing = 25;

        Label title = new Label("Genetic Algorithm Results");
        title.setLayoutX(xCenter - 10);
        title.setLayoutY(yCenter - 5);

        Label bestIndividual = new Label(  "Best Individual:    " + GPEngine.getCurrentBest().print());
        bestIndividual.setLayoutX(30);
        bestIndividual.setLayoutY(yCenter + 40 + vSpacing);

        Label bestFitness = new Label("Best Fitness:         " + GPEngine.getCurrentBestFitness());
        bestFitness.setLayoutX(30);
        bestFitness.setLayoutY(yCenter + 40 + vSpacing*2);

        Button fitnessGraph = new Button("Get Fitness Graph");
        fitnessGraph.setLayoutX(xCenter);
        fitnessGraph.setLayoutY(yCenter + 45 + vSpacing*3);
        fitnessGraph.setFocusTraversable(false);
        fitnessGraph.setOnAction(event -> FitnessHistory());

//        Label Target = new Label("Target:                 " + target.chromosomeToString());
//        Target.setLayoutX(30);
//        Target.setLayoutY(yCenter + 40);
//        root.getChildren().add(Target);

        root.getChildren().addAll(title, bestIndividual, bestFitness, fitnessGraph);

        stage.setScene(scene);
        stage.setTitle("Demo");
        stage.show();
    }

    public void FitnessHistory(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        int width = 1280;
        int height = 720;
        // Set axis
        final NumberAxis xAxis = new NumberAxis(0, iterations, 100);
        final NumberAxis yAxis = new NumberAxis(Collections.min(dataWorst), Collections.max(dataBest), 500);

        xAxis.setLabel("Generations");
        yAxis.setLabel("Fitness");

        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);

        // Set line plots
        LineChart<Number,Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("Fitness over Generations");
        lineChart.setCreateSymbols(false);

        XYChart.Series lineBest = new XYChart.Series();
        lineBest.setName("Best Fitness");
        for (int i = 0; i < dataBest.size(); i++) {
            lineBest.getData().add(new XYChart.Data<Number, Number>(i, dataBest.get(i)));
        }

        XYChart.Series lineWorst = new XYChart.Series();
        lineWorst.setName("Worst Fitness");
        for (int i = 0; i < dataWorst.size(); i++) {
            lineWorst.getData().add(new XYChart.Data<Number, Number>(i, dataWorst.get(i)));
        }

        XYChart.Series lineMean = new XYChart.Series();
        lineMean.setName("Mean Fitness");
        for (int i = 0; i < dataMean.size(); i++) {
            lineMean.getData().add(new XYChart.Data<Number, Number>(i, dataMean.get(i)));
        }


        // Chart size
        lineChart.setPrefSize(width, height);
        // Add data
        lineChart.getData().addAll(lineWorst,lineMean,lineBest);
        // Add charts to root
        StackPane root = new StackPane(lineChart);
        Scene scene = new Scene(root, width, height);

        stage.setScene(scene);
        stage.setTitle("Fitness over Generations");
        stage.show();
    }
}

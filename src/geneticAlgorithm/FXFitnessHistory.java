package geneticAlgorithm;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Collections;

public class FXFitnessHistory extends FXPlotter {

    @Override
    protected void init(Stage stage) {
        int width = 1280;
        int height = 720;
        // Set axis
        final NumberAxis xAxis = new NumberAxis(0, iterations, 100);
        final NumberAxis yAxis = new NumberAxis(0, Collections.max(dataBest), 50);

        xAxis.setLabel("Generations");
        yAxis.setLabel("Fitness");

        xAxis.setAutoRanging(true);

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

    public void begin(Stage stage){
        init(stage);
    }
}

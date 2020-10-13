package plots;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Collections;

public class FXLoss extends FXPlotter {

    @Override
    protected void init(Stage stage) {
        StackPane root = new StackPane();
        int width = 1280;
        int height = 720;
        Scene scene = new Scene(root, width, height);

        // Set axis
        final NumberAxis xAxis = new NumberAxis(0, iterations, 50);
        xAxis.setLabel("Epoch");
//        int minY = lossData.indexOf(Collections.min(lossData));
        double maxY = Collections.max(lossData);
        final NumberAxis yAxis = new NumberAxis(0, maxY, 0.01);
        yAxis.setLabel("Loss");

        // Set line plot
        XYChart.Series line = new XYChart.Series();
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setCreateSymbols(false);
        for (int i = 0; i < lossData.size(); i++) {
            line.getData().add(new XYChart.Data<Number, Number>(i, lossData.get(i)));
        }
        // Chart size
        lineChart.setPrefSize(width, height);
        // Add data
        lineChart.getData().add(line);
        // Add charts to root
        root.getChildren().addAll(lineChart);

        stage.setScene(scene);
        stage.setTitle("Loss vs Iterations");
        stage.show();
    }
}

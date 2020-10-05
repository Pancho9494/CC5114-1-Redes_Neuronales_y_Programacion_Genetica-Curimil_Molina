package plots;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import operations.SpaceClassifier;

import java.util.ArrayList;

public class FXPlotter extends Application {
    ArrayList<Double> xData = new ArrayList<>();
    ArrayList<Double> yData = new ArrayList<>();
    SpaceClassifier classifier = new SpaceClassifier();


    @Override
    public void start(Stage stage) throws Exception {
        classifier.train(0.1,1540);
        classifier.generateTestPoints(1400);
        this.xData = classifier.testingXInputs;
        this.yData = classifier.testingYInputs;
        init(stage);
    }


    private void init(Stage stage){
        StackPane root = new StackPane();
        int width = 1280;
        int height = 720;
        Scene scene = new Scene(root, width, height);

        // Set axis
        final NumberAxis xAxis = new NumberAxis(-26,26,1);
        xAxis.setLabel("X");
        final NumberAxis yAxis = new NumberAxis(-26,26,1);
        yAxis.setLabel("Y");

        // Set graph points (base chart)
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setLegendVisible(false);
        scatterChart.setAnimated(false);
        XYChart.Series<Number, Number> redData = new XYChart.Series<>();
        XYChart.Series<Number, Number> blueData = new XYChart.Series<>();
        for (int pointer: classifier.range(xData.size())){
            double xPoint = xData.get(pointer);
            double yPoint = yData.get(pointer);
            if (classifier.compute(xPoint,yPoint) == -1.0){
                redData.getData().add(new XYChart.Data<Number, Number>(xPoint, yPoint));
            }
            else{
                blueData.getData().add(new XYChart.Data<Number, Number>(xPoint, yPoint));
            }
        }



        // Set line plot
        XYChart.Series line = new XYChart.Series();
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        line.getData().add(new XYChart.Data(-27, classifier.function(-27)));
        line.getData().add(new XYChart.Data(27, classifier.function(27)));

        // Make background invisible
        lineChart.setLegendVisible(false);
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(true);
        lineChart.setAlternativeRowFillVisible(false);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.setHorizontalGridLinesVisible(false);
        lineChart.setVerticalGridLinesVisible(false);
        lineChart.getXAxis().setVisible(false);
        lineChart.getYAxis().setVisible(false);
        Object a = getClass().getResource("plots/chart.css").toExternalForm();
        lineChart.getStylesheets().addAll((String) a);


        // Chart size
        scatterChart.setPrefSize(width, height);
        lineChart.setPrefSize(width,height);

        // Add data
        scatterChart.getData().add(redData);
        scatterChart.getData().add(blueData);
        lineChart.getData().add(line);

        // Add charts to root
        root.getChildren().addAll(scatterChart, lineChart);

        stage.setScene(scene);
        stage.setTitle("2D Plotter");
        stage.show();
    }
}

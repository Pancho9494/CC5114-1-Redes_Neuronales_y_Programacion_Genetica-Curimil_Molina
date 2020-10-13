//package plots;
//
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.NumberAxis;
//import javafx.scene.chart.XYChart;
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//import operations.SpaceClassifier;
//
//import java.util.ArrayList;
//
//public class LearningCurve extends Application {
//    ArrayList<Double> xData = new ArrayList<>();
//    ArrayList<Double> yData = new ArrayList<>();
//    ArrayList<Double> outputs = new ArrayList<>();
//    SpaceClassifier classifier = new SpaceClassifier();
//    private int step;
//    private int limit;
//    ArrayList<Integer> steps = new ArrayList<>();
//    ArrayList<Double> accuracy = new ArrayList<>();
//
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        this.step = 1;
//        this.limit = 2000;
//
//        classifier.generateTestPoints(2000);
//        this.xData = classifier.testingXInputs;
//        this.yData = classifier.testingYInputs;
//        this.outputs = classifier.testingOutputs;
//
//        for (int i = 0; i < limit; i++){
//            steps.add(i);
//            accuracy.add(accuracy());
//            classifier.train(0.1, step);
//        }
//        init(stage);
//    }
//
//    public double accuracy(){
//        double numerator = 0.0;
//        int denominator = xData.size();
//        for (int pointer : classifier.range(denominator)) {
//            double xData = this.xData.get(pointer);
//            double yData = this.yData.get(pointer);
//            if (classifier.compute(xData,yData) == outputs.get(pointer)){
//                numerator += 1;
//            }
//        }
//        return numerator/denominator;
//    }
//
//
//    private void init(Stage stage){
//        StackPane root = new StackPane();
//        int width = 1280;
//        int height = 720;
//        Scene scene = new Scene(root, width, height);
//
//        // Set axis
//        int upper = steps.size();
//        final NumberAxis xAxis = new NumberAxis(0,upper,10);
//        xAxis.setLabel("X");
//        final NumberAxis yAxis = new NumberAxis(0,1,0.1);
//        yAxis.setLabel("Y");
//
//        // Set line plot
//        XYChart.Series line = new XYChart.Series();
//        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
//        for (int pointer: classifier.range(steps.size())){
//            int stp = steps.get(pointer);
//            double acc = accuracy.get(pointer);
//            line.getData().add(new XYChart.Data(stp, acc));
//        }
//
//        // Chart size
//        lineChart.setPrefSize(width,height);
//
//        // Add data
//        lineChart.getData().add(line);
//
//        // Add charts to root
//        root.getChildren().addAll(lineChart);
//
//        stage.setScene(scene);
//        stage.setTitle("2D Plotter");
//        stage.show();
//    }
//}

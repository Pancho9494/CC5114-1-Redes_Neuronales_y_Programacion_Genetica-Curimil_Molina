package geneticAlgorithm;

import geneticAlgorithm.Individuals.Individual;
import geneticAlgorithm.specialObjects.Queen;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class FXGA extends FXPlotter {
    private boolean chess = false;

    public void setChess(boolean chess) {
        this.chess = chess;
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

        Label bestIndividual = new Label(  "Best Individual:    " + engine.getCurrentBest().chromosomeToString());
        bestIndividual.setLayoutX(30);
        bestIndividual.setLayoutY(yCenter + 40 + vSpacing);

        Label bestFitness = new Label("Best Fitness:         " + engine.getCurrentBestFitness());
        bestFitness.setLayoutX(30);
        bestFitness.setLayoutY(yCenter + 40 + vSpacing*2);

        Button fitnessGraph = new Button("Get Fitness Graph");
        fitnessGraph.setLayoutX(xCenter);
        fitnessGraph.setLayoutY(yCenter + 45 + vSpacing*3);
        fitnessGraph.setFocusTraversable(false);
        fitnessGraph.setOnAction(event -> FitnessHistory());

        if (chess){
            Button chessBoard = new Button("Get Chess Board");
            chessBoard.setLayoutX(xCenter);
            chessBoard.setLayoutY(yCenter + 50 + vSpacing*4);
            chessBoard.setFocusTraversable(false);
            chessBoard.setOnAction(event -> {
            try {
                makeBoard(engine.getCurrentBest().getNumberOfGenes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
            root.getChildren().add(chessBoard);
        }
        else{
            Label Target = new Label("Target:                 " + target.chromosomeToString());
            Target.setLayoutX(30);
            Target.setLayoutY(yCenter + 40);
            root.getChildren().add(Target);
        }

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

    public void begin(Stage stage){
        init(stage);
    }


    public void makeBoard(int size) throws FileNotFoundException {
        Stage primaryStage = new Stage();
        GridPane root = new GridPane();
        double piecesSize = 70 + 0.4*size;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col ++) {
                StackPane square = new StackPane();
                String color ;
                if ((row + col) % 2 == 0) {
                    color = "white";
                } else {
                    color = "black";
                }
                square.setStyle("-fx-background-color: "+color+";");
                ImageView blank = new ImageView(new Image(new FileInputStream("D:\\Carpetas\\Documentos\\Java\\CC5114-1\\Tarea1\\src\\geneticAlgorithm\\resources\\blank.png")));
                blank.setFitHeight(piecesSize);
                blank.setFitWidth(piecesSize);
                root.add(blank, col, row);
                root.add(square, col, row);
            }
        }
        ArrayList<Queen> positions = new ArrayList<>();
        Individual best = engine.getCurrentBest();
        for (int i = 0; i < best.getNumberOfGenes(); i++) {
            positions.add((Queen) best.getGene(i));
        }
        for (Queen queen: positions){
            if ((queen.getX() + queen.getY()) % 2 == 0){
                ImageView blackQueen = new ImageView(new Image(new FileInputStream("src/geneticAlgorithm/resources/blackQueen.png")));
                blackQueen.setFitHeight(piecesSize);
                blackQueen.setFitWidth(piecesSize);
                root.add(blackQueen, queen.getX(), queen.getY());
            }
            else{
                ImageView whiteQueen = new ImageView(new Image(new FileInputStream("src/geneticAlgorithm/resources/whiteQueen.png")));
                whiteQueen.setFitHeight(piecesSize);
                whiteQueen.setFitWidth(piecesSize);
                root.add(whiteQueen, queen.getX(), queen.getY());
            }
        }

        for (int i = 0; i < size; i++) {
            root.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            root.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.setTitle(size + "-Queen solution");
        primaryStage.show();
    }
}

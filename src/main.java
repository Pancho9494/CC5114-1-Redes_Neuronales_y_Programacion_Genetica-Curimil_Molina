import geneticAlgorithm.*;
import geneticAlgorithm.Individuals.*;
import geneticAlgorithm.fitness.*;
import geneticAlgorithm.Individuals.Factory.*;
import geneticAlgorithm.geneticOperators.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.tc33.jheatchart.HeatChart;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class main extends Application {
    private static FXGA demo;

    public static void main(String[] args) {
        wordFinder("paralelepipedo");
//        binaryFinder(121);
//        nQueenFinder(18);
    }

    public static void wordFinder(String input){
        String word = input;
        Engine geneticAlg = new Engine();
        IndividualFactory factory = new WordFactory();
        geneticAlg.setIndividualFactory(factory);
        FitnessMatchWords fit = new FitnessMatchWords(word, factory);
        Crossover cross = new Crossover();
        AMutation mutate = new Mutation();
        demo = new FXGA();
        demo.setTarget(fit.transformInput(word));
        demo.setEngine(geneticAlg);
        demo.setChess(false);


        geneticAlg.executeAlgorithm(100,0.2,100,
                                    word.length(), 5,fit,cross,mutate);

        demo.setUpData(geneticAlg.getBestFitnessHistory(), geneticAlg.getWorstFitnessHistory(),
                            geneticAlg.getMeanFitnessHistory());
        demo.launch();
        Platform.exit();
    }

    public static void binaryFinder(int input){
        int number = input;
        Engine geneticAlg = new Engine();
        IndividualFactory factory = new BinaryFactory();
        geneticAlg.setIndividualFactory(factory);
        FitnessBinary fit = new FitnessBinary(number, factory);
        Crossover cross = new Crossover();
        AMutation mutate = new Mutation();
        demo = new FXGA();
        demo.setTarget(fit.transformInput(number));
        demo.setEngine(geneticAlg);
        demo.setChess(false);

        geneticAlg.executeAlgorithm(100,0.4,25,
                (int) Math.floor(Math.log(number)/Math.log(2)) + 1, 5,fit, cross, mutate);

        demo.setUpData(geneticAlg.getBestFitnessHistory(), geneticAlg.getWorstFitnessHistory(),
                geneticAlg.getMeanFitnessHistory());
        demo.launch();
        Platform.exit();
    }

    public static void nQueenFinder(int size){
        int nqueens = size;
        Engine geneticAlg = new Engine();
        IndividualFactory factory = new NQueenFactory();
        geneticAlg.setIndividualFactory(factory);
        FitnessFunctions fit = new FitnessNQueens(new IndividualNull(nqueens),factory);
        ACrossover cross = new CrossoverOrdered();
        AMutation mutation = new MutationFillBoard();


        geneticAlg.executeAlgorithm(2000, 0.6, 1000,
                nqueens,25,fit,cross, mutation);

        demo = new FXGA();
        demo.setEngine(geneticAlg);
        demo.setChess(true);

        demo.setUpData(geneticAlg.getBestFitnessHistory(), geneticAlg.getWorstFitnessHistory(),
                geneticAlg.getMeanFitnessHistory());
        demo.launch();
    }

    public static void nQueenFinderHeatmap() throws IOException {
        ArrayList<Integer> numberOfGenes = new ArrayList<>(Arrays.asList(5,6,7));
        ArrayList<Integer> maxGenerations = new ArrayList<>(Arrays.asList(500,700,1000));
        ArrayList<Double> mutationRate = new ArrayList<>(Arrays.asList(0.2,0.6,0.9));
        ArrayList<Integer> populationSize = new ArrayList<>(Arrays.asList(1000,2000,2500));
        ArrayList<Integer> selectionWindowSize = new ArrayList<>(Arrays.asList(5 ,25,50));
        ArrayList<ACrossover> crossover = new ArrayList<ACrossover>(Arrays.asList(new Crossover(),
                                                            new CrossoverKeepSame(), new CrossoverOrdered()));
        double[][] zValues = new double[3][5];

        int fixedQueens = 11;
        for (int maxGen: maxGenerations){
            Engine geneticAlg = new Engine();
            IndividualFactory factory = new NQueenFactory();
            geneticAlg.setIndividualFactory(factory);

            geneticAlg.executeAlgorithm(maxGen, 0.6, 2500, fixedQueens,25,
                    new FitnessNQueens(new IndividualNull(fixedQueens),factory), new CrossoverOrdered(), new MutationShuffle());

            zValues[maxGenerations.indexOf(maxGen)][0] = geneticAlg.getCurrentBestFitness();
        }
        for (double mutRate: mutationRate){
            Engine geneticAlg = new Engine();
            IndividualFactory factory = new NQueenFactory();
            geneticAlg.setIndividualFactory(factory);

            geneticAlg.executeAlgorithm(2000, mutRate, 2500, fixedQueens,25,
                    new FitnessNQueens(new IndividualNull(fixedQueens),factory), new CrossoverOrdered(), new MutationShuffle());

            zValues[mutationRate.indexOf(mutRate)][1] = geneticAlg.getCurrentBestFitness();
        }
        for (int popSize: populationSize){
            Engine geneticAlg = new Engine();
            IndividualFactory factory = new NQueenFactory();
            geneticAlg.setIndividualFactory(factory);

            geneticAlg.executeAlgorithm(2000, 0.6, popSize, fixedQueens,25,
                    new FitnessNQueens(new IndividualNull(fixedQueens),factory), new CrossoverOrdered(), new MutationShuffle());

            zValues[populationSize.indexOf(popSize)][2] = geneticAlg.getCurrentBestFitness();
        }
        for (int windSize: selectionWindowSize){
            Engine geneticAlg = new Engine();
            IndividualFactory factory = new NQueenFactory();
            geneticAlg.setIndividualFactory(factory);

            geneticAlg.executeAlgorithm(2000, 0.6, 2500, fixedQueens,windSize,
                    new FitnessNQueens(new IndividualNull(fixedQueens),factory), new CrossoverOrdered(), new MutationShuffle());

            zValues[selectionWindowSize.indexOf(windSize)][3] = geneticAlg.getCurrentBestFitness();
        }
        for (ACrossover cross: crossover){
            Engine geneticAlg = new Engine();
            IndividualFactory factory = new NQueenFactory();
            geneticAlg.setIndividualFactory(factory);

            geneticAlg.executeAlgorithm(2000, 0.6, 2500, fixedQueens,25,
                    new FitnessNQueens(new IndividualNull(fixedQueens),factory), cross, new MutationShuffle());

            zValues[crossover.indexOf(cross)][4] = geneticAlg.getCurrentBestFitness();
        }


        HeatChart chart = new HeatChart(zValues);

        double xOffset = 1.0;
        double yOffset = 0.0;
        double xInterval = 1.0;
        double yInterval = 2.0;

        chart.setXValues(xOffset, xInterval);
        chart.setYValues(yOffset, yInterval);

        chart.saveToFile(new File("D:\\Carpetas\\Documentos\\Java\\CC5114-1\\Tarea1\\src\\geneticAlgorithm\\resources\\heatmap.png"));
    }

    @Override
    public void start(Stage stage) throws Exception {
        demo.begin(stage);
    }

}

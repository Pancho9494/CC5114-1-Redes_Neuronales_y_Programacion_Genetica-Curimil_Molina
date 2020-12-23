import geneticAlgorithm.*;
import geneticAlgorithm.Individuals.*;
import geneticAlgorithm.fitness.*;
import geneticAlgorithm.Individuals.Factory.*;
import geneticAlgorithm.geneticOperators.*;

import geneticProgramming.FXGP;
import geneticProgramming.GPEngine;
import geneticProgramming.fitness.GPFitness;
import geneticProgramming.geneticOperators.AGPCrossover;
import geneticProgramming.geneticOperators.CrossoverSubTree;
import geneticProgramming.geneticOperators.MutationSubTree;
import geneticProgramming.structure.Tree;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.tc33.jheatchart.HeatChart;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class main extends Application {
    private static FXGA demo;
    private static FXGP demoGP;

    public static void main(String[] args) {
//        wordFinder("paralelepipedo");
//        binaryFinder(121);
//        nQueenFinder(5);
        double target = 595.0;
        ArrayList<Double> numbers = new ArrayList<>();
        numbers.add(10.0);
        numbers.add(1.0);
        numbers.add(25.0);
        numbers.add(9.0);
        numbers.add(3.0);
        numbers.add(6.0);

        GPEngine GP = new GPEngine(1000,3, 0.2);
        GP.setInputNumbers(numbers);
        GPFitness fit = new GPFitness();
        fit.setTarget(target);
        CrossoverSubTree cross = new CrossoverSubTree(GP);
        MutationSubTree mutate = new MutationSubTree(GP);

        Tree result = GP.executeAlgorithm(1000,25, fit,cross,mutate);
        System.out.println(result.print2());
        System.out.println(result.evaluate());

        demoGP = new FXGP();
        demoGP.setGPEngine(GP);
        demoGP.setUpData(GP.getBestFitnessHistory(), GP.getWorstFitnessHistory(),
                GP.getMeanFitnessHistory());
        demoGP.launch();
        Platform.exit();
    }

    public static void wordFinder(String input){
        String word = input;
        GAEngine geneticAlg = new GAEngine();
        IndividualFactory factory = new WordFactory();
        geneticAlg.setIndividualFactory(factory);
        FitnessMatchWords fit = new FitnessMatchWords(word, factory);
        Crossover cross = new Crossover();
        AGAMutation mutate = new Mutation();
        demo = new FXGA();
        demo.setTarget(fit.transformInput(word));
        demo.setGAEngine(geneticAlg);
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
        GAEngine geneticAlg = new GAEngine();
        IndividualFactory factory = new BinaryFactory();
        geneticAlg.setIndividualFactory(factory);
        FitnessBinary fit = new FitnessBinary(number, factory);
        Crossover cross = new Crossover();
        AGAMutation mutate = new Mutation();
        demo = new FXGA();
        demo.setTarget(fit.transformInput(number));
        demo.setGAEngine(geneticAlg);
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
        GAEngine geneticAlg = new GAEngine();
        IndividualFactory factory = new NQueenFactory();
        geneticAlg.setIndividualFactory(factory);
        FitnessFunctions fit = new FitnessNQueens(new IndividualNull(nqueens),factory);
        AGACrossover cross = new CrossoverOrdered();
        AGAMutation mutation = new MutationFillBoard();


        geneticAlg.executeAlgorithm(2000, 0.6, 1000,
                nqueens,25,fit,cross, mutation);

        demo = new FXGA();
        demo.setGAEngine(geneticAlg);
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
        ArrayList<AGACrossover> crossover = new ArrayList<AGACrossover>(Arrays.asList(new Crossover(),
                                                            new CrossoverKeepSame(), new CrossoverOrdered()));
        double[][] zValues = new double[3][5];

        int fixedQueens = 11;
        for (int maxGen: maxGenerations){
            GAEngine geneticAlg = new GAEngine();
            IndividualFactory factory = new NQueenFactory();
            geneticAlg.setIndividualFactory(factory);

            geneticAlg.executeAlgorithm(maxGen, 0.6, 2500, fixedQueens,25,
                    new FitnessNQueens(new IndividualNull(fixedQueens),factory), new CrossoverOrdered(), new MutationShuffle());

            zValues[maxGenerations.indexOf(maxGen)][0] = geneticAlg.getCurrentBestFitness();
        }
        for (double mutRate: mutationRate){
            GAEngine geneticAlg = new GAEngine();
            IndividualFactory factory = new NQueenFactory();
            geneticAlg.setIndividualFactory(factory);

            geneticAlg.executeAlgorithm(2000, mutRate, 2500, fixedQueens,25,
                    new FitnessNQueens(new IndividualNull(fixedQueens),factory), new CrossoverOrdered(), new MutationShuffle());

            zValues[mutationRate.indexOf(mutRate)][1] = geneticAlg.getCurrentBestFitness();
        }
        for (int popSize: populationSize){
            GAEngine geneticAlg = new GAEngine();
            IndividualFactory factory = new NQueenFactory();
            geneticAlg.setIndividualFactory(factory);

            geneticAlg.executeAlgorithm(2000, 0.6, popSize, fixedQueens,25,
                    new FitnessNQueens(new IndividualNull(fixedQueens),factory), new CrossoverOrdered(), new MutationShuffle());

            zValues[populationSize.indexOf(popSize)][2] = geneticAlg.getCurrentBestFitness();
        }
        for (int windSize: selectionWindowSize){
            GAEngine geneticAlg = new GAEngine();
            IndividualFactory factory = new NQueenFactory();
            geneticAlg.setIndividualFactory(factory);

            geneticAlg.executeAlgorithm(2000, 0.6, 2500, fixedQueens,windSize,
                    new FitnessNQueens(new IndividualNull(fixedQueens),factory), new CrossoverOrdered(), new MutationShuffle());

            zValues[selectionWindowSize.indexOf(windSize)][3] = geneticAlg.getCurrentBestFitness();
        }
        for (AGACrossover cross: crossover){
            GAEngine geneticAlg = new GAEngine();
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
//        demo.begin(stage);
        demoGP.begin(stage);
    }

}

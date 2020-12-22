package geneticProgramming;

import geneticAlgorithm.Individuals.Individual;
import geneticAlgorithm.fitness.FitnessFunctions;
import geneticAlgorithm.geneticOperators.AGACrossover;
import geneticAlgorithm.geneticOperators.AGAMutation;
import geneticProgramming.fitness.GPFitness;
import geneticProgramming.geneticOperators.AGPCrossover;
import geneticProgramming.geneticOperators.AGPMutation;
import geneticProgramming.nodeContents.*;
import geneticProgramming.structure.Node;
import geneticProgramming.structure.Tree;

import java.util.ArrayList;
import java.util.Random;

public class GPEngine {
    private ArrayList<Double> inputNumbers;
    private ArrayList<Tree> population = new ArrayList<>();
    private GPFitness fitness = new GPFitness();
    private Random random = new Random();
    private AGPCrossover crossover;
    private AGPMutation mutation;
    private int populationSize;
    private int maxDepth;
    private double mutationRate;
    private int selectionWindowSize = 5;
    private Tree currentBest;
    private Tree currentWorst;
    private double currentBestFitness;
    private double currentWorstFitness;
    private ArrayList<Double> bestFitnessHistory = new ArrayList<>();
    private ArrayList<Double> worstFitnessHistory = new ArrayList<>();
    private ArrayList<Double> meanFitnessHistory = new ArrayList<>();
    private int currentGeneration;


    public GPEngine(int populationSize, int maxDepth, double mutationRate){
        this.populationSize = populationSize;
        this.maxDepth = maxDepth;
        this.mutationRate = mutationRate;
    }

    public Tree tournamentSelection(int selectionSize){
        // Get random individuals from population
        ArrayList<Tree> subPopulation = new ArrayList<>();
        for (int i = 0; i < selectionSize; i++){
            int rand = random.nextInt(population.size());
            subPopulation.add(population.get(rand));
        }
        // Get best from subPopulation
        int bestIndex = 0;
        double best = Double.MIN_VALUE;
        for (int i = 0; i < selectionSize; i++) {
            Tree testSubject = subPopulation.get(i);
            double score = fitness.evaluate(testSubject);
            if (score >= best){
                best = score;
                bestIndex = i;
            }
        }
        return subPopulation.get(bestIndex);
    }

    public void newGeneration(){
        for (int i = 0; i < populationSize; i ++){
            Tree selection1 = tournamentSelection(selectionWindowSize);
            Tree selection2 = tournamentSelection(selectionWindowSize);
            Tree cross = crossover.crossover(selection1, selection2);
            Tree child = mutation.mutate(cross);
            population.set(i,child);
        }
    }

    public boolean isSolutionFound(){
        int bestIndex = 0;
        double bestResult = Double.MIN_VALUE;
        int worstIndex = 0;
        double worstResult = Double.MAX_VALUE;
        for (int i = 0; i < population.size(); i ++ ){
            double fit = fitness.evaluate(population.get(i));
            if (fit - fitness.getTarget() < 0.1){
                this.currentBest = population.get(i);
                this.currentBestFitness = fitness.evaluate(population.get(i));
                bestFitnessHistory.add(currentBestFitness);
                worstFitnessHistory.add(currentBestFitness);
                meanFitnessHistory.add(currentBestFitness);
                return true;
            }
            else{
                if (fit >= bestResult){
                    bestIndex = i;
                    bestResult = fit;
                }
                if (fit <= worstResult){
                    worstIndex = i;
                    worstResult = fit;
                }
            }
        }
        this.currentBest = population.get(bestIndex);
        this.currentBestFitness = fitness.evaluate(population.get(bestIndex));
        bestFitnessHistory.add(currentBestFitness);

        this.currentWorst = population.get(worstIndex);
        this.currentWorstFitness = fitness.evaluate(population.get(worstIndex));
        worstFitnessHistory.add(currentWorstFitness);

        meanFitnessHistory.add((currentBestFitness + currentWorstFitness)/2);
        return false;
    }

    public Tree executeAlgorithm(int maxGenerations, double mutationRate, int populationSize,
                                       int maxDepth, int selectionWindowSize, GPFitness fitness,
                                       AGPCrossover crossover, AGPMutation mutation){
        this.crossover = crossover;
        this.mutation = mutation;
        population = generateTrees(populationSize, maxDepth, false);
        setMutationRate(mutationRate);
        setFitness(fitness);
        setSelectionWindowSize(selectionWindowSize);
        while (currentGeneration < maxGenerations) {
            if (isSolutionFound()) {
                return currentBest;
            }
            else {
                newGeneration();
            }
            currentGeneration ++;
        }
        return  currentBest;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setFitness(GPFitness fitness) {
        this.fitness = fitness;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public void setSelectionWindowSize(int selectionWindowSize) {
        this.selectionWindowSize = selectionWindowSize;
    }

    public void setInputNumbers(ArrayList<Double> inputNumbers){
        this.inputNumbers = inputNumbers;
    }

    /**
     * Generates perfectly balanced random binary Trees
     * @param maxDepth generated Trees can't exceed this depth
     * @param populationSize amount of Trees to be generated
     * @return ArrayList containing the resulting Trees
     */
    public ArrayList<Tree> generateTrees(int populationSize, int maxDepth, boolean randomOrder){
        // Create empty arrays for each generation
        ArrayList<ArrayList<Tree>> allTrees = new ArrayList<>();
        ArrayList<ContentFunction> functions = new ArrayList<>();
        for (int i = 0; i < maxDepth; i ++){
            ArrayList<Tree> trees = new ArrayList<>();
            allTrees.add(trees);
        }
        // Fill the first iteration arrays with the algebraic operators and the numbers provided by the user
        functions.add(new ContentFunctionPlus('+'));
        functions.add(new ContentFunctionMinus('-'));
        functions.add(new ContentFunctionTimes('*'));
        functions.add(new ContentFunctionDivided('/'));
        for (int i = 0; i < inputNumbers.size(); i ++){
            ContentConstant constant = null;
            if (randomOrder){
                constant = new ContentConstant(inputNumbers.get(random.nextInt(inputNumbers.size())));
            }
            else{
                constant = new ContentConstant(inputNumbers.get(i));
            }
            Node aNode = new Node(
                    constant,
                    null,
                    null);
            Tree aTree = new Tree(aNode);
            allTrees.get(0).add(aTree);
        }
        // Iterate to generate random Trees
        for (int i = 1; i < maxDepth; i++){
            for (int j = 0; j < populationSize; j ++){
                // Indices of parents and operator
                int p1 = random.nextInt(populationSize);
                int p2 = random.nextInt(populationSize);
                while (p2 == p1 && populationSize != 1){
                    p2 = random.nextInt(populationSize);
                }
                int op = random.nextInt(3);
                // Create new Tree
                Node rNode = new Node(
                        functions.get(op),
                        allTrees.get(i-1).get(p1).getRoot(),
                        allTrees.get(i-1).get(p2).getRoot()
                );
                allTrees.get(i).add(new Tree(rNode));
            }
        }
        // Choose randomly a tree that doesn't exceed the maxDepth
        for (Tree tree: allTrees.get(maxDepth - 1)){
            if (tree.depth() > maxDepth){
                return null;
            }
        }
        return allTrees.get(maxDepth - 1);
    }
}

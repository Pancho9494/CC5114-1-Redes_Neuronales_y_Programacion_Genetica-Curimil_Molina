package geneticProgramming;

import geneticProgramming.fitness.ChiffLettFitness;
import geneticProgramming.fitness.GPFitnessFunctions;
import geneticProgramming.geneticOperators.AGPCrossover;
import geneticProgramming.geneticOperators.AGPMutation;
import geneticProgramming.nodeContents.*;
import geneticProgramming.nodeContents.factory.VariableFactory;
import geneticProgramming.nodeContents.factory.constants.ConstantFactory;
import geneticProgramming.nodeContents.factory.functions.*;
import geneticProgramming.structure.Node;
import geneticProgramming.structure.Tree;

import java.util.ArrayList;
import java.util.Random;

public class GPEngine {
    private ArrayList<Double> inputNumbers;
    private ArrayList<Tree> population = new ArrayList<>();
    private GPFitnessFunctions fitness = new ChiffLettFitness();
    private Random random = new Random();
    private AGPCrossover crossover;
    private AGPMutation mutation;
    private int populationSize;
    private int maxDepth;
    private double mutationRate;
    private int selectionWindowSize;
    private Tree currentBest;
    private Tree currentWorst;
    private double currentBestFitness;
    private double currentWorstFitness;
    private ArrayList<Double> bestFitnessHistory = new ArrayList<>();
    private ArrayList<Double> worstFitnessHistory = new ArrayList<>();
    private ArrayList<Double> meanFitnessHistory = new ArrayList<>();
    private int currentGeneration;
    private IContentFunctionFactory contentFactory;
    private int upperBound;
    private int lowerBound;
    private ArrayList<Point> spacePoints = new ArrayList<>();
    private boolean chiff = true;


    public GPEngine(int populationSize, int maxDepth, double mutationRate){
        this.populationSize = populationSize;
        this.maxDepth = maxDepth;
        this.mutationRate = mutationRate;
    }

    public void setRandomSeed(int seed) {
        this.random.setSeed(seed);
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
            if (Math.abs(fitness.getTarget() - fit) < 0.00001){
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

    public Tree executeAlgorithm(int maxGenerations, int selectionWindowSize, GPFitnessFunctions fitness,
                                       AGPCrossover crossover, AGPMutation mutation){
        this.crossover = crossover;
        this.mutation = mutation;
        population = generateTrees(populationSize, maxDepth);
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

    public void setFitness(GPFitnessFunctions fitness) {
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

    public Tree getCurrentBest() {
        return currentBest;
    }

    public double getCurrentBestFitness() {
        return currentBestFitness;
    }

    public ArrayList<Double> getBestFitnessHistory() {
        return bestFitnessHistory;
    }

    public ArrayList<Double> getWorstFitnessHistory() {
        return worstFitnessHistory;
    }

    public ArrayList<Double> getMeanFitnessHistory() {
        return meanFitnessHistory;
    }

    public ArrayList<Double> getInputNumbers() {
        return inputNumbers;
    }

    /**
     * Generates perfectly balanced random binary Trees
     * @param maxDepth generated Trees can't exceed this depth
     * @param populationSize amount of Trees to be generated
     * @return ArrayList containing the resulting Trees
     */
    public ArrayList<Tree> generateTrees(int populationSize, int maxDepth){
        // Create empty arrays for each generation
        ArrayList<ArrayList<Tree>> allTrees = new ArrayList<>();
        ArrayList<ContentFunctionFactory> functions = new ArrayList<>();
        ArrayList<Tree> finalArray = new ArrayList<>();
        for (int i = 0; i < maxDepth; i ++){
            ArrayList<Tree> trees = new ArrayList<>();
            allTrees.add(trees);
        }
        // Fill the first iteration arrays with the algebraic operators and the numbers provided by the user
        functions.add(new PlusFunctionFactory());
        functions.add(new MinusFunctionFactory());
        functions.add(new TimesFunctionFactory());
        functions.add(new DividedFunctionFactory());
        ConstantFactory constFactory = new ConstantFactory();
        VariableFactory varFactory = new VariableFactory();
        if (maxDepth == 0){
            Node leaf = new Node(null,null,null);
            if (isChiff()){
                leaf.setContent(new ContentConstant(inputNumbers.get(random.nextInt(inputNumbers.size())), null));
            }
            else {
                if (Math.random() < 0.5) {
                    leaf.setContent(constFactory.create(random.nextInt(upperBound - lowerBound) + lowerBound, leaf));
                }
                else{
                    leaf.setContent(varFactory.create('x',0, leaf));
                }
            }
            Tree tree = new Tree(leaf);
            ArrayList<Tree> trees = new ArrayList<>();
            trees.add(tree);
            return trees;
        }
        // Create the initial leaves
        for (int i = 0; i < populationSize; i ++){
            Content constant = null;
            Node aNode = new Node(null, null, null);
            if (this.chiff){
                constant = constFactory.create(inputNumbers.get(random.nextInt(inputNumbers.size())), aNode);
            }
            else{
                if (Math.random() < 0.5) {
                    constant = constFactory.create(random.nextInt(upperBound - lowerBound) + lowerBound, aNode);
                }
                else{
                    constant = varFactory.create('x',0, aNode);
                }
            }
            aNode.setContent(constant);
            Tree aTree = new Tree(aNode);
            allTrees.get(0).add(aTree);
        }
        // Iterate to generate random Trees
        for (int i = 1; i < maxDepth; i++){
            for (int j = 0; j < populationSize; j ++){
                // Indices of parents and operator
                int p1 = random.nextInt(allTrees.get(i - 1).size());
                int p2 = random.nextInt(allTrees.get(i - 1).size());
                while (p2 == p1 && populationSize != 1){
                    p2 = random.nextInt(allTrees.get(i - 1).size());
                }
                int op = random.nextInt(4);

                // Create new Tree
                Node rNode = new Node(null,null,null);

                rNode.setContent(functions.get(op).create(rNode));
                rNode.setLeft(allTrees.get(i - 1).get(p1).getRoot().copy());
                rNode.setRight(allTrees.get(i - 1).get(p2).getRoot().copy());

                allTrees.get(i).add(new Tree(rNode));
            }
        }
        return allTrees.get(maxDepth - 1);
    }

    public void setBounds(int upperBound, int lowerBound) {
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    public ArrayList<Point> getPoints() {
        return spacePoints;
    }

    public void setSpacePoints(ArrayList<Point> spacePoints) {
        this.spacePoints = spacePoints;
    }

    public void setChiff(boolean chiff) {
        this.chiff = chiff;
    }

    public boolean isChiff() {
        return chiff;
    }
}

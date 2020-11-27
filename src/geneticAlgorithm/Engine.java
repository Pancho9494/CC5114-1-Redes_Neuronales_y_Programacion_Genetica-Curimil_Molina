package geneticAlgorithm;

import geneticAlgorithm.Individuals.Factory.*;
import geneticAlgorithm.fitness.*;
import geneticAlgorithm.Individuals.*;
import geneticAlgorithm.geneticOperators.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a genetic algorithm engine
 * It provides the tools required to set up the algorithm and to execute it
 */
public class Engine {
    private Random random = new Random();
    private ArrayList<Individual> population = new ArrayList<>();
    private ArrayList<Integer> bestFitnessHistory = new ArrayList<>();
    private ArrayList<Integer> worstFitnessHistory = new ArrayList<>();
    private ArrayList<Integer> meanFitnessHistory = new ArrayList<>();
    private FitnessFunctions fitness;
    private Individual targetWord;
    private Individual currentBest;
    private Individual currentWorst;
    private IIndividualFactory individualFactory;
    private ACrossover crossover;
    private AMutation mutation;
    private double mutationRate = 0.2;
    private int selectionWindowSize = 5;
    private int currentGeneration;
    private int currentBestFitness;
    private int currentWorstFitness;
    private int populationSize;
    private int numberOfGenes;


    /**
     * Initializes a random population made of random individuals
     * @param populationSize the size of the population
     * @param numberOfGenes the number of genes of each individual
     */
    public void initializePopulation(int populationSize, int numberOfGenes){
        this.populationSize = populationSize;
        this.numberOfGenes = numberOfGenes;
        this.currentGeneration = 0;
        for (int i = 0; i < populationSize; i ++){
            population.add(generateIndividual(numberOfGenes));
        }
    }

    /**
     * Creates a random individual
     * @param size number of genes of the individual
     * @return the individual as a Character array
     */
    public Individual generateIndividual(int size){
        Individual ind = this.individualFactory.create(size);
        ind.initialize();
        return ind;
    }

    /**
     * Selects an individual from the current population via the tournament method
     * The selection phase should be extracted as classes so the Engine could use more than the tournament method
     * @param selectionSize how many individuals we choose for each tournament
     * @return the best individual of the tournament
     */
    public Individual tournamentSelection(int selectionSize){
        // Get random individuals from population
        ArrayList<Individual> subPopulation = new ArrayList<>();
        for (int i = 0; i < selectionSize; i++){
            int rand = random.nextInt(population.size());
            subPopulation.add(population.get(rand));
        }
        // Get best from subPopulation
        int bestIndex = 0;
        int best = -99999;
        for (int i = 0; i < selectionSize; i++) {
            Individual testSubject = subPopulation.get(i);
            int score = fitness.evaluate(testSubject);
            if (score >= best){
                best = score;
                bestIndex = i;
            }
        }
        return subPopulation.get(bestIndex);
    }

    /**
     * Executes the selection and reproduction phases of the algorithm
     * Picks two individuals via the tournament selection
     * They reproduce via the crossover method
     * Their offspring mutates
     * The process repeats until the previous population is completely replaced
     */
    public void newGeneration(){
        for (int i = 0; i < populationSize; i ++){
            Individual selection1 = tournamentSelection(selectionWindowSize);
            Individual selection2 = tournamentSelection(selectionWindowSize);
            Individual cross = crossover.crossover(selection1, selection2, (IndividualFactory) individualFactory);
            Individual child = mutation.mutate(cross, (IndividualFactory) individualFactory, mutationRate);
            population.set(i,child);
        }
    }

    /**
     * Executes the Fitness phase of the algorithm and keeps track of best worst and mean fitness
     * Checks if the algorithm has found the individual that maximizes the Fitness function
     * At each call of this function the currentBest and currentFitness are replaced
     * @return true if the solution is found, false for anything else
     */
    public boolean isSolutionFound(){
        int bestIndex = 0;
        int bestResult = -999999;
        int worstIndex = 0;
        int worstResult = 9999;
        for (int i = 0; i < population.size(); i ++ ){
            int fit = fitness.evaluate(population.get(i));
            if (fit == fitness.getGoal()){
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

        meanFitnessHistory.add((int) ((currentBestFitness + currentWorstFitness)/2));
        return false;
    }

    /**
     * Executes the genetic algorithm
     * If it can't find the individual that maximizes the fitness function in the given generations, it returns
     * the current best individual
     * @param maxGenerations the maximum generations the algorithm will execute
     * @param mutationRate the probability of a new child of being mutated
     * @param populationSize the number of individuals in the population
     * @param numberOfGenes the number of genes of each individual (in this case the length of the Character arrays)
     * @param selectionWindowSize the number of individuals considered for each tournament selection
     * @param fitness the fitness function used to test the individuals
     * @return the best individual found by the algorithm
     */
    public Individual executeAlgorithm(int maxGenerations, double mutationRate, int populationSize,
                                       int numberOfGenes, int selectionWindowSize, FitnessFunctions fitness,
                                       ACrossover crossover, AMutation mutation){
        this.crossover = crossover;
        this.mutation = mutation;
        initializePopulation(populationSize, numberOfGenes);
        setMutationRate(mutationRate);
        setFitnessFunction(fitness);
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

    // Getters
    /**
     * Mainly used in Tests
     * @return the size of the population
     */
    public int getPopulationSize(){
        return population.size();
    }

    /**
     * Mainly used in Tests
     * @return the target word
     */
    public Individual getTargetWord() {
        return targetWord;
    }

    /**
     * Mainly used in Tests
     * @return the current population
     */
    public ArrayList<Individual> getPopulation() {
        return population;
    }

    /**
     * Mainly used in Tests
     * @return the Crossover genetic operator
     */
    public ACrossover getCrossover() {
        return crossover;
    }

    /**
     * Mainly used in Tests
     * @return the Mutation genetic operator
     */
    public AMutation getMutation() {
        return mutation;
    }

    /**
     * Used to make the fitness curves
     * @return the best fitness of each generation
     */
    public ArrayList<Integer> getBestFitnessHistory() {
        return bestFitnessHistory;
    }

    /**
     * Used to make the fitness curves
     * @return the worst fitness of each generation
     */
    public ArrayList<Integer> getWorstFitnessHistory() {
        return worstFitnessHistory;
    }

    /**
     * Used to make the fitness curves
     * @return the mean between the best and the worst fitness of each generation
     */
    public ArrayList<Integer> getMeanFitnessHistory() {
        return meanFitnessHistory;
    }

    /**
     * Used to show the results of the algorithm
     * @return the best Individual
     */
    public Individual getCurrentBest() {
        return currentBest;
    }

    /**
     * Used to show the results of the algorithm
     * @return the best fitness obtained by the engine
     */
    public int getCurrentBestFitness() {
        return currentBestFitness;
    }


    // Setters
    /**
     * Mainly used in Tests for the WordFinder problem
     * With the new implementation the target is set in the Fitness class
     * This method should be deleted in the future
     * @param target the target word
     */
    public void setTargetWord(String target){
        this.numberOfGenes = target.length();
        Individual word = new IndividualWord(numberOfGenes);
        for (int i = 0; i < target.length(); i++) {
            word.addGene(target.charAt(i));
        }
        targetWord = word;
    }

    /**
     * Mainly used in Tests
     * @param seed the new Random seed
     */
    public void setRandomSeed(int seed) {
        this.random.setSeed(seed);
    }

    /**
     * Changes the mutation rate of the algorithm
     * @param mutationRate the new algorithm rate
     */
    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    /**
     * Changes the fitness function used in the algorithm
     * @param fitness the new fitness function
     */
    public void setFitnessFunction(FitnessFunctions fitness) {
        this.fitness = fitness;
    }

    /**
     * Changes the window size used in the tournament selection
     * Window refers to how many individuals compete in each iteration of the tournament
     * @param selectionWindowSize the new window size
     */
    public void setSelectionWindowSize(int selectionWindowSize) {
        this.selectionWindowSize = selectionWindowSize;
    }

    /**
     * Changes the Individual Factory used in the algorithm
     * @param anIndividualFactory the new Individual Factory
     */
    public void setIndividualFactory(IIndividualFactory anIndividualFactory){
        this.individualFactory = anIndividualFactory;
    }

    /**
     * Changes the Crossover operation used in the algorithm
     * @param crossover the new Crossover operation
     */
    public void setCrossover(ACrossover crossover) {
        this.crossover = crossover;
    }
}

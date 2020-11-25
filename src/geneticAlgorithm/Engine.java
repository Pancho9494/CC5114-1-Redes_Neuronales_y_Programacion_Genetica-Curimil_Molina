package geneticAlgorithm;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a genetic algorithm engine
 * It provides the tools required to set up the algorithm and to execute it
 */
public class Engine {
    private Random random = new Random();
    private ArrayList<Character[]> contents = new ArrayList<>();
    private ArrayList<Integer> bestFitnessHistory = new ArrayList<>();
    private ArrayList<Integer> worstFitnessHistory = new ArrayList<>();
    private ArrayList<Integer> meanFitnessHistory = new ArrayList<>();
    private FitnessFunctions fitness;
    private Character[] targetWord;
    private Character[] currentBest;
    private Character[] currentWorst;
    double mutationRate = 0.2;
    int selectionWindowSize = 5;
    int currentGeneration;
    int currentBestFitness;
    int currentWorstFitness;
    int populationSize;
    int numberOfGenes;


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
            contents.add(generateIndividual(numberOfGenes));
        }
    }

    /**
     * Creates a random individual
     * @param size number of genes of the individual
     * @return the individual as a Character array
     */
    public Character[] generateIndividual(int size){
        Character[] out = new Character[size];
        for (int i = 0; i < size; i++){
            out[i] = (char) ('a' + random.nextInt(26));
        }
        return out;
    }

    /**
     * Selects an individual from the current population via the tournament method
     * @param selectionSize how many individuals we choose for each tournament
     * @return the best individual of the tournament
     */
    public Character[] tournamentSelection(int selectionSize){
        // Get random individuals from population
        ArrayList<Character[]> subPopulation = new ArrayList<>();
        for (int i = 0; i < selectionSize; i++){
            int rand = random.nextInt(contents.size());
            subPopulation.add(contents.get(rand));
        }
        // Get best from subPopulation
        Character[] out = new Character[numberOfGenes];
        int best = 0;
        for (int i = 0; i < selectionSize; i++) {
            Character[] testSubject = subPopulation.get(i);
            int score = fitness.evaluate(testSubject);
            if (score >= best){
                best = score;
                out = testSubject;
            }
        }
        return out;
    }

    /**
     * Two individuals reproduce via the crossover method
     * @param parent1 parent of the new individual
     * @param parent2 parent of the new individual
     * @return the resulting children of the crossover
     */
    public Character[] crossover(Character[] parent1, Character[] parent2){
        Character[] child = new Character[parent1.length];
        // Randomly cut words between 0 and length - 1
        int cut = random.nextInt(parent1.length - 1) + 1;
        for (int i = 0; i < cut; i ++) {
            child[i] = parent1[i];
        }
        for (int j = cut; j < parent2.length; j ++){
            child[j] = parent2[j];
        }
        return child;
    }

    /**
     * Randomly changes one gene from an individual
     * But only if the rolled double is less than the mutationRate
     * @param original the individual whose genes have been modified
     * @return the resulting individual
     */
    public Character[] mutate(Character[] original){
        Character[] copy = copyCharacterArray(original);
        if (Math.random() <= mutationRate){
            int mutatedGene = random.nextInt(original.length);
            copy[mutatedGene] = (char) ('a' + random.nextInt(26));
        }
        return copy;
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
            Character[] child = mutate(crossover(tournamentSelection(this.selectionWindowSize),
                    tournamentSelection(this.selectionWindowSize)));
            contents.set(i,child);
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
        int bestResult = 0;
        int worstIndex = 0;
        int worstResult = 0;
        for (int i = 0; i < contents.size(); i ++ ){
            int fit = fitness.evaluate(contents.get(i));
            if (fit == fitness.getGoal()){
                this.currentBest = contents.get(i);
                this.currentBestFitness = fitness.evaluate(contents.get(i));
                bestFitnessHistory.add(currentBestFitness);
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
        this.currentBest = contents.get(bestIndex);
        this.currentBestFitness = fitness.evaluate(contents.get(bestIndex));
        bestFitnessHistory.add(currentBestFitness);

        this.currentWorst = contents.get(worstIndex);
        this.currentWorstFitness = fitness.evaluate(contents.get(worstIndex));
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
    public Character[] executeAlgorithm(int maxGenerations, double mutationRate, int populationSize,
                                        int numberOfGenes, int selectionWindowSize, FitnessFunctions fitness){
        initializePopulation(populationSize, numberOfGenes);
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

    // Getters
    public int getPopulationSize(){
        return contents.size();
    }

    public Character[] getTargetWord() {
        return targetWord;
    }

    public ArrayList<Character[]> getPopulation() {
        return contents;
    }

    public ArrayList<Integer> getBestFitnessHistory() {
        return bestFitnessHistory;
    }

    public ArrayList<Integer> getWorstFitnessHistory() {
        return worstFitnessHistory;
    }

    public ArrayList<Integer> getMeanFitnessHistory() {
        return meanFitnessHistory;
    }

    // Setters
    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public void setTargetWord(String target){
        this.numberOfGenes = target.length();
        Character word[] = new Character[numberOfGenes];
        for (int i = 0; i < target.length(); i++) {
            word[i] = target.charAt(i);
        }
        targetWord = word;
    }

    public void setFitness(FitnessFunctions fitness) {
        this.fitness = fitness;
    }

    public void setSelectionWindowSize(int selectionWindowSize) {
        this.selectionWindowSize = selectionWindowSize;
    }

    public void setRandomSeed(int seed) {
        this.random.setSeed(seed);
    }

    // Auxiliary Functions
    /**
     * Copies an array of characters
     * @param original the original array of characters
     * @return the copy array
     */
    public Character[] copyCharacterArray(Character[] original){
        Character[] out = new Character[original.length];
        for (int i = 0; i < original.length; i++){
            out[i] = original[i];
        }
        return out;
    }

    /**
     * Converts a string into an array of characters
     * @param original the original string
     * @return the resulting Character array
     */
    public Character[] stringToCharArray(String original){
        Character word[] = new Character[original.length()];
        for (int i = 0; i < original.length(); i++) {
            word[i] = original.charAt(i);
        }
        return word;
    }
}

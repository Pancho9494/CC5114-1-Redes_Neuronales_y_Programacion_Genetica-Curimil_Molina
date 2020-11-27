package geneticAlgorithm.Individuals;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents an Individual of the genetic algorithm
 * Each type of Individual extends this class and overrides the abstract methods to fit their implementation
 */
public abstract class Individual {
    protected ArrayList<Object> chromosome = new ArrayList<>();
    protected int numberOfGenes;
    protected Random random = new Random();

    public Individual(int size){
        this.numberOfGenes = size;
        this.dummyGenes();
    }

    /**
     * Sometimes we must set the genes of a new Individual in a specific order
     * The method setGene takes care of that, however, if the Individuals are empty then setGene raises a NullPointerException
     * In order to avoid that we fill new individuals with the integer values of their indices, they will later be replaced
     */
    public void dummyGenes(){
        for (int i = 0; i < this.numberOfGenes; i ++){
            addGene(i);
        }
    }

    /**
     * Adds genes to the chromosome of the Individual,
     * but only if the size of the chromosome isn't exceeding the numberOfGenes specified by the Engine
     * @param gene the gene added to the Individual
     */
    public void addGene(Object gene){
        if (chromosome.size() >= this.numberOfGenes){
            return;
        }
        chromosome.add(gene);
    }

    // Abstract methods
    /**
     * Fills the chromosome of an Individual with random genes
     */
    public abstract void initialize();

    /**
     * This method is used to make the standard mutation operation
     * Mutates a gene of the Individual with the corresponding AMutation operator
     * @param position the position of the mutated gene in the chromosome
     */
    public abstract void mutateGene(int position);

    /**
     * Determines if two genes are equal
     * This can't be done just using (==) because the genes are Objects
     * Each type of Individual has a way to determine if two genes are equal
     * @param gene1 one of the genes that's being compared
     * @param gene2 the other gene
     * @return true if they are equal, false if else
     */
    public abstract boolean compareGenes(Object gene1, Object gene2);

    /**
     * This class makes a String representation of the chromosome of an individual
     * Primarily used for the the JavaFX classes
     * @return the String representation of the individual
     */
    public abstract String chromosomeToString();


    // Getters

    /**
     * Get a gene from the individual
     * @param index the position of the gene in the chromosome
     * @return the gene in the position with value index
     */
    public Object getGene(int index) {
        return chromosome.get(index);
    }

    /**
     * Get how many genes this Individual can have
     * @return integer representing the number of genes
     */
    public int getNumberOfGenes() {
        return numberOfGenes;
    }

    /**
     * Basically the same as numberOfGenes, but this value is obtained from the length of the
     * ArrayList of the chromosome instead of the value set by the Engine
     * However, by the addGene method, an individual can't have more genes than numberOfGenes,
     * so this method should be deleted in the future
     * @return integer representing the length of the chromosome
     */
    public int getChromosomeSize() {
        return chromosome.size();
    }

    /**
     * Get the chromosome of an Individual
     * @return the chromosome of the individual
     */
    public ArrayList<Object> getChromosome() {
        return chromosome;
    }

    // Setters

    /**
     * Set a gene in a specific position of the chromosome of the Individual
     * @param position the specified position where the gene will be set
     * @param gene the gene that will be set
     */
    public void setGene(int position, Object gene) {
        this.chromosome.set(position, gene);
    }






}

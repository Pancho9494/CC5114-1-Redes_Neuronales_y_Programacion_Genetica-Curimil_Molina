package geneticAlgorithm.Individuals.Factory;

import geneticAlgorithm.Individuals.Individual;

/**
 * This interface represents the contract that all Factories must obey
 * The Factories are used to create new Individuals with the correct class by calling a generic method
 */
public interface IIndividualFactory {

    /**
     * Create an Individual with its corresponding class
     * @param size the number of Genes in the Individual
     * @return the new Individual
     */
    Individual create(int size);

    /**
     * Copies the genes from an Individual into a new one
     * @param ind the Individual that's being copied
     * @return the copy of the Individual
     */
    Individual copyIndividual(Individual ind);
}

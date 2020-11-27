package geneticAlgorithm.Individuals.Factory;

import geneticAlgorithm.Individuals.Individual;

/**
 * This class represents the different types of Factories of individuals
 * It must obey the contract specified in the IIndividualFactory interface
 * It implements common methods for all Individuals
 */
public abstract class IndividualFactory implements IIndividualFactory {

    /**
     * Create an Individual with its corresponding class
     * @param size the number of Genes in the Individual
     * @return the new Individual
     */
    public abstract Individual create(int size);

    /**
     * Copies the genes from an Individual into a new one
     * This is done by making a new empty Individual, taking each gene of the original
     * and placing it in the corresponding place in the copy
     * @param ind the Individual that's being copied
     * @return the copy of the Individual
     */
    public Individual copyIndividual(Individual ind){
        Individual out = create(ind.getNumberOfGenes());
        for (int i = 0; i < ind.getNumberOfGenes(); i++){
            out.setGene(i,ind.getGene(i));
        }
        return out;
    }
}

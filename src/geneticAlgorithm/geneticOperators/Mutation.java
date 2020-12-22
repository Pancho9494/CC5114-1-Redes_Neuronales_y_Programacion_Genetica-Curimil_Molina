package geneticAlgorithm.geneticOperators;

import geneticAlgorithm.Individuals.Factory.IndividualFactory;
import geneticAlgorithm.Individuals.Individual;

/**
 * This class represents the standard mutation operation
 */
public class Mutation extends AGAMutation {

    /**
     * A number between 0 and 1 is rolled, if its greater than the mutation rate then the mutation happens
     * A random gene from the individual is chosen and the mutateGene method is called, this is so each class
     * of individual can mutate according to the type of their genes
     * @param original the original Individual
     * @param individualFactory the factory of the class of the Individual
     * @param mutationRate the mutation rate specified in the engine, maybe this should
     *                     be set as a global variable for the abstract class instead.
     * @return the mutated individual
     */
    @Override
    public Individual mutate(Individual original, IndividualFactory individualFactory, double mutationRate) {
        Individual copy = individualFactory.copyIndividual(original);
        if (Math.random() <= mutationRate){
            int mutatedGene = random.nextInt(original.getNumberOfGenes());
            copy.mutateGene(mutatedGene);
        }
        return copy;
    }
}

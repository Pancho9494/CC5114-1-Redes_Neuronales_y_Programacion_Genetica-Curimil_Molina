package geneticAlgorithm.geneticOperators;

import geneticAlgorithm.Individuals.Factory.IndividualFactory;
import geneticAlgorithm.Individuals.Individual;

/**
 * This class represents a mutation that swaps two genes from an Individual
 */
public class MutationSwap extends AGAMutation {

    /**
     * Two random indices are chosen, these will be the swapped genes of the Individual
     * The chosen indices must be different, so the method enters a while loop if they are equal
     * The gene from index1 is stored in a temp Object, then the gene from index2 is set in index1
     * and finally the gene from index2 is set with the temp Object.
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
            int index1 = random.nextInt(original.getNumberOfGenes());
            int index2 = random.nextInt(original.getNumberOfGenes());
            while (index2 == index1){
                index2 = random.nextInt(original.getNumberOfGenes());
            }
            Object temp = copy.getGene(index1);
            copy.setGene(index1, copy.getGene(index2));
            copy.setGene(index2, temp);
        }
        return copy;
    }
}

package geneticAlgorithm.geneticOperators;

import geneticAlgorithm.Individuals.Factory.IndividualFactory;
import geneticAlgorithm.Individuals.Individual;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents a mutation that just reorders genes in an individual
 */
public class MutationShuffle extends AGAMutation {

    /**
     * The genes of the Individual ar shuffled and then returned
     * @param original the original Individual
     * @param individualFactory the factory of the class of the Individual
     * @param mutationRate the mutation rate specified in the engine, maybe this should
     *                     be set as a global variable for the abstract class instead.
     * @return the mutated individual
     */
    @Override
    public Individual mutate(Individual original, IndividualFactory individualFactory, double mutationRate) {
        Individual copy = individualFactory.copyIndividual(original);
        ArrayList<Object> shuffleGenes = original.getChromosome();
        Collections.shuffle(shuffleGenes);
        for (int i = 0; i < copy.getNumberOfGenes(); i++){
            copy.setGene(i,shuffleGenes.get(i));
        }
        return copy;
    }
}

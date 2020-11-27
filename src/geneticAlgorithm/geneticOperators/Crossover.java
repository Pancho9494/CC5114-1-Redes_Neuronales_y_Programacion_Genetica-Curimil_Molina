package geneticAlgorithm.geneticOperators;

import geneticAlgorithm.Individuals.Factory.IndividualFactory;
import geneticAlgorithm.Individuals.Individual;

/**
 * This class represents the standard crossover operation
 */
public class Crossover extends ACrossover {

    /**
     * A random index is chosen to make a cut
     * From 0 to the cut the child has the parent1 genes
     * From the cut to the last gene the child has the parent2 genes
     * @param parent1 one parent
     * @param parent2 the other parent
     * @param individualFactory the factory used to create the new child with a corresponding Individual class
     * @return the child obtained by the crossover
     */
    public Individual crossover(Individual parent1, Individual parent2, IndividualFactory individualFactory){
        Individual child = individualFactory.create(parent1.getChromosomeSize());
        // Randomly cut words between 0 and length - 1
        int cut = random.nextInt(parent1.getChromosomeSize() - 1) + 1;
        for (int i = 0; i < cut; i ++) {
            child.setGene(i,parent1.getGene(i));
        }
        for (int j = cut; j < parent2.getChromosomeSize(); j ++){
            child.setGene(j,parent2.getGene(j));
        }
        return child;
    }
}

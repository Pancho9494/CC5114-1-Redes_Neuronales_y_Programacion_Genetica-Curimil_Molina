package geneticAlgorithm.geneticOperators;

import geneticAlgorithm.Individuals.Factory.IndividualFactory;
import geneticAlgorithm.Individuals.Individual;

import java.util.Random;

/**
 * This class represents one of the variations of the crossover genetic operation
 * These classes are given as a parameter to the genetic algorithm engine to use the corresponding crossover operation
 */
public abstract class AGACrossover {
    Random random = new Random();

    /**
     * Apply the corresponding crossover operation
     * @param parent1 one parent
     * @param parent2 the other parent
     * @param individualFactory the factory used to create the new child with a corresponding Individual class
     * @return the child obtained by the crossover
     */
    public abstract Individual crossover(Individual parent1, Individual parent2, IndividualFactory individualFactory);
}

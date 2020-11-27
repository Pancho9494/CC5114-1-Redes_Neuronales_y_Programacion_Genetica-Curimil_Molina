package geneticAlgorithm.geneticOperators;

import geneticAlgorithm.Individuals.Factory.IndividualFactory;
import geneticAlgorithm.Individuals.Individual;

import java.util.Random;

/**
 * This class represents one of the variations of the mutation genetic operator
 * These classes are given as a parameter to the genetic algorithm engine to use the corresponding mutate operation
 */
public abstract class AMutation {
    Random random = new Random();

    /**
     * Apply the corresponding mutation operation
     * @param original the original Individual
     * @param individualFactory the factory of the class of the Individual
     * @param mutationRate the mutation rate specified in the engine, maybe this should
     *                     be set as a global variable for the abstract class instead.
     * @return the mutated Individual
     */
    public abstract Individual mutate(Individual original, IndividualFactory individualFactory, double mutationRate);
}

package geneticAlgorithm;

/**
 * This class represents the possible fitness function used in the engine
 */
public abstract class FitnessFunctions {

    /**
     * Evaluates the fitness of a given individual
     * @param test the individual that's being tested
     * @return the score obtained by the individual
     */
    public abstract int evaluate(Character[] test);

    /**
     * Gets the maximum possible score that an individual can obtain
     * @return the maximum score possible
     */
    public abstract int getGoal();
}

package geneticAlgorithm.fitness;

import geneticAlgorithm.Individuals.Factory.IndividualFactory;
import geneticAlgorithm.Individuals.Individual;

/**
 * This class represents a fitness function used by the genetic algorithm engine
 */
public abstract class FitnessFunctions {
    Individual target;
    IndividualFactory factory;
    int goal;

    public FitnessFunctions(Object target, IndividualFactory factory){
        this.factory = factory;
        this.target = transformInput(target);
        this.goal = this.target.getChromosomeSize();
    }

    /**
     * Evaluates the fitness of a given individual
     * @param test the individual that's being tested
     * @return the score obtained by the individual
     */
    public abstract int evaluate(Individual test);

    /**
     * Gets the raw input an adjusts the needed format of each Individual
     * @param input the input given to the function
     * @return the Individual that represents the input
     */
    public abstract Individual transformInput(Object input);

    /**
     * Gets the maximum possible score that an individual can obtain
     * @return the maximum score possible
     */
    public int getGoal(){
        return this.goal;
    }

    /**
     * Sets the corresponding Individual Factory so subclasses can create the proper Individual objects
     * @param factory the Individual Factory
     */
    public void setFactory(IndividualFactory factory){
        this.factory = factory;
    }
}

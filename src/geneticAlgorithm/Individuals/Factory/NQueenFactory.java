package geneticAlgorithm.Individuals.Factory;

import geneticAlgorithm.Individuals.Individual;
import geneticAlgorithm.Individuals.IndividualNQueen;

/**
 * This class represents a factory of IndividualNQueen
 * These Individuals have Queen objects in their genes
 */
public class NQueenFactory extends  IndividualFactory {

    @Override
    public Individual create(int size) {
        return new IndividualNQueen(size);
    }
}

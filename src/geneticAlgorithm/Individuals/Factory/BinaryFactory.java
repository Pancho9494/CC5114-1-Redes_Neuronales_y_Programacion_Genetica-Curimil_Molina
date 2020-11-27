package geneticAlgorithm.Individuals.Factory;

import geneticAlgorithm.Individuals.Individual;
import geneticAlgorithm.Individuals.IndividualBinary;

/**
 * This class represents a factory of IndividualBinary
 * These Individuals have only the values 0 or 1 in their genes
 */
public class BinaryFactory extends IndividualFactory {

    @Override
    public Individual create(int size) {
        return new IndividualBinary(size);
    }
}

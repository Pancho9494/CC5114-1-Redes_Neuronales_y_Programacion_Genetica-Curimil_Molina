package geneticAlgorithm.Individuals.Factory;

import geneticAlgorithm.Individuals.Individual;
import geneticAlgorithm.Individuals.IndividualWord;

/**
 * This class represents a factory of IndividualWord
 * These Individuals have chars in their genes
 */
public class WordFactory extends IndividualFactory {

    @Override
    public Individual create(int size) {
        return new IndividualWord(size);
    }

}

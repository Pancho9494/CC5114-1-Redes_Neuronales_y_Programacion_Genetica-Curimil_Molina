package geneticAlgorithm.fitness;

import geneticAlgorithm.Individuals.Factory.IndividualFactory;
import geneticAlgorithm.Individuals.Individual;
import geneticAlgorithm.Individuals.IndividualWord;

/**
 * This class represents a fitness function that matches words letter by letter
 */
public class FitnessMatchWords extends FitnessFunctions{

    public FitnessMatchWords(Object target, IndividualFactory factory){
        super(target, factory);
    }

    /**
     * An individual gets one point for each correct letter in its corresponding place
     * @param test the individual that's being tested
     * @return the total score of the individual
     */
    @Override
    public int evaluate(Individual test){
        int score = 0;
        if (target.getChromosomeSize() != test.getChromosomeSize()){
            return score;
        }
        for (int i = 0; i < target.getChromosomeSize(); i ++){
            if (target.getGene(i) == test.getGene(i)){
                score ++;
            }
        }
        return score;
    }

    /**
     * Converts a string into an Individual object where each character is a gene
     * The created i
     * @param input the original string
     * @return the resulting Individual array
     */
    @Override
    public Individual transformInput(Object input) {
        String original = (String) input;
        Individual word = this.factory.create(original.length());
        for (int i = 0; i < original.length(); i++) {
            word.setGene(i,original.charAt(i));
        }
        return word;
    }





}
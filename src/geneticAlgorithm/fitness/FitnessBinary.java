package geneticAlgorithm.fitness;

import geneticAlgorithm.Individuals.*;
import geneticAlgorithm.Individuals.Factory.IndividualFactory;

/**
 * This class represents a fitness function that compares the difference between two int numbers
 */
public class FitnessBinary extends FitnessFunctions {

    public FitnessBinary(Object target, IndividualFactory factory) {
        super(target,factory);
        this.goal = 0;
    }

    /**
     * An individual gets a score closer to 0 as its decimal value gets closer to the target,
     * this is measured as the absolute difference of the decimal representation of the target and the individual
     * @param test the individual that's being tested
     * @return the total score of the individual
     */
    @Override
    public int evaluate(Individual test) {
        return -Math.abs(getDigits(target) - binaryDigits(test));
    }

    /**
     * Transforms an int into an Individual object where each digit is a gene
     * The created Individual should be from the IndividualBinary class, if the BinaryFactory is used
     * @param input the original int
     * @return the resulting Individual
     */
    @Override
    public Individual transformInput(Object input) {
        Integer original = (Integer) input;
        int len = sequenceLength(original);
        Individual number = this.factory.create(len);
        for (int i = 0; i < len; i++) {
            int value = (int) ((original % Math.pow(10,i + 1))/Math.pow(10,i));
            number.setGene(i,value);
        }
        return number;
    }

    /**
     * Auxiliary function used to the determine how many digits are there in a given int
     * @param number the number we want to know the length of
     * @return the calculated length of the number
     */
    public int sequenceLength(int number){
        int copy = number;
        int divisions = 0;
        while (copy > 0){
            copy = (int) copy/10;
            divisions ++;
        }
        return divisions;
    }

    /**
     * Auxiliary function used to determine the decimal representation of the
     * binary sequence contained in the genes of an Individual
     * The Individual should have only the values 1 or 0 in its genes
     * @param input the Individual
     * @return the decimal representation of its genes
     */
    public int binaryDigits(Individual input){
        int result = 0;
        int exp = 1;
        for (int i = 0; i < input.getNumberOfGenes(); i ++){
            result += ((Integer) input.getGene(i))*exp;
            exp *= 2;
        }
        return result;
    }

    /**
     * Auxiliary function used to extract the integer number contained in the genes of an Individual
     * The Individual should have values from 0 to 9 in its genes
     * @param ind the Individual
     * @return the integer value
     */
    public int getDigits(Individual ind){
        int result = 0;
        for (int i = 0; i < ind.getNumberOfGenes(); i++){
            result += ((Integer) ind.getGene(i))*Math.pow(10,i);
        }
        return result;
    }


}

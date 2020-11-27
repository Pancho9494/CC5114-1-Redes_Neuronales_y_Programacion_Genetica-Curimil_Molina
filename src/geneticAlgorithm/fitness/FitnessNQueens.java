package geneticAlgorithm.fitness;

import geneticAlgorithm.Individuals.Factory.IndividualFactory;
import geneticAlgorithm.Individuals.Individual;
import geneticAlgorithm.Individuals.IndividualNull;
import geneticAlgorithm.specialObjects.Queen;

import java.util.ArrayList;

/**
 * This class represents a fitness function that assigns more points for each direction
 * where a Queen is not in risk of being captured
 */
public class FitnessNQueens extends  FitnessFunctions {

    public FitnessNQueens(Object target, IndividualFactory factory) {
        super(target, factory);
        int n = ((IndividualNull)target).getNumberOfGenes();
        this.goal = 3*n*(n-1);
    }

    /**
     * An individual gets more points for each condition that is met
     * We propose three conditions:
     * - The xAxis condition: The score increases for each one of the other queens that don't have the same x value as this queen (max n - 1 points for each queen)
     * - The yAxis condition: Same as the yAxis condition but for the y value (max n - 1 points for each queen)
     * - The diagonal condition: We know to queens are in diagonal squares from each other if their difference in x values and y values are the same,
     *                           this is, if the queens have (x1,y1) and (x2,y2) coordinates, they are diagonal from each other only if x2 - x1 == y2 - y1.
     *                           This condition then increases the score for each queen that is not in a diagonal of this queen (max n - 1 points for each other)
     * Given that the 3 conditions have a maximum value of n - 1 for each of the queens, then the target value must be 3*n*(n - 1) for the Individual
     * @param test the individual (chess board) that's being tested
     * @return the total score of the individual
     */
    @Override
    public int evaluate(Individual test) {
        int score = 0;
        for (int i = 0; i < test.getNumberOfGenes(); i ++){
            ArrayList<Queen> subQueens = getOtherQueens(test.getChromosome(), (Queen) test.getGene(i));
            Queen tested = (Queen) test.getGene(i);
            // Check xAxis
            for (Queen otherQueen: subQueens){
                if (tested.getX() != otherQueen.getX()){
                    score ++;
                }
                if (tested.getY() != otherQueen.getY()){
                    score ++;
                }
                if (Math.abs(tested.getX() - otherQueen.getX()) != Math.abs(tested.getY() - otherQueen.getY())){
                    score ++;
                }
            }
        }
        return score;
    }

    /**
     * This function should't be used but it is called, so it returns a null Individual
     * @param input the input given to the function
     * @return a null Individual
     */
    @Override
    public Individual transformInput(Object input) {
        return (IndividualNull) input;
    }

    /**
     * Auxiliary function used to get an ArrayList that contains all the queens in an Individual except from one
     * @param input the Individual that contains the Queens
     * @param ind the Queen that is being excluded
     * @return the resulting ArrayList of Queens
     */
    public ArrayList<Queen> getOtherQueens(ArrayList<Object> input, Queen ind){
        ArrayList<Queen> out = new ArrayList<>();
        for (int i = 0; i < input.size(); i ++){
            if (!target.compareGenes(input.get(i), ind)){
                out.add((Queen) input.get(i));
            }
        }
        return out;
    }

}

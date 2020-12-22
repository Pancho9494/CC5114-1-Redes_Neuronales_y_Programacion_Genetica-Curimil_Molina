package geneticAlgorithm.geneticOperators;

import geneticAlgorithm.Individuals.Factory.IndividualFactory;
import geneticAlgorithm.Individuals.Individual;
import geneticAlgorithm.specialObjects.Queen;

import java.util.ArrayList;

/**
 * This class represents a special mutation for the N-Queens problem
 * It moves a Queen that's occupying an X or a Y coordinate that is already taken to an X or Y coordinate that is not
 * If no Queens share the same X or Y coordinates, then nothing happens
 *
 * This increases the chances of a board not getting stuck with invalid positions
 *
 * Currently this class only cares about rows and columns in the board but not diagonals, this could be added in the future
 */
public class MutationFillBoard extends AGAMutation {

    /**
     * There must be a more efficient way to do this, this will be revised in the future
     *
     * Generate two Integer ArrayLists with values from 0 to numberOfGenes, these will hold the non-occupied X and Y coordinates
     * Generate an empty Integer ArrayLists, this will hold the index of the Queens that are in taken coordinates
     * Generate two more empty Integer ArrayLists of size numberOfGenes these will hold the already visited
     *
     * We go through every Queen of the original Individual
     * If the Queen's X or Y coordinates are in the Seen arrays then we add this Queen to the repeated array and skip this
     * iteration, but first we check if X or Y are NOT in the Seen arrays, so we can remove them from the validPositions
     * Array and add them to the Seen arrays
     *
     * If the Queen's X or Y coordinates aren't in the Seen arrays, then they are removed from the validPositions array
     * and added to the seen array
     *
     * Then, we choose randomly one of the repeated Queens, one of the available X in validXpPositions and one of the
     * available Y in validYPositions. If there are no available coordinates left we choose at random.
     * The chosen repeated Queen is then placed in the chosen X and Y coordinates
     * @param original the original Individual
     * @param individualFactory the factory of the class of the Individual
     * @param mutationRate the mutation rate specified in the engine, maybe this should
     *                     be set as a global variable for the abstract class instead.
     * @return the original individual but mutated
     */
    @Override
    public Individual mutate(Individual original, IndividualFactory individualFactory, double mutationRate) {
        // Generate array of integers for positions in board
        ArrayList<Integer> validXPositions = new ArrayList<>(original.getNumberOfGenes());
        ArrayList<Integer> validYPositions = new ArrayList<>(original.getNumberOfGenes());

        for(int i = 0; i < original.getNumberOfGenes(); i++){
            validXPositions.add(i);
            validYPositions.add(i);
        }

        ArrayList<Integer> repeatingQueens = new ArrayList<>();
        ArrayList<Integer> seenX = new ArrayList<>(original.getNumberOfGenes());
        ArrayList<Integer> seenY = new ArrayList<>(original.getNumberOfGenes());
        // Remove the integers that are already being used by a Queen
        // And determine which Queens are in repeating coordinates
        for (int i = 0; i < original.getNumberOfGenes(); i ++){
            int tempX = ((Queen) original.getGene(i)).getX();
            int tempY = ((Queen) original.getGene(i)).getY();

            // If the X or the Y of a Queen are already occupied, we skip this iteration
            if (seenX.contains(tempX) || seenY.contains(tempY)){
                // If Y is repeated but X is not, we remove X from the valid positions
                if (!seenX.contains(tempX)){
                    validXPositions.remove(validXPositions.indexOf(tempX));
                    seenX.add(tempX);
                }
                // If X is repeated but Y is not, we remove Y from the valid positions
                if (!seenY.contains(tempY)){
                    validYPositions.remove(validYPositions.indexOf(tempY));
                    seenY.add(tempY);
                }
                // This Queen's index is added to the repeated lists and this iteration is skipped
                repeatingQueens.add(i);
                continue;
            }
            validXPositions.remove(validXPositions.indexOf(tempX));
            validYPositions.remove(validYPositions.indexOf(tempY));

            seenX.add(tempX);
            seenY.add(tempY);
        }
        // If there are positions that aren't being used move a Queen to an unused space
        if (repeatingQueens.size() > 0){
            int chosenQueen = repeatingQueens.get(random.nextInt(repeatingQueens.size()));
            int newX = 0;
            int newY = 0;
            if (validXPositions.size() > 0 ){
                newX = validXPositions.get(random.nextInt(validXPositions.size()));
                validXPositions.remove(validXPositions.indexOf(newX));
            }
            else{
                newX = random.nextInt(original.getNumberOfGenes());
            }
            if (validYPositions.size() > 0 ){
                newY = validYPositions.get(random.nextInt(validYPositions.size()));
                validYPositions.remove(validYPositions.indexOf(newY));
            }
            else{
                newY = random.nextInt(original.getNumberOfGenes());
            }
            original.setGene(chosenQueen, new Queen(newX, newY));
        }
        return original;
    }
}

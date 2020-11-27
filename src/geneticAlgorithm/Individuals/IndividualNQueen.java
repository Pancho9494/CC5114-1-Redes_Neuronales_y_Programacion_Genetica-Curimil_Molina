package geneticAlgorithm.Individuals;

import geneticAlgorithm.specialObjects.Queen;

/**
 * This class represents a ChessBoard (combination of N Queens really) used in the NQueenFinder problem
 * Each gene is a Queen
 */
public class IndividualNQueen extends Individual {

    public IndividualNQueen(int size) {
        super(size);
    }

    /**
     * The genes are initialized with Queens in random positions of the board
     */
    @Override
    public void initialize() {
        for (int i = 0; i < this.numberOfGenes; i++){
            this.setGene(i, new Queen(random.nextInt(numberOfGenes), random.nextInt(numberOfGenes)));
        }
    }

    /**
     * The NQueensProblem doesn't use the standard mutation so this class is almost never used
     * However, it must be implemented in the event that the standard mutation is used
     * A Queen is placed in a random position in the board
     * @param position the position of the mutated gene in the chromosome
     */
    @Override
    public void mutateGene(int position) {
        ((Queen) this.getGene(position)).newCoordinates(random.nextInt(numberOfGenes),random.nextInt(numberOfGenes));
    }

    /**
     * Auxiliary function used to determine if a Queen is equal to this Queen
     * It does it by comparing their coordinates
     * @param gene1 the first Queen
     * @param gene2 the other Queen
     * @return true if they are equal, false if else
     */
    @Override
    public boolean compareGenes(Object gene1, Object gene2) {
        if (((Queen) gene1).getX() == ((Queen) gene2).getX() && ((Queen) gene1).getY() == ((Queen) gene2).getY()){
            return true;
        }
        return false;
    }

    /**
     * This method isn't used so it isn't implemented
     * In the future it could be implemented but right now isn't necessary
     * @return null
     */
    @Override
    public String chromosomeToString() {
        return null;
    }






}

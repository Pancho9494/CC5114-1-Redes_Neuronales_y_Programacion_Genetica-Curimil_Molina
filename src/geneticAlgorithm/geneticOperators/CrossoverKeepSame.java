package geneticAlgorithm.geneticOperators;

import geneticAlgorithm.Individuals.Factory.IndividualFactory;
import geneticAlgorithm.Individuals.Individual;
import geneticAlgorithm.specialObjects.Queen;

import java.util.ArrayList;

/**
 * This class represents a crossover that only keeps the repeating genes in the parents
 */
public class CrossoverKeepSame extends AGACrossover {

    /**
     * If the parents have genes with the same values in them, their indices are stored
     * The child then will have those repeating genes in their corresponding positions
     * The rest of the genes are randomly filled
     * @param parent1 one parent
     * @param parent2 the other parent
     * @param individualFactory the factory used to create the new child with a corresponding Individual class
     * @return the child obtained by the crossover
     */
    @Override
    public Individual crossover(Individual parent1, Individual parent2, IndividualFactory individualFactory) {
        Individual child = individualFactory.create(parent1.getNumberOfGenes());
        ArrayList<Integer> sameGenesIndex = new ArrayList<>();
        // Find indices of repeating genes in parents
        for (int i = 0; i < parent1.getNumberOfGenes(); i ++){
            if (equalQueens((Queen) parent1.getGene(i),(Queen) parent2.getGene(i))){
                sameGenesIndex.add(i);
            }
        }
        // Random new values
        for (int i = 0; i < child.getNumberOfGenes(); i ++){
            child.setGene(i, new Queen(random.nextInt(child.getNumberOfGenes()), random.nextInt(child.getNumberOfGenes())));
        }
        // Overwrite with repeated genes from the parents
        for (int index: sameGenesIndex){
            child.setGene(index, parent1.getGene(index));
        }


        return child;
    }

    /**
     * Auxiliary function used if two genes are equal
     * @param aQueen one gene
     * @param bQueen another gene
     * @return true fi they are equal, false if else
     */
    public boolean equalQueens(Queen aQueen, Queen bQueen){
        if (aQueen.getX() == bQueen.getX() && aQueen.getY() == bQueen.getY()){
            return true;
        }
        return false;
    }
}

package geneticAlgorithm.Individuals;

/**
 * This class represents a Null Individual
 * Used mainly in the nQueenFinder problem because some methods require a target Individual
 * but the nQueenFinder doesn't have a target Individual
 */
public class IndividualNull extends Individual{

    public IndividualNull(int size) {
        super(size);
        this.numberOfGenes = size;
    }

    /**
     * Shouldn't be used
     * @return null
     */
    @Override
    public String chromosomeToString() {
        return null;
    }

    /**
     * Shouldn't be used
     */
    @Override
    public void initialize() {
        return;
    }

    /**
     * Shouldn't be used
     * @param position the position of the mutated gene in the chromosome
     */
    @Override
    public void mutateGene(int position) {

    }

    /**
     * Shouldn't be used
     * @param gene1 one of the genes that's being compared
     * @param gene2 the other gene
     * @return
     */
    @Override
    public boolean compareGenes(Object gene1, Object gene2) {
        return false;
    }
}

package geneticAlgorithm.Individuals;

/**
 * This class represents a binary number used in the binaryFinder problem
 * Each gene is a binary digit ordered from LSB to MSB
 */
public class IndividualBinary extends Individual {

    public IndividualBinary(int size) {
        super(size);
    }

    /**
     * The genes are initialized with random binary digits
     */
    @Override
    public void initialize() {
        for (int i = 0; i < this.numberOfGenes; i++){
            if (random.nextInt(10) > 5){
                this.setGene(i,1);
            }
            else{
                this.setGene(i,0);
            }
        }
    }

    /**
     * The mutated gene is replaced with a random binary digit
     * @param position the position of the mutated gene in the chromosome
     */
    @Override
    public void mutateGene(int position){
        this.chromosome.set(position,random.nextInt(1));
    }

    /**
     * Checks if two binary digits are equal
     * @param gene1 one of the genes that's being compared
     * @param gene2 the other gene
     * @return true if they are equal, false if else
     */
    @Override
    public boolean compareGenes(Object gene1, Object gene2) {
        if ((Integer) gene1 == (Integer) gene2){
            return true;
        }
        return false;
    }

    /**
     * Each binary digit is concatenated to an empty String, but they are placed in
     * order from MSB to LSB (the inverse of the way they are stored in the chromosome)
     * @return the String resulting from concatenating all binary digits
     */
    @Override
    public String chromosomeToString() {
        String out = "";
        for (int i = numberOfGenes - 1; i >= 0; i --){
            out += getGene(i).toString();
        }
        return  out;
    }

}

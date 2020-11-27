package geneticAlgorithm.Individuals;

/**
 * This class represents a Word used in the wordFinder problem
 * Each gene is a character of the word
 */
public class IndividualWord extends Individual {

    public IndividualWord(int size){
        super(size);
    }

    /**
     * The genes are initialized with random characters in the english alphabet
     */
    @Override
    public void initialize(){
        for (int i = 0; i < this.numberOfGenes; i++){
            this.setGene(i,(char) ('a' + random.nextInt(26)));
        }
    }

    /**
     * The mutated gene is replaced with a random character in the english alphabet
     * @param position the position of the mutated gene in the chromosome
     */
    @Override
    public void mutateGene(int position) {
        this.chromosome.set(position,(char) ('a' + random.nextInt(26)));
    }

    /**
     * Checks if two characters are equal
     * @param gene1 one of the genes that's being compared
     * @param gene2 the other gene
     * @return true if they are equal, false if else
     */
    @Override
    public boolean compareGenes(Object gene1, Object gene2) {
        if ((char) gene1 == (char) gene2){
            return true;
        }
        return false;
    }

    /**
     * Each character is concatenated to an empty String
     * @return the String resulting from concatenating all characters
     */
    @Override
    public String chromosomeToString() {
        String out = "";
        for (Object gene: this.getChromosome()){
            out += gene.toString();
        }
        return  out;
    }
}

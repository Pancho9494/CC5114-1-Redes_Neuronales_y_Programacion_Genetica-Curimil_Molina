package geneticAlgorithm.geneticOperators;

import geneticAlgorithm.Individuals.Factory.IndividualFactory;
import geneticAlgorithm.Individuals.Individual;
import geneticAlgorithm.specialObjects.Queen;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents a crossover that doesn't repeat genes
 * At the moment this class is meant to be used ONLY with the N-Queens problem
 * this is due to the fact that in the last for loop of crossover() the remaining genes are filled with queens instead
 * of a common method among individuals
 */
public class CrossoverOrdered extends AGACrossover {

    /**
     * Two random indices (index1,index2) are chosen to extract a portion of the genes of the parent1
     * The child then has that portion in its corresponding position, and the rest of the genes are filled
     * with genes from the parent2 that aren't repeated in the parent1
     * @param parent1 one parent
     * @param parent2 the other parent
     * @param individualFactory the factory used to create the new child with a corresponding Individual class
     * @return the child obtained by the crossover
     */
    @Override
    public Individual crossover(Individual parent1, Individual parent2, IndividualFactory individualFactory) {
        Individual child = individualFactory.create(parent1.getNumberOfGenes());

        int index1 = random.nextInt(parent1.getChromosomeSize());
        int index2 = random.nextInt(parent1.getNumberOfGenes() - index1) + index1;
        while (index2 == index1 && index1 != parent1.getNumberOfGenes() - 1){
            index2 =  random.nextInt(parent1.getNumberOfGenes() - index1) + index1;
        }

        ArrayList<Integer> filledGenes = new ArrayList<>();
        // The subsection of parent1's genes is assigned to the child
        for (int i = index1; i <= index2; i ++) {
            child.setGene(i,parent1.getGene(i));
            filledGenes.add(i);
        }
        // The rest is filled with non repeating genes, skipping the parent1's section
        int position = 0;
        for (int i = 0; i < index1; i ++) {
            if (!geneInChromosome(parent2.getGene(position),parent1.getChromosome(), parent1)){
                child.setGene(i,parent2.getGene(i));
                filledGenes.add(position);
                position ++;
            }
        }
        for (int i = index2 + 1; i < parent2.getNumberOfGenes(); i ++) {
            if (!geneInChromosome(parent2.getGene(position),parent1.getChromosome(), parent1)){
                child.setGene(i,parent2.getGene(position));
                filledGenes.add(position);
                position ++;
            }
        }
        // If the parents didn't have enough non-repeating genes to fill the child's chromosome, then its filled at random
        Collections.sort(filledGenes);
        if (filledGenes.size() != parent1.getNumberOfGenes()){
            for (int i = 0; i < parent1.getNumberOfGenes(); i ++){
                if (!filledGenes.contains(i)){
                    child.setGene(i, new Queen(random.nextInt(parent1.getNumberOfGenes()), random.nextInt(parent1.getNumberOfGenes())));
                }
            }
        }
        return  child;
    }

    /**
     * Auxiliary function used to determine if a specific gene is already in the chromosome of an Individual
     * @param gene the gene we are looking for
     * @param chromosome the chromosome of the individual
     * @param individual currently the Individual class has the method compareGenes, so this function needs an
     *                   Individual to call that function. This shouldn't be like this but for now it works
     * @return true if the gene is in the chromosome, false if else
     */
    public boolean geneInChromosome(Object gene, ArrayList<Object> chromosome, Individual individual){
        for (int i=0; i < chromosome.size(); i ++){
            if (individual.compareGenes(gene, chromosome.get(i)) == true) {
                return true;
            }
        }
        return false;
    }
}

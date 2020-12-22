package geneticProgramming.geneticOperators;

import geneticProgramming.structure.Tree;

public class CrossoverSubTree extends AGPCrossover {
    @Override
    public Tree crossover(Tree parent1, Tree parent2) {
        // Copies of modified parent
        Tree copy = parent1.copyTree();
        // How many nodes in each tree
        int size1 = parent1.numberOfNodes();
        int size2 = parent2.numberOfNodes();
        // Crossover Points
        int CP1 = random.nextInt(size1);
        int CP2 = random.nextInt(size2);
        // Extract and Replace
        Tree temp = parent2.extractSubTree(CP2);
        Tree out = copy.replaceSubTree(temp,CP1);
        return out;
    }

    public void setRandomSeed(int seed){
        this.random.setSeed(seed);
    }
}

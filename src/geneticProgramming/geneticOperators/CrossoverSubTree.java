package geneticProgramming.geneticOperators;

import geneticProgramming.GPEngine;
import geneticProgramming.structure.Tree;

public class CrossoverSubTree extends AGPCrossover {

    public CrossoverSubTree(GPEngine engine){
        this.engine = engine;
    }

    @Override
    public Tree crossover(Tree parent1, Tree parent2) {
        // Copies of modified parent
        Tree copy = parent1.copyTree();
        // How many nodes in each tree
        int size1 = parent1.numberOfNodes();
        int size2 = parent2.numberOfNodes();
        // Crossover Points
        while(copy.depth() > engine.getMaxDepth()){
            int CP1 = random.nextInt(size1);
            int CP2 = random.nextInt(size2);
            while (CP2 == CP1) {
                CP2 = random.nextInt(size2);
            }
            // Extract and Replace
            Tree temp = parent2.extractSubTree(CP2);
            copy.replaceSubTree(temp,CP1);
        }
        return copy;
    }

    public void setRandomSeed(int seed){
        this.random.setSeed(seed);
    }
}

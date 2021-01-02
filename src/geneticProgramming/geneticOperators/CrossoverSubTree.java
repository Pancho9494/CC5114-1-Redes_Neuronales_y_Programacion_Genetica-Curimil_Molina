package geneticProgramming.geneticOperators;

import geneticProgramming.GPEngine;
import geneticProgramming.structure.Tree;

/**
 * This class represents a crossover operation that replaces a subTree
 * of one parent with a subTree of the other parent
 */
public class CrossoverSubTree extends AGPCrossover {

    public CrossoverSubTree(GPEngine engine){
        this.engine = engine;
    }

    /**
     * The algorithm goes as follows:
     * 1. Make a copy of the first parent
     * 2. Select a random crossover point in both parents (CP1 and CP2)
     * 3. Replace the subTree in CP1 with the subTree in CP2
     * 4. Return the modified copy of the first parent
     * This operation makes sure the resulting tree doesn't exceed the max Depth
     * @param parent1 the first parent
     * @param parent2 the second parent
     * @return the child, resulting from the crossover of the parents
     */
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
}

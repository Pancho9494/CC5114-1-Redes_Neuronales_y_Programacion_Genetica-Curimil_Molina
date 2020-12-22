package geneticProgramming.geneticOperators;

import geneticProgramming.GPEngine;
import geneticProgramming.structure.Tree;

public class MutationSubTree extends AGPMutation {

    public MutationSubTree(GPEngine engine){
        this.engine = engine;
    }

    /**
     * Random mutation of a Tree
     * Choose a random Node from the original Tree
     * A random Tree is generated, with a depth equal to the depth of the sub-Tree of the chosen Node
     * @param original the original Tree
     * @return the mutated Tree
     */
    @Override
    public Tree mutate(Tree original) {
        Tree copy = original.copyTree();
        if (Math.random() <= engine.getMutationRate()){
            int MP = random.nextInt(copy.numberOfNodes());
            int depthNeeded = copy.extractSubTree(MP).depth();
            Tree randomTree = engine.generateTrees(1,depthNeeded, true).get(0);
            copy = copy.replaceSubTree(randomTree,MP);
        }
        return copy;
    }
}

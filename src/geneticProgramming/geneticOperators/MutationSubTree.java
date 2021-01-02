package geneticProgramming.geneticOperators;

import geneticProgramming.GPEngine;
import geneticProgramming.nodeContents.contents.ContentConstant;
import geneticProgramming.nodeContents.factory.constants.ConstantFactory;
import geneticProgramming.structure.Node;
import geneticProgramming.structure.Tree;

import java.util.ArrayList;

/**
 * This class represents a mutation operation that replaces
 * a random subTree with a randomly generated new subTree
 */
public class MutationSubTree extends AGPMutation {

    public MutationSubTree(GPEngine engine){
        this.engine = engine;
    }

    /**
     * The algorithm goes as follows:
     * 1. Make a copy of the tree
     * 2. Select a random mutation point in the copy (MP)
     * 3. Generate a random subTree
     * 4. Replace the subTree in MP with the new subTree
     * 5. Return the modified copy
     * This operation makes sure the resulting tree doesn't exceed the max Depth
     * @param original the original Tree
     * @return the mutated Tree
     */
    @Override
    public Tree mutate(Tree original) {
        Tree copy = original.copyTree();
        if (Math.random() <= engine.getMutationRate()){
            int MP = random.nextInt(copy.numberOfNodes());
            int distanceFromRoot = copy.depth() - copy.extractSubTree(MP).depth();
            int topDepthPermitted = engine.getMaxDepth() - distanceFromRoot;
            int depthChosen = random.nextInt(topDepthPermitted);
            Tree randomTree = engine.generateTrees(1,depthChosen).get(0);
            copy = copy.replaceSubTree(randomTree,MP);
            ArrayList<Node> nodes = copy.inOrder();
            nodes.remove(copy.getRoot());
            ArrayList<Double> seen = new ArrayList<>();
            ArrayList<Node> repeated = new ArrayList<>();
            ArrayList<Double> available = new ArrayList<>();
            for (Node node: nodes){
                if (seen.contains(node.value().getContent())){
                    repeated.add(node);
                }
                else if (node.value() instanceof ContentConstant){
                    seen.add((double) node.value().getContent());
                }
            }
            if (engine.isChiff()){
                for (int i = 0; i < engine.getInputNumbers().size(); i ++){
                    if (!seen.contains(engine.getInputNumbers().get(i))){
                        available.add(engine.getInputNumbers().get(i));
                    }
                }
                ArrayList<Node> toBeRemoved = new ArrayList<>();
                for (Node dup: repeated){
                    if (available.size() == 0){
                        break;
                    }
                    ConstantFactory contFact = new ConstantFactory();
                    double newValue = available.get(random.nextInt(available.size()));
                    dup.setContent(contFact.create(newValue, dup));
                    toBeRemoved.add(dup);
                    available.remove(newValue);
                }
                repeated.removeAll(toBeRemoved);
            }
        }
        return copy;
    }
}

package geneticProgramming.geneticOperators;

import geneticProgramming.GPEngine;
import geneticProgramming.nodeContents.Content;
import geneticProgramming.nodeContents.ContentConstant;
import geneticProgramming.nodeContents.factory.constants.ConstantFactory;
import geneticProgramming.nodeContents.factory.constants.ContentConstantFactory;
import geneticProgramming.structure.Node;
import geneticProgramming.structure.Tree;

import java.util.ArrayList;

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
            int distanceFromRoot = copy.depth() - copy.extractSubTree(MP).depth();
            int topDepthPermitted = engine.getMaxDepth() - distanceFromRoot;
            int depthChosen = random.nextInt(topDepthPermitted);
            Tree randomTree = engine.generateTrees(1,depthChosen, true).get(0);
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
            for (int i = 0; i < engine.getInputNumbers().size(); i ++){
                if (!seen.contains(engine.getInputNumbers().get(i))){
                    available.add(engine.getInputNumbers().get(i));
                }
            }
            ArrayList<Node> toBeRemoved = new ArrayList<>();
            for (Node dup: repeated){
                ConstantFactory contFact = new ConstantFactory();
                double newValue = available.get(random.nextInt(available.size()));
                dup.setContent(contFact.create(newValue, dup));
                toBeRemoved.add(dup);
                available.remove(newValue);
            }
            repeated.removeAll(toBeRemoved);
        }
        return copy;
    }

    public void setRandomSeed(int seed){
        this.random.setSeed(seed);
    }

}

package geneticProgramming.fitness;

import geneticProgramming.nodeContents.contents.ContentConstant;
import geneticProgramming.structure.Node;
import geneticProgramming.structure.Tree;

import java.util.ArrayList;

/**
 * This class represents the fitness function used to evaluate
 * how good is a Tree at solving the Le Compte est Bon problem
 */
public class ChiffLettFitness extends GPFitnessFunctions {

    /**
     * The fitness is calculated as 1/(1 + diff)
     * Where diff is the difference between the target score and the real score
     * As the difference between the target and the real score decreases,
     * the diff value gets closer to 0, so the denominator of the function gets closer to 1
     *
     * This also has a penalty for repeated values
     * @param test the Tree that is being tested
     * @return the fitness of the tree
     */
    @Override
    public double evaluate(Tree test){
        ArrayList<Object> seen = new ArrayList<>();
        ArrayList<Node> nodes = test.inOrder();
        int penalty = 0;
        for (Node node : nodes){
            if (seen.contains(node.value().getContent()) && node.value() instanceof ContentConstant){
                penalty ++;
            }
            seen.add(node.value().getContent());
        }
        double diff = Math.abs(target - test.evaluate()) + penalty*10;
        return 1/(1 + diff);
    }
}

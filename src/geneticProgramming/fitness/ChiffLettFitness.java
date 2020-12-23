package geneticProgramming.fitness;

import geneticProgramming.nodeContents.ContentConstant;
import geneticProgramming.structure.Node;
import geneticProgramming.structure.Tree;

import java.util.ArrayList;

public class ChiffLettFitness extends GPFitnessFunctions {

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

package geneticProgramming.fitness;

import geneticProgramming.GPEngine;
import geneticProgramming.Point;
import geneticProgramming.nodeContents.contents.ContentVariable;
import geneticProgramming.structure.Node;
import geneticProgramming.structure.Tree;

import java.util.ArrayList;

/**
 * This class represents the fitness function used to evaluate
 * how good is a Tree at solving the space points problem
 */
public class PointsFitness extends GPFitnessFunctions {
    GPEngine engine;


    public void setEngine(GPEngine engine) {
        this.engine = engine;
    }

    /**
     * The tree is evaluated with the x coordinate of each point given
     * If the result of evaluating the expression is close to the y coordinate of the point
     * the score increases by one
     * So, for N points the maximum score is N
     * @param test the tree that's being tested
     * @return the fitness of the tree
     */
    @Override
    public double evaluate(Tree test){
        double score = 0;
        ArrayList<Node> nodes = test.inOrder();
        // For each point
        for (Point point : engine.getPoints()){
            // Set the value of the variables
            for (Node node: nodes){
                if (node.value() instanceof ContentVariable){
                    node.value().setContent(point.getX());
                    test.updateMarkers();
                }
            }
            // Calculate the score
            if (Math.abs(point.getY() - test.evaluate()) < 0.01){
                score ++;
            }
        }
        return score;
    }
}

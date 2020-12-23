package geneticProgramming.fitness;

import geneticProgramming.GPEngine;
import geneticProgramming.Point;
import geneticProgramming.nodeContents.ContentVariable;
import geneticProgramming.structure.Node;
import geneticProgramming.structure.Tree;

import java.util.ArrayList;

public class PointsFitness extends GPFitnessFunctions {
    GPEngine engine;


    public void setEngine(GPEngine engine) {
        this.engine = engine;
    }

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

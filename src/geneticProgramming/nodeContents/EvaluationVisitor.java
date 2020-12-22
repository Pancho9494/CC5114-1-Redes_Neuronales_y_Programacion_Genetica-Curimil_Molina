package geneticProgramming.nodeContents;

import geneticProgramming.structure.Node;

public class EvaluationVisitor {

    public double forConstant(Node node){
        return (double) node.value().getContent();
    }

    public double forFunctionPlus(Node node){
        return node.getLeft().acceptEvaluation(this) + node.getRight().acceptEvaluation(this);
    }

    public double forFunctionMinus(Node node){
        return node.getLeft().acceptEvaluation(this) - node.getRight().acceptEvaluation(this);
    }

    public double forFunctionTimes(Node node){
        return node.getLeft().acceptEvaluation(this)*node.getRight().acceptEvaluation(this);
    }

    public double forFunctionDivided(Node node){
        if (node.getRight().acceptEvaluation(this) == 0){
            return 1;
        }
        else{
            return node.getLeft().acceptEvaluation(this)/node.getRight().acceptEvaluation(this);
        }
    }
}

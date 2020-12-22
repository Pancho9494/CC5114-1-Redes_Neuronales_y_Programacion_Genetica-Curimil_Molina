package geneticProgramming;

public class Visitor {

    public double forConstant(Node node){
        return (double) node.value().getContent();
    }

    public double forFunctionPlus(Node node){
        return node.getLeft().accept(this) + node.getRight().accept(this);
    }

    public double forFunctionTimes(Node node){
        return node.getLeft().accept(this)*node.getRight().accept(this);
    }
}

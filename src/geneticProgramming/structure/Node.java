package geneticProgramming.structure;

import geneticProgramming.nodeContents.Content;
import geneticProgramming.nodeContents.CopyVisitor;
import geneticProgramming.nodeContents.EvaluationVisitor;

public class Node {
    private Content content;
    private Node left;
    private Node right;
    private int index;

    public Node (Content value, Node lNode, Node rNode){
        this.content = value;
        this.left = lNode;
        this.right = rNode;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public Content value(){
        return content;
    }

    public double acceptEvaluation(EvaluationVisitor evaluationVisitor){
        return this.content.acceptEvaluation(evaluationVisitor, this);
    }

    public Node copy(){
        CopyVisitor copyVisitor = new CopyVisitor();
        if (left != null & right != null){
            return new Node(this.content.acceptCopy(copyVisitor), this.left.copy(), this.right.copy());
        }
        else{
            return new Node(this.content.acceptCopy(copyVisitor), this.left, this.right);
        }
    }

    public String print(){
        String cont = content.print();
        if(left==null){
            return cont;
        }
        else {
            String izq = left.print();
            String der = right.print();
            return "(" + izq + cont + der + ")";
        }
    }
}

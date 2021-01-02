package geneticProgramming.structure;

import geneticProgramming.nodeContents.contents.Content;
import geneticProgramming.nodeContents.visitors.CopyVisitor;
import geneticProgramming.nodeContents.visitors.EvaluationVisitor;

/**
 * This class represents a Node
 * A node has some content from the Content class (constant, variable or function)
 * and it also is connected to a left and a right node
 */
public class Node {
    private Content content;
    private Node left;
    private Node right;

    public Node (Content value, Node lNode, Node rNode){
        this.content = value;
        this.left = lNode;
        this.right = rNode;
    }

    /**
     * Makes a copy of this Node
     * Uses visitor and double dispatch pattern designs to copy the contents of a Node
     * Each node tells the visitor which type of content the copy should hold
     * @return A copy of this Tree
     */
    public Node copy(){
        CopyVisitor copyVisitor = new CopyVisitor();
        if (left != null & right != null){
            return new Node(this.content.acceptCopy(copyVisitor), this.left.copy(), this.right.copy());
        }
        else{
            return new Node(this.content.acceptCopy(copyVisitor), this.left, this.right);
        }
    }

    /**
     * Prints the contents of this Node
     * Goes through the Node inorder telling each node to return its content as a String
     * @return a String with the contents of the Node
     */
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

    /**
     * This Node accepts being evaluated by the Evaluation visitor
     * The Node sends the visitor to the content, and the content tells the visitor how to evaluate its content
     * @param evaluationVisitor the visitor that's evaluating the content of this tree
     * @return the number resulting from the equation that the Node holds
     */
    public double acceptEvaluation(EvaluationVisitor evaluationVisitor){
        return this.content.acceptEvaluation(evaluationVisitor, this);
    }

    // Getters and Setters
    /**
     * Getter for the content of this node
     * @return the content in the node
     */
    public Content value(){
        return content;
    }

    /**
     * Getter for the left node of this node
     * @return the left node
     */
    public Node getLeft() {
        return left;
    }

    /**
     * Getter for the right node of this node
     * @return the right node
     */
    public Node getRight() {
        return right;
    }

    /**
     * Setter for the content of this node
     * @param content
     */
    public void setContent(Content content) {
        this.content = content;
    }

    /**
     * Setter for the left node of this node
     * @param left the new left node
     */
    public void setLeft(Node left) {
        this.left = left;
    }

    /**
     * Setter for the right node of this node
     * @param right the new right node
     */
    public void setRight(Node right) {
        this.right = right;
    }
}

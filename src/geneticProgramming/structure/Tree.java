package geneticProgramming.structure;

import geneticProgramming.nodeContents.visitors.EvaluationVisitor;

import java.util.ArrayList;

/**
 * This class represents an Abstract Syntax Tree, which represents an equation
 * A tree is made by various nodes from the Node class connected together
 * The principal component of the tree is its rootNode, the node from which all the other nodes originate from
 * The print String holds the current contents of the tree, this makes debugging easier
 * The score double holds the current value of the equation of the tree, this makes debugging easier
 */
public class Tree {
    private Node rootNode;
    private String print;
    private double score;

    public Tree(Node root) {
        this.rootNode = root;
        this.print = print();
        this.score = evaluate();
    }


    /**
     * Makes a copy of this Tree
     * Uses the copy method in the Node class
     * @return a copy of this tree
     */
    public Tree copyTree() {
        Node newRoot = new Node(this.rootNode.copy().value(), this.rootNode.getLeft().copy(), this.rootNode.getRight().copy());
        return new Tree(newRoot);
    }


    /**
     * Evaluates the equation of this Tree
     * Uses visitor and double dispatch pattern design
     * @return the number resulting from calculating the equation in the tree
     */
    public double evaluate(){
        EvaluationVisitor evaluationVisitor = new EvaluationVisitor();
        return this.rootNode.acceptEvaluation(evaluationVisitor);
    }

    /**
     * Prints the contents of the tree
     * but without brackets
     * @return a String that holds the contents of the tree
     */
    public String print() {
        String out = "";
        ArrayList<Node> inorder = inOrder();
        for (Node node: inorder){
            out = out.concat(String.valueOf(node.value().getContent()));
        }
        return out;
    }

    /**
     * Prints the contents of the tree
     * but using brackets
     * @return a String that holds the contents of the tree
     */
    public String realPrint() {
        return rootNode.print();
    }

    /**
     * Goes through the tree inorder
     * Adds each element of the tree to an ArrayList of Nodes
     * @return a list of the nodes of the tree inorder
     */
    public ArrayList<Node> inOrder(){
        ArrayList<Node> out = new ArrayList<>();
        Node currentNode = rootNode;
        Node predecessor;
        while (currentNode != null) {
            if (currentNode.getLeft() == null) {
                out.add(currentNode);
                currentNode = currentNode.getRight();
            } else {
                predecessor = currentNode.getLeft();
                while (predecessor.getRight() != null & predecessor.getRight() != currentNode) {
                    predecessor = predecessor.getRight();
                }
                if (predecessor.getRight() == null) {
                    predecessor.setRight(currentNode);
                    currentNode = currentNode.getLeft();
                }
                else {
                    predecessor.setRight(null);
                    out.add(currentNode);
                    currentNode = currentNode.getRight();
                }
            }
        }
        return out;
    }

    /**
     * Gets the node at the index position of the tree
     * This assumes the indices of the nodes of the tree are given inorder
     * This removes the root node of the accessible nodes, so it can't be manipulated!
     * @param index the position of the node being searched
     * @return the node at the position specified by index
     */
    public Node nodeAt(int index){
        ArrayList<Node> inorder = inOrder();
        inorder.remove(rootNode);
        return inorder.get(index);
    }

    /**
     * Counts how many nodes are the tree
     * @return the number of nodes in the tree
     */
    public int numberOfNodes(){
        ArrayList<Node> inorder = this.inOrder();
        return inorder.size() - 1;
    }

    /**
     * Calculates the depth of the tree
     * Assuming this tree has a root node, the count starts from 1
     * @return the depth of the tree
     */
    public int depth(){
        int count = 1;
        Node currentNode = rootNode;
        Node previous = rootNode;
        while (currentNode != null){
            // Look through left node
            if (currentNode.getLeft() != null){
                previous = currentNode;
                currentNode = currentNode.getLeft();
                count++;
            }
            // Look through right node
            else if (currentNode.getRight() != null){
                previous = currentNode;
                currentNode = currentNode.getRight();
                count++;
            }
            // Current node is a leaf, go back to right node
            else{
                if (previous.getRight() == currentNode){
                    break;
                }
                currentNode = previous.getRight();
            }
        }
        return count;
    }

    /**
     * Replaces a subTree of this tree with another tree
     * @param newSubTree the other tree
     * @param index the position of the tree that's being replaced
     * @return this tree but with the new subTree in place
     */
    public Tree replaceSubTree(Tree newSubTree, int index){
        Node currentNode = this.nodeAt(index);
        currentNode.setContent(newSubTree.getRoot().value());
        currentNode.setLeft(newSubTree.getRoot().getLeft());
        currentNode.setRight(newSubTree.getRoot().getRight());
        updateMarkers();
        return this;
    }

    /**
     * Gets a subTree of this tree in the index position
     * This assumes the indices of the nodes of the tree are given inorder
     * @param index the index of the root node of the subTree
     * @return
     */
    public Tree extractSubTree(int index){
        return new Tree(nodeAt(index));
    }

    /**
     * Updates the current values of the print String and the score double
     * These variables are useful for debugging
     */
    public void updateMarkers(){
        this.print = realPrint();
        this.score = evaluate();
    }

    // Getters and Setters

    /**
     * Getter for the root node of the tree
     * @return the root node of the tree
     */
    public Node getRoot() {
        return rootNode;
    }
}

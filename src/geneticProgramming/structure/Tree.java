package geneticProgramming.structure;

import geneticProgramming.nodeContents.EvaluationVisitor;

import java.util.ArrayList;

public class Tree {
    private Node rootNode;
    private String print;
    private double score;

    public Tree(Node root) {
        this.rootNode = root;
        this.print = print();
        this.score = evaluate();
    }

    public Node getRoot() {
        return rootNode;
    }

    public Tree copyTree() {
        Node newRoot = new Node(this.rootNode.copy().value(), this.rootNode.getLeft().copy(), this.rootNode.getRight().copy());
        return new Tree(newRoot);
    }


    /**
     * Using visitor pattern design
     * @return
     */
    public double evaluate(){
        EvaluationVisitor evaluationVisitor = new EvaluationVisitor();
        return this.rootNode.acceptEvaluation(evaluationVisitor);
    }

    /**
     * Perfectly unbalanced tree
     * @return
     */
    public String print() {
        String out = "";
        ArrayList<Node> inorder = inOrder();
        for (Node node: inorder){
            out = out.concat(String.valueOf(node.value().getContent()));
        }
        return out;
    }

    public String print2() {
        return rootNode.print();
    }

    public Node nodeAt(int index){
        ArrayList<Node> inorder = inOrder();
        inorder.remove(rootNode);
        return inorder.get(index);
    }

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

    public int numberOfNodes(){
        ArrayList<Node> inorder = this.inOrder();
        return inorder.size() - 1;
    }

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


    public Tree replaceSubTree(Tree newSubTree, int index){
        Node currentNode = this.nodeAt(index);
        currentNode.setContent(newSubTree.getRoot().value());
        currentNode.setLeft(newSubTree.getRoot().getLeft());
        currentNode.setRight(newSubTree.getRoot().getRight());
        updateMarkers();
        return this;
    }

    public Tree extractSubTree(int index){
        return new Tree(nodeAt(index));
    }

    public void updateMarkers(){
        this.print = print2();
        this.score = evaluate();
    }


}

package geneticProgramming.structure;

import geneticProgramming.nodeContents.Visitor;

public class Tree {
    private Node rootNode;

    public Tree(Node root) {
        this.rootNode = root;
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
        Visitor visitor = new Visitor();
        return this.rootNode.accept(visitor);
    }

    /**
     * Perfectly unbalanced tree
     * @return
     */
    public String print() {
        String out = "";
        Node currentNode = rootNode;
        Node predecessor;
        while (currentNode != null) {
            if (currentNode.getLeft() == null) {
                out = out.concat(String.valueOf(currentNode.value().getContent()));
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
                    out = out.concat(String.valueOf(currentNode.value().getContent()));
                    currentNode = currentNode.getRight();
                }
            }
        }
        return out;
    }

    public int numberOfNodes(){
        int count = 0;
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
                count++;
            }
        }
        return count;
    }

    public Node getNodeAt(Node currentNode, Node previousNode, int index){
        while (index + 1> 0){
            // No more nodes to look for
            if (currentNode == null){
                break;
            }
            // Look through left node
            else if (currentNode.getLeft() != null){
                previousNode = currentNode;
                currentNode = currentNode.getLeft();
                index--;
            }
            // Look through right node
            else if (currentNode.getRight() != null){
                previousNode = currentNode;
                currentNode = currentNode.getRight();
                index--;
            }
            // Current node is a leaf, go back to right node
            else{
                currentNode = previousNode.getRight();
                index--;
            }
        }
        return currentNode;
    }

    public Tree replaceSubTree(Tree newSubTree, int index){
        Tree copy = this.copyTree();
        Node currentNode = copy.getRoot();
        Node previous = copy.getRoot();
        currentNode = getNodeAt(currentNode, previous,index);
        currentNode.setContent(newSubTree.getRoot().value());
        currentNode.setLeft(newSubTree.getRoot().getLeft());
        currentNode.setRight(newSubTree.getRoot().getRight());
        return copy;
    }

    public Tree extractSubTree(int index){
        Node currentNode = rootNode;
        Node previous = rootNode;
        currentNode = getNodeAt(currentNode, previous,index);
        return new Tree(currentNode);
    }

}

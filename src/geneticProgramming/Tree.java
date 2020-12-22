package geneticProgramming;

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

    public Tree replaceSubTree(Tree newSubTree, int index){
        Tree copy = this.copyTree();
        Node currentNode = copy.getRoot();
        Node previous = copy.getRoot();
        while (index + 1> 0){
            // No more nodes to look for
            if (currentNode == null){
                return null;
            }
            // Look through left node
            else if (currentNode.getLeft() != null){
                previous = currentNode;
                currentNode = currentNode.getLeft();
                index--;
            }
            // Look through right node
            else if (currentNode.getRight() != null){
                previous = currentNode;
                currentNode = currentNode.getRight();
                index--;
            }
            // Current node is a leaf, go back to right node
            else{
                currentNode = previous.getRight();
                index--;
            }
        }
        currentNode.setContent(newSubTree.getRoot().value());
        currentNode.setLeft(newSubTree.getRoot().getLeft());
        currentNode.setRight(newSubTree.getRoot().getRight());
        return copy;
    }
}

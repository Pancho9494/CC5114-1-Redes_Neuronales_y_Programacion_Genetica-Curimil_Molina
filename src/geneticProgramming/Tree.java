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
        Node newRoot = new Node(this.rootNode.value(), this.rootNode.getLeft(), this.rootNode.getRight());
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

    public void replaceSubTree(Tree newSubTree, int index){
        Node currentNode = rootNode;
        while (index > 0){
            if (currentNode.getLeft() != null){
                currentNode = currentNode.getLeft();
                index--;
            }
            else if (currentNode.getRight() != null){
                currentNode = currentNode.getRight();
                index--;
            }
        }
    }
}

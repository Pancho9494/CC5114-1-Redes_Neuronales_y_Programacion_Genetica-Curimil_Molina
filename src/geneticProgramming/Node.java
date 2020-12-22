package geneticProgramming;

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

    public double accept(Visitor visitor){
        return this.content.accept(visitor, this);
    }

    public Node copy(){
        return new Node(this.content, this.left, this.right);
    }
}

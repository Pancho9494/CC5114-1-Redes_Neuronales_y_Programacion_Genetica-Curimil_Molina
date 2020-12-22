package geneticProgramming.nodeContents;

import geneticProgramming.structure.Node;

public abstract class Content {
    protected Node ownerNode;

    public abstract void setContent(Object content);
    public abstract Object getContent();
    public abstract double accept(Visitor visitor, Node node);
}

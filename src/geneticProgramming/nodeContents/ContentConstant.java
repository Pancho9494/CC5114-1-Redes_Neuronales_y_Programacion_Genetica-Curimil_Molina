package geneticProgramming.nodeContents;

import geneticProgramming.structure.Node;

public class ContentConstant extends Content {
    private double content;

    public ContentConstant(double content){
        this.content = content;
    }

    @Override
    public void setContent(Object content){
        this.content = (int) content;
    }

    @Override
    public Object getContent() {
        return content;
    }

    @Override
    public double accept(Visitor visitor, Node owner) {
        this.ownerNode = owner;
        return visitor.forConstant(this.ownerNode);
    }
}

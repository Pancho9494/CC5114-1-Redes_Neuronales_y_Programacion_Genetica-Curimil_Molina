package geneticProgramming.nodeContents;

import geneticProgramming.structure.Node;

public class ContentFunctionTimes extends ContentFunction {

    public ContentFunctionTimes(char content) {
        super(content);
    }

    @Override
    public double operate(Content op1, Content op2) {
        return ((double) op1.getContent())*((double)op2.getContent());
    }

    @Override
    public double accept(Visitor visitor, Node owner) {
        this.ownerNode = owner;
        return visitor.forFunctionTimes(this.ownerNode);
    }
}
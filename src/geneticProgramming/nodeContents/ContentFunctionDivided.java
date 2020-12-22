package geneticProgramming.nodeContents;

import geneticProgramming.structure.Node;

public class ContentFunctionDivided extends ContentFunction{

    public ContentFunctionDivided(char content) {
        super(content);
    }

    @Override
    public double operate(Content op1, Content op2) {
        if ((double) op2.getContent() == 0){
            return 1;
        }
        else{
            return ((double) op1.getContent())*((double)op2.getContent());
        }
    }

    @Override
    public double accept(Visitor visitor, Node owner) {
        this.ownerNode = owner;
        return visitor.forFunctionDivided(this.ownerNode);
    }
}

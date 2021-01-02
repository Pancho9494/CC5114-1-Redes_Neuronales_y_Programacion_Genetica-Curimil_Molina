package geneticProgramming.nodeContents.contents;

import geneticProgramming.nodeContents.visitors.CopyVisitor;
import geneticProgramming.nodeContents.visitors.EvaluationVisitor;
import geneticProgramming.structure.Node;

public class ContentVariable extends Content {
    private double content;
    private char identifier;

    public ContentVariable(char identifier){
        this.identifier = identifier;
        this.content = 0;
    }

    @Override
    public double acceptEvaluation(EvaluationVisitor evaluationVisitor, Node node) {
        this.ownerNode = node;
        return evaluationVisitor.forVariable(this.ownerNode);
    }

    @Override
    public Content acceptCopy(CopyVisitor copyVisitor) {
        return copyVisitor.forVariable(this.content, identifier);
    }

    @Override
    public String print() {
        return String.valueOf(identifier);
    }

    @Override
    public void setContent(Object content) {
        this.content = (double) content;
    }

    @Override
    public void setOwner(Node owner) {
        this.ownerNode = owner;
    }

    @Override
    public Object getContent() {
        return content;
    }
}

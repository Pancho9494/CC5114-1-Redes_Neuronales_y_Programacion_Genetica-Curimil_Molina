package geneticProgramming.nodeContents.contents;

import geneticProgramming.nodeContents.visitors.CopyVisitor;
import geneticProgramming.nodeContents.visitors.EvaluationVisitor;
import geneticProgramming.nodeContents.factory.constants.ContentConstantFactory;
import geneticProgramming.structure.Node;

public class ContentConstant extends Content {
    private double content;
    private ContentConstantFactory factory;

    public ContentConstant(double content, ContentConstantFactory factory){
        this.content = content;
        this.factory = factory;
    }

    @Override
    public double acceptEvaluation(EvaluationVisitor evaluationVisitor, Node owner) {
        this.ownerNode = owner;
        return evaluationVisitor.forConstant(this.ownerNode);
    }

    @Override
    public Content acceptCopy(CopyVisitor copyVisitor) {
        return copyVisitor.forConstant(this);
    }

    @Override
    public String print() {
        return String.valueOf(content);
    }

    public ContentConstantFactory getFactory() {
        return factory;
    }

    @Override
    public void setContent(Object content){
        this.content = (int) content;
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

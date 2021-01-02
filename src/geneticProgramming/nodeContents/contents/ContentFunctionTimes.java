package geneticProgramming.nodeContents.contents;

import geneticProgramming.nodeContents.visitors.CopyVisitor;
import geneticProgramming.nodeContents.visitors.EvaluationVisitor;
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
    public double acceptEvaluation(EvaluationVisitor evaluationVisitor, Node owner) {
        this.ownerNode = owner;
        return evaluationVisitor.forFunctionTimes(this.ownerNode);
    }

    @Override
    public Content acceptCopy(CopyVisitor copyVisitor) {
        return copyVisitor.forFunctionTimes(this);
    }
}

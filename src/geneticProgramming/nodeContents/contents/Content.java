package geneticProgramming.nodeContents.contents;

import geneticProgramming.nodeContents.visitors.CopyVisitor;
import geneticProgramming.nodeContents.visitors.EvaluationVisitor;
import geneticProgramming.structure.Node;

public abstract class Content {
    protected Node ownerNode;

    public abstract void setContent(Object content);
    public abstract void setOwner(Node owner);
    public abstract Object getContent();
    public abstract double acceptEvaluation(EvaluationVisitor evaluationVisitor, Node node);
    public abstract Content acceptCopy(CopyVisitor copyVisitor);
    public abstract String print();
}

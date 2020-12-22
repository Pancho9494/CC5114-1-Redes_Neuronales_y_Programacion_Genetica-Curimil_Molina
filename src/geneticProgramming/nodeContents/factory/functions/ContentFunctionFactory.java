package geneticProgramming.nodeContents.factory.functions;

import geneticProgramming.nodeContents.Content;
import geneticProgramming.structure.Node;

public abstract class ContentFunctionFactory implements IContentFunctionFactory {

    public abstract Content create(Node owner);

    public abstract Content copyContent(Content cont, Node newOwner);
}

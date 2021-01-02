package geneticProgramming.nodeContents.factory.functions;

import geneticProgramming.nodeContents.contents.Content;
import geneticProgramming.structure.Node;

public interface IContentFunctionFactory {

    Content create(Node owner);
    Content copyContent(Content cont, Node newOwner);
}

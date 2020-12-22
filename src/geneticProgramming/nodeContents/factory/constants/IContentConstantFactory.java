package geneticProgramming.nodeContents.factory.constants;

import geneticProgramming.nodeContents.Content;
import geneticProgramming.structure.Node;

public interface IContentConstantFactory {

    Content create(double content, Node owner);
    Content copyContent(Content cont, Node newOwner);
}

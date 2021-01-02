package geneticProgramming.nodeContents.factory.constants;

import geneticProgramming.nodeContents.contents.Content;
import geneticProgramming.structure.Node;

public abstract class ContentConstantFactory implements IContentConstantFactory {

    public abstract Content create(double content, Node owner);

    public abstract Content copyContent(Content cont, Node newOwner);
}

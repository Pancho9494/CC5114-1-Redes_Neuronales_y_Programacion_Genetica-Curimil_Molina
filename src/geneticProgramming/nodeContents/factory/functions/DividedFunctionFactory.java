package geneticProgramming.nodeContents.factory.functions;

import geneticProgramming.nodeContents.contents.Content;
import geneticProgramming.nodeContents.contents.ContentFunctionDivided;
import geneticProgramming.structure.Node;

public class DividedFunctionFactory extends ContentFunctionFactory {
    @Override
    public Content create(Node owner) {
        ContentFunctionDivided divided = new ContentFunctionDivided('/');
        divided.setOwner(owner);
        return divided;
    }

    @Override
    public Content copyContent(Content cont, Node newOwner) {
        ContentFunctionDivided newDivided = new ContentFunctionDivided((char) cont.getContent());
        newDivided.setOwner(newOwner);
        return newDivided;
    }
}

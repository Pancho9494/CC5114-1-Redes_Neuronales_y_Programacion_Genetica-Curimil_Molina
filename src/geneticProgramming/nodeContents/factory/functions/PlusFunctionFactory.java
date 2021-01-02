package geneticProgramming.nodeContents.factory.functions;

import geneticProgramming.nodeContents.contents.Content;
import geneticProgramming.nodeContents.contents.ContentFunctionPlus;
import geneticProgramming.structure.Node;

public class PlusFunctionFactory extends ContentFunctionFactory {
    @Override
    public Content create(Node owner) {
        ContentFunctionPlus plus = new ContentFunctionPlus('+');
        plus.setOwner(owner);
        return plus;
    }

    @Override
    public Content copyContent(Content cont, Node newOwner) {
        ContentFunctionPlus newPlus = new ContentFunctionPlus((char) cont.getContent());
        newPlus.setOwner(newOwner);
        return newPlus;
    }
}

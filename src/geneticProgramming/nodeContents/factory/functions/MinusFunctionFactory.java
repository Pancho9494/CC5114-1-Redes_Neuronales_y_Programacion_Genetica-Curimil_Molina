package geneticProgramming.nodeContents.factory.functions;

import geneticProgramming.nodeContents.contents.Content;
import geneticProgramming.nodeContents.contents.ContentFunctionMinus;
import geneticProgramming.structure.Node;

public class MinusFunctionFactory extends ContentFunctionFactory {
    @Override
    public Content create(Node owner) {
        ContentFunctionMinus minus = new ContentFunctionMinus('-');
        minus.setOwner(owner);
        return minus;
    }

    @Override
    public Content copyContent(Content cont, Node newOwner) {
        ContentFunctionMinus newMinus = new ContentFunctionMinus((char) cont.getContent());
        newMinus.setOwner(newOwner);
        return newMinus;
    }
}

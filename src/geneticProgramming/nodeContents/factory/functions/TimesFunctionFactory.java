package geneticProgramming.nodeContents.factory.functions;

import geneticProgramming.nodeContents.Content;
import geneticProgramming.nodeContents.ContentFunctionTimes;
import geneticProgramming.structure.Node;

public class TimesFunctionFactory extends ContentFunctionFactory {
    @Override
    public Content create(Node owner) {
        ContentFunctionTimes times = new ContentFunctionTimes('*');
        times.setOwner(owner);
        return times;
    }

    @Override
    public Content copyContent(Content cont, Node newOwner) {
        ContentFunctionTimes newTimes = new ContentFunctionTimes((char) cont.getContent());
        newTimes.setOwner(newOwner);
        return newTimes;
    }
}

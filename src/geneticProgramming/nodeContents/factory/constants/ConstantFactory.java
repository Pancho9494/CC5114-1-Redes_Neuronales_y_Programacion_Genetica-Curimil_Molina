package geneticProgramming.nodeContents.factory.constants;

import geneticProgramming.nodeContents.Content;
import geneticProgramming.nodeContents.ContentConstant;
import geneticProgramming.structure.Node;

public class ConstantFactory extends ContentConstantFactory {

    @Override
    public Content create(double content, Node owner) {
        ContentConstant constant = new ContentConstant(content, this);
        constant.setOwner(owner);
        return constant;
    }

    @Override
    public Content copyContent(Content cont, Node newOwner) {
        ContentConstant newConstant = new ContentConstant((double) cont.getContent(), this);
        newConstant.setOwner(newOwner);
        return newConstant;
    }
}

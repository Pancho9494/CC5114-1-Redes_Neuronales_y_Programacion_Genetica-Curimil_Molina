package geneticProgramming.nodeContents.factory;

import geneticProgramming.nodeContents.Content;
import geneticProgramming.nodeContents.ContentVariable;
import geneticProgramming.structure.Node;

public class VariableFactory {

    public Content create(char identifier, double content, Node owner){
        ContentVariable variable = new ContentVariable(identifier);
        variable.setContent(content);
        variable.setOwner(owner);
        return variable;
    }
}

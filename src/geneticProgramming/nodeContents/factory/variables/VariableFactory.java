package geneticProgramming.nodeContents.factory.variables;

import geneticProgramming.nodeContents.contents.Content;
import geneticProgramming.nodeContents.contents.ContentVariable;
import geneticProgramming.structure.Node;

public class VariableFactory {

    public Content create(char identifier, double content, Node owner){
        ContentVariable variable = new ContentVariable(identifier);
        variable.setContent(content);
        variable.setOwner(owner);
        return variable;
    }
}

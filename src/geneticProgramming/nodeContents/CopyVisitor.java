package geneticProgramming.nodeContents;

import geneticProgramming.structure.Node;

public class CopyVisitor {

    public Content forConstant(Content cont){
        ContentConstant newConstant = new ContentConstant((double) cont.getContent(), null);
        return newConstant;
    }

    public Content forFunctionPlus(Content cont){
        ContentFunctionPlus newPlus = new ContentFunctionPlus((char) cont.getContent());
        return newPlus;
    }

    public Content forFunctionMinus(Content cont){
        ContentFunctionMinus newMinus = new ContentFunctionMinus((char) cont.getContent());
        return newMinus;
    }

    public Content forFunctionTimes(Content cont){
        ContentFunctionTimes newTimes = new ContentFunctionTimes((char) cont.getContent());
        return newTimes;
    }

    public Content forFunctionDivided(Content cont){
        ContentFunctionDivided newDivided = new ContentFunctionDivided((char) cont.getContent());
        return newDivided;
    }

}



package geneticProgramming.geneticOperators;

import geneticProgramming.GPEngine;
import geneticProgramming.structure.Tree;

import java.util.Random;

public abstract class AGPMutation {
    Random random = new Random();
    GPEngine engine;

    public abstract Tree mutate(Tree original);
}

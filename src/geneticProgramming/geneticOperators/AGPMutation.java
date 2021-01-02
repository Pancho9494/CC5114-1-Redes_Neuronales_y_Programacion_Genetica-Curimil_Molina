package geneticProgramming.geneticOperators;

import geneticProgramming.GPEngine;
import geneticProgramming.structure.Tree;

import java.util.Random;

/**
 * This class represents some mutation operation used in genetic programming
 */
public abstract class AGPMutation {
    protected Random random = new Random();
    protected GPEngine engine;

    public abstract Tree mutate(Tree original);
}

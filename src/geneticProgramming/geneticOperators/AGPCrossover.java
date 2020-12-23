package geneticProgramming.geneticOperators;

import geneticProgramming.GPEngine;
import geneticProgramming.structure.Tree;

import java.util.Random;

public abstract class AGPCrossover {
    Random random = new Random();
    GPEngine engine;

    public abstract Tree crossover(Tree parent1, Tree parent2);
}

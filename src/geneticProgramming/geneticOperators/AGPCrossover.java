package geneticProgramming.geneticOperators;

import geneticProgramming.structure.Tree;

import java.util.Random;

public abstract class AGPCrossover {
    Random random = new Random();

    public abstract Tree crossover(Tree parent1, Tree parent2);
}

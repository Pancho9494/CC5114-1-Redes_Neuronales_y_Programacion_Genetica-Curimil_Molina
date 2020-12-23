package geneticProgramming.fitness;

import geneticProgramming.structure.Tree;

public abstract class GPFitnessFunctions {
    protected double target;

    public double getTarget() {
        return target;
    }

    public void setTarget(double input){
        this.target = input;
    }

    public abstract double evaluate(Tree test);
}

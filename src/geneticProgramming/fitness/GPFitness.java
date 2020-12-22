package geneticProgramming.fitness;

import geneticProgramming.structure.Tree;

public class GPFitness {
    double target;

    public void setTarget(double input){
        this.target = input;
    }

    public double getTarget() {
        return target;
    }

    public double evaluate(Tree test){
        double diff = Math.abs(target - test.evaluate());
        return 1/(1 + diff);
    }
}

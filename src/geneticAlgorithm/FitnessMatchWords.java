package geneticAlgorithm;

/**
 * This class represents a fitness function that matches words letter by letter
 * The score increases by one for each character in the corresponding position
 */
public class FitnessMatchWords extends FitnessFunctions{
    Character[] target;
    int goal;

    public FitnessMatchWords(Character[] target){
        this.target = target;
        this.goal = target.length;
    }

    @Override
    public int evaluate(Character[] test){
        int score = 0;
        if (target.length != test.length){
            return score;
        }
        for (int i = 0; i < target.length; i ++){
            if (target[i] == test[i]){
                score ++;
            }
        }
        return score;
    }

    @Override
    public int getGoal() {
        return goal;
    }
}
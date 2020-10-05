package operations;

public class ApplyActivation extends  ApplyToMatrix{
    @Override
    public double operation(double input) {
        if (input > 0.5){
            return 1;
        }
        else{
            return 0;
        }
    }
}

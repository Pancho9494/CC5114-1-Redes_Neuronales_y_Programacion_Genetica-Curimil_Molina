package neuralNetwork.operations;

public class ApplyRound extends ApplyToMatrix {
    int decimals = 2;

    public ApplyRound(int decimals){
        this.decimals = decimals;
    }


    @Override
    public double operation(double input) {
        double decimalPlaces = Math.pow(10,this.decimals);
        return Math.floor(input*decimalPlaces)/decimalPlaces;
    }
}

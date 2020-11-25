import geneticAlgorithm.Engine;
import geneticAlgorithm.FXFitnessHistory;
import geneticAlgorithm.FitnessMatchWords;
import javafx.application.Application;
import javafx.stage.Stage;

public class main extends Application {
    private static FXFitnessHistory fitnessPlot;

    public static void main(String[] args) {
//        String inputSentence = "On offering to help the blind man," +
//                " the man who then stole his car, had not, at that precise moment," +
//                " had any evil intention, quite the contrary, what he did was nothing more" +
//                " than obey those feelings of generosity and altruism which, as everyone knows," +
//                " are the two best traits of human nature and to be found in much more hardened criminals" +
//                " than this one, a simple car-thief without any hope of advancing in his profession, exploited by" +
//                " the real owners of this enterprise, for it is they who take advantage of the needs of the poor";
//
//        String word = inputSentence.toLowerCase().replace(" ", "").replace(",", "").replace("-","");
        String word = "paralelepipedo";
        Engine geneticAlg = new Engine();
        FitnessMatchWords fit = new FitnessMatchWords(geneticAlg.stringToCharArray(word));
        fitnessPlot = new FXFitnessHistory();

        geneticAlg.executeAlgorithm(50,0.2,100,
                                    word.length(), 5,fit);

        fitnessPlot.setUpData(geneticAlg.getBestFitnessHistory(), geneticAlg.getWorstFitnessHistory(),
                            geneticAlg.getMeanFitnessHistory());
        fitnessPlot.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        fitnessPlot.begin(stage);
    }

}

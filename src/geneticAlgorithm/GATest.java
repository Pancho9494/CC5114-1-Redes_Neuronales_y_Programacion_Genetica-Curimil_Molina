package geneticAlgorithm;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static junit.framework.TestCase.*;

public class GATest {
    Random random = new Random();
    Engine testPop;
    int populationSize;
    int genes;

    public Character[] stringToCharArray(String original){
        Character word[] = new Character[original.length()];
        for (int i = 0; i < original.length(); i++) {
            word[i] = original.charAt(i);
        }
        return word;
    }

    public boolean compareCharArrays(Character[] char1, Character[] char2){
        if (char1.length != char2.length){
            return false;
        }
        for (int i = 0; i < char1.length; i ++){
            if (char1[i] != char2[i]){
                return false;
            }
        }
        return true;
    }

    @Before
    public void setUp() {
        random.setSeed(42);
        this.populationSize = 1000;
    }

    @Test
    public void generateWordTest(){
        int test = random.nextInt(100);
        testPop = new Engine();
        testPop.initializePopulation(populationSize,5);
        assertEquals(test,testPop.generateIndividual(test).length);

    }

    @Test
    public void initializePopulationTest(){
        testPop = new Engine();
        testPop.initializePopulation(populationSize,5);
        assertEquals(populationSize,testPop.getPopulationSize());
        for (int i = 0; i < populationSize; i ++){
            assertEquals(5, testPop.getPopulation().get(i).length);
        }
    }

    @Test
    public void tournamentSelectionTest(){
        testPop = new Engine();
        testPop.initializePopulation(populationSize,3);
        testPop.setTargetWord("cat");
        FitnessMatchWords fitFunction = new FitnessMatchWords(stringToCharArray("cat"));
        testPop.setFitness(fitFunction);
        Character[] selection = testPop.tournamentSelection(5);
        assertEquals(testPop.getTargetWord().length, selection.length);
    }

    @Test
    public void crossoverTest(){
        testPop = new Engine();
        testPop.initializePopulation(populationSize,5);
        testPop.setRandomSeed(7);
        Character[] parent1 = stringToCharArray("Table");
        Character[] parent2 = stringToCharArray("Chair");
        Character[] result = testPop.crossover(parent1,parent2);
        assertTrue(compareCharArrays(stringToCharArray("Tabir"), result));
    }


    @Test
    public void mutationTest(){
        testPop = new Engine();
        testPop.initializePopulation(populationSize,3);
        Character[] original = stringToCharArray("cat");

        testPop.setMutationRate(1.0);
        Character[] mutated = testPop.mutate(original);

        assertEquals(original.length, mutated.length);

        int difference = 0;
        for (int i = 0; i < original.length; i ++) {
            if (original[i] != mutated[i]){
                difference ++;
            }
        }
        assertEquals(1,difference);

        testPop.setMutationRate(0.0);
        Character[] notMutated = testPop.mutate(original);

        assertEquals(original.length, notMutated.length);

        int same = 0;
        for (int i = 0; i < original.length; i ++) {
            if (original[i] == notMutated[i]){
                same ++;
            }
        }
        assertEquals(original.length,same);
    }

    @Test
    public void newGenerationTest(){
        testPop = new Engine();
        testPop.initializePopulation(10,3);
        FitnessMatchWords fitFunction = new FitnessMatchWords(stringToCharArray("cat"));
        testPop.setTargetWord("cat");
        testPop.setFitness(fitFunction);

        ArrayList<Character[]> original = new ArrayList<>(testPop.getPopulation());
        testPop.newGeneration();

        assertEquals(original.size(), testPop.getPopulation().size());
        for (int i = 0; i < original.size(); i ++){
            assertFalse(compareCharArrays(original.get(i),testPop.getPopulation().get(i)));
        }
    }

    @Test
    public void algorithmTest(){
        // Simple word
        String word = "cat";
        testPop = new Engine();
        FitnessMatchWords fitFunction = new FitnessMatchWords(stringToCharArray(word));
        testPop.setTargetWord(word);

        Character[] expected = stringToCharArray(word);
        Character[] real = testPop.executeAlgorithm(50, 0.02, 500,
                                                    word.length(), 3, fitFunction);
        assertTrue(compareCharArrays(expected,real));

        // Long sentence
        // transforms into lowercase, without special characters such as "," "-" "'" or "."
        String inputSentence = "On offering to help the blind man," +
                " the man who then stole his car, had not, at that precise moment," +
                " had any evil intention, quite the contrary, what he did was nothing more" +
                " than obey those feelings of generosity and altruism which, as everyone knows," +
                " are the two best traits of human nature and to be found in much more hardened criminals" +
                " than this one, a simple car-thief without any hope of advancing in his profession, exploited by" +
                " the real owners of this enterprise, for it is they who take advantage of the needs of the poor";

        String sentence = inputSentence.toLowerCase().replace(" ", "").replace(",", "").replace("-","");
        testPop = new Engine();
        FitnessMatchWords fitSentence = new FitnessMatchWords(stringToCharArray(sentence));
        testPop.setTargetWord(sentence);

        Character[] expectedSent = stringToCharArray(sentence);
        Character[] realSent = testPop.executeAlgorithm(1000, 0.02, 5000,
                                                        sentence.length(), 5, fitSentence);
        assertTrue(compareCharArrays(expectedSent,realSent));
    }
}

package geneticAlgorithm;

import geneticAlgorithm.fitness.FitnessMatchWords;
import geneticAlgorithm.fitness.FitnessNQueens;
import geneticAlgorithm.Individuals.Factory.IndividualFactory;
import geneticAlgorithm.Individuals.Factory.NQueenFactory;
import geneticAlgorithm.Individuals.Factory.WordFactory;
import geneticAlgorithm.Individuals.Individual;
import geneticAlgorithm.Individuals.IndividualNQueen;
import geneticAlgorithm.Individuals.IndividualNull;
import geneticAlgorithm.Individuals.IndividualWord;
import geneticAlgorithm.geneticOperators.*;
import geneticAlgorithm.specialObjects.Queen;
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

    public Individual stringToIndividual(String original){
        IndividualWord word = new IndividualWord(original.length());
        for (int i = 0; i < original.length(); i++) {
            word.setGene(i,original.charAt(i));
        }
        return word;
    }

    public boolean compareCharArrays(Individual ind1, Individual ind2){
        if (ind1.getChromosomeSize() != ind2.getChromosomeSize()){
            return false;
        }
        for (int i = 0; i < ind1.getChromosomeSize(); i ++){
            if (ind1.getGene(i) != ind2.getGene(i)){
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
        assertEquals(test,testPop.generateIndividual(test).getChromosomeSize());

    }

    @Test
    public void initializePopulationTest(){
        testPop = new Engine();
        testPop.initializePopulation(populationSize,5);
        assertEquals(populationSize,testPop.getPopulationSize());
        for (int i = 0; i < populationSize; i ++){
            assertEquals(5, testPop.getPopulation().get(i).getChromosomeSize());
        }
    }

    @Test
    public void tournamentSelectionTest(){
        testPop = new Engine();
        testPop.initializePopulation(populationSize,3);
        testPop.setTargetWord("cat");
        IndividualFactory factory = new WordFactory();
        FitnessMatchWords fitFunction = new FitnessMatchWords(stringToIndividual("cat"),factory);
        testPop.setFitnessFunction(fitFunction);
        Individual selection = testPop.tournamentSelection(5);
        assertEquals(testPop.getTargetWord().getChromosomeSize(), selection.getChromosomeSize());
    }

    @Test
    public void crossoverTest(){
        testPop = new Engine();
        Crossover cross = new Crossover();
        testPop.setCrossover(cross);
        IndividualFactory factory = new WordFactory();
        testPop.setIndividualFactory(factory);
        testPop.initializePopulation(populationSize,5);
        testPop.setRandomSeed(7);
        Individual parent1 = stringToIndividual("Table");
        Individual parent2 = stringToIndividual("Chair");
        Individual result = testPop.getCrossover().crossover(parent1,parent2, new WordFactory());
        assertTrue(compareCharArrays(stringToIndividual("Tabir"), result));
    }


    @Test
    public void mutationTest(){
        testPop = new Engine();
        IndividualFactory factory = new WordFactory();
        testPop.initializePopulation(populationSize,3);
        Individual original = stringToIndividual("cat");

        testPop.setMutationRate(1.0);
        Individual mutated = testPop.getMutation().mutate(original,factory,1.0);

        assertEquals(original.getChromosomeSize(), mutated.getChromosomeSize());

        int difference = 0;
        for (int i = 0; i < original.getChromosomeSize(); i ++) {
            if (original.getGene(i) != mutated.getGene(i)){
                difference ++;
            }
        }
        assertEquals(1,difference);

        testPop.setMutationRate(0.0);

        Individual notMutated = testPop.getMutation().mutate(original,factory,0.0);

        assertEquals(original.getChromosomeSize(), notMutated.getChromosomeSize());

        int same = 0;
        for (int i = 0; i < original.getChromosomeSize(); i ++) {
            if (original.getGene(i) == notMutated.getGene(i)){
                same ++;
            }
        }
        assertEquals(original.getChromosomeSize(),same);
    }

    @Test
    public void newGenerationTest(){
        testPop = new Engine();
        IndividualFactory factory = new WordFactory();
        testPop.setIndividualFactory(factory);
        testPop.initializePopulation(10,3);
        FitnessMatchWords fitFunction = new FitnessMatchWords("cat",factory);
        Crossover cross = new Crossover();
        testPop.setCrossover(cross);
        Mutation mutation = new Mutation();
        testPop.setTargetWord("cat");
        testPop.setFitnessFunction(fitFunction);

        ArrayList<Individual> original = new ArrayList<>(testPop.getPopulation());
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
        Crossover cross = new Crossover();
        AMutation mutate = new Mutation();
        testPop.setCrossover(cross);
        IndividualFactory factory = new WordFactory();
        FitnessMatchWords fitFunction = new FitnessMatchWords(stringToIndividual(word), factory);
        testPop.setTargetWord(word);

        Individual expected = stringToIndividual(word);
        Individual real = testPop.executeAlgorithm(50, 0.2, 500,
                word.length(), 3, fitFunction,cross,mutate);
        assertTrue(compareCharArrays(expected,real));

        // Long sentence
        // transforms into lowercase, without special characterss such as "," "-" "'" or "."
//        String inputSentence = "On offering to help the blind man," +
//                " the man who then stole his car, had not, at that precise moment," +
//                " had any evil intention, quite the contrary, what he did was nothing more" +
//                " than obey those feelings of generosity and altruism which, as everyone knows," +
//                " are the two best traits of human nature and to be found in much more hardened criminals" +
//                " than this one, a simple car-thief without any hope of advancing in his profession, exploited by" +
//                " the real owners of this enterprise, for it is they who take advantage of the needs of the poor";
//
//        String sentence = inputSentence.toLowerCase().replace(" ", "").replace(",", "").replace("-","");
//        testPop = new Engine();
//        FitnessMatchWords fitSentence = new FitnessMatchWords(stringToIndividual(sentence));
//        testPop.setTargetWord(sentence);
//
//        Individual expectedSent = stringToIndividual(sentence);
//        Individual realSent = testPop.executeAlgorithm(1000, 0.2, 5000,
//                sentence.length(), 5, fitSentence);
//        assertTrue(compareCharArrays(expectedSent,realSent));
    }

    @Test
    public void orderedCrossoverTest(){
        AGACrossover cross = new CrossoverOrdered();
        IndividualFactory fact = new WordFactory();
        Individual parent1 = stringToIndividual("table");
        Individual parent2 = stringToIndividual("chair");

        Individual child = cross.crossover(parent1,parent2, fact);
        assertEquals(child.getNumberOfGenes(), parent1.getNumberOfGenes());
        assertEquals(child.getNumberOfGenes(), parent2.getNumberOfGenes());
    }


    @Test
    public void queenFitnessTest(){
        int expectedScore = 2*4*3;
        Queen queen0 = new Queen(0,0);
        Queen queen1 = new Queen(1,0);
        Queen queen2 = new Queen(2,0);
        Queen queen3 = new Queen(3,0);
        IndividualNQueen board = new IndividualNQueen(4);
        board.setGene(0,queen0);
        board.setGene(1,queen1);
        board.setGene(2,queen2);
        board.setGene(3,queen3);
        IndividualFactory factory = new NQueenFactory();
        FitnessNQueens fit = new FitnessNQueens(new IndividualNull(4),factory);
        assertEquals(expectedScore, fit.evaluate(board));


    }
}
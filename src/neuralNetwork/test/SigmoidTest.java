//package neuralNetwork.datasets.test;
//
//import junit.framework.TestCase;
//import neuralNetwork.datasets.neurons.Sigmoid;
//import neuralNetwork.datasets.operations.BinaryAdder;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Random;
//
//import static junit.framework.TestCase.assertEquals;
//
//public class SigmoidTest {
//    Sigmoid sNeuron = new Sigmoid();
//    BinaryAdder BA = new BinaryAdder();
//    Random random = new Random();
//    ArrayList<ArrayList<Double>> andExamples = new ArrayList<>();
//    ArrayList<Double> andLabels = new ArrayList<>();
//    ArrayList<ArrayList<Double>> orExamples = new ArrayList<>();
//    ArrayList<Double> orLabels = new ArrayList<>();
//    ArrayList<ArrayList<Double>> nandExamples = new ArrayList<>();
//    ArrayList<Double> nandLabels = new ArrayList<>();
//    ArrayList<ArrayList<Double>> xorExamples = new ArrayList<>();
//    ArrayList<Double> xorLabels = new ArrayList<>();
//
//    @Before
//    public void setUp(){
//        random.setSeed(42);
//        // Initialize weights
//        sNeuron.addWeights((double) random.nextInt(4) - 2); //random.nextInt((high-low)-low);
//        sNeuron.addWeights((double) random.nextInt(4) - 2);
//
//        // Create example set for AND
//        andExamples.add(new ArrayList<Double>(Arrays.asList(-1.0,-1.0)));
//        andLabels.add(-1.0);
//        andExamples.add(new ArrayList<Double>(Arrays.asList(-1.0,1.0)));
//        andLabels.add(-1.0);
//        andExamples.add(new ArrayList<Double>(Arrays.asList(1.0,-1.0)));
//        andLabels.add(-1.0);
//        andExamples.add(new ArrayList<Double>(Arrays.asList(1.0,1.0)));
//        andLabels.add(1.0);
//
//        // Create example set for OR
//        orExamples.add(new ArrayList<Double>(Arrays.asList(-1.0,-1.0)));
//        orLabels.add(-1.0);
//        orExamples.add(new ArrayList<Double>(Arrays.asList(-1.0,1.0)));
//        orLabels.add(1.0);
//        orExamples.add(new ArrayList<Double>(Arrays.asList(1.0,-1.0)));
//        orLabels.add(1.0);
//        orExamples.add(new ArrayList<Double>(Arrays.asList(1.0,1.0)));
//        orLabels.add(1.0);
//
//        // Create example set for NAND
//        nandExamples.add(new ArrayList<Double>(Arrays.asList(-1.0,-1.0)));
//        nandLabels.add(1.0);
//        nandExamples.add(new ArrayList<Double>(Arrays.asList(-1.0,1.0)));
//        nandLabels.add(1.0);
//        nandExamples.add(new ArrayList<Double>(Arrays.asList(1.0,-1.0)));
//        nandLabels.add(1.0);
//        nandExamples.add(new ArrayList<Double>(Arrays.asList(1.0,1.0)));
//        nandLabels.add(-1.0);
//
//        // Create example set for XOR
//        xorExamples.add(new ArrayList<Double>(Arrays.asList(-1.0,-1.0)));
//        xorLabels.add(-1.0);
//        xorExamples.add(new ArrayList<Double>(Arrays.asList(-1.0,1.0)));
//        xorLabels.add(1.0);
//        xorExamples.add(new ArrayList<Double>(Arrays.asList(1.0,-1.0)));
//        xorLabels.add(1.0);
//        xorExamples.add(new ArrayList<Double>(Arrays.asList(1.0,1.0)));
//        xorLabels.add(-1.0);
//    }
//
//    @Test
//    public void ANDTest(){
//        sNeuron.train(andExamples,andLabels);
//        TestCase.assertEquals(-1.0, sNeuron.compute(-1.0,-1.0));
//        TestCase.assertEquals(-1.0, sNeuron.compute(-1.0,1.0));
//        TestCase.assertEquals(-1.0, sNeuron.compute(1.0,-1.0));
//        TestCase.assertEquals(1.0, sNeuron.compute(1,1));
//    }
//
//    @Test
//    public void ORTest(){
//        sNeuron.train(orExamples,orLabels);
//        TestCase.assertEquals(-1.0, sNeuron.compute(-1.0,0));
//        TestCase.assertEquals(1.0, sNeuron.compute(-1.0,1.0));
//        TestCase.assertEquals(1.0, sNeuron.compute(1.0,-1.0));
//        TestCase.assertEquals(1.0, sNeuron.compute(1.0,1.0));
//    }
//
//    @Test
//    public void NANDTest(){
//        sNeuron.train(nandExamples,nandLabels);
//        TestCase.assertEquals(1.0, sNeuron.compute(-1.0,-1.0));
//        TestCase.assertEquals(1.0, sNeuron.compute(-1.0,1.0));
//        TestCase.assertEquals(1.0, sNeuron.compute(1.0,-1.0));
//        TestCase.assertEquals(-1.0, sNeuron.compute(1.0,1.0));
//    }
//
//    /**
//     * The sigmoid neuron by itself shouldn't be able to compute the XOR
//     */
////    @Test
////    public void XORTest(){
////        sNeuron.train(xorExamples,xorLabels);
////        TestCase.assertEquals(-1.0, sNeuron.compute(-1.0,0));
////        TestCase.assertEquals(1.0, sNeuron.compute(-1.0,1.0));
////        TestCase.assertEquals(1.0, sNeuron.compute(1.0,-1.0));
////        TestCase.assertEquals(-1.0, sNeuron.compute(1.0,1.0));
////    }
//}

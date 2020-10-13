//package test;
//
//import junit.framework.TestCase;
//import neurons.Perceptron;
//import operations.BinaryAdder;
//import org.junit.Test;
//
//
//public class PerceptronTest {
//    Perceptron PT = new Perceptron();
//    BinaryAdder BA = new BinaryAdder();
//
//    @Test
//    public void ANDTest(){
//        PT.AND();
//        TestCase.assertEquals(-1.0,PT.compute(0,0));
//        TestCase.assertEquals(-1.0,PT.compute(0,1));
//        TestCase.assertEquals(-1.0,PT.compute(1,0));
//        TestCase.assertEquals(1.0,PT.compute(1,1));
//    }
//
//    @Test
//    public void ORTest(){
//        PT.OR();
//        TestCase.assertEquals(-1.0,PT.compute(0,0));
//        TestCase.assertEquals(1.0,PT.compute(0,1));
//        TestCase.assertEquals(1.0,PT.compute(1,0));
//        TestCase.assertEquals(1.0,PT.compute(1,1));
//    }
//
//    @Test
//    public void NANDTest(){
//        PT.NAND();
//        TestCase.assertEquals(1.0,PT.compute(0,0));
//        TestCase.assertEquals(1.0,PT.compute(0,1));
//        TestCase.assertEquals(1.0,PT.compute(1,0));
//        TestCase.assertEquals(-1.0,PT.compute(1,1));
//    }
//
//    @Test
//    public void BinaryAddTest(){
//        TestCase.assertEquals(-1.0,BA.sum(0,0));
//        TestCase.assertEquals(1.0,BA.sum(0,1));
//        TestCase.assertEquals(1.0,BA.sum(1,0));
//        TestCase.assertEquals(-1.0,BA.sum(1,1));
//
//        TestCase.assertEquals(-1.0,BA.cOut(0,0));
//        TestCase.assertEquals(-1.0,BA.cOut(0,1));
//        TestCase.assertEquals(-1.0,BA.cOut(1,0));
//        TestCase.assertEquals(1.0,BA.cOut(1,1));
//
//    }
//}

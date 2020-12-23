package geneticProgramming;

import geneticProgramming.geneticOperators.CrossoverSubTree;
import geneticProgramming.geneticOperators.MutationSubTree;
import geneticProgramming.nodeContents.ContentConstant;
import geneticProgramming.nodeContents.ContentFunction;
import geneticProgramming.nodeContents.ContentFunctionPlus;
import geneticProgramming.nodeContents.ContentFunctionTimes;
import geneticProgramming.structure.Node;
import geneticProgramming.structure.Tree;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class GPTest {
    ArrayList<Double> numbers = new ArrayList<>();
    ContentConstant four;
    ContentConstant five;
    ContentConstant two;
    ContentFunction plus;
    ContentFunction times;
    Node root;
    Tree tree;

    @Before
    public void setUp(){
        four = new ContentConstant(4,null);
        five = new ContentConstant(5,null);
        two = new ContentConstant(2,null);
        plus = new ContentFunctionPlus('+');
        times = new ContentFunctionTimes('*');
        Node lNode = new Node(four,
                null,
                null);
        Node rNode = new Node(times,
                new Node(five,
                        null,
                        null),
                new Node(two,
                        null,
                        null));
        root = new Node(plus, lNode, rNode);
        tree = new Tree(root);

        numbers.add(1.0);
        numbers.add(2.0);
        numbers.add(3.0);
        numbers.add(4.0);
        numbers.add(5.0);
        numbers.add(6.0);
        numbers.add(7.0);
        numbers.add(8.0);
        numbers.add(9.0);
        numbers.add(10.0);
    }

    @Test
    public void createTreeTest(){
        assertEquals('+',tree.getRoot().value().getContent());
        assertEquals(4.0,tree.getRoot().getLeft().value().getContent());
        assertEquals('*',tree.getRoot().getRight().value().getContent());
        assertEquals(5.0,tree.getRoot().getRight().getLeft().value().getContent());
        assertEquals(2.0,tree.getRoot().getRight().getRight().value().getContent());
    }

    @Test
    public void copyTreeTest(){
        Tree newTree = tree.copyTree();
        assertEquals('+',newTree.getRoot().value().getContent());
        assertEquals(4.0,newTree.getRoot().getLeft().value().getContent());
        assertEquals('*',newTree.getRoot().getRight().value().getContent());
        assertEquals(5.0,newTree.getRoot().getRight().getLeft().value().getContent());
        assertEquals(2.0,newTree.getRoot().getRight().getRight().value().getContent());
    }

    @Test
    public void printTest(){
        String out = tree.print();
        assertEquals("4.0+5.0*2.0", out);
    }

    @Test
    public void evaluateTest(){
        tree.evaluate();
    }

    @Test
    public void getNodeAtTest(){
        GPEngine GP = new GPEngine(4,3,0);
        GP.setInputNumbers(numbers);
        GP.setRandomSeed(42);
        ArrayList<Tree> Trees = GP.generateTrees(4,3,false);
        Tree test = Trees.get(0);

        Tree one = test.extractSubTree(0);
        Tree divided = test.extractSubTree(1);
        Tree two0 = test.extractSubTree(2);
        Tree two1 = test.extractSubTree(3);
        Tree times = test.extractSubTree(4);
        Tree three = test.extractSubTree(5);

        assertEquals(1.0,one.getRoot().value().getContent());
        assertEquals('/',divided.getRoot().value().getContent());
        assertEquals(2.0,two0.getRoot().value().getContent());
        assertEquals(2.0,two1.getRoot().value().getContent());
        assertEquals('*',times.getRoot().value().getContent());
        assertEquals(3.0,three.getRoot().value().getContent());

    }

    @Test
    public void replaceTest(){
        Node lNode = new Node(
                new ContentConstant(3,null),
                null,
                null);
        Node rNode = new Node(
                new ContentConstant(4,null),
                null,
                null);
        Node root = new Node(
                new ContentFunctionTimes('*'),
                lNode,
                rNode);
        Tree newSubTree = new Tree(root);
        Tree newTree = tree.replaceSubTree(newSubTree, 0);
        assertEquals("3.0*4.0+5.0*2.0", newTree.print());
    }

    @Test
    public void extractTest(){
        Tree newTree = tree.extractSubTree(1);
        assertEquals("5.0*2.0",newTree.print());
    }

    @Test
    public void numberOfNodesTest(){
        assertEquals(4,tree.numberOfNodes());
    }

    @Test
    public void crossoverTest(){
        for (int i = 0; i < 100; i ++){
        GPEngine GP = new GPEngine(6,5,0.5);
        CrossoverSubTree cross = new CrossoverSubTree(GP);
        Node lNode = new Node(
                new ContentConstant(3,null),
                null,
                null);
        Node rNode = new Node(
                new ContentFunctionPlus('+'),
                new Node(
                        new ContentConstant(6,null),
                        null,
                        null),
                new Node(
                        new ContentConstant(4,null),
                        null,
                        null)
        );
        Node root = new Node(
                new ContentFunctionTimes('*'),
                lNode,
                rNode);
        Tree anotherTree = new Tree(root);
            Tree result = cross.crossover(tree,anotherTree);
            assertTrue(result.depth() <= GP.getMaxDepth());
        }
    }

    @Test
    public void depthTest(){
        assertEquals(3, tree.depth());
    }

    @Test
    public void generateTrees(){
        int popSize = 5;
        int maxDepth = 3;
        double mutRate = 0.5;
        GPEngine GP = new GPEngine(popSize, maxDepth, mutRate);
        GP.setInputNumbers(numbers);
        ArrayList<Tree> population = GP.generateTrees(popSize, maxDepth, false);
//        String out = null;
        for (int i = 0; i < 5; i++){
            assertTrue(population.get(i).depth() <= 3);
//            out = population.get(i).print();
        }
    }

    @Test
    public void mutationTest(){
        for (int i = 0; i < 100; i ++) {
            GPEngine GP = new GPEngine(5, 5, 1);
            GP.setInputNumbers(numbers);
            MutationSubTree mut = new MutationSubTree(GP);
            Tree result = mut.mutate(tree);
            assertTrue(result.depth() <= 5);
        }
    }
}



















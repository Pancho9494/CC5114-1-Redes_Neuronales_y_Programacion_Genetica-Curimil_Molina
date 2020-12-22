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
        four = new ContentConstant(4);
        five = new ContentConstant(5);
        two = new ContentConstant(2);
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

        numbers.add(11.0);
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
    public void replaceTest(){
        Node lNode = new Node(
                new ContentConstant(3),
                null,
                null);
        Node rNode = new Node(
                new ContentConstant(4),
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
        CrossoverSubTree cross = new CrossoverSubTree();
        Node lNode = new Node(
                new ContentConstant(3),
                null,
                null);
        Node rNode = new Node(
                new ContentFunctionPlus('+'),
                new Node(
                        new ContentConstant(6),
                        null,
                        null),
                new Node(
                        new ContentConstant(4),
                        null,
                        null)
        );
        Node root = new Node(
                new ContentFunctionTimes('*'),
                lNode,
                rNode);
        Tree anotherTree = new Tree(root);
        cross.setRandomSeed(128);
        Tree result = cross.crossover(tree,anotherTree);
        assertEquals("4.0+6.0+4.0*2.0",result.print());
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
        ArrayList<Tree> population = GP.generateTrees(popSize, maxDepth);
//        String out = null;
        for (int i = 0; i < 5; i++){
            assertTrue(population.get(i).depth() <= 3);
//            out = population.get(i).print();
        }
    }

    @Test
    public void mutationTest(){
        GPEngine GP = new GPEngine(5,3,0.5);
        GP.setInputNumbers(numbers);
        System.out.print("Original= "+tree.print()+"\n");
        MutationSubTree mut = new MutationSubTree(GP);
        Tree result = mut.mutate(tree);
        System.out.print("Mutate= "+result.print());
        assertTrue(result.depth() <= 3);
    }
}



















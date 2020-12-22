package geneticProgramming;

import geneticAlgorithm.Engine;
import geneticAlgorithm.Individuals.Factory.IndividualFactory;
import geneticAlgorithm.Individuals.Factory.WordFactory;
import geneticAlgorithm.Individuals.Individual;
import geneticAlgorithm.geneticOperators.Crossover;
import geneticProgramming.geneticOperators.CrossoverSubTree;
import geneticProgramming.nodeContents.ContentConstant;
import geneticProgramming.nodeContents.ContentFunction;
import geneticProgramming.nodeContents.ContentFunctionPlus;
import geneticProgramming.nodeContents.ContentFunctionTimes;
import geneticProgramming.structure.Node;
import geneticProgramming.structure.Tree;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class GPTest {
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
}



















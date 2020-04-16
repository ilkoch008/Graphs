package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;


public class AppTest 
{

    @Test
    public void TreeTest()
    {
        ArrayList<Integer> keys = new ArrayList<>();
        Random random = new Random();
        RBTree tree = new RBTree();
        boolean added;
        Integer adding;
        for (int i = 0; i < 500; i++) {
            adding = random.nextInt(1000);
            added = tree.addElement(new RBNode.Builder().setKey(adding).build());
            if(added){
                keys.add(adding);
            }
            assertTrue(tree.testIsOK());
        }

        for (Integer key : keys) {
            tree.deleteElement(key);
            assertTrue(tree.testIsOK());
        }

    }

    @Test
    public void KraskalAlgorithmTest1(){
        Graph graph = new Graph();
        graph.init();
        graph.readFrom("C:\\ProgsProjects\\Graphs_task\\src\\KraskalAlgorithmTest1.txt");

        Graph minTreeWiki = graph.readAnswerForKraskalFrom("C:\\ProgsProjects\\Graphs_task\\src\\KraskalAlgorithmTest1_Answer.txt");

        Graph minTree = graph.getMinSpanningTree();

        assertTrue(minTree.equalsUndirected(minTreeWiki));
    }

    @Test
    public void KraskalAlgorithmTest2() {
        Graph graph = new Graph();
        graph.init();
        graph.readFrom("C:\\ProgsProjects\\Graphs_task\\src\\KraskalAlgorithmTest2.txt");

        Graph minTreeWiki = graph.readAnswerForKraskalFrom("C:\\ProgsProjects\\Graphs_task\\src\\KraskalAlgorithmTest2_Answer.txt");

        Graph minTree = graph.getMinSpanningTree();

        assertTrue(minTree.equalsUndirected(minTreeWiki));
    }

    @Test
    public void DijkstraAlgorithmTest1() {
        Graph graph = new Graph();
        graph.init();
        graph.readFrom("C:\\ProgsProjects\\Graphs_task\\src\\DijkstraTest1.txt");

        graph.findMinWays(graph.getNode(1));

        ArrayList<Node> answer = graph.readAnswerForDijkstraFrom("C:\\ProgsProjects\\Graphs_task\\src\\DijkstraTest1_Answer.txt");

        for (Node node: answer){
            assertEquals(graph.getNode(node.getKey()).wayWeight, node.wayWeight);
        }
    }

    @Test
    public void DijkstraAlgorithmTest2() {
        Graph graph = new Graph();
        graph.init();
        graph.readFrom("C:\\ProgsProjects\\Graphs_task\\src\\DijkstraTest2.txt");

        graph.findMinWays(graph.getNode(1));

        ArrayList<Node> answer = graph.readAnswerForDijkstraFrom("C:\\ProgsProjects\\Graphs_task\\src\\DijkstraTest2_Answer.txt");

        for (Node node: answer){
            assertEquals(graph.getNode(node.getKey()).wayWeight, node.wayWeight);
        }

    }
}

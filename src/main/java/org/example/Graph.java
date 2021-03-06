package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Graph {
    private ArrayList<Node> nodes = null;
    private ArrayList<Edge> edges = null;
    private Node lastRoot = null;

    public void init(){
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public Node getNode(Integer key){
        for(Node node: nodes){
            if(node.getKey().equals(key)){
                return node;
            }
        }
        return null;
    }

    public boolean isInitialized(){
        return (nodes != null && edges != null);
    }

    public boolean addEdge(Edge edge){
        if(!edges.contains(edge)){
            edges.add(edge);
            if(!nodes.contains(edge.getStartNode())){
                nodes.add(edge.getStartNode());
            }
            if(!nodes.contains(edge.getEndNode())){
                nodes.add(edge.getEndNode());
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean addNode(Node node){
        if(!nodes.contains(node)){
            nodes.add(node);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeEdge(Edge edge){
        return edges.remove(edge);
    }

    public boolean removeNode(Node node){
        ArrayList<Edge> toRemove = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getStartNode().equals(node) || edge.getEndNode().equals(node)) {
                toRemove.add(edge);
            }
        }
        edges.removeAll(toRemove);
        return nodes.remove(node);
    }

    public boolean removeNode(Integer key){
        for(Node node: nodes){
            if(node.getKey().equals(key)){
                return removeNode(node);
            }
        }
        return false;
    }
    
    public ArrayList<Edge> getEdgesFromNode(Node node){
        ArrayList<Edge> retEdges = new ArrayList<>();
        int i = 0;
        for(Edge edge: edges){
            if(edge.getStartNode().equals(node)){
                retEdges.add(edge);
            }
        }
        return retEdges;
    }

    public ArrayList<Edge> getEdgesFromNode(Integer key){
        for(Node node: nodes){
            if(node.getKey().equals(key)){
                return getEdgesFromNode(node);
            }
        }
        return new ArrayList<>();
    }

    public ArrayList<Edge> getEdgesToNode(Node node){
        ArrayList<Edge> retEdges = new ArrayList<>();
        for(Edge edge: edges){
            if(edge.getEndNode().equals(node)){
                retEdges.add(edge);
            }
        }
        return retEdges;
    }

    public ArrayList<Edge> getEdgesToNode(Integer key){
        for(Node node: nodes){
            if(node.getKey().equals(key)){
                return getEdgesToNode(node);
            }
        }
        return new ArrayList<>();
    }

    public ArrayList<Edge> getEdges(Integer key){
        ArrayList<Edge> ret = getEdgesToNode(key);
        ret.addAll(getEdgesFromNode(key));
        return ret;
    }

    public Graph getMinSpanningTree(){ // Kraskal Algorithm
        Graph minTree = new Graph();
        minTree.init();
        ArrayList<Edge> edges = new ArrayList<>();
        for(Edge edge: this.edges){
            edges.add(edge.getCopy());
        }
        edges.sort(Edge::compareTo);
        ArrayList<Graph> interGraphs = new ArrayList<>();
        interGraphs.add(new Graph());
        interGraphs.get(0).init();
        for (int i = 0; i < edges.size(); i++) {
            ArrayList<Integer> nothingInCommon = new ArrayList<>();
            ArrayList<Integer> oneInCommon = new ArrayList<>();
            ArrayList<Integer> twoInCommon = new ArrayList<>();
            for (int j = 0; j < interGraphs.size(); j++) {
                if(haveNothingInCommon(interGraphs.get(j), edges.get(i))){
                    nothingInCommon.add(j);
                } else if(haveOneNodeInCommon(interGraphs.get(j), edges.get(i))){
                    oneInCommon.add(j);
                } else {
                    twoInCommon.add(j);
                }
            }
            if(twoInCommon.size() != 0){
                //nothiiiiiiiiiiiiiiiing
            } else if(oneInCommon.size() != 0){
                if(oneInCommon.size() == 1){
                    interGraphs.get(oneInCommon.get(0)).addEdge(edges.get(i));
                } else if(oneInCommon.size() == 2){
                    interGraphs.get(oneInCommon.get(0)).addGraphSloppy(interGraphs.get(oneInCommon.get(1)));
                    interGraphs.get(oneInCommon.get(0)).addEdge(edges.get(i));
                    interGraphs.remove((int) oneInCommon.get(1));
                }
            } else if (nothingInCommon.size() != 0){
                Graph newGraph = new Graph();
                newGraph.init();
                newGraph.addEdge(edges.get(i));
                interGraphs.add(newGraph);
            }
        }
        for (Graph interGraph: interGraphs){
            minTree.addGraphSloppy(interGraph);
        }
        return minTree;
    }

    private void addGraphSloppy(Graph graph) {
        this.edges.addAll(graph.getEdges());
        this.nodes.addAll(graph.getNodes());
    }

    private boolean haveOneNodeInCommon(Graph graph, Edge edge) {
        return graph.getNodes().contains(edge.getStartNode()) ^ graph.getNodes().contains(edge.getEndNode());
    }

    private boolean haveNothingInCommon(Graph graph, Edge edge) {
        return !graph.getNodes().contains(edge.getStartNode()) && !graph.getNodes().contains(edge.getEndNode());
    }

    public boolean equalsUndirected(Graph graph){
        for (int i = 0; i < this.getEdges().size(); i++) {
            if(!graph.containsEdgeWithKeys(this.getEdges().get(i))){
                return false;
            }
        }
        return true;
    }

    public boolean containsEdgeWithKeys(Edge edge){
        for (int i = 0; i < this.getEdges().size(); i++) {
            if (this.getEdges().get(i).getStartNode().getKey().equals(edge.getStartNode().getKey())) {
                if (this.getEdges().get(i).getEndNode().getKey().equals(edge.getEndNode().getKey())){
                    return true;
                }
            }
            if (this.getEdges().get(i).getEndNode().getKey().equals(edge.getStartNode().getKey())) {
                if (this.getEdges().get(i).getStartNode().getKey().equals(edge.getEndNode().getKey())){
                    return true;
                }
            }
        }
        return false;
    }

    public void printEdgesUndirected(){
        for (int i = 0; i < this.getEdges().size(); i++) {
            System.out.println(this.getEdges().get(i).getStartNode().getKey().toString() + " - " + this.getEdges().get(i).getEndNode().getKey().toString());
        }
    }

    public void readFrom(String str){
        File file = new File(str);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            int numOfNodes = sc.nextInt();
            for (int i = 0; i < numOfNodes; i++) {
                this.getNodes().add(new Node.Builder().setKey(i + 1).build());
            }
            while (sc.hasNext()){
                int node1 = sc.nextInt() - 1;
                int node2 = sc.nextInt() - 1;
                int mass = sc.nextInt();
                this.getEdges().add(new Edge.Builder()
                        .setMass(mass)
                        .setStartNode(this.getNodes().get(node1))
                        .setEndNode(this.getNodes().get(node2))
                        .build());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Graph readAnswerForKraskalFrom(String str){
        File file = new File(str);
        Graph ret = new Graph();
        ret.init();
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            while (sc.hasNext()){
                int node1 = sc.nextInt();
                int node2 = sc.nextInt();
                ret.addEdge(this.getEdge(node1, node2));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public ArrayList<Node> readAnswerForDijkstraFrom(String str){
        File file = new File(str);
        ArrayList<Node> ret = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            while (sc.hasNext()){
                int key = sc.nextInt();
                int wayWeight = sc.nextInt();
                ret.add(new Node.Builder().setKey(key).setWayWeight(wayWeight).build());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private Edge getEdge(int key1, int key2) {
        for (int i = 0; i < this.getEdges().size(); i++) {
            if (this.getEdges().get(i).getStartNode().getKey() == key1) {
                if (this.getEdges().get(i).getEndNode().getKey() == key2){
                    return this.getEdges().get(i);
                }
            }
            if (this.getEdges().get(i).getEndNode().getKey() == key1) {
                if (this.getEdges().get(i).getStartNode().getKey() == key2){
                    return this.getEdges().get(i);
                }
            }
        }
        return null;
    }

    Queue<Node> nextNode = new LinkedList<>();

    public void findMinWays(Node rootNode){ // Dijkstra’s algorithm
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).way = new int[this.getNodes().size() + 10];
        }
        rootNode.wayWeight = 0;
        lastRoot = rootNode;
        nextNode.add(rootNode);
        ArrayList<Node> visited = new ArrayList<Node>();
        while (!nextNode.isEmpty()) {
            findMinWayHelper(nextNode.remove(), visited);
        }
    }

    private void findMinWayHelper(Node node, ArrayList<Node> visited){
        ArrayList<Node> neighbours = getNeighbours(node);
        neighbours.removeAll(visited);
        visited.add(node);
        if(lastRoot.equals(node)){
            node.way[0] = node.getKey();
            node.wayLength = 1;
        }

        for (int i = 0; i < neighbours.size(); i++) {
            int massToAdd = getEdge(node.getKey(), neighbours.get(i).getKey()).getMass();
            if((node.wayWeight + massToAdd) < neighbours.get(i).wayWeight){
                neighbours.get(i).wayWeight = node.wayWeight + massToAdd;
                neighbours.get(i).wayLength = node.wayLength + 1;
                for (int j = 0; j < node.wayLength; j++) {
                    neighbours.get(i).way[j] = node.way[j];
                }
                neighbours.get(i).way[node.wayLength] = neighbours.get(i).getKey();
            }
        }

        ArrayList<NodeAndWeight> nodeAndWeights = new ArrayList<>();
        for (int i = 0; i < neighbours.size(); i++){
            NodeAndWeight nodeAndWeight = new NodeAndWeight();
            nodeAndWeight.setNode(neighbours.get(i));
            nodeAndWeight.setWeight(getEdge(node.getKey(), neighbours.get(i).getKey()).getMass());
            nodeAndWeights.add(nodeAndWeight);
        }

        nodeAndWeights.sort(NodeAndWeight::compareTo);

        for (int i = 0; i < neighbours.size(); i++) {
            nextNode.add(nodeAndWeights.get(i).getNode());
        }

    }

    public ArrayList<Node> getNeighbours(Node node){
        ArrayList<Node> ret = new ArrayList<>();
        ArrayList<Edge> neighEdges;
        neighEdges = getEdges(node.getKey());
        for (Edge edge: neighEdges){
            if(!edge.getStartNode().equals(node)){
                ret.add(edge.getStartNode());
            } else if(!edge.getEndNode().equals(node)) {
                ret.add(edge.getEndNode());
            }
        }
        return ret;
    }

    public void printWays(){
        if(lastRoot == null){
            System.out.println("FindMinWays was not executed before printing");
            return;
        }
        for (Node node: this.getNodes()){
            StringBuilder sb = new StringBuilder();
            sb.append("Node ").append(node.getKey().toString()).append("; way weight: ");
            sb.append(node.wayWeight).append("; way: ");
            for (int i = 0; i < node.wayLength; i++) {
                if(i != 0){
                    sb.append("->");
                }
                sb.append(node.way[i]);
            }
            System.out.println(sb.toString());
        }
    }
}

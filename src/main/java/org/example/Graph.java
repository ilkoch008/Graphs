package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
    private ArrayList<Node> nodes = null;
    private ArrayList<Edge> edges = null;

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
        ArrayList<Edge> retEdges = null;
        int i = 0;
        for(Edge edge: edges){
            if(edge.getStartNode().equals(node)){
                if(i == 0){
                    i++;
                    retEdges = new ArrayList<>();
                }
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
        return null;
    }

    public ArrayList<Edge> getEdgesToNode(Node node){
        ArrayList<Edge> retEdges = null;
        int i = 0;
        for(Edge edge: edges){
            if(edge.getEndNode().equals(node)){
                if(i == 0){
                    i++;
                    retEdges = new ArrayList<>();
                }
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
        return null;
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
//        for (int i = 0; i < edges.size(); i++) {
//            System.out.println(edges.get(i).getMass().toString());
//        }
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
                this.getNodes().add(new Node.Builder().setKey(i).build());
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

    public Graph readAnswerFrom(String str){
        File file = new File(str);
        Graph ret = new Graph();
        ret.init();
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            while (sc.hasNext()){
                int node1 = sc.nextInt() - 1;
                int node2 = sc.nextInt() - 1;
                ret.addEdge(this.getEdge(node1, node2));
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
}

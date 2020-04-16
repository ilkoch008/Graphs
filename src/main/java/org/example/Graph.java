package org.example;

import java.util.ArrayList;

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
}

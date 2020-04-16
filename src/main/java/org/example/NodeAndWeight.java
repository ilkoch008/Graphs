package org.example;

public class NodeAndWeight implements Comparable<NodeAndWeight> {
    private Node node;

    private int weight;


    @Override
    public int compareTo(NodeAndWeight o) {
        if (this.weight == o.getWeight()) {
            return 0;
        } else if (this.weight < o.getWeight()) {
            return -1;
        } else {
            return 1;
        }
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

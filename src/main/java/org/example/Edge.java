package org.example;

public class Edge {
    private Node startNode;
    private Node endNode;
    private Integer mass;

    public Integer getMass() {
        return mass;
    }
    public void setMass(Integer mass) {
        this.mass = mass;
    }

    public Node getEndNode() {
        return endNode;
    }
    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public Node getStartNode() {
        return startNode;
    }
    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public class Builder {
        private Builder() {}
        public Builder setMass(Integer mass) {
            Edge.this.mass = mass;
            return this;
        }

        public Builder setStartNode(Node startNode) {
            Edge.this.startNode = startNode;
            return this;
        }

        public Builder setEndNode(Node endNode) {
            Edge.this.endNode = endNode;
            return this;
        }

        public Edge build() {
            return Edge.this;
        }
    }
}

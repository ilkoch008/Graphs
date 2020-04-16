package org.example;

public class Edge implements Comparable<Edge>{
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

    @Override
    public int compareTo(Edge anotherEdge) {
        if (this.mass.equals(anotherEdge.getMass())) {
            return 0;
        } else if (this.mass < anotherEdge.getMass()) {
            return -1;
        } else {
            return 1;
        }
    }

    public static class Builder {

        private Edge newEdge;

        public Builder(){
            newEdge = new Edge();
        }


        public Builder setMass(Integer mass) {
            newEdge.mass = mass;
            return this;
        }

        public Builder setStartNode(Node startNode) {
            newEdge.startNode = startNode;
            return this;
        }

        public Builder setEndNode(Node endNode) {
            newEdge.endNode = endNode;
            return this;
        }

        public Edge build() {
            return newEdge;
        }
    }

    public Edge getCopy(){
        return new Builder()
                .setMass(this.getMass())
                .setStartNode(this.getStartNode())
                .setEndNode(this.getEndNode())
                .build();
    }
}

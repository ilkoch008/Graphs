package org.example;

import static org.example.Colors.*;

public class Node {
    private Integer key;
    private Object data;

    public void setData(Object data) {
        this.data = data;
    }
    public Object getData() {
        return data;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
    public Integer getKey() {
        return key;
    }

    public Node(Integer key, Object data){
        this.key = key;
        this.data = data;
    }

    public Node(){
    }

    public static class Builder {

        private Node newNode;

        public Builder(){
            newNode = new Node();
        }

        public Builder setKey(Integer key) {
            newNode.key = key;
            return this;
        }

        public Builder setData(Object data) {
            newNode.data = data;
            return this;
        }

        public Node build(){
            return newNode;
        }
    }
}

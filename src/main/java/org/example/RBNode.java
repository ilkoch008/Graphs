package org.example;

import com.sun.org.apache.regexp.internal.RE;

import static org.example.Colors.*;

public class RBNode extends Node {

    private Boolean color;
    private RBNode parent;
    private RBNode leftChild;
    private RBNode rightChild;
    private Boolean nil = false;

    public RBNode() {
        super();
    }

    public void isNIL(){
        nil = true;
    }

    public void isNotNIL(){
        nil = false;
    }

    public Boolean nil(){
        return nil;
    }


    public RBNode(Integer key, Object data) {
        super(key, data);
    }

    public RBNode(Integer key, Object data, RBNode leftChild, RBNode rightChild, RBNode parent, boolean color, Boolean nil){
        super(key, data);
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.color = color;
        this.nil = nil;
    }

    public Boolean getColor() {
        return color;
    }

    public void makeRed(){
        color = RED;
    }

    public void makeBlack(){
        color = BLACK;
    }

    public void setColor(boolean color){
        this.color = color;
    }

    public RBNode getParent() {
        return parent;
    }

    public void setParent(RBNode parent) {
        this.parent = parent;
    }

    public RBNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(RBNode leftChild) {
        this.leftChild = leftChild;
    }

    public RBNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(RBNode rightChild) {
        this.rightChild = rightChild;
    }

    public void setChild(RBNode child){
        if(child.getKey() < this.getKey()){
            this.setLeftChild(child);
        } else  if(child.getKey() > this.getKey()){
            this.setRightChild(child);
        }
    }

    public RBNode getAnotherChild(RBNode child){
        if(child.equals(this.getLeftChild())){
            return this.getRightChild();
        } else {
            return this.getLeftChild();
        }
    }

    public static class Builder {

        private RBNode newRBNode;

        public Builder(){
            newRBNode = new RBNode();
        }

        public Builder setColor(boolean color) {
            newRBNode.color = color;
            return this;
        }

        public Builder setParent(RBNode parent) {
            newRBNode.parent = parent;
            return  this;
        }

        public Builder setLeftChild(RBNode leftChild) {
            newRBNode.leftChild = leftChild;
            return  this;
        }

        public Builder setRightChild(RBNode rightChild) {
            newRBNode.rightChild = rightChild;
            return  this;
        }

        public Builder setKey(Integer key) {
            newRBNode.setKey(key);
            return  this;
        }

        public Builder setData(Object data) {
            newRBNode.setData(data);
            return  this;
        }

        public RBNode build(){
            return newRBNode;
        }
    }
    
    public void createNILs(){
        this.setLeftChild(RBNode.buildNIL(this));
        this.setRightChild(RBNode.buildNIL(this));
    }

    public static RBNode buildNIL(RBNode parent){
        return new RBNode(null, null, null, null, parent, BLACK, true);
    }

    public String toStringWithChildren() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        if(this.getColor() == RED) {
            buffer.append(ANSI_RED).append(this.getKey().toString()).append(ANSI_RESET);
        } else {
            if(this.getKey() != null) {
                buffer.append(this.getKey().toString());
            } else {
                buffer.append("nil");
            }
        }
        buffer.append('\n');


            if (!(this.getRightChild() == null) && !(this.getLeftChild() == null)) {
                this.getRightChild().print(buffer, childrenPrefix + "├── " + "right ", childrenPrefix + "│   ");
            }
            if (!(this.getRightChild() == null) && this.getLeftChild() == null) {
                this.getRightChild().print(buffer, childrenPrefix + "└── " + "right ", childrenPrefix + "    ");
            }
            if(!(this.getLeftChild() == null)){
                this.getLeftChild().print(buffer, childrenPrefix + "└── " + "left ", childrenPrefix + "    ");
            }

    }

}

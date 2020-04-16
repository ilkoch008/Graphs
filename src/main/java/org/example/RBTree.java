package org.example;

import java.util.List;

import static java.lang.System.exit;
import static org.example.Colors.*;

public class RBTree {

    private RBNode root = null;

    public RBNode getRoot(){
        return root;
    }

    public boolean addElement(RBNode node) {
        if (root == null) {
            this.root = node;
            root.makeBlack();
            root.createNILs();
            return true;
        } else {
            RBNode place = searchForPlace(node.getKey(), root);
            if (place != null) {
                node.setParent(place.getParent());
                node.getParent().setChild(node);
                node.makeRed();
                node.createNILs();
                //System.out.println("before balance: ");
                //System.out.println(root.toStringWithChildren());
                this.fixAdding(node);
                return true;
            }
        }
        return false;
    }

    public boolean deleteElement(Integer key) {
        return deleteElement(search(key));
    }


    private boolean deleteElement(RBNode node) {
        RBNode child = null;
        RBNode nilChild = null;
        if (node == null) {
            return false;
        }

        if (node.equals(root)) {
            RBNode toSwap;
            if (node.getLeftChild().nil() && !node.getRightChild().nil()) {
                toSwap = findMaxLeft(node.getRightChild());
                swap(toSwap, node);
                RBNode saveToSwap = toSwap;
                toSwap = node;
                node = saveToSwap;
                return deleteElement(node);
            } else if (node.getRightChild().nil() && !node.getLeftChild().nil()) {
                toSwap = findMaxRight(node.getLeftChild());
                swap(toSwap, node);
                RBNode saveToSwap = toSwap;
                toSwap = node;
                node = saveToSwap;
                return deleteElement(node);
            } else if (node.getRightChild().nil() && node.getLeftChild().nil()) {
                root = null;
                return true;
            }
        }

        if (!node.getRightChild().nil() && !node.getLeftChild().nil()) { // two children
            RBNode toSwap = findMaxLeft(node.getRightChild());
            swap(node, toSwap);
            RBNode saveToSwap = toSwap;
            toSwap = node;
            node = saveToSwap;
//            System.out.println("swap: ");
//            print();
            return deleteElement(node);
        } else if (node.getRightChild().nil() && node.getLeftChild().nil()) { // no children
            nilChild = RBNode.buildNIL(node.getParent());
            if (node.getParent().getLeftChild().equals(node)) {
                node.getParent().setLeftChild(nilChild);
            } else {
                node.getParent().setRightChild(nilChild);
            }

        } else if (node.getRightChild().nil()) { // no right child
            node.getLeftChild().setParent(node.getParent());
            node.getParent().setChild(node.getLeftChild());
            child = node.getLeftChild();
        } else if (node.getLeftChild().nil()) { // no left child
            node.getRightChild().setParent(node.getParent());
            node.getParent().setChild(node.getRightChild());
            child = node.getRightChild();
        }

        if (child == null) {
            child = nilChild;
        }



        if (node.getColor() == RED) {
            return true;
        } else if (node.getColor() == BLACK && child.getColor() == RED) {
            child.makeBlack();
            return true;
        } else if (node.getColor() == BLACK) {
            fixDeleting(child);
        }
        return true;
    }

    private void fixDeleting(RBNode child) {
        if (!child.equals(root)) {
            RBNode brother = child.getParent().getAnotherChild(child);
            if (brother.equals(brother.getParent().getRightChild())) {
//                System.out.println("brother to the right");
                if (brother.getColor() == RED) { // red brother
//                    System.out.println("red brother: ");
                    turnLeft(brother);
                    brother.makeBlack();
                    child.getParent().makeRed();

                    brother = child.getParent().getAnotherChild(child);
//                    print();
                }

                if (brother.getLeftChild().getColor() == BLACK && brother.getRightChild().getColor() == BLACK) {
//                    System.out.println("brother's children are black: ");
                    blackBrotherChildren(child, brother);
                    return;
                }

                if (brother.getLeftChild().getColor() == RED && brother.getRightChild().getColor() == BLACK) {
//                    System.out.println("brother's left child is red, right child is black: ");
                    brother.getLeftChild().makeBlack();
                    turnRight(brother.getLeftChild());
                    brother.makeRed();
                    brother = child.getParent().getAnotherChild(child);
//                    print();
                }

                if (brother.getRightChild().getColor() == RED) {
//                    System.out.println("brother's right child is red: ");
                    brother.setColor(brother.getParent().getColor());
                    brother.getRightChild().makeBlack();
                    brother.getParent().makeBlack();
                    turnLeft(brother);
//                    print();
                }
            } else {
//                System.out.println("brother to the left");
                if (brother.getColor() == RED) { // red brother
//                    System.out.println("red brother: ");
                    turnRight(brother);
                    brother.makeBlack();
                    child.getParent().makeRed();

                    brother = child.getParent().getAnotherChild(child);
//                    print();
                }

                if (brother.getLeftChild().getColor() == BLACK && brother.getRightChild().getColor() == BLACK) {
//                    System.out.println("brother's children are black: ");
                    blackBrotherChildren(child, brother);
                    return;
                }

                if (brother.getRightChild().getColor() == RED && brother.getLeftChild().getColor() == BLACK) {
//                    System.out.println("brother's right child is red, left child is black: ");
                    brother.getRightChild().makeBlack();
                    turnLeft(brother.getRightChild());
                    brother.makeRed();
                    brother = child.getParent().getAnotherChild(child);
//                    print();
                }

                if (brother.getLeftChild().getColor() == RED) {
//                    System.out.println("brother's left child is red: ");
                    brother.setColor(brother.getParent().getColor());
                    brother.getLeftChild().makeBlack();
                    brother.getParent().makeBlack();
                    turnRight(brother);
//                    print();
                }

            }
        }
    }

    private int blackBrotherChildren(RBNode child, RBNode brother) {
        if (child.getParent().getColor() == BLACK) {
            brother.makeRed();
            fixDeleting(child.getParent());
        } else {
            child.getParent().makeBlack();
            brother.makeRed();
        }
        return 0;
    }

    private RBNode findMaxLeft(RBNode node) {
        if (node.getLeftChild().nil()) {
            return node;
        } else {
            return findMaxLeft(node.getLeftChild());
        }
    }

    private RBNode findMaxRight(RBNode node) {
        if (node.getRightChild().nil()) {
            return node;
        } else {
            return findMaxRight(node.getRightChild());
        }
    }

    private void swap(RBNode node1, RBNode node2) {
        Object data1 = node1.getData();
        Object data2 = node2.getData();
        Integer key1 = node1.getKey();
        Integer key2 = node2.getKey();
        node1.setData(data2);
        node1.setKey(key2);
        node2.setData(data1);
        node2.setKey(key1);
        RBNode saveNode1 = node1;
        node1 = node2;
        node2 = saveNode1;
    }


    public RBNode search(Integer key) {
        return search(key, root);
    }

    /* Returns null if element with given key doesn't exist */
    public RBNode search(Integer key, RBNode startNode) {
        if (startNode.nil()) {
            return null;
        } else if (startNode.getKey().equals(key)) {
            return startNode;
        } else if (key < startNode.getKey()) {
            return search(key, startNode.getLeftChild());
        } else if (key > startNode.getKey()) {
            return search(key, startNode.getRightChild());
        } else {
            return null;
        }
    }

    /* Returns null if element with given key already exists */
    private RBNode searchForPlace(Integer key, RBNode startNode) {
        if (startNode.nil()) {
            return startNode;
        } else if (startNode.getKey().equals(key)) {
            return null;
        } else if (key < startNode.getKey()) {
            return searchForPlace(key, startNode.getLeftChild());
        } else if (key > startNode.getKey()) {
            return searchForPlace(key, startNode.getRightChild());
        } else {
            return null;
        }
    }

    private void fixAdding(RBNode node) {
        RBNode parent = null;
        RBNode grandparent = null;
        if (node.getParent() != null) {
            parent = node.getParent();
            if (parent.getParent() != null) {
                grandparent = parent.getParent();
            }
        }

        if (parent != null && grandparent != null) {
            if (parent.getColor() == RED) {
                if (grandparent.getAnotherChild(parent).getColor() == RED) { // Uncle is read
                    grandparent.makeRed();
                    grandparent.getLeftChild().makeBlack();
                    grandparent.getRightChild().makeBlack();
                    fixAdding(grandparent);
                    // Uncle is black, father and grandfather on different sides:
                    // father to the left of his son and grandfather:
                } else if ((parent.getKey() < node.getKey() && node.getKey() < grandparent.getKey())) {
                    turnLeft(node);
                    fixAdding(node.getLeftChild());
                    // father to the right of his son and grandfather:
                } else if (grandparent.getKey() < node.getKey() && node.getKey() < parent.getKey()) {
                    turnRight(node);
                    fixAdding(node.getRightChild());
                    // Uncle is black, father and grandfather on one side:
                    // grandfather on the right:
                } else if (parent.getKey() < grandparent.getKey()) {
                    turnRight(parent);
                    parent.makeBlack();
                    grandparent.makeRed();
                    // grandfather on the left
                } else if (grandparent.getKey() < parent.getKey()) {
                    turnLeft(parent);
                    parent.makeBlack();
                    grandparent.makeRed();
                }
            }
        } else {
            root.makeBlack();
        }
        if (node.getRightChild().equals(node) || node.getLeftChild().equals(node)) {
            System.out.println("my key: " + node.getKey().toString());
            exit(1);
        }
        if (parent != null) {
            if (parent.getRightChild().equals(parent) || parent.getLeftChild().equals(parent)) {
                System.out.println("my key: " + parent.getKey().toString());
                exit(1);
            }
        }
        if (grandparent != null) {
            if (grandparent.getRightChild().equals(grandparent) || grandparent.getLeftChild().equals(grandparent)) {
                System.out.println("my key: " + grandparent.getKey().toString());
                exit(1);
            }
        }
    }

    private void turnLeft(RBNode son) {
        //System.out.println("turnLeft for " + son.getKey());
        RBNode parent = son.getParent();
        RBNode grandparent = son.getParent().getParent();
        parent.setRightChild(son.getLeftChild());
        son.setLeftChild(parent);
        parent.setParent(son);
        parent.getRightChild().setParent(parent);
        if (grandparent != null) {
            son.setParent(grandparent);
            grandparent.setChild(son);
        } else {
            root = son;
            root.setParent(null);
        }
        //System.out.println(root.toStringWithChildren());
    }

    private void turnRight(RBNode son) {
        //System.out.println("turnRight for " + son.getKey());
        RBNode parent = son.getParent();
        RBNode grandparent = son.getParent().getParent();
        parent.setLeftChild(son.getRightChild());
        son.setRightChild(parent);
        parent.setParent(son);
        parent.getLeftChild().setParent(parent);
        if (grandparent != null) {
            son.setParent(grandparent);
            grandparent.setChild(son);
        } else {
            root = son;
            root.setParent(null);
        }
        //System.out.println(root.toStringWithChildren());
    }

    private Integer getDepthHelper(Integer level, RBNode node) {
        Integer depthLeft = 0;
        Integer depthRight = 0;
        level++;
        if (!node.getLeftChild().nil()) {
            depthLeft = getDepthHelper(level, node.getLeftChild());
        }
        if (!node.getRightChild().nil()) {
            depthRight = getDepthHelper(level, node.getRightChild());
        }
        if (!node.getLeftChild().nil() || !node.getRightChild().nil()) {
            if (depthLeft > depthRight) {
                level = depthLeft;
            } else {
                level = depthRight;
            }
        }
        return level;
    }

    public Integer getDepth() {
        return getDepthHelper(0, root);
    }

    public void print() {
        if (root != null) {
            System.out.println(root.toStringWithChildren());
        } else {
            System.out.println("null");
        }
    }

    private boolean test(RBNode previousNode, RBNode node){
        if(!node.equals(root)) {
            if (previousNode.getColor() == RED && node.getColor() == RED) {
                System.out.println("wrong colors for node " + node.getKey().toString());
                return false;
            }
        }
        int leftBlackDepth;
        int rightBlackDepth;
        if(node.getLeftChild() != null && node.getRightChild() != null) {
            leftBlackDepth = getBlackDepth(node.getLeftChild());
            rightBlackDepth = getBlackDepth(node.getRightChild());
            if (leftBlackDepth != rightBlackDepth) {
                System.out.println("wrong depths for node " + node.getKey().toString());
                return false;
            }
        } else if(node.getLeftChild() == null && node.getRightChild() == null){
            return true;
        }
        boolean testLeft;
        boolean testRight;
        testLeft = test(node, node.getLeftChild());
        testRight = test(node, node.getRightChild());
        return (testLeft && testRight);
    }

    public boolean testIsOK(){
        if(root == null){
            return true;
        } else {
            return test(null, root);
        }
    }

    private Integer getBlackDepthHelper(Integer level, RBNode node) {
        Integer depthLeft = 0;
        Integer depthRight = 0;
        if(node.getColor() == BLACK) {
            level++;
        }
        if (node.getLeftChild() != null) {
            depthLeft = getBlackDepthHelper(level, node.getLeftChild());
        }
        if (node.getRightChild() != null) {
            depthRight = getBlackDepthHelper(level, node.getRightChild());
        }
        if ((node.getLeftChild() != null) || (node.getRightChild() != null)) {
            if (depthLeft > depthRight) {
                level = depthLeft;
            } else {
                level = depthRight;
            }
        }
        return level;
    }

    public Integer getBlackDepth(RBNode node) {
        return getBlackDepthHelper(0, node);
    }

}

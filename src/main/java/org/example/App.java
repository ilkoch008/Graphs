package org.example;
import org.w3c.dom.ls.LSInput;

import java.util.Random;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Random random = new Random();
        Scanner sc = new Scanner(System.in);
        String act = "";
        Integer key;
        RBTree tree = new RBTree();

        for (int i = 0; i < 15; i++) {
            tree.addElement(new RBNode.Builder().setKey(random.nextInt(100)).build());
        }

        tree.print();

        System.out.println("test is OK: " + tree.testIsOK());

        while (true) {
            System.out.println("Enter action and node key");
            act = sc.next();
            if(act.equals("break")){
                break;
            } else if(act.equals("add")){
                key = sc.nextInt();
                tree.addElement(new RBNode.Builder().setKey(key).build());
            } else if(act.equals("rm")){
                key = sc.nextInt();
                tree.deleteElement(key);
            }
            tree.print();
            System.out.println("test is OK: " + tree.testIsOK());
        }

//        tree.addElement(new RBNode.Builder().setKey(4).build());
//        tree.addElement(new RBNode.Builder().setKey(45).build());
//        tree.addElement(new RBNode.Builder().setKey(2).build());
//        tree.addElement(new RBNode.Builder().setKey(90).build());
//        tree.addElement(new RBNode.Builder().setKey(43).build());
//        tree.addElement(new RBNode.Builder().setKey(1).build());
//        tree.addElement(new RBNode.Builder().setKey(3).build());
//        tree.print();
//        tree.deleteElement(45);
//        tree.print();

    }
}

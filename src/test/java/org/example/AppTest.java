package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void TreeTest()
    {
        ArrayList<Integer> keys = new ArrayList<>();
        Random random = new Random();
        RBTree tree = new RBTree();
        boolean added;
        Integer adding;
        for (int i = 0; i < 500; i++) {
            adding = random.nextInt(1000);
            added = tree.addElement(new RBNode.Builder().setKey(adding).build());
            if(added){
                keys.add(adding);
            }
            assertTrue(tree.testIsOK());
        }

        for (Integer key : keys) {
            tree.deleteElement(key);
            assertTrue(tree.testIsOK());
        }
    }
}

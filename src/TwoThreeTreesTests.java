import static org.junit.Assert.*;

import org.junit.Test;

public class TwoThreeTreesTests {
    @Test
    public void testOutOfBoundGet() {
        Tree t = new Tree();
        t.insert(3);
        t.insert(5);
        t.insert(6);

        assertEquals(-1, t.get(8));
    }

    @Test
    public void cascadeSplitFromRight() {
        Tree t = new Tree();
        t.insert(3);
        t.insert(5);
        t.insert(6);
        t.insert(1);
        t.insert(4);
        t.insert(7);
        t.insert(8);

        assertEquals(1, t.get(0));
        assertEquals(3, t.get(1));
        assertEquals(5, t.get(3));
        assertEquals(6, t.get(4));
        assertEquals(7, t.get(5));
        assertEquals(8, t.get(6));
    }

    @Test
    public void cascadeSplitFromMiddle() {
        Tree t = new Tree();
        t.insert(1);
        t.insert(8);
        t.insert(7);
        t.insert(3);
        t.insert(4);
        t.insert(5);
        t.insert(6);

        assertEquals(1, t.get(0));
        assertEquals(3, t.get(1));
        assertEquals(5, t.get(3));
        assertEquals(6, t.get(4));
        assertEquals(7, t.get(5));
        assertEquals(8, t.get(6));

    }

    @Test
    public void cascadeSplitFromLeft() {
        Tree t = new Tree();
        t.insert(7);
        t.insert(6);
        t.insert(8);
        t.insert(5);
        t.insert(4);
        t.insert(3);
        t.insert(1);

        assertEquals(1, t.get(0));
        assertEquals(3, t.get(1));
        assertEquals(5, t.get(3));
        assertEquals(6, t.get(4));
        assertEquals(7, t.get(5));
        assertEquals(8, t.get(6));
    }

    @Test
    public void checkParentSize() {
        Tree t = new Tree();
        t.insert(0);
        t.insert(1);
        t.insert(2);
        t.insert(3);

        assertEquals(4, t.size());
    }

    @Test
    public void doubleCascadeFromRight() {
        Tree t = new Tree();
        int size = 7;
        for(int i = 0; i <= size; i++) {
            t.insert(i);
        }

        for(int i = 0; i < size; i++) {
            assertEquals(i, t.get(i));
        }

    }
}

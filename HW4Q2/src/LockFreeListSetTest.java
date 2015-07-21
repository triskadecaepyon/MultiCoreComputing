import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by davidliu on 7/12/15.
 */
public class LockFreeListSetTest {
    @Test
    public void BasicTest() {
        LockFreeListSet myset = new LockFreeListSet();

        myset.add(4);
        assertTrue(myset.contains(4));
    }

    @Test
    public void RemovalSimple() {
        LockFreeListSet myset = new LockFreeListSet();

        myset.add(4);
        myset.add(3);
        myset.remove(3);
        assertFalse(myset.contains(3));
    }

    @Test
    public void RemoveAdvanced() {
        LockFreeListSet myset = new LockFreeListSet();

        myset.add(4);
        myset.add(3);
        myset.remove(4);
        myset.remove(3);
        myset.add(3);
        assertTrue(myset.contains(3));
    }

}
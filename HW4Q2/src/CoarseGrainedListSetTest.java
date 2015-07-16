import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by davidliu on 7/12/15.
 */

public class CoarseGrainedListSetTest {

    @Test
    public void BasicSetTest() {
        CoarseGrainedListSet myset = new CoarseGrainedListSet();

        myset.add(4);
        myset.add(3);
        assertTrue(myset.contains(3));
    }

    @Test
    public void BasicAddTest() {
        CoarseGrainedListSet myset = new CoarseGrainedListSet();

        myset.add(4);
        assertTrue(myset.contains(4));
    }

    @Test
    public void RemovalSimple() {
        CoarseGrainedListSet myset = new CoarseGrainedListSet();

        myset.add(4);
        myset.add(3);
        myset.remove(3);
        assertFalse(myset.contains(3));
    }

    @Test
    public void RemoveAdvanced() {
        CoarseGrainedListSet myset = new CoarseGrainedListSet();

        myset.add(4);
        myset.add(3);
        myset.remove(4);
        myset.remove(3);
        myset.add(3);
        assertTrue(myset.contains(3));
    }
}
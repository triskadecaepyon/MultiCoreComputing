import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void BasicMultiThread() {
        final CoarseGrainedListSet myset = new CoarseGrainedListSet();
        final boolean[] what_added = new boolean[2];
        final boolean[] what_subtracted = new boolean[2];
        Thread mythread1 = new Thread() {
            @Override
            public void run() {
                //TODO: Use boolean array to determine what numbers got added
                boolean fouradd = myset.add(4);
                boolean threeadd = myset.add(3);
                what_added[0] = fouradd;
                what_added[1] = threeadd;

            }
        };

        Thread mythread2 = new Thread() {
            @Override
            public void run() {
                boolean fourremove = myset.remove(4);
                boolean threeremove = myset.remove(3);
                what_subtracted[0] = fourremove;
                what_subtracted[1] = threeremove;
            }
        };
        try {
            mythread1.start();
            mythread2.start();
        } catch (Exception e) {
            System.out.println("thread error");
        }
        System.out.println(what_added[0] + " " + what_added[1]);
        System.out.println(what_subtracted[0] + " " + what_subtracted[1]);
        boolean isThree;
        if (what_subtracted[1]) {
            assertFalse(myset.contains(3));
        } else {
            assertTrue(myset.contains(3));
        }
    }

    @Test
    public void MultiTestWithSameAdds() {
        final CoarseGrainedListSet myset = new CoarseGrainedListSet();

        Thread mythread1 = new Thread() {
            @Override
            public void run() {
                //TODO: Use boolean array to determine what numbers got added
                myset.add(4);
                myset.add(3);
            }
        };

        Thread mythread2 = new Thread() {
            @Override
            public void run() {
                myset.add(4);
                myset.add(3);

            }
        };
        try {
            mythread1.start();
            mythread2.start();
        } catch (Exception e) {
            System.out.println("thread error");
        }

        assertTrue(myset.contains(3) && myset.contains(4));
    }
}
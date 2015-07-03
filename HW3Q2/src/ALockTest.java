import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by davidliu on 7/1/15.
 */
public class ALockTest {

    @Test
    public void testBasicFunctionality() {
        final ALock testlock = new ALock(1);
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                testlock.lock(2);
                testlock.unlock(2);
            }
        };
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                testlock.lock(3);
                testlock.lock(2);
                testlock.unlock(3);
            }
        };
        thread1.start();
        thread2.start();
        System.out.println("Finished test");
    }

    @Test
    public void testNlockedAtOne() {
        //n = 2, m = 2
        final ALock testlock = new ALock(1);
        testlock.mySlot.set(2);

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                testlock.lock(2);
                testlock.unlock(2);
            }
        };
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                testlock.lock(3);
                testlock.lock(2);
                testlock.unlock(2);
                testlock.unlock(3);
            }
        };
        thread1.start();
        thread2.start();
        System.out.println("finished locked test");
    }
}
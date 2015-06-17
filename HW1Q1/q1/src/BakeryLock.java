import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicBoolean;

// TODO
// Implement the bakery algorithm

public class BakeryLock implements MyLock {
	protected volatile int N;
	protected volatile AtomicBoolean [] choosing; // make boolean array atomic
	protected volatile AtomicIntegerArray number; // Make int array atomic
    public BakeryLock(int numThread) {
        N = numThread;
        choosing = new AtomicBoolean[N];
        number = new AtomicIntegerArray(N);
        for (int j = 0; j < N; j++) {
            choosing[j] = new AtomicBoolean(false);
            number.set(j,0);
        }
    }

    @Override
    public void lock(int myId) {
        // step 1: doorway: choose a number
        choosing[myId].set(true);
        for (int j = 0; j < N; j++)
            if (number.get(j) > number.get(myId))
                number.set(myId, number.get(j));
        number.set(myId, number.get(myId)+1);
        choosing[myId].set(false);;

        // step 2: check if my number is the smallest
        for (int j = 0; j < N; j++) {
            while (choosing[j].get()) ; // process j in doorway
            while ((number.get(j) != 0) &&
                    ((number.get(j) < number.get(myId) ||
                    ((number.get(j) == number.get(myId)) && j < myId))))
                ; // busy wait
        }
    }

    @Override
    public void unlock(int myId) {
    	number.set(myId, 0);
    }
}

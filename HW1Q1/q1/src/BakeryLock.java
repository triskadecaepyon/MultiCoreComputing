// TODO
// Implement the bakery algorithm

public class BakeryLock implements MyLock {
	protected volatile int N;
	protected volatile boolean [] choosing;
	protected volatile int [] number;
    public BakeryLock(int numThread) {
        this.N = numThread;
        this.choosing = new boolean[N];
        this.number = new int[N];
        for (int j = 0; j < N; j++) {
            this.choosing[j] = false;
            this.number[j] = 0;
        }
    }

    @Override
    public void lock(int myId) {
        // step 1: doorway: choose a number
        choosing[myId] = true;
        for (int j = 0; j < N; j++)
            if (number[j] > number[myId])
                number[myId] = number[j];
        number[myId]++;
        choosing[myId] = false;

        // step 2: check if my number is the smallest
        for (int j = 0; j < N; j++) {
            while (choosing[j]) ; // process j in doorway
            while ((number[j] != 0) &&
                    ((number[j] < number[myId]) ||
                    ((number[j] == number[myId]) && j < myId)))
                ; // busy wait
        }
    }

    @Override
    public void unlock(int myId) {
    	number[myId] = 0;
    }
}

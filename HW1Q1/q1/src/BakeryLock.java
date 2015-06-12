// TODO
// Implement the bakery algorithm

public class BakeryLock implements MyLock {
	int N;
	boolean [] choosing;
	int [] number;
    public BakeryLock(int numThread) {
        N = numThread;
        choosing = new boolean[N];
        number = new int[N];
        for (int j = 0; j < N; j++) {
            choosing[j] = false;
            number[j] = 0;
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

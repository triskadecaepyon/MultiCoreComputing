// TODO
// Use MyLock to protect the count
// This counter can use either BakeryLock or FastMutexLock to protect the count

public class LockCounter extends Counter {
	private MyLock counterlock;
    public LockCounter(MyLock lock) { // lock constructor
    	this.counterlock = lock; 
    }

    @Override
    public void increment() {
    	Thread currentThread = Thread.currentThread(); // get thread ID value
    	counterlock.lock((int)currentThread.getId()); // lock appropriate thread
    	try{
    		int temp = count; // in critical section
    		count = temp + 1; // in critical section
   // 		System.out.println(currentThread.getId());
    	}
    	finally{
    		counterlock.unlock((int)currentThread.getId());
    	}
    }
}

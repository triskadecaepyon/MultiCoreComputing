// TODO
// Use ReentrantLock to protect the count
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class ReentrantCounter extends Counter {
	private Lock reLock = new ReentrantLock();
    @Override
    public void increment() {
    	reLock.lock();
    	try
    	{
    		count++; // enter critical section
    	}
    	finally{
    	reLock.unlock();
    	}
    }
}

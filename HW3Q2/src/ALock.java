// TODO 
// Implement Anderson’s array-based lock
import java.util.concurrent.atomic.*;

public class ALock implements MyLock {
	AtomicInteger tailSlot = new AtomicInteger ( 0 ) ;
	boolean [ ] Available ;
	ThreadLocal<Integer> mySlot = new ThreadLocal<Integer>(); //initialize to 0
	private int numThread;
    public ALock(int numThread) {
      // TODO: initialize your algorithm
      this.Available = new boolean[numThread];
      this.Available[0] = true;
      for (int i = 1; i < numThread; i++){
    	  this.Available[i] = false;
      }
      this.numThread = numThread;
      this.mySlot.set(0);
    }

    @Override
    public void lock(int myId) {
    	mySlot.set(tailSlot.getAndIncrement( ) % numThread) ;// TODO: the locking algorithm
    	while(!Available[mySlot.get()]);
    }

    @Override
    public void unlock(int myId) {
      // TODO: the unlocking algorithm
    	Available[mySlot.get()] = false ;
    	Available[(mySlot.get()+1)%numThread] = true ;
    }
}
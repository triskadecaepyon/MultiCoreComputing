import java.util.Arrays;

// TODO 
// Implement Fast Mutex Algorithm

public class FastMutexLock implements MyLock {
	protected volatile int X, Y;
	protected volatile boolean [] flag; 
    public FastMutexLock(int numThread) {
      // TODO: initialize your algorithm
      this.X = -1;
      this.Y = -1;
      this.flag = new boolean [numThread];
      Arrays.fill(this.flag, Boolean.FALSE);
    }

    @Override
    public void lock(int myId) {
      // TODO: the locking algorithm
      while (true){
    	  flag[myId] = true;
    	  X = myId;
    	  if(Y != -1){
    		  flag[myId] = false;
    		  while(Y != -1);
    		  continue;
    	  }
    	  else{
    		  Y = myId;
    		  if (X == myId)
    			  return;
    		  else{
    			  flag[myId] = false;
    		  }
    	  }
      }
    }

    @Override
    public void unlock(int myId) {
      // TODO: the unlocking algorithm
      Y = -1;
      flag[myId] = false;
    }
}

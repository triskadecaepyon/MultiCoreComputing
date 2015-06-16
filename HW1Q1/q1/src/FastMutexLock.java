import java.util.Arrays;

// TODO 
// Implement Fast Mutex Algorithm

public class FastMutexLock implements MyLock {
	protected volatile int X, Y;
	protected volatile boolean [] flag; 
    public FastMutexLock(int numThread) {
      // TODO: initialize your algorithm
      this.X = -1; 
      this.Y = -1; // doorway open
      this.flag = new boolean [numThread];
      Arrays.fill(this.flag, Boolean.TRUE);
    }

    @Override
    public void lock(int myId) {
      // TODO: the locking algorithm
      while (true){
    	  flag[myId] = false;
    	  X = myId;
    	  if(Y != -1){ // Check if door is still open
    		  flag[myId] = true;
    		  while(Y != -1); // Wait until door open to reenter lock
    		  continue;
    	  }
    	  else{
    		  Y = myId; 
    		  if (X == myId){ // Check if going "Down", Fast path
    			  return;
    		  }
    		  else{ // Going "Right"
    			  flag[myId] = true;
    			  for (int j = 0; j < flag.length; j++)
    			  {
    				  while (flag[j] != true);
    			  }
    			  if(Y == myId) // Slow Path. Slowest of process to simultaneously access Right returns
    				  return;
    			  else{
    				  while(Y != -1); // Check door is open. Reenter if that's the case.
    				  continue;
    			  }
    		  }
    	  }
      }
    }

    @Override
    public void unlock(int myId) {
      // TODO: the unlocking algorithm
      Y = -1;
      flag[myId] = true;
    }
}
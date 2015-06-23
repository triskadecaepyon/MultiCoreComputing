import java.util.concurrent.Semaphore;
public class CyclicBarrier {
	// TODO: Declare variables and the constructor for CyclicBarrier
	// Note that you can use only semaphores but not synchronized blocks and
	// locks 
    private Semaphore inc; // Increment semaphore mutex protection
    private Semaphore dec; // Decrement semaphore mutex protection
    private Semaphore turnstileOne; // Prevent release till parties-1 thread accessed await
    private Semaphore turnstileTwo;
    private int index; // index how many threads have initiated await
    private int parties; // number of threads anticipated
    
    public CyclicBarrier(int parties) {
    // TODO: The constructor for this CyclicBarrier
        	this.parties = parties;
            this.inc = new Semaphore(1);
            this.dec = new Semaphore(1);
            this.turnstileOne = new Semaphore(0);
            this.turnstileTwo = new Semaphore(1);
            this.index = 0;
    }       
    
    public int await() throws InterruptedException {       
        // Waits until all parties have invoked await on this barrier.
        // If the current thread is not the last to arrive then it is
        // disabled for thread scheduling purposes and lies dormant until
        // the last thread arrives.
        // Returns: the arrival index of the current thread, where index
        // (parties - 1) indicates the first to arrive and zero indicates
        // the last to arrive.

            inc.acquire(); // enter mutex
            int temp = index; 
            index = temp + 1; // increment index
            int arrival_index = temp; // record entering index
            if( index == parties-1){
            	turnstileTwo.acquire();
            	turnstileOne.release();
            }
            inc.release();
            
            turnstileOne.acquire(); // block until all processes entered
            turnstileOne.release();
            
            dec.acquire();
            temp = index;   
            index = temp - 1; // count down to zero
            if(index == 0) {   
            	turnstileOne.acquire();
                turnstileTwo.release(); 
            }   
            dec.release(); 
           
           turnstileTwo.acquire(); // block until all processes are complete
           turnstileTwo.release(); 
           return arrival_index; // return index when process first arrived
    }

}
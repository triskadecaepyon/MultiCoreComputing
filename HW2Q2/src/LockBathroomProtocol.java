// TODO
import java.util.concurrent.locks.ReentrantLock;


// Use locks and condition variables to implement this bathroom protocol
public class LockBathroomProtocol implements BathroomProtocol {
  // declare the lock and conditions here
    ReentrantLock doorcheck = new ReentrantLock();
    ReentrantLock enter_or_exit = new ReentrantLock();

    boolean maleInRestroom = false;
    int pid = 0;

    public void enterMale() {
        int current_id = pid;
        pid++;
        System.out.println(current_id);

        while (!doorcheck.isLocked()) {
            enter_or_exit.lock();
            System.out.println("locked by: " + current_id);
            maleInRestroom = true;
            enter_or_exit.unlock();
            doorcheck.lock();
        }
        //Door is locked, try to get it.
        doorcheck.lock();
        System.out.println("Queued " + doorcheck.hasQueuedThreads());
    }

    public void leaveMale() {
        if (doorcheck.isLocked() && doorcheck.isHeldByCurrentThread()) {
            enter_or_exit.lock();
            System.out.println("Leaving...");
            enter_or_exit.unlock();
            doorcheck.unlock();
        }
    }

    public void enterFemale() {

    }

    public void leaveFemale() {

    }
}

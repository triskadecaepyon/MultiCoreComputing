// TODO
import java.util.concurrent.locks.ReentrantLock;


// Use locks and condition variables to implement this bathroom protocol
public class LockBathroomProtocol implements BathroomProtocol {
  // declare the lock and conditions here
    //Each thread is a simulated person with the following commands in the interface
    //Threads are created outside of this class
    ReentrantLock restroom_access = new ReentrantLock();
    int male_count = 0;
    int female_count = 0;
    int male_queue = 0;
    int female_queue = 0;

    public void enterMale() {
        while(restroom_access.isLocked()) {
            //Busy wait
        }
        restroom_access.lock(); //Get the access
        //CS
        if (female_count > 0) {
            //Get out
            System.out.println("Females in Restroom");
            male_queue++;
            System.out.println("Male Queue" + male_queue);
        } else if (female_queue > male_queue) {
            //wait turn
            System.out.println("More females waiting in line than males");
            if (male_count == 0) {
                //signal females to go in
                male_queue++;
                //We need to signal here
            }
        } else {
            male_count++;
            System.out.println("Get in and use restroom (M)");
            System.out.println("Current M in restroom: " + male_count);
        }

        restroom_access.unlock();
    }

    public void leaveMale() {
        while(restroom_access.isLocked()) {
            //busy wait
        }
        restroom_access.lock();
        //CS
        if (male_count > 0) {
            //Safe to step out
            male_count--;
            System.out.println("1 M left, total is now: " + male_count);
        }
        restroom_access.unlock();
    }

    public void enterFemale() {
        while(restroom_access.isLocked()) {
            //Busy wait
        }
        restroom_access.lock(); //Get the access
        //CS
        if (male_count > 0) {
            //Get out
            System.out.println("Female attempted enter: Males in Restroom");
            female_queue++;
            System.out.println("Female Queue" + female_queue);
        } else if (male_queue > female_queue) {
            //wait turn
            System.out.println("More males waiting in line than females");
            if (female_count == 0) {
                //signal females to go in
                female_queue++;
                //We need to signal here
            }
        } else {
            female_count++;
            System.out.println("Get in and use restroom (F)");
            System.out.println("Current F in restroom: " + female_count);
        }

        restroom_access.unlock();
    }

    public void leaveFemale() {
        while(restroom_access.isLocked()) {
            //busy wait
        }
        restroom_access.lock();
        //CS
        if (female_count > 0) {
            //Safe to step out
            female_count--;
            System.out.println("1 F left, total is now: " + female_count);
        }
        restroom_access.unlock();
    }
}

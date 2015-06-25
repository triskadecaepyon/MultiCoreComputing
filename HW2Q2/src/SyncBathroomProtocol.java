// TODO
// Use synchronized, wait(), notify(), and notifyAll() to implement this
// bathroom protocol

public class SyncBathroomProtocol implements BathroomProtocol {

    int male_count = 0;
    int female_count = 0;
    int male_queue = 0;
    int female_queue = 0;
    boolean restroom_access = true;


    public void enterMale() {
        synchronized(this) {
            while(!restroom_access) {
              //female_count > 0 || female_queue > male_queue
              try {
                  wait();
              }
              catch(Exception e){
                  e.printStackTrace();}
            }
            System.out.println("Not waiting anymore (enterMale)");
            if(female_count > 0 || (female_queue > male_queue && male_count==0))
              male_queue++;
            else {
              male_count++;
            }
            System.out.println("EM: " + male_count + " " + male_queue);
            restroom_access = false;
        }
    }

    public void leaveMale() {
        synchronized(this){
            System.out.println("Leave (LeaveMale)");
            if (male_count > 0) {
                //Safe to step out
                male_count--;
            }
            System.out.println("EM: " + male_count + " " + male_queue);
            restroom_access = true;
            notifyAll();
        }
    }

    public void enterFemale() {
        synchronized (this) {
            while (!restroom_access) {
                try {
                    wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Not waiting anymore (enterFemale)");
            if (male_count > 0 || (male_queue > female_queue && female_count == 0))
                female_queue++;
            else {
                female_count++;
            }
            System.out.println("FM: " + female_count + " " + female_queue);
            restroom_access = false;
        }
    }

    public void leaveFemale() {
      synchronized(this){
            System.out.println("Leave (LeaveFemale)");
            if (female_count > 0) {
                //Safe to step out
                female_count--;
            }
            System.out.println("FM: " + female_count + " " + female_queue);
            restroom_access = true;
            notifyAll();
      }
    }


}
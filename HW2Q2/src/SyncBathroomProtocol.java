// TODO
// Use synchronized, wait(), notify(), and notifyAll() to implement this
// bathroom protocol

public class SyncBathroomProtocol implements BathroomProtocol {

    int male_count = 0;
    int female_count = 0;
    int male_queue = 0;
    int female_queue = 0;

    boolean male_restroom_access = true;
    boolean female_restroom_access = false;
    
    boolean male_turn = true;
    boolean female_turn = true;
    int male_total = 0;
    int female_total = 0;
    
    public void enterMale() {
        synchronized(this) {
        	male_queue++;
  //          if(male_queue > female_queue){
  //          	female_turn = false;
  //          }
            while(female_count > 0 || (female_turn &&  !male_turn && !male_restroom_access)) {
              //female_count > 0 || female_queue > male_queue
              try {
                  wait();
              }             
              catch(Exception e){
                  e.printStackTrace();}
            }
            female_restroom_access = false;
            System.out.println("Not waiting anymore (enterMale)");
            female_turn = true;
            male_turn = false;
              male_count++;
              male_queue--;
              male_total++;
            System.out.println("EM: " + male_count + " " + male_queue + " total male " + male_total);
        }
    }

    public void leaveMale() {
        synchronized(this){
            System.out.println("Leave (LeaveMale)");
            male_count--;
            if (male_count == 0) {
                //Safe to step out
            	System.out.println("Notify all");
            	female_restroom_access = true;
            	male_restroom_access = false;
            	notifyAll();
            }
 //           female_restroom_access = true;
            System.out.println("EM: " + male_count + " " + male_queue);
        }
    }

    public void enterFemale() {
        synchronized (this) {
        	female_queue++;
            if(female_queue > male_queue){
            	male_turn = false;
            }
            System.out.println("Female access: " + female_turn + "Male access: " + male_turn);
            while (male_count > 0 || (male_turn && !female_turn && !female_restroom_access)) {
                try {
                    wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            male_restroom_access = false;
 //           female_restroom_access = true;
            System.out.println("Not waiting anymore (enterFemale)");
            female_turn = false;
            male_turn = true;
            female_count++; 
            female_queue--;
            female_total++;
            
            System.out.println("FM: " + female_count + " " + female_queue + " total female " + female_total);
        }
    }

    public void leaveFemale() {
      synchronized(this){
            System.out.println("LeaveFemale" + " female count: " + female_count);
            female_count--;
            if (female_count == 0) {
            	System.out.println("Notifying everyone!");
            	male_restroom_access = true;
            	female_restroom_access = false;
                notifyAll();
            }
 //           male_restroom_access = true;
            System.out.println("FM: " + female_count + " " + female_queue);
      }
    }


}
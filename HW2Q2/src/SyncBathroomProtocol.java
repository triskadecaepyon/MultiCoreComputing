// TODO
// Use synchronized, wait(), notify(), and notifyAll() to implement this
// bathroom protocol

public class SyncBathroomProtocol implements BathroomProtocol {

  int male_count = 0;
  int female_count = 0;
  int male_queue = 0;
  int female_queue = 0;

  public void enterMale() {
	  synchronized(this){
		  if(female_count > 0 || (female_queue > male_queue && male_count==0))
			  male_queue++;
		  while(female_count > 0 || female_queue > male_queue){
			  try{
				  wait();
			  }
			  catch(Exception e){System.out.println(e);}
		  }
		  male_count++;
	  }
  }

  public void leaveMale() {
	  synchronized(this){
	        if (male_count > 0) {
	            //Safe to step out
	            male_count--;
	        }
	        else
	        {
	        	notifyAll();
	        }
	  }
  }

  public void enterFemale() {
	  if(male_count > 0 || (male_queue > female_queue && female_count==0))
		 female_queue++;
	  while(male_count > 0 || male_queue > female_queue){
		  try{
			  wait();
		  }
		  catch(Exception e){System.out.println(e);}
	  }
	  female_count++;
  }

  public void leaveFemale() {
	  synchronized(this){
	        if (female_count > 0) {
	            //Safe to step out
	            female_count--;
	        }
	        else {
	        notifyAll();
	        }
	  }
  }


}
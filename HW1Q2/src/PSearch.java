import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class PSearch implements Callable<Integer> {
  // TODO: Declare variables
  int x;
  int [] A;
  int begin;
  int end;
  
  public PSearch(int x, int[] A, int begin, int end) {   
    // TODO: The constructor for PSearch 
    // x: the target that you want to search
    // A: the array that you want to search for the target
    // begin: the beginning index (inclusive)
    // end: the ending index (exclusive)
	  this.x = x;
	  this.A = A;
	  this.begin = begin;
	  this.end = end;
  }

  public Integer call() throws Exception {
    // TODO: your algorithm needs to use this method to get results
    // You should search for x in A within begin and end
    // Return -1 if no such target
	  try {
		  int last_section;
		  int cut_size = 5;
		  if (A.length > cut_size) {  //Cuts array into many pieces (cutoff value)
			  last_section = A.length % cut_size;
			  int i=0;
			  while (i <= (A.length-cut_size)) {

				  System.out.println("calling parallel search: " + x);
				  System.out.println(i);
				  System.out.println("A Array: " + i + " " + (i+cut_size));
				  i=i+cut_size;
			  }
			  System.out.println("Last section: " + last_section);
		  } else { //Cut array into individual portions or less
			  System.out.println("using cut line of 2");
		  }
		  return -1;
	  } catch (Exception e) {
		  e.printStackTrace();
		  return -1;
	  }

  }

  public static int parallelSearch(int x, int[] A, int n) {
	  // TODO: your search algorithm goes here
	  // You should create a thread pool with n threads
	  // Then you create PSearch objects and submit those objects to the thread
	  // pool
	  System.out.println("calling parallel search");
	  List<Future<Integer>> parallel_threads = new ArrayList<Future<Integer>>(); // create list of threads
	  ExecutorService thread_pool = Executors.newFixedThreadPool(n); // create thread pool


	  return -1;

  }
}
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
		  return  parallelSearch(x, A, 6);
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
	int begin = 0, end = 0, segment = 0;
	List <Future<Integer>> parallelthreads = new ArrayList<Future<Integer>>(); // create list of threads
	ExecutorService threadpool = Executors.newFixedThreadPool(n); // create
	try{
		if (A.length/n >= 1) // Check if there can be at least one array element per thread
		{
			segment = A.length/n;
			for (int i = 0; i < n; i++){
				// Update the begin and end indices
				if ((end + segment) < A.length)
				{
					end = end + segment;
				}
				else
				{
					end = A.length - 1;
				}
				Future<Integer>newthread = threadpool.submit(new PSearch(x, A, begin, end));
				parallelthreads.add(newthread); // Populate thread list
				begin = end; 
			}
			for (Future<Integer>ListElement:parallelthreads){
				if (ListElement.get() != -1)
					return ListElement.get(); // return matching element if found
			}
		}
		else{ // Create single thread to handle array check
			Future<Integer>newthread = threadpool.submit(new PSearch(x, A, begin, end));
			return newthread.get();
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
    return -1; // return -1 if the target is not found
  }


}
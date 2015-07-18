import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock ;

public class LockQueue<T> implements MyQueue<T> {
  ReentrantLock enqLock , deqLock;
  Node<T> head;
  Node<T> tail;
  AtomicInteger count;
  public class Node<T> {

	    private T data;
	    private Node<T> next;

	    public Node(T data){
	        this.data = data;
	        this.next = null;
	    }
  }
  
  public LockQueue(){
	  head = new Node<T>(null);
	  tail = head;
	  enqLock = new ReentrantLock();
	  deqLock = new ReentrantLock();
	  count.set(0);
  }
  public boolean enq(T value) {
	  if( value == null ) throw new NullPointerException();
	  enqLock.lock( ) ;
	  try{ 
		  Node<T> e = new Node<T>(value) ;
		  tail.next = e ;
		  tail = e;
		  count.incrementAndGet();
	  }
	  finally{
		  enqLock.unlock();
  	  }
      return false;
  }
  public T deq() {
	T result;
	deqLock.lock();
	try{
		if(head.next == null ) {
			return null;
		}
		result = head.next.data;
		head = head.next;
		count.decrementAndGet();
	}
	finally{
		deqLock.unlock();
	}
	
    return result;
  }
}

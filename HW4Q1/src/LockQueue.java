import java.util.concurrent.locks.ReentrantLock ;

public class LockQueue<T> implements MyQueue<T> {
  ReentrantLock enqLock , deqLock;
  Node<T> head;
  Node<T> tail;
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
  }
  public boolean enq(T value) {
	  if( value == null ) throw new NullPointerException();
	  enqLock.lock( ) ;
	  try{ 
		  Node<T> e = new Node<T>(value) ;
		  tail.next = e ;
		  tail = e;
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
		while(head.next == null ) {
		}
		result = head.next.data;
		head = head.next;
	}
	finally{
		deqLock.unlock();
	}
	
    return result;
  }
}

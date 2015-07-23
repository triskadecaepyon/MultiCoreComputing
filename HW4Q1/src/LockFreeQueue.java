import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class LockFreeQueue<T> implements MyQueue<T> {
  AtomicReference<Node> head;
  AtomicReference<Node> tail;
  AtomicInteger count;
  
  public class Node {

		    public T data;
		    public AtomicReference<Node> next;

			public Node(T data, int counter){
		        this.data = data;
		        next = new AtomicReference<Node>(null);
		    }

  }
  
public LockFreeQueue(T data){
	  count = new AtomicInteger(0);
	  head = new AtomicReference<Node>(new Node(data, count.get()));
	  tail = head;
  }
  
public boolean enq(T value) {
	    Node e = new Node(value, count.getAndIncrement()) ;
		while (true) {
			Node last = tail.get();
			Node next = last.next.get();
			if (last == tail.get()) {
				if (next == null) { // all clean for enq
					if (last.next.compareAndSet(next, e)) { // set last.next first
						tail.compareAndSet(last, e);
						return false;
					}
				} else {
					tail.compareAndSet(last, next); // cannot return because my
													// job is not finished yet
				}
			}
		}
  }

public T deq() {
		while (true) {
			Node first = head.get();
			Node last = tail.get();
			Node next = first.next.get();

			if (first == head.get()){
				if (first == last){
					if (next == null){
						return null;//throw new IllegalStateException("Cannot deq an empty queue");
					}
					tail.compareAndSet(last, next);
				}else{
					T result = next.data;
					System.out.println("result available");
					if (head.compareAndSet(first, next)){
						return result;
					}
				}
			}
		}
  }
  public static void main(String args[]){
	  LockFreeQueue<Integer> myQueue = new LockFreeQueue<Integer>(12);
	  System.out.println(myQueue.enq(20));
	  System.out.println(myQueue.enq(35));
	  System.out.println(myQueue.enq(12));
	  System.out.println(myQueue.deq());
	 // System.out.println(myQueue.deq());
  }
}

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class LockFreeQueue<T> implements MyQueue<T> {
  AtomicStampedReference<Node> head;
  AtomicStampedReference<Node> tail;
  AtomicInteger count;
  
  public class Node<T> {

		    private T data;
		    private AtomicStampedReference<Node> next;

		    public Node(T data, int counter){
		        this.data = data;
		        this.next = new AtomicStampedReference<Node>(null, counter);
		    }
  }
  
  public LockFreeQueue(){
	  count.set(0);
	  head = new AtomicStampedReference<Node>(null, count.get());
	  tail = head;
  }
  
  public boolean enq(T value) {
	  int[] lastStamp = {0};
	  int[] nextStamp = {0};
	  Node<T> e = new Node<T>(value, count.getAndIncrement()) ;
		while (true) {
			Node<T> last = tail.get(lastStamp);
			Node<T> next = last.next.get(nextStamp);
			if (last == tail.get(lastStamp)) {
				if (next == null) { // all clean for enq
					if (last.next.compareAndSet(next, e, nextStamp[0], count.get())) { // set last.next first
						tail.compareAndSet(last, e, lastStamp[0],count.get());
						return false;
					}
				} else {
					tail.compareAndSet(last, next, lastStamp[0], nextStamp[0]); // cannot return because my
													// job is not finished yet
				}
			}
		}
  }
  public T deq() {
	  int[] firstStamp = {0};
		int[] lastStamp = {0};
		int[] nextStamp = {0};
		
		while (true) {
			Node<T> first = head.get(firstStamp);
			Node<T> last = tail.get(lastStamp);
			Node<T> next = first.next.get(nextStamp);
			if (first == head.get(firstStamp)){
				if (first == last){
					if (next == null){
						throw new IllegalStateException("Cannot deq an empty queue");
					}
					tail.compareAndSet(last, next, lastStamp[0], nextStamp[0]);
				}else{
					T result = next.data;
					if (head.compareAndSet(first, next, firstStamp[0], nextStamp[0])){
						return result;
					}
				}
			}
		}
  }
}
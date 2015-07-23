import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock ;

public class LockQueue<T> implements MyQueue<T> {
  ReentrantLock enqLock , deqLock;
  Node<T> head;
  Node<T> tail;
  AtomicInteger count = new AtomicInteger(0);
  public Condition deq_condition;
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
	  deq_condition = deqLock.newCondition();
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
		while(head.next == null){
			System.out.println("Null queue");
			try {
				deq_condition.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		deq_condition.signalAll();
		result = head.next.data;
		head = head.next;
		count.decrementAndGet();
	}
	finally{
		deqLock.unlock();
	}
	
    return result;
  }
  public static void main(String args[]){
	  LockQueue<Integer> q = new LockQueue<Integer>();

		class queueThread extends Thread {
			int totalNum;
			int dice;
			volatile LockQueue<Integer> q;
			Random rand;
			Random diceRand;

			queueThread(int n, LockQueue<Integer> q) {
				totalNum = n;
				this.q = q;
				rand = new Random();
				diceRand = new Random();
			}

			public void run() {
				for (int i = 0; i < totalNum; i++) {
					int value = 0;
					dice = diceRand.nextInt(100);
					try{
						if (dice < 60) {
							value = rand.nextInt();
							q.enq(value);
						} else {
							System.out.println(q.deq());
						}
					}catch(IllegalStateException e){
						System.out.println("EMPTY!!");
					}
					//System.out.println("i: " + i + "\tdice: "+ dice + "\tvalue: "+ value);
				}
			}
		}

		ArrayList<queueThread> threadPool = new ArrayList<queueThread>();
	    int threadNum = 10;
	    int operationNum = 2000;
		//	System.out.println("perThread:" + perThread);
		for (int i = 0; i < threadNum ; i++) {
			int perThread = operationNum / threadNum;
			threadPool.add(new queueThread(perThread , q));
		}
		for (int i = 0; i < threadNum; i++) {
			threadPool.get(i).start();
		}
		int i = 0;
		try {
			for (i = 0; i < threadNum; i++) {
				threadPool.get(i).join();
			}
		} catch (InterruptedException e) {
			System.out.println("Thread " + i + " is interrupted");
		}
	  
  }
  
}
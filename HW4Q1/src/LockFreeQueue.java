import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class LockFreeQueue<T> implements MyQueue<T> {
    AtomicStampedReference<Node> head;
    AtomicStampedReference<Node> tail;
    AtomicInteger count;

    public class Node {

        T data;
        AtomicStampedReference<Node> next;

        public Node(T data) {
            this.data = data;
            next = new AtomicStampedReference<Node>(null, 0);
        }

    }

    public LockFreeQueue(T data) {
        count = new AtomicInteger(0);
        Node node = new Node(data);
        head = new AtomicStampedReference<Node>(node, 0);
        tail = new AtomicStampedReference<Node>(node, 0);
    }

    public boolean enq(T value) {
        Node node = new Node(value);

        while (true) {
            int[] tail_mark = new int[1];
            Node last = tail.get(tail_mark);
            int[] last_mark = new int[1];
            Node next = tail.getReference().next.get(last_mark);

            if (last == tail.getReference()) {
                if (next == null) { // all clean for enq
                    if (last.next.compareAndSet(next, node, last_mark[0], last_mark[0] + 1)) { // set last.next first
                        tail.compareAndSet(last, node, tail_mark[0], tail_mark[0] + 1);
                        return true;
                    }
                } else {
                    tail.compareAndSet(last, next, tail_mark[0], tail_mark[0] + 1); // cannot return because my
                    // job is not finished yet
                }
            }
        }
    }

    public T deq() {
        while (true) {
            int[] tail_mark = new int[1];
            int[] head_mark = new int[1];
            int[] next_mark = new int[1];
            Node first = head.get(head_mark);
            Node last = tail.get(tail_mark);
            Node next = first.next.get(next_mark);

            if (first == head.getReference()) {
                if (first == last) {
                    if (next == null) {
                        return null;//continue;
                    }
                    tail.compareAndSet(last, next, tail_mark[0], tail_mark[0] + 1);
                } else {
                    T result = next.data;
                    if (head.compareAndSet(first, next, head_mark[0], head_mark[0] + 1)) {
                        return result;
                    }
                }
            }
        }
    }

    public static void main(String args[]) {
        LockFreeQueue<Integer> q = new LockFreeQueue<Integer>(12);
 /*       System.out.println(q.deq());
        System.out.println(q.enq(20));
        System.out.println(q.enq(35));
        System.out.println(q.enq(12));
        System.out.println(q.deq());
        */

        class queueThread extends Thread {
			int totalNum;
			int dice;
			volatile LockFreeQueue<Integer> q;
			Random rand;
			Random diceRand;

			queueThread(int n, LockFreeQueue<Integer> q) {
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
						if (dice > 30) {
							value = rand.nextInt(100);
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
	    int operationNum = 200;
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
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
                        continue;
                    }
                    tail.compareAndSet(last, next, tail_mark[0], tail_mark[0] + 1);
                } else {
                    T result = next.data;
                    System.out.println("result available");
                    if (head.compareAndSet(first, next, head_mark[0], head_mark[0] + 1)) {
                        return result;
                    }
                }
            }
        }
    }

    public static void main(String args[]) {
        LockFreeQueue<Integer> myQueue = new LockFreeQueue<Integer>(12);
        System.out.println(myQueue.enq(20));
        System.out.println(myQueue.enq(35));
        System.out.println(myQueue.enq(12));
        System.out.println(myQueue.deq());
        // System.out.println(myQueue.deq());
    }
}

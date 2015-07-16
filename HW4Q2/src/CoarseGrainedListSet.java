import java.util.concurrent.locks.ReentrantLock;

public class CoarseGrainedListSet<T> implements ListSet<T> {
    private Node head;
    ReentrantLock listlock;

    public CoarseGrainedListSet() {
        this.head = new Node(Integer.MIN_VALUE);
        this.head.next = new Node(Integer.MAX_VALUE);
        this.listlock = new ReentrantLock();
    }

    public boolean add(T value) {
        Node pred, curr;
        int key = value.hashCode();
        listlock.lock();

        try {
            pred = head;
            curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            if (key == curr.key) {
                return false;
            } else {
                Node node = new Node(value);
                node.next = curr;
                pred.next = node;
                return true;
            }
        } finally {
            listlock.unlock();
        }
    }

    public boolean remove(T value) {
        Node pred, curr;
        int key = value.hashCode();
        listlock.lock();

        try {
            pred = head;
            curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            if (key == curr.key) {
                pred.next = curr.next;
                return true;
            } else {
                return false;
            }
        } finally {
            listlock.unlock();
        }
    }

    public boolean contains(T value) {
        Node pred, curr;
        int key = value.hashCode();
        listlock.lock();
        try {
            pred = head;
            curr = pred.next;
            while (curr.next != null && key != curr.key) {
                //System.out.println(curr.key + " " + key);
                curr = curr.next;
            }
            if (key == curr.key) {
                //System.out.println("Found: " + value);
                return true;
            }
        } finally {
            listlock.unlock();
        }
        //System.out.println("Not Found: " + value);
        return false;
    }
}

class Node<T> {
    T item;
    int key;
    Node next;

    public Node(T t) {
        item = t;
        key = item.hashCode();
    }
}
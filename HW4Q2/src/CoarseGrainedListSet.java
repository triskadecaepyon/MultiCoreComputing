import java.util.concurrent.locks.ReentrantLock;

public class CoarseGrainedListSet<T> implements ListSet<T> {
    private Node head;
    ReentrantLock list_lock;

    public CoarseGrainedListSet() {
        this.head = new Node(Integer.MIN_VALUE);
        this.head.next = new Node(Integer.MAX_VALUE);
        this.list_lock = new ReentrantLock();
    }

    public boolean add(T value) {
        Node pred, curr;
        int key = value.hashCode();
        list_lock.lock();

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
            list_lock.unlock();
        }
    }

    public boolean remove(T value) {
        Node pred, curr;
        int key = value.hashCode();
        list_lock.lock();

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
            list_lock.unlock();
        }
    }

    public boolean contains(T value) {
        Node pred, curr;
        int key = value.hashCode();
        list_lock.lock();
        try {
            pred = head;
            curr = pred.next;
            while (curr.next != null && key != curr.key) { //Might need to be Integer.MAX?
                System.out.println(curr.key + " " + key);
                curr = curr.next;
            }
            if (key == curr.key) {
                System.out.println("Found: " + value);
                return true;
            }
        } finally {
            list_lock.unlock();
        }
        System.out.println("Not Found: " + value);
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
import java.util.concurrent.locks.ReentrantLock;

public class FineGrainedListSet<T> implements ListSet<T> {

    private FineNode head;

    public FineGrainedListSet() {
        this.head = new FineNode(Integer.MIN_VALUE);
        this.head.next = new FineNode(Integer.MAX_VALUE);
    }

    public boolean add(T value) {
        int key = value.hashCode();
        head.NodeLock.lock();
        FineNode pred = head;
        try {
            FineNode curr = pred.next;
            curr.NodeLock.lock();
            try {
                while (curr.key < key) {
                    pred.NodeLock.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.NodeLock.lock();
                }
                if (curr.key == key) {
                    return false;
                }
                FineNode newNode = new FineNode(value);
                newNode.next = curr;
                pred.next = newNode;
                return true;
            } finally {
                curr.NodeLock.unlock();
            }
        } finally {
            pred.NodeLock.unlock();
        }
    }

    public boolean remove(T value) {
        FineNode pred = null;
        FineNode curr = null;
        int key = value.hashCode();
        head.NodeLock.lock();
        try {
            pred = head;
            curr = pred.next;
            curr.NodeLock.lock();
            try {
                while (curr.key < key) {
                    pred.NodeLock.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.NodeLock.lock();
                }
                if (curr.key == key) {
                    pred.next = curr.next;
                    return true;
                }
                return false;
            } finally {
                curr.NodeLock.unlock();
            }
        } finally {
            pred.NodeLock.unlock();
        }
    }

    public boolean contains(T value) {
        FineNode pred = null;
        FineNode curr = null;
        int key = value.hashCode();
        head.NodeLock.lock();
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
            pred.NodeLock.unlock();
        }
        return false;
    }
}

class FineNode<T> {
    ReentrantLock NodeLock;
    T item;
    int key;
    FineNode next;

    public FineNode(T t) {
        item = t;
        key = item.hashCode();
        this.NodeLock = new ReentrantLock();
    }
}
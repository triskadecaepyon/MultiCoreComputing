import java.util.concurrent.atomic.AtomicMarkableReference;


public class LockFreeListSet<T> implements ListSet<T> {
    final Node<T> head;
    final Node<T> tail;

    static class Node<T> {
        final T value;
        final AtomicMarkableReference<Node<T>> next;

        Node(T value) {
            this.value = value;
            this.next = new AtomicMarkableReference<Node<T>>(null, false);
        }
    }

    public LockFreeListSet() {
        tail = new Node<T>(null);
        head = new Node<T>(null);
        head.next.set(tail, false);
    }

    static class Window<T> {
        public Node<T> pred;
        public Node<T> curr;

        Window(Node<T> myPred, Node<T> myCurr) {
            pred = myPred;
            curr = myCurr;
        }
    }

    public boolean add(T value) {
        final Node<T> newNode = new Node<T>(value);
        while (true) {
            final Window<T> window = find(value);
            final Node<T> pred = window.pred;
            final Node<T> curr = window.curr;
            if (curr.value == value) {
                return false;
            } else {
                newNode.next.set(curr, false);
                if (pred.next.compareAndSet(curr, newNode, false, false)) {
                    return true;
                }
            }
        }
    }
    public boolean remove(T value) {
        while (true) {
            final Window<T> window = find(value);
            final Node<T> pred = window.pred;
            final Node<T> curr = window.curr;
            if (curr.value != value) {
                return false;
            }
            final Node<T> succ = curr.next.getReference();
            if (!curr.next.compareAndSet(succ, succ, false, true)) {
                continue;
            }
            pred.next.compareAndSet(curr, succ, false, false);
            return true;
        }
    }
    public boolean contains(T value) {
        boolean[] marked = {false};
        Node<T> curr = head.next.getReference();
        curr.next.get(marked);
        @SuppressWarnings("unchecked")
        final Comparable<? super T> keyComp = (Comparable<? super T>)value;
        while (curr != tail && keyComp.compareTo(curr.value) > 0) {
            curr = curr.next.getReference();
            curr.next.get(marked);
        }
        return (curr.value == value && !marked[0]);
    }


    public Window<T> find(T key) {
        Node<T> pred = null, curr = null, succ = null;
        boolean[] marked = {false};
        @SuppressWarnings("unchecked")
        final Comparable<? super T> keyComp = (Comparable<? super T>)key;

        if (head.next.getReference() == tail) {
            return new Window<T>(head, tail);
        }

        retry:
        while (true) {
            pred = head;
            curr = pred.next.getReference();
            while (true) {
                succ = curr.next.get(marked);
                while (marked[0]) {
                    if (!pred.next.compareAndSet(curr, succ, false, false)) {
                        continue retry;
                    }
                    curr = succ;
                    succ = curr.next.get(marked);
                }

                if (curr == tail || keyComp.compareTo(curr.value) <= 0) {
                    return new Window<T>(pred, curr);
                }
                pred = curr;
                curr = succ;
            }
        }
    }
}


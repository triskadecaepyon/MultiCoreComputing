import java.util.concurrent.atomic.AtomicReference;


// TODO
// Implement the MCS Lock 

public class MCSLock implements MyLock {
	private volatile AtomicReference<Node> tailNode;
	private volatile ThreadLocal<Node> myNode; // initialize properly
	
	private static class Node
	{
	    volatile Node next = null;
	    volatile boolean locked = false;
	   
	}
	
    public MCSLock(int numThread) {
        tailNode = new AtomicReference<Node>(null);
        myNode = new ThreadLocal<Node>(){                  
            @Override
            protected Node initialValue()
            {
                return new Node();
            }      
        };    
    }

    @Override
    public void lock(int myId) {
    	Node qnode = myNode.get();
    	Node pred = tailNode.getAndSet(qnode) ;
    	if(pred != null )
    	{
    		qnode.locked = true ;
    		pred.next = qnode;
    		while(qnode.locked);
    	}
    }

    @Override
    public void unlock(int myId) {
    	Node qnode = myNode.get();
    	if(qnode.next == null) 
    	{     		
    		if(tailNode.compareAndSet(qnode, null ))
    			return ;
    		while(qnode.next == null) ;
    	}  
    	qnode.next.locked = false ;
    	qnode.next = null ;
    }
}
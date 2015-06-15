// TODO 
// Use synchronized to protect count
public class SynchronizedCounter extends Counter {
    @Override
    public void increment() {
         synchronized(this){
        	int temp = this.count; //Enter critical section
            this.count = temp + 1;
         }
    }
}

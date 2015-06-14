// TODO 
// Use synchronized to protect count
public class SynchronizedCounter extends Counter {
    @Override
    public void increment() {
		 synchronized(this){
            this.count ++;   //Synchronized construct increment
         }
    }
}

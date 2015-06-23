public class CyclicBarrierTester extends Thread{
CyclicBarrier barrier;
private int i;
static int test;
        /*
         * Tester class that is used to validate and verify CyclicBarrier
         */

        public static void main(String[] args) {        
                int parties = 100;
                CyclicBarrier barrier = new CyclicBarrier(parties);
                for(int i = 0; i < parties; i++) {
                        //test = i;
                	    CyclicBarrierTester tester = new CyclicBarrierTester(barrier, i);
                        Thread temp = new Thread(tester);
                        temp.start();           
                }       
        }
        
        /*
         * Constructor that points to CyclicBarrier object
         */
        public CyclicBarrierTester(CyclicBarrier barrier, int i) {
                this.barrier = barrier;
                this.i = i;
        }
        public void run() {             
                try {                
                	    System.out.println("Thread " + i + " is executing!");
                        barrier.await();
                        System.out.println("Thread "+ i + " Task Completed");                   
                } catch (InterruptedException e) {}     
        }
}

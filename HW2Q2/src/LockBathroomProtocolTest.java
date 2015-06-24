import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by davidliu on 6/21/15.
 */
public class LockBathroomProtocolTest {

    @Test
    public void testEnterMaleForTwoIter() throws Exception {
        final LockBathroomProtocol my_protocol = new LockBathroomProtocol();
        Thread entering_test = new Thread() {
            @Override
            public void run() {
                my_protocol.enterMale();
                my_protocol.leaveMale();
                my_protocol.enterFemale();
            }
        };
        Thread second_entering = new Thread() {
            @Override
            public void run() {
                my_protocol.enterMale();
                my_protocol.leaveMale();
                my_protocol.enterFemale();
                my_protocol.enterFemale();
            }
        };
        try {
            entering_test.start();
            second_entering.start();
            while (entering_test.isAlive()) {
                //System.out.println("First: " + entering_test.isAlive() + " " + second_entering.isAlive());
            }
            while (second_entering.isAlive()) {
                //System.out.println("Second: " + entering_test.isAlive() + " " + second_entering.isAlive());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testLeaveMale() throws Exception {
//        final LockBathroomProtocol my_protocol = new LockBathroomProtocol();
//        Thread entering_test = new Thread() {
//            @Override
//            public void run() {
//                my_protocol.enterMale();
//                my_protocol.leaveMale();
//                System.out.println("I am all done");
//            }
//        };
//        Thread second_entering = new Thread() {
//            @Override
//            public void run() {
//                my_protocol.enterMale();
//                System.out.println("I should be able to enter");
//                my_protocol.leaveMale();
//            }
//        };
//        try {
//            entering_test.start();
//            second_entering.start();
//            while (entering_test.isAlive()) {
//                System.out.println("First: " + entering_test.isAlive() + " " + second_entering.isAlive());
//            }
//            while (second_entering.isAlive()) {
//                System.out.println("Second: " + entering_test.isAlive() + " " + second_entering.isAlive());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testEnterFemale() throws Exception {
//
//    }
//
//    @Test
//    public void testLeaveFemale() throws Exception {
//
//    }
}
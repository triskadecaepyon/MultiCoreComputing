import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by davidliu on 6/21/15.
 */
public class LockBathroomProtocolTest {

    @Test
    public void testEnterMale() throws Exception {
        final LockBathroomProtocol my_protocol = new LockBathroomProtocol();
        Thread entering_test = new Thread() {
            @Override
            public void run() {
                my_protocol.enterMale();
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                my_protocol.leaveMale();
            }
        };
        Thread second_entering = new Thread() {
            @Override
            public void run() {
                my_protocol.enterMale();
                //Do stuff
                my_protocol.leaveMale();
            }
        };
        try {
            entering_test.start();
            second_entering.start();
            entering_test.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testLeaveMale() throws Exception {
//
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
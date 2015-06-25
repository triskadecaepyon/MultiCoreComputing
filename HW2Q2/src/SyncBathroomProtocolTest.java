import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by davidliu on 6/21/15.
 */
public class SyncBathroomProtocolTest {

    @Test
    public void testStandardEnter() throws Exception {
        final SyncBathroomProtocol my_protocol = new SyncBathroomProtocol();
        Thread entering_test = new Thread() {
            @Override
            public void run() {
                my_protocol.enterMale();
                my_protocol.leaveMale();
                my_protocol.enterFemale();
                my_protocol.leaveFemale();
            }
        };
        Thread second_entering = new Thread() {
            @Override
            public void run() {
                my_protocol.enterMale();
                my_protocol.leaveMale();
                my_protocol.enterFemale();
                my_protocol.enterFemale();
                my_protocol.leaveFemale();
                my_protocol.leaveFemale();
            }
        };
        try {
            System.out.println("Starting Second test");
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

    @Test
    public void testStarvationThreeVtwo() throws Exception {
        final SyncBathroomProtocol my_protocol = new SyncBathroomProtocol();
        Thread entering_test = new Thread() {
            @Override
            public void run() {
                my_protocol.enterMale();
                my_protocol.enterMale();
                my_protocol.enterFemale();
                my_protocol.leaveFemale();
                my_protocol.enterMale();
                my_protocol.leaveMale();
                my_protocol.leaveMale();
                my_protocol.leaveMale();
                my_protocol.enterFemale();
                my_protocol.enterFemale();
                my_protocol.enterFemale();
                my_protocol.leaveFemale();
                my_protocol.leaveFemale();
                my_protocol.leaveFemale();
            }
        };
        Thread second_entering = new Thread() {
            @Override
            public void run() {
                my_protocol.enterMale();
                my_protocol.enterMale();
                my_protocol.leaveMale();
                my_protocol.leaveMale();
                my_protocol.enterFemale();
                my_protocol.leaveFemale();
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

    @Test
    public void testLeaveMale() throws Exception {

    }

    @Test
    public void testEnterFemale() throws Exception {

    }

    @Test
    public void testLeaveFemale() throws Exception {

    }
}
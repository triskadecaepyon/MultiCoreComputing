import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by davidliu on 6/21/15.
 */
public class LockBathroomProtocolTest {

    @Test
    public void testEnterMale() throws Exception {
        LockBathroomProtocol my_protocol = new LockBathroomProtocol();
        my_protocol.enterMale();
        my_protocol.enterMale();
        my_protocol.leaveMale();
        my_protocol.leaveMale();
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
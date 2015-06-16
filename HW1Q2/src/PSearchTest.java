import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by davidliu on 6/14/15.
 */
public class PSearchTest {

    @Test
    public void testParallelSearch() throws Exception {
        int a[] = {1,2,3,4,5,6,7,8,9,10,11,12,16,17,20};
        PSearch pclass = new PSearch(3, a, 0, a.length);
        pclass.call();
    }
}
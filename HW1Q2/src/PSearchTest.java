import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by davidliu on 6/14/15.
 */
public class PSearchTest {

    @Test
    public void testParallelSearch() throws Exception {
        int a[] = {1,2,3,4,5,6,7,8,9,10};
        PSearch pclass = new PSearch(3, a, 0, a.length);
        for (int i=0; i < 3; i++) {
            pclass.call();
        }
    }
}
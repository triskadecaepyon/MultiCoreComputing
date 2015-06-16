import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by davidliu on 6/14/15.
 */
public class PSearchTest {

    @Test
    public void testParallelSearch() throws Exception {
        int a[] = {1,2,3,4,5,6,7,8,9,10,11,12,16,17,20};
        int return_val = PSearch.parallelSearch(20, a, 6);
        System.out.println("Search Results: " + return_val);
        assertTrue(return_val == 14);
    }

    @Test
    public void testParallelSearchLarge() throws Exception {
        int a[] = {1,2,3,4,5,6,7,8,9,10,11,12,16,17,20,150,160,276,280,276,407};
        int return_val = PSearch.parallelSearch(280, a, 6);
        System.out.println("Search Results: " + return_val);
        assertTrue(return_val == 18);
    }
}
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
	
	@Test
	public void testParallelSearchEmpty() throws Exception {
        int a[] = {};
        int return_val = PSearch.parallelSearch(5, a, 6);
        System.out.println("Search Results: " + return_val);
        assertTrue(return_val == -1);
    }
	
	@Test
	public void testParallelSearchFives() throws Exception {
         int a[] = new int[250];
		 for (int i = 0; i < 250; i++)
		{
    	   a[i] = 5*i;
		}
		int return_val = PSearch.parallelSearch(45, a, 5);
        System.out.println("Search Results: " + return_val);
        assertTrue(return_val == 9);
    }
	
	@Test
	public void testParallelSearchPolynomials() throws Exception {
         int a[] = new int[15];
		 for (int i = 0; i < 15; i++)
		{
    	   a[i] = (int) (Math.pow(i, 3) + 3*Math.pow(i, 2) + 3*Math.pow(i, 2) + 1);
		}
		int return_val = PSearch.parallelSearch(897, a, 1);
        System.out.println(return_val);
        assertTrue(return_val==8);
    }
	
	@Test
	public void testParallelSearchMultiple() throws Exception {
         int a[] = {1, 3, 15, 4, 6, 7, 1, 4, 15, 3, 2, 1};
         int return_val = PSearch.parallelSearch(4, a, 6);
         System.out.println(return_val);
         assertTrue(return_val==3||return_val==7);
    }
}
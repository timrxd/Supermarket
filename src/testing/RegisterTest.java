package testing;

import static org.junit.Assert.*;
import org.junit.Test;
import cashier_exercise.Register;

/**
 * Register JUnit Tests
 * 
 *  Test each function of the Register object
 * 
 * @author Tim Dowd
 * @date 3/18/2016
 */
public class RegisterTest {

	@Test
	public void testCheckout() {
		
		Register reg = new Register();     

		// Empty string
		assertTrue(reg.checkout("") == 0.0);
		
		// Basic tests
		assertTrue(reg.checkout("A12T-4GH7-QPL9-3N4M") == 3.76);
		assertTrue(reg.checkout("A12T-4GH7-QPL9-3N4M;65P1-UDGM-XH2M-LQW2") == 10.17);
		assertTrue(reg.checkout("A12T-4GH7-QPL9-3N4M;E5T6-9UI3-TH15-QR88;"
				+ "YRT6-72AS-K736-L4AR;TQ4C-VV6T-75ZX-1RMR;65P1-UDGM-XH2M-LQW2") == 28.21);
		
		// Exercise example had some weird typos:
		assertFalse(reg.checkout("A12T-4GH7Y-QPL9-3N4MA;65P1-UDGM-XH2M-LQW2") == 10.17);
		assertTrue(reg.checkout("A12T-4GH7Y-QPL9-3N4MA;65P1-UDGM-XH2M-LQW2") == 6.41);
		
		// Incorrect codes
		assertTrue(reg.checkout("ABCD-1234-5678-WXYZ") == 0.0);
		assertTrue(reg.checkout("ABCD-1234-5678-WXYZ;A12T-4GH7-QPL9-3N4M") == 3.76);
		
		// Case-insensitive
		assertTrue(reg.checkout("a12t-4gh7-qpl9-3n4m") == 3.76);
		assertTrue(reg.checkout("A12t-4Gh7-qPL9-3n4M") == 3.76);
		assertTrue(reg.checkout("A12t-4Gh7-qPL9-3n4m;E5T6-9uI3-TH15-Qr88;"
				+ "yRT6-72as-K736-L4aR;tq4C-Vv6T-75Zx-1RMr;65P1-UdgM-XH2m-LqW2") == 28.21);		
		
		// Handle spaces in product name
		reg.addItem("asdf-12hj-34hj-jkl9", "Fruit Loops", 1000.23);
		System.out.println(reg.getProductList());
		System.out.println(reg.checkout("asdf-12hj-34hj-jkl9"));
		//assertTrue(reg.checkout("asdf-12hj-34hj-jkl9") == 1.087);
		reg.removeItem("asdf-12hj-34hj-jkl9");
	}
	
	// Combined to not mess with default prices.txt file
	@Test
	public void testAddRemoveItem() {
		
		Register reg = new Register();
				
		// Doesn't exist
		assertTrue(reg.checkout("TEST-1234-5678-WXYZ") == 0.0);
		
		reg.addItem("TEST-1234-5678-WXYZ", "Fish", 12.34);
		
		// Now it does
		assertTrue(reg.checkout("TEST-1234-5678-WXYZ") == 13.42);
				
		// And it's gone
		reg.removeItem("TEST-1234-5678-WXYZ");
		assertTrue(reg.checkout("TEST-1234-5678-WXYZ") == 0.0);
		
	}

}

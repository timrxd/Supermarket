package test.java;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import main.java.Register;

/**
 * Register JUnit Tests
 * 
 *  Test each function of the Register object
 * 
 * @author Tim Dowd
 * @date 3/18/2016
 */
public class RegisterTest {
	
	@Before
	public void defaultItems() {
		Register reg = new Register();
		
		// Ensure default items exist for unit tests
		if (reg.checkout("A12t-4Gh7-qPL9-3n4m;E5T6-9uI3-TH15-Qr88;"
			+ "yRT6-72as-K736-L4aR;tq4C-Vv6T-75Zx-1RMr;65P1-UdgM-XH2m-LqW2") != 28.21) {
				reg.addItem("A12T-4GH7-QPL9-3N4M", "Cereal", 3.46);
				reg.addItem("E5T6-9UI3-TH15-QR88", "Chicken", 8.18);
				reg.addItem("YRT6-72AS-K736-L4AR", "Pop", 1.63);
				reg.addItem("TQ4C-VV6T-75ZX-1RMR", "Pizza", 6.78);
				reg.addItem("65P1-UDGM-XH2M-LQW2", "Tuna", 5.89);
		}
		
		reg.close();		
	}

	@Test
	public void testEmptyString() {		
		Register reg = new Register();
		
		assertTrue(reg.checkout("") == 0.0);
		
		reg.close();
	}
	
	@Test
	public void testDefaultProducts() {
		Register reg = new Register();

		assertTrue(reg.checkout("A12T-4GH7-QPL9-3N4M") == 3.76);
		assertTrue(reg.checkout("E5T6-9UI3-TH15-QR88") == 8.90);
		assertTrue(reg.checkout("YRT6-72AS-K736-L4AR") == 1.77);
		assertTrue(reg.checkout("TQ4C-VV6T-75ZX-1RMR") == 7.37);
		assertTrue(reg.checkout("65P1-UDGM-XH2M-LQW2") == 6.41);		
		
		reg.close();
	}
	
	@Test
	public void testInvalidCodes() {
		Register reg = new Register();
		
		assertTrue(reg.checkout("ABCD-1234-5678-WXYZ") == 0.0);
		assertTrue(reg.checkout("ABCD") == 0.0);
		assertTrue(reg.checkout("ABCD12345678WXYZ") == 0.0);
		assertTrue(reg.checkout("A12T4GH7QPL93N4M") == 0.0);
		
		// Exercise example had some weird typos:
		assertFalse(reg.checkout("A12T-4GH7Y-QPL9-3N4MA;65P1-UDGM-XH2M-LQW2") == 10.17);
		assertTrue(reg.checkout("A12T-4GH7Y-QPL9-3N4MA;65P1-UDGM-XH2M-LQW2") == 6.41);
		
		reg.close();
	}
	
	@Test
	public void testCaseInsensitive() {
		Register reg = new Register();
		
		assertTrue(reg.checkout("a12t-4gh7-qpl9-3n4m") == 3.76);
		assertTrue(reg.checkout("A12t-4Gh7-qPL9-3n4M") == 3.76);
		assertTrue(reg.checkout("A12t-4Gh7-qPL9-3n4m;E5T6-9uI3-TH15-Qr88;"
				+ "yRT6-72as-K736-L4aR;tq4C-Vv6T-75Zx-1RMr;65P1-UdgM-XH2m-LqW2") == 28.21);		
		
		reg.close();
	}
	
	@Test
	public void testMultipleCodes() {
		Register reg = new Register();
		
		// Default codes
		assertTrue(reg.checkout("A12T-4GH7-QPL9-3N4M;65P1-UDGM-XH2M-LQW2") == 10.17);
		assertTrue(reg.checkout("A12T-4GH7-QPL9-3N4M;E5T6-9UI3-TH15-QR88;"
				+ "YRT6-72AS-K736-L4AR;TQ4C-VV6T-75ZX-1RMR;65P1-UDGM-XH2M-LQW2") == 28.21);

		// With invalid codes
		assertTrue(reg.checkout("ABCD-1234-5678-WXYZ;A12T-4GH7-QPL9-3N4M") == 3.76);
		assertTrue(reg.checkout("A12T-4GH7-QPL9-3N4M;E5T6-9UI3-TH15-QR88;ABCD-1234-5678-WXYZ;"
				+ "YRT6-72AS-K736-L4AR;TQ4C-VV6T-75ZX-1RMR;65P1-UDGM-XH2M-LQW2") == 28.21);
		
		// With empty codes
		assertTrue(reg.checkout(";;;A12T-4GH7-QPL9-3N4M;;;") == 3.76);
		
		reg.close();
	}	
	
	@Test
	public void testAddRemoveItem() {
		
		Register reg = new Register();
				
		// Doesn't exist
		reg.removeItem("TEST-1234-5678-WXYZ");
		assertTrue(reg.checkout("TEST-1234-5678-WXYZ") == 0.0);
		
		// Now it does
		reg.addItem("TEST-1234-5678-WXYZ", "Fish", 12.34);
		assertTrue(reg.checkout("TEST-1234-5678-WXYZ") == 13.42);
				
		// And it's gone
		reg.removeItem("TEST-1234-5678-WXYZ");
		reg.checkout("TEST-1234-5678-WXYZ");
		assertTrue(reg.checkout("TEST-1234-5678-WXYZ") == 0.0);		
		
		
		reg.close();
	}
	
	@Test
	public void testInvalidAdd() {
		Register reg = new Register();
		
		// Empty code
		reg.addItem("", "Cloud", 5.99);
		assertTrue(reg.checkout("") == 0);
		
		// Missing dashes
		reg.addItem("abcd12345678wxyz", "Bayo", 5.99);
		assertTrue(reg.checkout("abcd12345678wxyz") == 0);

		// Not enough characters
		reg.addItem("abc-123-456-xyz", "Ryu", 5.99);
		assertTrue(reg.checkout("abc-123-456-xyz") == 0);
		
		// Too many characters, from example
		reg.addItem("A12T-4GH7Y-QPL9-3N4MA", "Example", 1.00);
		assertTrue(reg.checkout("A12T-4GH7Y-QPL9-3N4MA") == 0);
		
		// Too many characters, from example
		reg.addItem("!@##-%^&*-*()&-!@#$", "!@#$%^&", 1.00);
		assertTrue(reg.checkout("!@##-%^&*-*()&-!@#$") == 0);
				
		
		reg.close();
	}
	
	@Test
	public void testSpacesInName() {
		Register reg = new Register();
		
		// Handle spaces in product name
		reg.addItem("asdf-12hj-34hj-jkl9", "Fruit Loops", 1.00);
		assertTrue(reg.checkout("asdf-12hj-34hj-jkl9") == 1.09);
		reg.removeItem("asdf-12hj-34hj-jkl9");
		
		reg.close();
	}

}

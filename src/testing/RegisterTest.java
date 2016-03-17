package testing;

import static org.junit.Assert.*;
import org.junit.Test;
import cashier_exercise.Register;

public class RegisterTest {

	@Test
	public void testCheckout() {
		Register reg = new Register();
		assertTrue(reg.checkout("") == 0.0);
		System.out.println(reg.checkout("A12T-4GH7-QPL9-3N4M"));
		assertTrue(reg.checkout("A12T-4GH7-QPL9-3N4M") == 3.76);
		//assertTrue(reg.checkout("A12T-4GH7-QPL9-3N4M;E5T6-9UI3-TH15-QR88") == 12.6585);
		assertTrue(reg.checkout("") == 0.0);
		assertTrue(reg.checkout("") == 0.0);
	}

}

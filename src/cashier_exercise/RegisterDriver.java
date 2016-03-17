package cashier_exercise;

import java.util.Scanner;

/**
 * Cash Register Driver
 * 
 *  Tests the Register object
 * 
 * @author Tim Dowd
 * @date 3/16/2016
 * @version 1.0
 */
public class RegisterDriver {
	
	/**
	 * Main method to interact with user
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		
		// Initialize register
		Register reg = new Register();
		
		// Prompt
		System.out.println("Welcome to The Supermarket.\n" + 
					"Enter the item codes below, seperated by semicolons.");
		
		// Input
		Scanner scan = new Scanner(System.in);
		String codes = scan.nextLine();
		
		// Calculate sale price
		Double sale = reg.checkout(codes);
		System.out.printf("Total: $%.2f\n", sale);	
		
		// Test insert
		System.out.printf("Total: $%.2f\n", reg.checkout("A12T-4GH7-QPL9-3N4M;65P1-UDGM-XH2M-LQW2;ABCD-1234-5678-LMNO"));	
		//reg.addItem("ABCD-1234-5678-LMNO", 5.00);
		System.out.printf("Total: $%.2f\n", reg.checkout("A12T-4GH7-QPL9-3N4M;65P1-UDGM-XH2M-LQW2;ABCD-1234-5678-LMNO"));	
			
		
		scan.close();
		
	}
}

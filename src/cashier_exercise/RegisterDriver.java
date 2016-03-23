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
		String codes = "";//scan.nextLine();
		
		// Calculate sale price
		Double sale = reg.checkout(codes);
		System.out.printf("Total: $%.2f\n", sale);	

		/*
		reg.addItem("ABCD-1234-4567-WXYZ", "Test", 100.0);
		reg.removeItem("ABCD-1234-4567-WXYZ");
		*/
		
		reg.addItem("asdf-12hj-34hj-jkl9", "Fruit", 7.01);
		System.out.println(reg.getProductList());
		System.out.println(reg.checkout("asdf-12hj-34hj-jkl9"));
		reg.removeItem("asdf-12hj-34hj-jkl9");
		
		scan.close();
		
	}
}

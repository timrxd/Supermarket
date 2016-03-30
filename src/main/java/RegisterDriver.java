package main.java;

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
		// Input
		Scanner scan = new Scanner(System.in);
		
		// Prompt
		int code = 0;
		System.out.println("Welcome to The Supermarket.\n"
				+ "Enter the your selection from menu below:\n"  
				+ "[1]\tEnter codes for checkout\n"
				+ "[2]\tAdd an item\n"
				+ "[3]\tRemove an item\n"
				+ "[4]\tDisplay product list\n"
				+ "[5]\tExit\n");
		String input = scan.nextLine();
		try {code = Integer.parseInt(input);}
		catch (NumberFormatException e) {
			code = 0;
		}	
		
		while (code != 5) {
			switch (code){
				case 1:
					System.out.println("Enter product codes seperated by semicolons:");
					String line = scan.nextLine();
					line.replaceAll("\\s","");
					System.out.printf("Total: $%.2f\n\n", reg.checkout(line));	
					break;
				case 2:
					System.out.println("Enter product code (xxxx-xxxx-xxxx-xxxx) of new product:");
					String c = scan.nextLine();
					c.replaceAll("\\s","");
					System.out.println("Enter name of product:");
					String n = scan.nextLine();
					System.out.println("Enter price of product");
					String p = scan.nextLine();
					try {reg.addItem(c, n, Double.parseDouble(p));}
					catch (NumberFormatException e) {
						System.out.println("Invalid price.");
					}
					System.out.println("");
					break;
				case 3:
					System.out.println("Enter product code to be removed:");
					input = scan.nextLine();
					input.replaceAll("\\s","");
					reg.removeItem(input);
					System.out.println("");
					break;
				case 4:
					System.out.println("Product List:\n" + reg.getProductList());
					break;
				default:
					System.out.println("Invalid selection.");
			}
				
			// Re-prompt
			System.out.println("Enter the your selection from menu below:\n" + 
					  "[1]\tEnter codes for checkout\n"
						+ "[2]\tAdd an item\n"
						+ "[3]\tRemove an item\n"
						+ "[4]\tDisplay product list\n"
						+ "[5]\tExit\n");
			input = scan.nextLine();
			try {code = Integer.parseInt(input);}
			catch (NumberFormatException e) {
				code = 0;
			}	
		}
		
		System.out.println("Register closed.\n");
		
		scan.close();
		
	}
}

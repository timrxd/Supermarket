package cashier_exercise;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Cash Register Program
 * 
 *  Takes a set of product codes, 
 *  look up the price of each product, 
 *  and compute the total amount of the sale + tax.
 * 
 * @author Tim Dowd
 * @date 3/16/2016
 * @version 1.0
 */
public class Register {
	
	/**
	 * Local sales tax, in percent format
	 */
	private final double SALES_TAX = 8.75;
	
	/**
	 * Hashtable that links item code to item price
	 */
	private Hashtable<String, Double> prices;
	
	/**
	 * Constructor a Register object
	 */
	public Register() {
		initPrices();
	}	
	
	/**
	 * Calculates total price of items 
	 * @param items String of item codes delineated by ';'
	 * @return Total price of the sale
	 */
	public Double checkout(String items) {
		
		// Parse item codes 
		String[] codes = items.toUpperCase().split(";");
		
		// Running total
		double total = 0.0;

		// For each item, add its price to total
		for(int i = 0; i < codes.length; i++) {
			if (prices.get(codes[i]) != null)
				total += prices.get(codes[i]);
		}
		
		// Add tax
		total *= (1+(SALES_TAX/100));
		
		// Round to nearest penny
		total = Math.round(total*100.0) / 100.0;
	
		return total;
	}
	
	/**
	 * Initialize hashtable of prices with product codes
	 */
	public void initPrices() {
		
		// Initialize hashtable
		prices = new Hashtable<String, Double>();
		
		try {
			
			// Read in codes and prices from price file
			Scanner read = new Scanner(new File("prices.txt"));
			
			// For each product, add a hashtable entry to prices
			while (read.hasNext()) {
				prices.put(read.next(), Double.parseDouble(read.next()));
			}
			
			// Close scanner
			read.close();
			
		} catch (FileNotFoundException e) {
			
			// Create blank price file for next time
			System.out.println("Price file not found, generating blank file.");
			try {
				new File("prices.txt").createNewFile();
			} catch (IOException e1) {
				System.err.println("Could not create new price file, terminating register.");
				System.exit(0);
			}
		}
	}
	
	/**
	 * Add an item to the hashtable of prices and to the prices file
	 * @param code String of characters representing product
	 * @param price Price of item
	 */
	public void addItem(String code, double price) {
		
		// Check if duplicate item code
		if (prices.get(code) == null) {
			prices.put(code, price);
			try {
				Files.write(Paths.get("prices.txt"), 
						("\n" + code + " " + price).getBytes(), StandardOpenOption.APPEND);				
			} catch (IOException e) {
				System.err.println("Error, could not add item to price list.");
			}
		}
		else
			System.out.println("Item code " + code + " already exists.");
	}
}

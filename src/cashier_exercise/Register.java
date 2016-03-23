package cashier_exercise;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

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
	private Hashtable<String, Product> productList;
	
	/**
	 * Location of the list of products
	 * Defaults to Supermarket/prices.txt
	 */
	private String productFile;
	
	/**
	 * Default constructor a Register object
	 */
	public Register() {
		productFile = "prices.txt";
		initProductList();
	}	
	
	/**
	 * Constructor that initializes Register to a specified file
	 * @param f File name
	 */
	public Register(String f) {
		productFile = f;
		initProductList();
	}
	
	/**
	 * Calculates total price of items 
	 * @param items String of item codes delineated by ';'
	 * @return Total price of sale
	 */
	public Double checkout(String items) {
		
		// Parse item codes 
		String[] codes = items.toUpperCase().split(";");
		
		// Running total
		double total = 0.0;

		// For each item, add its price to total
		for(int i = 0; i < codes.length; i++) {
			//System.out.println(productList.get("codes[i]"));
			if (productList.get(codes[i]) != null)
				total += productList.get(codes[i]).getPrice();
		}
		
		// Add tax
		total *= (1+(SALES_TAX/100));
		
		// Round to nearest penny
		total = Math.round(total*100.0) / 100.0;
	
		return total;
	}
	
	/**
	 * Change the list of products the register sells
	 * Clears the old list and reinitializes based on new file contents
	 *  
	 * @param f New file name
	 */
	public void changeProductFile(String f) {
		productFile = f;
		productList.clear();
		initProductList();
	}
	
	/**
	 * Initialize hashtable of prices with product codes
	 */
	public void initProductList() {
		
		// Initialize hashtable
		productList = new Hashtable<String, Product>();
		
		try {
			
			// Read in codes and prices from price file
			Scanner read = new Scanner(new File(productFile));
			
			// For each product, add a hashtable entry to prices
			while (read.hasNext()) {
				String code = read.next();				
				double price = Double.parseDouble(read.next());
				String name = read.nextLine();
				productList.put(code , new Product(code, price, name));
			}
			
			// Close scanner
			read.close();
			
		} catch (FileNotFoundException e) {
			
			// Create blank price file for next time
			System.out.println("Price file not found, generating blank file.");
			try {
				new File(productFile).createNewFile();
			} catch (IOException e1) {
				System.err.println("Could not create new price file, terminating register.");
				System.exit(0);
			}
		}
	}
	
	/**
	 * Quick dump of the products in the register
	 * @return String containing <code price name> for each product
	 */
	public String getProductList() {
		String list = "";
        Set<String> keys = productList.keySet();
		for(String key: keys){
            list += key + " :: " + productList.get(key).getCode() + " " +
            		+ productList.get(key).getPrice() + 
            		" " + productList.get(key).getName() + "\n";
        }
		list.substring(0,list.length()-1);
		return list;
	}
	
	/**
	 * Add an item to the hashtable of prices and to the prices file
	 * @param code String of characters representing product
	 * @param price Price of item
	 */
	public void addItem(String code, String name, double price) {
		
		// Price cannot be below 0
		if (price < 0) {
			System.out.println("Price cannot be less than free.");
		}		
		// Check if duplicate item code
		else if (productList.get(code) != null) {
			System.out.println("Item code " + code + " already exists.");
		}
		else {
			try {
				code = code.toUpperCase();
				Files.write(Paths.get(productFile), 
						("\n" + code + " " + price + " " + name).getBytes(), 
						StandardOpenOption.APPEND);	
				productList.put(code, new Product(code, price, name));
			} catch (IOException e) {
				System.err.println("Error, could not add item to price list.");
			}
		}
	}
	
	/**
	 * Removes the item from the price list
	 * @param code Item code
	 */
	public void removeItem(String code) {
		
		if (productList.get(code) == null) {
			System.out.println("Item doesn't exist.");
		}
		else {
			// Read in codes and prices from price file
			Scanner read;
			try {
				read = new Scanner(new File(productFile));
				
				String line = "";
				String file = "";
				
				// Loop through each line and exclude product 
				while (read.hasNext()) {
					line = read.nextLine();
					if (!line.toUpperCase().contains(code.toUpperCase()))
						file += line + "\n";
				}
				file = file.substring(0,file.length()-1);
				read.close();
				
				// Rewrite products to prices.txt
				FileWriter print = new FileWriter(productFile);
				print.write(file);
				print.close();
				
				productList.remove(code);
				
				
			} catch (IOException e) {
				System.err.println("Error rewriting prices.txt");
			}
		}
			
	}
}

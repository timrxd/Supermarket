package cashier_exercise.java;

/**
 * Represents a Product object to be stored in a Register
 * 
 * @author Tim
 * @date 3/21/2016
 * @version 1.0
 */
public class Product {

	/**
	 * Product name
	 */
	private String productName;
	
	/**
	 * Product code - 16 alphanumeric characters in groups of 4
	 * ex. ABCD-1234-45WX-YZ67
	 */
	private String productCode;
	
	/**
	 * Price of product, cannot be below 0
	 */
	private double productPrice;
	
	/**
	 * Constructor for Product object
	 * @param n Name
	 * @param c Code
	 * @param p Price
	 */
	public Product(String c, double p, String n) {
		productName = n;
		productCode = c;
		// Price cannot be below 0
		productPrice = (p < 0) ? 0 : p;
	}
	
	/**
	 * Rename product
	 * @param n New name of product
	 */
	public void setName(String n) {
		productName = n;
	}
	
	/**
	 * Change product code
	 * @param c New code for product
	 */
	public void setCode(String c) {
		productCode = c;
	}
	
	/**
	 * Change price of product, cannot be less than 0
	 * @param p New price for object
	 */
	public void setPrice(double p) {
		productPrice = (p < 0) ? 0 : p;
	}
	
	/**
	 * Get product name
	 * @return productName
	 */
	public String getName() {
		return productName;
	}
	
	/**
	 * Get product code
	 * @return productCode
	 */
	public String getCode() {
		return productCode;
	}
	
	/**
	 * Get product price
	 * @return productPrice
	 */
	public double getPrice() {
		return productPrice;
	}
}

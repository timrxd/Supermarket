import java.sql.*;

/**
 * Cash Register Program
 * 
 *  Takes a set of product codes, 
 *  look up the price of each product from database, 
 *  and compute the total amount of the sale + tax.
 * 
 * @author Tim Dowd
 * @date 3/16/2016
 * @version 2.0
 */
public class Register {
	
	/**
	 * Local sales tax, in percent format
	 */
	private final double SALES_TAX = 8.75;
	
	/**
	 * Connection to local SQLite database
	 * Database holds the product list
	 */
	private Connection db;
	
	/**
	 * Sends SQL statements to database
	 */
	private Statement sql;
	
	/**
	 * Default constructor a Register object
	 */
	public Register() {  
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

		double total = 0.0;
		
		ResultSet rs = null;

		// For each item, add its price to total
		for(int i = 0; i < codes.length; i++) {			
			try {
				rs = sql.executeQuery("SELECT PRICE FROM ITEMLIST WHERE CODE='" + codes[i] + "';");
			    if (rs.isBeforeFirst() )  {
			    	total += rs.getDouble("PRICE");			
			    }
				rs.close();
			}
			catch (SQLException e) {
				System.err.println("Error retrieving price of " + codes[i] + " from database in checkout\n" + e);
			}			
		}		
		
		// Add tax
		total *= (1+(SALES_TAX/100));
		
		// Round to nearest penny
		total = Math.round(total*100.0) / 100.0;
	
		return total;
	}
		
	/**
	 *  Initialize connection to product list database
	 */
	public void initProductList() {
			
		// Create connection
		try {
			
			Class.forName("org.sqlite.JDBC");
		    db = DriverManager.getConnection("jdbc:sqlite:Register.db");		      
		    sql = db.createStatement();		      
			
		} catch (SQLException e) {
			System.err.println("Could not connect to the product list." + e);
		} catch (ClassNotFoundException e) {
			System.err.println("Could not find SQLite class, check build path." + e);
		}
	}
	
	/**
	 * Close connection to database when register is done being used
	 */
	public void close() {
		// Close connections
		try {
			sql.close();
			db.close();
		} catch (SQLException e) {
			System.out.println("Error closing connection to database.\n" + e);
		}
	}
	
	/**
	 * Quick dump of the products in the register
	 * @return String containing <code name price> for each product
	 */
	public String getProductList() {
		
		String list = "";
		try {
			ResultSet rs = sql.executeQuery( "SELECT * FROM ITEMLIST;" );
		    while ( rs.next() ) {
		    	String code = rs.getString("CODE");
			    String  name = rs.getString("NAME");
			    double price  = rs.getDouble("PRICE");
			    list += code + " " + name + " $" + price + "\n";
		    }
		    rs.close();
		} catch (SQLException e) {
			System.err.println("Couldn't retrieve product list from database." + e);
		}
		
		return list;
	}
	
	/**
	 * Add an item to the product database
	 * @param code String of characters representing product
	 * @param name String of the products name
	 * @param price Price of item
	 */
	public void addItem(String code, String name, double price) {
		
		// Check if item already exists
		ResultSet rs;
		boolean exists = false;
		try {
			rs = sql.executeQuery("SELECT PRICE FROM ITEMLIST WHERE CODE='" + code + "';");
			if (rs.isBeforeFirst()) {
				exists = true;
			}
		} catch (SQLException e) {
			System.out.println("Error connecting to database in addItem." + e);
		}
		
		if (exists) {
			System.out.println("Item " + code + " already exists.");
		}
		else if (!code.matches("\\w{4}-\\w{4}-\\w{4}-\\w{4}")) {
			System.out.println("Invalid code format. {Must be xxxx-xxxx-xxxx-xxxx)");
		}
		else if (price < 0) {
			System.out.println("Price cannot be less than free.");
		}	
		else {
			try {
				// Keep all codes uppercase only
				code = code.toUpperCase();
				sql.executeUpdate("INSERT INTO ITEMLIST (CODE, NAME, PRICE) " + 
				"VALUES ('" + code + "', '" + name + "', " + price + ");");
			} catch (SQLException e) {
				System.err.println("Error, could not add item to price list.\n" + e);
			}
		}
	}
	
	/**
	 * Removes the item from the database
	 * @param code Item code
	 */
	public void removeItem(String code) {		
		try {			
			// Check for uppercase code
			code = code.toUpperCase();
			sql.executeUpdate("DELETE from ITEMLIST where CODE='" + code + "';");	
			
		} catch (SQLException e) {
			System.err.println("Error removing " + code + " from database." + e);
		}			
	}
}

package cashier_exercise;

import java.sql.*;

public class SQL_Test
{
  public static void main( String args[] )
  {
    Connection db = null;
    Statement sql = null;
    try {
      Class.forName("org.sqlite.JDBC");
      db = DriverManager.getConnection("jdbc:sqlite:Register.db");
      
      sql = db.createStatement();
      
      //sql.executeUpdate("DROP TABLE ITEMLIST");
      
      /*sql.executeUpdate("CREATE TABLE ITEMLIST " +
              			"(CODE CHAR(19)	PRIMARY KEY     NOT NULL," +
              			" NAME          TEXT    		NOT NULL, " + 
              			" PRICE         SMALLMONEY     	NOT NULL)");
      */
      
      //sql.executeUpdate("INSERT INTO ITEMLIST (CODE, NAME, PRICE) " + "VALUES ('A12T-4GH7-QPL9-3N4M', 'Cereal', 3.46);");
      //sql.executeUpdate("INSERT INTO ITEMLIST (CODE, NAME, PRICE) " + "VALUES ('65P1-UDGM-XH2M-LQW2', 'Tuna', 5.89);");
      
      ResultSet rs = sql.executeQuery( "SELECT * FROM ITEMLIST;" );
      
      if (!rs.isBeforeFirst() ) 
    	  System.out.println("No data");  
      else
    	  System.out.println("Data exists");
      
      System.out.println(!rs.isBeforeFirst() ? "No Data" : "Yes Data");
      
      while ( rs.next() ) {
         String code = rs.getString("CODE");
         String  name = rs.getString("NAME");
         double price  = rs.getDouble("PRICE");
         System.out.println( "Code = " + code );
         System.out.println( "Name = " + name );
         System.out.println( "Price = " + price );
         System.out.println();
      }     
      if (!rs.isBeforeFirst() ) {    
    	  System.out.println("No data"); 
      } 
      
      rs = sql.executeQuery( "SELECT PRICE FROM ITEMLIST WHERE CODE=" + "'65P1-UDGM-XH2M-LQW2'" + ";" );
      if (!rs.isBeforeFirst() ) {    
    	  System.out.println("No data"); 
      } 
      while ( rs.next() ) {
          double price  = rs.getDouble("PRICE");
          System.out.println( "Price = " + price );
          System.out.println();
       }
      
      sql.close();
      db.close();
      
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Opened database successfully");
    
    
    
    
    
    
  }
}
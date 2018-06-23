package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Scanner;

import com.mysql.jdbc.StringUtils;

import ims.DataConverter;
import jdk.nashorn.api.scripting.ScriptUtils;

/*
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 * 15 methods in total, add more if required.
 * Donot change any method signatures or the package name.
 * 
 */

public class InvoiceData {

	/**
	 * 1. Method that removes every person record from the database
	 */
	public static void removeAllPersons() {
		Connection conn = null;
		PreparedStatement ps = null;
		conn = ConnectionFactory.getOne();
		//delete personID fk from Invoices, Emails, and Customers
		String query1 = "DELETE FROM Emails";
		String query2 = "DELETE FROM Invoices";
		String query3 = "DELETE FROM Customers";
		String query4 = "DELETE FROM Persons";
		try {
			ps = conn.prepareStatement(query1);
			ps.executeUpdate();
			ps = conn.prepareStatement(query2);
			ps.executeUpdate();
			ps = conn.prepareStatement(query3);
			ps.executeUpdate();
			ps = conn.prepareStatement(query4);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 2. Method to add a person record to the database with the provided data.
	 * 
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country) {
		Connection conn = null;
		PreparedStatement ps = null;
		int addressID = 0;
		
		conn = ConnectionFactory.getOne();
		//assume state country has been added already just get the stateCountryID
		int stateCountryID = InvoiceData.getStateCountryID(state);
		String query = "INSERT INTO Persons (personCode, lastName, firstName, addressID)"
				+ "VALUES (?, ?, ?, ?)";
		
		// check if the address already exists, if not, then add.
		int existAddress = InvoiceData.getAddressID(street, city, stateCountryID, zip);
		
		//add new Address if does not exist already
		if(existAddress != 0) {
			 addressID =  existAddress;
		}else {
			InvoiceData.addAddress(street, city, stateCountryID, zip);
			addressID=InvoiceData.getAddressID(street, city, stateCountryID, zip);
		}
		
		try {
		
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.setString(2, lastName);
			ps.setString(3, firstName);
			ps.setInt(4, addressID);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 3. Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		Connection conn = null;
		PreparedStatement ps = null;
		conn = ConnectionFactory.getOne();
		String getPersonID = "(SELECT id FROM Persons WHERE personCode = ?)"; 
		String addEmail = "INSERT INTO Emails (personID, email) VALUES (" + getPersonID + ", ?)";
		try {
			ps = conn.prepareStatement(addEmail);
			ps.setString(1, personCode);
			ps.setString(2, email);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 4. Method that removes every customer record from the database
	 */
	public static void removeAllCustomers() {
		Connection conn = null;
		PreparedStatement ps = null;
		conn = ConnectionFactory.getOne();
		String deleteInvoices = "DELETE FROM Invoices";
		String deleteCustomers = "DELETE FROM Customers";
		try {
			ps = conn.prepareStatement(deleteInvoices);
			ps.executeUpdate();
			ps = conn.prepareStatement(deleteCustomers);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * adds customer and corresponding primary contact and address
	 */
	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,String name, String street, String city, String state, String zip, String country) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet addressRs = null;
		int addressID = 0;
		conn = ConnectionFactory.getOne();
		String getPrimaryContactID = "(SELECT id FROM Persons WHERE personCode = ?)";
		//assume state country has been added already just get the stateCountryID
		int stateCountryID = InvoiceData.getStateCountryID(state);
		//InvoiceData.addAddress(street, city, stateCountryID, zip);
		String insertCustomer = "INSERT INTO Customers (customerCode, customerName, customerType, primaryContactID, addressID) VALUES (?, ?, ?, " + getPrimaryContactID + ", ?)";
		
		try {
			int existAddress = InvoiceData.getAddressID(street, city, stateCountryID, zip);
			
			//add new Address if it does not exist already
			if(existAddress != 0) {
				 addressID=  existAddress;
			}else {
				InvoiceData.addAddress(street, city, stateCountryID, zip);
				addressID=InvoiceData.getAddressID(street, city, stateCountryID, zip);
			}
			
			
			ps = conn.prepareStatement(insertCustomer);
			ps.setString(1, customerCode);
			ps.setString(2, name);
			ps.setString(3, customerType);
			ps.setString(4, primaryContactPersonCode);
			ps.setInt(5, addressID);
			
			ps.executeUpdate();
			
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 5. Removes all product records from the database
	 */
	public static void removeAllProducts() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = ConnectionFactory.getOne();
		String query1 = "DELETE FROM MovieTickets";
		String query2 = "DELETE FROM SeasonPasses";
		String query3 = "DELETE FROM Refreshments";
		String query4 = "DELETE FROM ParkingPasses";
		String query5 = "DELETE FROM InvoiceProducts";
		String query6 = "DELETE FROM Products";
		
		try{
			ps = conn.prepareStatement(query1);
			rs = ps.executeQuery();
			ps = conn.prepareStatement(query2);
			rs = ps.executeQuery();
			ps = conn.prepareStatement(query3);
			rs = ps.executeQuery();
			ps = conn.prepareStatement(query4);
			rs = ps.executeQuery();
			ps = conn.prepareStatement(query5);
			rs = ps.executeQuery();
			ps = conn.prepareStatement(query6);
			rs = ps.executeQuery();
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 6. Adds an movieTicket record to the database with the provided data.
	 */
	public static void addMovieTicket(String productCode, String dateTime, String movieName, String street, String city,String state, String zip, String country, String screenNo, double pricePerUnit) {
		Connection conn = null;
		PreparedStatement ps = null;
		InvoiceData.addProduct(productCode, "M");
		// get ID from product just added
		int productID = InvoiceData.getProductID(productCode);
		conn = ConnectionFactory.getOne();	
		int stateCountryID = InvoiceData.getStateCountryID(state);
		// add the address
		InvoiceData.addAddress(street, city, stateCountryID, zip);
		// get ID from address
		int addressID = InvoiceData.getAddressID(street, city, stateCountryID, zip);
		String insertMovieTicket = "INSERT INTO MovieTickets (dateTime, movieName, addressID, screenNumber, pricePerUnit, productID) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		try {
			ps = conn.prepareStatement(insertMovieTicket);
			ps.setTimestamp(1, Timestamp.valueOf(dateTime+":00"));
			ps.setString(2, movieName);
			ps.setInt(3, addressID);
			ps.setString(4, screenNo);
			ps.setFloat(5, (float) pricePerUnit);
			ps.setInt(6, productID);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
			
		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	/**
	 * 7. Adds a seasonPass record to the database with the provided data.
	 */
	public static void addSeasonPass(String productCode, String name, String seasonStartDate, String seasonEndDate,	double cost) {
		Connection conn = null;
		PreparedStatement ps = null;
		InvoiceData.addProduct(productCode, "S");
		int productID = InvoiceData.getProductID(productCode);
		conn = ConnectionFactory.getOne();
		
		String insertSeasonPass = "INSERT INTO SeasonPasses (name, startDate, endDate, cost, productID)"
				+ "VALUES (?, ?, ?, ?, ?)";
		try {
			ps = conn.prepareStatement(insertSeasonPass);
			ps.setString(1, name);
			ps.setDate(2, Date.valueOf(seasonStartDate)); 
			ps.setDate(3, Date.valueOf(seasonEndDate));
			ps.setFloat(4, (float) cost);
			ps.setInt(5, productID);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
}

	/**
	 * 8. Adds a ParkingPass record to the database with the provided data.
	 */
	public static void addParkingPass(String productCode, double parkingFee) {
		String createProductQ = "INSERT INTO Products(productCode,productType) VALUES (?,'P');";
		//LAST_INSERT_ID() refer to id that you just add to any of your table
		String createPQ = "INSERT INTO ParkingPasses(fee,productID) VALUES (?,(SELECT LAST_INSERT_ID()));";
		
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		ResultSet rs= null;
		
		try {
			// create a Product
			ps = conn.prepareStatement(createProductQ);
			ps.setString(1, productCode);
			ps.executeUpdate();
			
			
			//create a parking pass.
			ps = conn.prepareStatement(createPQ);
			ps.setDouble(1, parkingFee);
			ps.executeUpdate();
			ConnectionFactory.release(conn);
			ps.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 9. Adds a refreshment record to the database with the provided data.
	 */
	public static void addRefreshment(String productCode, String name, double cost) {
		String createProductQ = "INSERT INTO Products(productCode,productType) VALUES (?,'R');";
		
		//LAST_INSERT_ID() refer to id that you just add to any of your table
		String createRefreshmentQ = "INSERT INTO Refreshments(name,cost,productID) VALUES (?,?,(SELECT LAST_INSERT_ID()));";
		
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		ResultSet rs= null;
		
		try {
			// create a Product
			ps = conn.prepareStatement(createProductQ);
			ps.setString(1, productCode);
			ps.executeUpdate();
			
			
			//create a parking pass.
			ps = conn.prepareStatement(createRefreshmentQ);
			ps.setString(1, name);
			ps.setDouble(2, cost);
			ps.executeUpdate();
			
			ConnectionFactory.release(conn);
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 10. Removes all invoice records from the database
	 * Delete all Product associated to the invoice in InvoiceProduct table as well.
	 */
	public static void removeAllInvoices() {
		
		String dropInvoiceProductQ = "DELETE FROM `InvoiceProducts`";
		String dropInvoiceQ = "DELETE FROM `Invoices`;";
		
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		
		try {
			// drop InvoiceProduct table
			ps = conn.prepareStatement(dropInvoiceProductQ);
			ps.executeUpdate();
			
			
			//drop Invoice Table
			ps = conn.prepareStatement(dropInvoiceQ);
			ps.executeUpdate();
			
			ConnectionFactory.release(conn);
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 11. Adds an invoice record to the database with the given data.
	 * 
	 **/
	public static void addInvoice(String invoiceCode, String customerCode, String salesPersonCode, String invoiceDate) {
		//query to create new invoice to database 
		String getCustomerIDQ = "(SELECT id FROM Customers c WHERE c.customerCode=?)";
		String getSalesPersonIDQ = "(SELECT id FROM Persons p WHERE p.personCode=?)";	
		
		String createInvoiceQ = String.format("INSERT INTO Invoices(invoiceCode,customerID,salesPersonID,invoiceDate) VALUES(?,(%s),(%s),?);", 
				getCustomerIDQ,getSalesPersonIDQ);
		
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		
		try {
			// create new row of invoice
			ps = conn.prepareStatement(createInvoiceQ);
			ps.setString(1, invoiceCode);
			ps.setString(2, customerCode);
			ps.setString(3, salesPersonCode);
			ps.setString(4, invoiceDate);
			ps.executeUpdate();

			
			ConnectionFactory.release(conn);
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 12. Adds a particular movieticket (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of units
	 */

	public static void addMovieTicketToInvoice(String invoiceCode, String productCode, int quantity) {
		//query to create new invoice product to database 
		String getInvoiceIDQ = "(SELECT id FROM Invoices i WHERE i.invoiceCode=?)";
		String getProductIDQ = "(SELECT id FROM Products p WHERE p.productCode=?)";	
		String addMovieToInvoiceQ = "INSERT INTO InvoiceProducts(invoiceID,productID,unit) VALUES ("+getInvoiceIDQ+","+getProductIDQ+",?)";

		int invoiceID = 0;
		int productId = 0;
		
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null; 
		ResultSet rs = null;
		
		try {
			// create new row of invoice product
			ps = conn.prepareStatement(addMovieToInvoiceQ);
			ps.setString(1, invoiceCode);
			ps.setString(2, productCode);
			ps.setInt(3, quantity);
			ps.executeUpdate();

			
			ConnectionFactory.release(conn);
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 13. Adds a particular seasonpass (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given begin/end dates
	 */
	public static void addSeasonPassToInvoice(String invoiceCode, String productCode, int quantity) {
		//query to create new invoice product to database 
		String getInvoiceIDQ = "(SELECT id FROM Invoices i WHERE i.invoiceCode=?)";
		String getProductIDQ = "(SELECT id FROM Products p WHERE p.productCode=?)";	
		String addSeasonPassToInvoiceQ = "INSERT INTO InvoiceProducts(invoiceID,productID,unit) VALUES ("+getInvoiceIDQ+","+getProductIDQ+",?)";

		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null; 
		ResultSet rs = null;
		
		try {
			// create new row of invoice product
			ps = conn.prepareStatement(addSeasonPassToInvoiceQ);
			ps.setString(1, invoiceCode);
			ps.setString(2, productCode);
			ps.setInt(3, quantity);
			ps.executeUpdate();

			
			ConnectionFactory.release(conn);
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

     /**
     * 14. Adds a particular ParkingPass (corresponding to <code>productCode</code> to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity.
     * NOTE: ticketCode may be null
     */
    public static void addParkingPassToInvoice(String invoiceCode, String productCode, int quantity, String ticketCode) {
    		//query to create new invoice product to database 
		String getInvoiceIDQ = "(SELECT id FROM Invoices i WHERE i.invoiceCode=?)";
		String getProductIDQ = "(SELECT id FROM Products p WHERE p.productCode=?)";	
		String addSeasonPassToInvoiceQ = "INSERT INTO InvoiceProducts(invoiceID,productID,unit,note) VALUES ("+getInvoiceIDQ+","+getProductIDQ+",?,?)";

		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null; 
		ResultSet rs = null;
		
		try {
			// create new row of invoice product
			ps = conn.prepareStatement(addSeasonPassToInvoiceQ);
			ps.setString(1, invoiceCode);
			ps.setString(2, productCode);
			ps.setInt(3, quantity);
			ps.setString(4, ticketCode);
			ps.executeUpdate();

			
			ConnectionFactory.release(conn);
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
    /**
     * 15. Adds a particular refreshment (corresponding to <code>productCode</code> to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity. 
     */
    public static void addRefreshmentToInvoice(String invoiceCode, String productCode, int quantity) {
    		//query to create new invoice product to database 
		String getInvoiceIDQ = "(SELECT id FROM Invoices i WHERE i.invoiceCode=?)";
		String getProductIDQ = "(SELECT id FROM Products p WHERE p.productCode=?)";	
		String addSeasonPassToInvoiceQ = "INSERT INTO InvoiceProducts(invoiceID,productID,unit) VALUES ("+getInvoiceIDQ+","+getProductIDQ+",?)";

		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null; 
		
		try {
			// create new row of invoice
			ps = conn.prepareStatement(addSeasonPassToInvoiceQ);
			ps.setString(1, invoiceCode);
			ps.setString(2, productCode);
			ps.setInt(3, quantity);
			ps.executeUpdate();

			
			ConnectionFactory.release(conn);
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    /***********************************************************************************************************/
    /***********************************************Additional Functions*****************************************/
    /***********************************************************************************************************/
    
    
    /**
     * adds an invoice product
     * @param invoiceCode
     * @param productCode
     * @param units
     */
    public static void addInvoiceProduct(String invoiceCode, String productCode, int units) {
    	Connection conn = null;
    	PreparedStatement ps = null;
    	conn = ConnectionFactory.getOne();
    	String selectInvoiceID = "(SELECT id from Invoices WHERE invoiceCode = ?)";
    	String selectProductID = "(SELECT id from Products WHERE productCode = ?)";
    	String insertIP = "INSERT INTO InvoiceProducts (invoiceID, productID, unit) "
    			+ "VALUES (" + selectInvoiceID + ", " + selectProductID + ", ?)";
    	try {
    		ps = conn.prepareStatement(insertIP);
    		ps.setString(1, invoiceCode);
    		ps.setString(2, productCode);
    		ps.setInt(3, units);
    		ps.executeUpdate();
    		
    		conn.close();
    		ps.close();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
    * adds an invoice product, overloaded method including note (associated ticket)
    * @param invoiceCode
    * @param productCode
    * @param units
    * @param note associated ticket with product
    *
    */
    public static void addInvoiceProduct(String invoiceCode, String productCode, int units, String note) {
      	Connection conn = null;
    	PreparedStatement ps = null;
    	conn = ConnectionFactory.getOne();
    	String selectInvoiceID = "(SELECT id from Invoices WHERE invoiceCode = ?)";
    	String selectProductID = "(SELECT id from Products WHERE productCode = ?)";
    	String insertIP = "INSERT INTO InvoiceProducts (invoiceID, productID, unit, note) "
    			+ "VALUES (" + selectInvoiceID + ", " + selectProductID + ", ?, ?)";
    	try {
    		ps = conn.prepareStatement(insertIP);
    		ps.setString(1, invoiceCode);
    		ps.setString(2, productCode);
    		ps.setInt(3, units);
    		ps.setString(4, note);
    		ps.executeUpdate();
    		
    		conn.close();
    		ps.close();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    /*
    * adds an address 
    * @param street
    * @param city
    * @param stateCountryID
    * @param zipcode
    */
    public static void addAddress(String street, String city, int stateCountryID, String zipcode) {
    	Connection conn = null;
		  PreparedStatement ps = null;
		  conn = ConnectionFactory.getOne();
		  String query = "INSERT INTO Addresses (street, city, stateCountryID, zipcode) VALUES (?, ?, ?, ?)";
		  try {
			  ps = conn.prepareStatement(query);
			  ps.setString(1, street);
			  ps.setString(2, city);
			  ps.setInt(3, stateCountryID);
			  ps.setString(4, zipcode);
			  ps.executeUpdate();
			
			  ps.close();
			  conn.close();
		  } catch (SQLException e) {
			  e.printStackTrace();
		}
    }
    
    /*
    * adds a product
    * @param productCode
    * @param productType
    */
    public static void addProduct(String productCode, String productType) {
    		  Connection conn = null;
		  PreparedStatement ps = null;
		  conn = ConnectionFactory.getOne();
		  String insertProduct = "INSERT INTO Products (productCode, productType) "
				+ "VALUES (?, ?)";
		  try{
			  ps = conn.prepareStatement(insertProduct);
			  ps.setString(1, productCode);
			  ps.setString(2, productType);
			  ps.executeUpdate();
			
			  ps.close();
			  conn.close();
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
				
    }
    
    /**
    * Gets the primary key, id, from the Countries table 
    * @param country the country to be searched for 
    *
    */ 
    public static int getCountryID(String country) {
    		  Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
		  conn = ConnectionFactory.getOne();
		  int countryID = 0;
		  String query = "SELECT id FROM Countries WHERE name = ?";
		  try{
			  ps = conn.prepareStatement(query);
			  ps.setString(1, country);
			  rs = ps.executeQuery();
			  if(rs.next()) {
				  countryID = rs.getInt("id");
			  }
			  rs.close();
			  ps.close();
			  conn.close();
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
		  return countryID;
    }
    
    /**
    * Gets the primary key, id, from the StateCountries table 
    * @param stateCountry the stateCountry to be searched for 
    *
    */ 
    public static int getStateCountryID(String stateCountry) {
    	          Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
		  conn = ConnectionFactory.getOne();
		  int stateCountryID = 0;
		  String query = "SELECT s.id FROM StateCountries s WHERE name = ?";
		  try{
			  ps = conn.prepareStatement(query);
			  ps.setString(1, stateCountry);
			  rs = ps.executeQuery();
			  if(rs.next()) {
				  stateCountryID = rs.getInt("id");
			  }
			 
			  rs.close();
			  ps.close();
			  conn.close();
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
		  return stateCountryID;
    }
    
    /**
    * Gets the primary key, id, from the Addresses table 
    * @param street
    * @param city
    * @param stateCountryID
    * @param zipcode
    *
    */ 
    public static int getAddressID(String street, String city, int stateCountryID, String zipcode) {
    	Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
		  conn = ConnectionFactory.getOne();
		  int addressID = 0;
		  String query = "SELECT id FROM Addresses WHERE street = ? AND city = ? AND stateCountryID = ? AND zipcode = ?";
		  try{
			  ps = conn.prepareStatement(query);
			  ps.setString(1, street);
			  ps.setString(2, city);
			  ps.setInt(3, stateCountryID);
			  ps.setString(4, zipcode);
			  rs = ps.executeQuery();
			  if(rs.next()) {
				  addressID = rs.getInt("id");
			  }
			  rs.close();
			  ps.close();
			  conn.close();
		  } catch (SQLException e) {
			    e.printStackTrace();
		  }
		  return addressID;
    }
    
    /**
    * Gets the primary key, id, from the Products table 
    * @param productCode the product to search for 
    */
    public static int getProductID(String productCode) {
    	Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
		  conn = ConnectionFactory.getOne();
		  int productID = 0;
		  String query = "SELECT id FROM Products WHERE productCode = ?";
		  try{
			  ps = conn.prepareStatement(query);
			  ps.setString(1, productCode);
			  rs = ps.executeQuery();
			  if(rs.next()) {
				  productID = rs.getInt("id");
			  }
			  rs.close();
			  ps.close();
			  conn.close();
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
		  return productID;
    }
   
    
    /**
     * This Method deletes all data that currently exists in database table
     */
    public static void deleteDatabaseTable() {
    	String deleteT1 = "DELETE FROM `InvoiceProducts`;";
		String deleteT2 = "DELETE FROM `Invoices`;";
    	String deleteT3 = "DELETE FROM `Refreshments`;";
		String deleteT4 = "DELETE FROM `ParkingPasses`;";
    	String deleteT5 = "DELETE FROM `MovieTickets`;";
		String deleteT6 = "DELETE FROM `SeasonPasses`;";
    	String deleteT7 = "DELETE FROM `Products`;";
		String deleteT8 = "DELETE FROM `Customers`;";
    	String deleteT9 = "DELETE FROM `Emails`;";
		String deleteT10 = "DELETE FROM `Persons`;";
    	String deleteT11 = "DELETE FROM `Addresses`;";
		String deleteT12 = "DELETE FROM `StateCountries`;";
		String deleteT13 = "DELETE FROM `Countries`;";
		
		
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null;
		
		try {
			// delete table- execute query
			ps = conn.prepareStatement(deleteT1);
			ps.executeUpdate();

			ps = conn.prepareStatement(deleteT2);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(deleteT3);
			ps.executeUpdate();

			ps = conn.prepareStatement(deleteT4);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(deleteT5);
			ps.executeUpdate();

			ps = conn.prepareStatement(deleteT6);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(deleteT7);
			ps.executeUpdate();

			ps = conn.prepareStatement(deleteT8);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(deleteT9);
			ps.executeUpdate();

			ps = conn.prepareStatement(deleteT10);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(deleteT11);
			ps.executeUpdate();

			ps = conn.prepareStatement(deleteT12);
			ps.executeUpdate();
			
			ps = conn.prepareStatement(deleteT13);
			ps.executeUpdate();
			
			ConnectionFactory.release(conn);
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * This method execute a query script using ScriptUtil class
     * https://stackoverflow.com/questions/30732314/execute-sql-file-from-spring-jdbc-template
     * @param filePath
     */
    public static void executeSqlScript(String filePath) {
    	Scanner scan = null;
        String delimiter = ";";
    
    	// open and read file
    	scan = DataConverter.openFile(filePath);
    	
		PreparedStatement ps = null; 
		Connection conn = null;
		Statement statement = null;
    	StringBuilder sqlScript = new StringBuilder();
    	
    	int counter = 0;
		try {
		
			String line = null;
			
			while(scan.hasNext()) {
				line = scan.nextLine();
				// skip comment or blank line
				if(line.length() == 0 || line.equals("") || line.charAt(0)=='-' || 
						line.charAt(1) == '-' || line == null || line.equals("\n")
						|| line.isEmpty()) {
					continue;
				}
				sqlScript.append(line);	
			}
			String queries[] = sqlScript.toString().split(delimiter);
			conn = ConnectionFactory.getOne();
			String s = null;
			for(int i = 0;i < queries.length;i++) {
				s = queries[i]+delimiter;
				ps = conn.prepareStatement(s);
				ps.executeUpdate();
			}
		
			// create new row of invoice
			
			
			ps.close();
			
			ConnectionFactory.release(conn);
			
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}// end cathc 
    	
    }// end exectuteSQlScript()
    

}

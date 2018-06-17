package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	public static void removeAllPersons() {}

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
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country) {}

	/**
	 * 3. Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {}

	/**
	 * 4. Method that removes every customer record from the database
	 */
	public static void removeAllCustomers() {}

	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,String name, String street, String city, String state, String zip, String country) {}
	
	/**
	 * 5. Removes all product records from the database
	 */
	public static void removeAllProducts() {}

	/**
	 * 6. Adds an movieTicket record to the database with the provided data.
	 */
	public static void addMovieTicket(String productCode, String dateTime, String movieName, String street, String city,String state, String zip, String country, String screenNo, double pricePerUnit) {}

	/**
	 * 7. Adds a seasonPass record to the database with the provided data.
	 */
	public static void addSeasonPass(String productCode, String name, String seasonStartDate, String seasonEndDate,	double cost) {}

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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 11. Adds an invoice record to the database with the given data.
	 ***********************Error ******************
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * 12. Adds a particular movieticket (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of units
	 */

	public static void addMovieTicketToInvoice(String invoiceCode, String productCode, int quantity) {
		//query to create new invoice to database 
		String getInvoiceIDQ = "(SELECT id FROM Invoices i WHERE i.invoiceCode=?)";
		String getProductIDQ = "(SELECT id FROM Products p WHERE p.productCode=?)";	
		String addMovieToInvoiceQ = "INSERT INTO InvoiceProducts(invoiceID,productID,unit) VALUES ("+getInvoiceIDQ+","+getProductIDQ+",?)";

		int invoiceID = 0;
		int productId = 0;
		
		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null; 
		ResultSet rs = null;
		
		try {
			// create new row of invoice
			ps = conn.prepareStatement(addMovieToInvoiceQ);
			ps.setString(1, invoiceCode);
			ps.setString(2, productCode);
			ps.setInt(3, quantity);
			ps.executeUpdate();

			
			ConnectionFactory.release(conn);
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 13. Adds a particular seasonpass (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given begin/end dates
	 */
	public static void addSeasonPassToInvoice(String invoiceCode, String productCode, int quantity) {
		//query to create new invoice to database 
		String getInvoiceIDQ = "(SELECT id FROM Invoices i WHERE i.invoiceCode=?)";
		String getProductIDQ = "(SELECT id FROM Products p WHERE p.productCode=?)";	
		String addSeasonPassToInvoiceQ = "INSERT INTO InvoiceProducts(invoiceID,productID,unit) VALUES ("+getInvoiceIDQ+","+getProductIDQ+",?)";

		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null; 
		ResultSet rs = null;
		
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
			// TODO Auto-generated catch block
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
    	//query to create new invoice to database 
		String getInvoiceIDQ = "(SELECT id FROM Invoices i WHERE i.invoiceCode=?)";
		String getProductIDQ = "(SELECT id FROM Products p WHERE p.productCode=?)";	
		String addSeasonPassToInvoiceQ = "INSERT INTO InvoiceProducts(invoiceID,productID,unit,note) VALUES ("+getInvoiceIDQ+","+getProductIDQ+",?,?)";

		Connection conn = ConnectionFactory.getOne();
		PreparedStatement ps = null; 
		ResultSet rs = null;
		
		try {
			// create new row of invoice
			ps = conn.prepareStatement(addSeasonPassToInvoiceQ);
			ps.setString(1, invoiceCode);
			ps.setString(2, productCode);
			ps.setInt(3, quantity);
			ps.setString(4, ticketCode);
			ps.executeUpdate();

			
			ConnectionFactory.release(conn);
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    /**
     * 15. Adds a particular refreshment (corresponding to <code>productCode</code> to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity. 
     */
    public static void addRefreshmentToInvoice(String invoiceCode, String productCode, int quantity) {
    	//query to create new invoice to database 
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

}

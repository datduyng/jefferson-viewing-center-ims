package com.jvc.ext;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import customer.Customer;
import customer.General;
import customer.Student;
import ims.Address;
import ims.DataConverter;
import ims.Invoice;
import ims.Person;
import product.MovieTicket;
import product.ParkingPass;
import product.Product;
import product.Refreshment;
import product.SeasonPass;

public class ProcessDatabase {
	
	private static final Logger LOGGER = ProcessDatabase.createLogger();
	
	/**
	 * Creates a Logger object to log database errors to a file.
	 * @return
	 */
	public static final Logger createLogger() {
		Logger logger = Logger.getLogger(ProcessDatabase.class.getName());
		try {
			FileHandler fh = new FileHandler();
			fh.setFormatter(new SimpleFormatter());
			logger.addHandler(fh);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return logger;
	}

	/** 
	 * This method retrieve Person data from Database then 
	 * create Object according to attribute
	 */
	public static void toPersonObject() {
		
		// queries 
		String getPersonQuery = "SELECT * FROM Persons";
		String getEmailQuery = "SELECT (email) FROM Emails e WHERE e.personID=?;";
		
		// Create connection and prepare Sql statement 
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet personRs = null;
		ResultSet emailRs = null;

		// result
		Person p = null;
		Address a = null;
		String personCode=null, lastName=null, firstName=null;
	
		try {	
			conn = ConnectionFactory.getOne();
			ps = conn.prepareStatement(getPersonQuery);
			personRs = ps.executeQuery();
			while(personRs.next()) {
				personCode = personRs.getString("personCode");
				lastName = personRs.getString("lastName");
				firstName = personRs.getString("firstName");
				int addressID = personRs.getInt("addressID");
				int personID = personRs.getInt("id");
				
				a = ProcessDatabase.toAddressObjectFromDB(addressID);
				// create a set to store email and deposite to create an object 
				ArrayList<String> emails = new ArrayList<String>();
				String email = null;
				//seperate query to get email Address 
				ps = conn.prepareStatement(getEmailQuery);
				ps.setInt(1, personID);
				emailRs = ps.executeQuery();
				while(emailRs.next()) {
					email = emailRs.getString("email");
					emails.add(email);
				}
				
				//create a person Object 
				//Person(String personCode, String lastName, String firstName, Address address, Set<String> emails)
				p = new Person(personCode,lastName,firstName,a,emails);
				
				//add to Person list 
				DataConverter.getPersons().add(p);
			}
		}catch(SQLException e) {
			//log error to logger
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
		
	}
	public static void toCustomerObjectFromDB() {
		
		//	public Customer(String customerCode, String customerName, String customerType, String personCode, Address customerAddress) {
		final String getPersonCodeQ = "SELECT personCode FROM Persons p "
							+ "JOIN Customers c ON p.id=c.primaryContactID WHERE c.id=?";
		final String getCustomerInfoQ = "SELECT * FROM Customers";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet personRs = null;
		ResultSet customerRs = null;
		
		Address a = null;
		Customer c = null;
		int customerID = 0,addressID = 0;
		String customerCode = null,customerName = null,customerType = null,personCode=null;
		
		try {
			conn = ConnectionFactory.getOne();
			
			// get data from Customer DB table 
			ps = conn.prepareStatement(getCustomerInfoQ);
			customerRs = ps.executeQuery();
			//process data from table
			while(customerRs.next()) {
				customerCode = customerRs.getString("customerCode");
				customerName = customerRs.getString("customerName");
				customerType = customerRs.getString("customerType");
				customerID = customerRs.getInt("id");
				addressID = customerRs.getInt("addressID");
			
				//get personCode 
				ps = conn.prepareStatement(getPersonCodeQ);
				ps.setInt(1, customerID);
				personRs = ps.executeQuery();
				if(personRs.next()) {
					personCode = personRs.getString("personCode");
				}
				//get Address 
				a = ProcessDatabase.toAddressObjectFromDB(addressID);
				
				if(customerType.equalsIgnoreCase("S")||customerType.equals("Student")) {
					c = new Student(customerCode,customerName,personCode,a);
				}else if(customerType.equalsIgnoreCase("G")||customerType.equals("General")){
					c = new General(customerCode,customerName,personCode,a);
				}

				// add customer to LIst 
				DataConverter.getCustomers().add(c);
				
				//retrieve data from persons Table DB 
			}// end while
		}catch(SQLException e) {
			//log error to logger
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	public static Address toAddressObjectFromDB(int addressID) {
		final String getAddressQuery = "SELECT * FROM Addresses WHERE id=?;";
		final String getStateQuery = "SELECT name,countryID FROM StateCountries WHERE id=?;";
		final String getCountryQuery = "SELECT Countries.name FROM Countries WHERE id=?;";
	
		// Create connection and prepare Sql statement 
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet addressRs = null;
		ResultSet stateRs = null;
		ResultSet countryRs = null;
		
		Address a = null;
		String street=null,city=null, state = null, country=null, zipcode = null;
		
		try {	
			conn = ConnectionFactory.getOne();
			ps = conn.prepareStatement(getAddressQuery);
			ps.setInt(1, addressID);
			addressRs = ps.executeQuery();
			
			//retrieve data from database
			while(addressRs.next()) {
				street = addressRs.getString("street");
				city = addressRs.getString("city");
				zipcode = addressRs.getString("zipcode");
				int stateCountryID = addressRs.getInt("stateCountryID");
				
				// inner query to get state country
				// restate the prepared statement
				ps = conn.prepareStatement(getStateQuery);
				ps.setInt(1, stateCountryID);
				stateRs = ps.executeQuery();
				while(stateRs.next()) {
					state = stateRs.getString("name");
					int countryID = stateRs.getInt("countryID");
					
					//innner oiceData.executeSqlScript("assignment04/createDatabaseTable.sql");query to get country attribute 
					// restate the preparedStatement 
					ps = conn.prepareStatement(getCountryQuery);
					ps.setInt(1, countryID);
					countryRs = ps.executeQuery();
					while(countryRs.next()) {
						country = countryRs.getString("name");
					}// end stateRs.next() loop 
					
				}// end stateRs.next( lop 
				a = new Address(street,city,state,country,zipcode);
			
			}// end addressRs.next() while loop
			ps.close();
			conn.close();
		}catch(SQLException e) {
			//log error to logger
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
		return a;
	}
	
	public static void toInvoiceObjectFromDB() {
		//	public Invoice(String invoiceCode,String customerCode,String salePersonCode,String invoiceDate,HashMap<Product,Integer> productList) {
		/*
		+----+-------------+------------+---------------+-------------+
		| id | invoiceCode | customerID | salesPersonID | invoiceDate |
		+----+-------------+------------+---------------+-------------+
		|  1 | INV001      |          1 |            16 | 2016-09-03  |
		|  2 | INV002      |          2 |            17 | 2016-11-10  |
		|  3 | INV003      |          5 |            18 | 2016-11-26  |
		|  4 | INV004      |          3 |            12 | 2016-10-16  |
		+----+-------------+------------+---------------+-------------+
 		*/
		final String getInvoiceQ = "SELECT * FROM Invoices i;";
		final String getCustomerCodeQ = "SELECT customerCode FROM Customers c JOIN Invoices i ON c.id=i.customerID WHERE i.id=?;";
		final String getSalePersonCodeQ = "SELECT personCode FROM Persons p JOIN Invoices i ON p.id=i.salesPersonID WHERE i.id=?;";
		final String getInvoiceProductQ = "SELECT * FROM InvoiceProducts ip WHERE ip.invoiceID=?;";
		final String getProductCode = "SELECT productCode FROM Products p WHERE p.id=?;";
		
		//create conn and sql Prepared statement 
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet customerRs = null;
		ResultSet personRs = null;
		ResultSet invoiceProductRs = null;
		ResultSet productRs = null;
		ResultSet invoiceRs = null;
		
		String invoiceCode=null,customerCode=null,salesPersonCode=null,invoiceDate=null;
		String productCode=null,note = null;
		int unit=0;
		int customerID = 0,salesPersonID= 0,invoiceID = 0,productID=0;
		LinkedHashMap<Product,Integer> productList = null;
		Product p =null;
		Invoice i = null;
		//retrieve data from DB table
		try {
			conn = ConnectionFactory.getOne();
			
			//get data from Invoice DB table 
			ps = conn.prepareStatement(getInvoiceQ);
			invoiceRs = ps.executeQuery();
			while(invoiceRs.next()) {
				invoiceID = invoiceRs.getInt("id");
				invoiceCode = invoiceRs.getString("invoiceCode");
				invoiceDate = invoiceRs.getString("invoiceDate");
				customerID = invoiceRs.getInt("customerID");
				salesPersonID=invoiceRs.getInt("salesPersonID");
				
				//get Person Code from personID 
				ps = conn.prepareStatement(getSalePersonCodeQ);
				ps.setInt(1, invoiceID);
				personRs = ps.executeQuery();
				if(personRs.next()) {
					salesPersonCode = personRs.getString("personCode");
				}
				
				//get customer Code from customerID 
				ps = conn.prepareStatement(getCustomerCodeQ);
				ps.setInt(1, invoiceID);
				customerRs = ps.executeQuery();
				if(customerRs.next()) {
					customerCode = customerRs.getString("customerCode");
				}
				
				//get productList for an invoice.
				productList = new LinkedHashMap<Product,Integer>();
				ps = conn.prepareStatement(getInvoiceProductQ);
				ps.setInt(1, invoiceID);
				invoiceProductRs = ps.executeQuery();
				while(invoiceProductRs.next()) {
					productID = invoiceProductRs.getInt("productID");
					unit = invoiceProductRs.getInt("unit");
					note = invoiceProductRs.getString("note");
					
					ps = conn.prepareStatement(getProductCode);
					ps.setInt(1, productID);
					productRs = ps.executeQuery();
					if(productRs.next()) {
						productCode = productRs.getString("productCode");
					}

					p = DataConverter.findProduct(productCode, DataConverter.getProducts());
					
					// check if there is ticket associate with tick or not
					if(p instanceof ParkingPass && note!= null) {
						// find associated ticket with parkingpass.
						Product associatedTicket =  DataConverter.findProduct(note,DataConverter.getProducts());
						if(associatedTicket instanceof MovieTicket) {
							// deep copy
							p = new ParkingPass((ParkingPass)p);
							MovieTicket m = (MovieTicket) associatedTicket;
							((ParkingPass)p).setTicket(m);				
						}else if(associatedTicket instanceof SeasonPass) {
							// deep copy
							p = new ParkingPass((ParkingPass)p);
							SeasonPass s = (SeasonPass) associatedTicket;
							((ParkingPass)p).setTicket(s);
						}
						
					}
					//store product into a hashmap
					productList.put(p, unit);
				}
				
				i = new Invoice( invoiceCode, customerCode, salesPersonCode, invoiceDate, productList) ;
				DataConverter.getInvoices().add(i);
			}// end while
			
			 conn.close();
			 ps.close();
			 customerRs.close();
			 personRs.close();
			 invoiceProductRs.close();
			 productRs.close();
			 invoiceRs.close();
			
		}catch(SQLException e) {
			//log error to logger
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}
	

	public static void toMovieTicketObjectFromDB() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = ConnectionFactory.getOne();
		String selectMovieTickets = "SELECT * FROM MovieTickets JOIN Products on MovieTickets.productID = Products.id";
		try {
			ps = conn.prepareStatement(selectMovieTickets);
			rs = ps.executeQuery();
			while(rs.next()) {
				String dateTime = rs.getString("dateTime");
				String movieName = rs.getString("movieName");
				int addressID = rs.getInt("addressID");
				String screenNumber = rs.getString("screenNumber");
				double pricePerUnit = (double) rs.getFloat("pricePerUnit");
				String productCode  = rs.getString("productCode");
				String productType = rs.getString("productType");
				
				Address address = toAddressObjectFromDB(addressID);
				MovieTicket movieTicket = new MovieTicket(productCode, productType, movieName, dateTime, address, screenNumber, pricePerUnit);
				DataConverter.getProducts().add(movieTicket);
			}	
		} catch (SQLException e) {
			//log error to logger
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	public static void toSeasonPassObjectFromDB() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = ConnectionFactory.getOne();
		String selectSeasonPasses = "SELECT * FROM SeasonPasses JOIN Products on SeasonPasses.productID = Products.id";
		try {
			ps = conn.prepareStatement(selectSeasonPasses);
			rs = ps.executeQuery();
			while(rs.next()) {
				String productCode  = rs.getString("productCode");
				String productType = rs.getString("productType");
				String name = rs.getString("name");
				String startDate = rs.getString("startDate");
				String endDate = rs.getString("endDate");
				double cost = (double) rs.getFloat("cost");
				SeasonPass seasonPass = new SeasonPass(productCode, productType, name, startDate, endDate, cost);
				DataConverter.getProducts().add(seasonPass);
			}	
		} catch (SQLException e) {
			//log error to logger
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	public static void toParkingPassObjectFromDB() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = ConnectionFactory.getOne();
		String selectParkingPasses = "SELECT * FROM ParkingPasses JOIN Products on ParkingPasses.productID = Products.id";
		try {
			ps = conn.prepareStatement(selectParkingPasses);
			rs = ps.executeQuery();
			while(rs.next()) {
				String productCode  = rs.getString("productCode");
				String productType = rs.getString("productType");
				double parkingFee = (double) rs.getFloat("fee");
				ParkingPass parkingPass = new ParkingPass(productCode, productType, parkingFee);
				DataConverter.getProducts().add(parkingPass);
			}	
		} catch (SQLException e) {
			//log error to logger
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}
	
	public static void toRefreshmentObjectFromDB() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = ConnectionFactory.getOne();
		String selectRefreshments = "SELECT * FROM Refreshments JOIN Products on Refreshments.productID = Products.id";
		try {
			ps = conn.prepareStatement(selectRefreshments);
			rs = ps.executeQuery();
			while(rs.next()) {
				String productCode  = rs.getString("productCode");
				String productType = rs.getString("productType");
				String name = rs.getString("name");
				double cost = (double) rs.getFloat("cost");				
				Refreshment refreshment = new Refreshment(productCode, productType, name, cost);
				DataConverter.getProducts().add(refreshment);
			}	
		} catch (SQLException e) {
			//log error to logger
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
}
}

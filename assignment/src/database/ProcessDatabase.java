package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import ims.Address;
import ims.Person;

public class ProcessDatabase {
	
	/** 
	 * This method retrieve Person data from Database then 
	 * create Object according to attribute
	 */
	public static void toPersonObject() {
		
		// queries 
		String getPersonQuery = "SELECT * FROM Persons";
		String getEmailQuery = "SELECT (email) FROM Emails e WHERE e.personID=?;";
		String getAddressQuery = "SELECT * FROM Addresses WHERE id=?;";
		String getStateQuery = "SELECT (name,countryID) FROM StateCountries WHERE id=?;";
		String getCountryQuery = "SELECT (Countries.name) FROM Countries WHERE id=?;";
		
		
		// Create connection and prepare Sql statement 
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet personRs = null;
		ResultSet emailRs = null;
		ResultSet addressRs = null;
		ResultSet stateRs = null;
		ResultSet countryRs = null;
		
		// result
		Person p = null;
		Address a = null;
		String personCode=null, lastName=null, firstName=null;
		String street=null,city=null, state = null, country=null, zipcode = null;
		
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
			}
		}catch(SQLException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return a;
	}
	
	
}

package ims;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Person {
	
	private String personCode;
	private String lastName;
	private String firstName;
	private Address address;
	private ArrayList<String> emails;


	
	public Person() {
		// default constructor
	}
	
	public Person(String personCode, String lastName, String firstName, Address address, ArrayList<String> emails) {
		this.setPersonCode(personCode);
		this.setLastName(lastName);
		this.setFirstName(firstName);
		this.setAddress(address);
		this.setEmails(emails);
	}
	
	/**
	 * Parses the next line and sets the appropriate values of
	 * the Person object.
	 * @param line scanned line from flat file to be parsed
	 */
	public void setAttribute(String nextLine) {
		String[] token = nextLine.split(";");
		setPersonCode(token[0]);
		String[] nameToken = token[1].split(",");
		setLastName(nameToken[0]);
		setFirstName(nameToken[1].trim());
		this.address = new Address(token[2]);
		if(token.length == 4) {
			this.emails = new ArrayList<String>();
			String[] emailToken = token[3].split(",");
			for(String stringEmail : emailToken) {
				this.emails.add(stringEmail);
			}
		}
	}// end setAttribute
	
	public String getPersonCode() {
		return this.personCode;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public Address getAddress() {
		return address;
	}

	public ArrayList<String> getEmail() {
		return emails;
	}	


	public void setEmails(ArrayList<String> emails) {
		this.emails = emails;
	}

	public void setPersonCode(String personCode) {
		if(personCode != null && personCode.length() > 0) {
			this.personCode = personCode;
		}else {
			this.personCode = "na";
		}
	}

	public void setLastName(String lastName) {
		if(lastName != null && lastName.length() > 0) {
			this.lastName = lastName;
		}else{
			this.lastName = "na";
		}
	}

	public void setFirstName(String firstName) {
		if(firstName != null && firstName.length() > 0) {
			this.firstName = firstName;
		}else{
			this.firstName = "na";
		}
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public String getName() {
		return this.lastName +", "+ this.firstName;
	}

	/**
	 * @Override 
	 */
	public String toString() {
		return this.personCode + ";" + this.lastName + ", " + this.firstName + ";" + this.address.toString() + this.emails; 
	}
	
}

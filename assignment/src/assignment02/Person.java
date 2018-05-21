package assignment02;

import java.util.HashSet;
import java.util.Set;

public class Person {
	
	private String personCode;
	private String lastName;
	private String firstName;
	private Address address;
	private Set<String> emailAddresses;

	// default constructor
	public Person() {
		
	}
	
	public Person(String nextLine) {
		String[] tokens = nextLine.split(";");
			this.personCode = tokens[0];
			String[] nameTokens = tokens[1].split(",");
			this.lastName = nameTokens[0];
			this.firstName = nameTokens[1].trim();
			this.address = new Address(tokens[2]);
			if(tokens.length == 4) {
				this.emailAddresses = new HashSet<String>();
				String[] emailAddressTokens = tokens[3].split(",");
				for(String emailAddressToken : emailAddressTokens) {
					this.emailAddresses.add(emailAddressToken);
				}
			}
	}
	
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

	public Set<String> getEmailAddresses() {
		return emailAddresses;
	}	
	
	/**
	 * @Override 
	 */
	public String toString() {
		return this.personCode + ";" + this.lastName + ", " + this.firstName + ";" + this.address.toString() + this.emailAddresses; 
	}
	
}

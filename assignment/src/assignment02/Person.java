package assignment02;

import java.util.HashSet;
import java.util.Set;

public class Person {
	
	private String personCode;
	private String lastName;
	private String firstName;
	private Address address;
	private Set<String> email;

	// default constructor
	public Person() {
		
	}
	
	public Person(String nextLine) {
		String[] token = nextLine.split(";");
			this.personCode = token[0];
			String[] nameToken = token[1].split(",");
			this.lastName = nameToken[0];
			this.firstName = nameToken[1].trim();
			this.address = new Address(token[2]);
			if(token.length == 4) {
				this.email = new HashSet<String>();
				String[] emailToken = token[3].split(",");
				for(String stringEmail : emailToken) {
					this.email.add(stringEmail);
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

	public Set<String> getEmail() {
		return email;
	}	
	
	/**
	 * @Override 
	 */
	public String toString() {
		return this.personCode + ";" + this.lastName + ", " + this.firstName + ";" + this.address.toString() + this.email; 
	}
	
}
package assignment02;

import java.util.Arrays;

public class Person {
	
	private String personCode;
	private String firstName;
	private String lastName;
	private Address address;
	private String email;
	
	
	/*Constructor*/
	public Person(String personCode, String firstName, 
			String lastName, Address address, String email) {
		this.personCode = personCode;
		this.firstName  = firstName;
		this.lastName   = lastName;
		this.address    = address;
		this.email 		= email;
	}
	
	public String toStringPerson() {
		String result = String.format("%s;%s,%s;%s;%s", 
				personCode,firstName,lastName,address.toStringAddress(),
				email);
		return result;
			
	}
	
}

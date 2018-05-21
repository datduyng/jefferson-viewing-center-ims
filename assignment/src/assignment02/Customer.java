package assignment02;

import assignment02.DataConverter;

public class Customer {
	
	private String customerCode;
	private String customerType;
	private Person primaryContact;
	private String customerName;
	private Address customerAddress;
	
	// default constructor
	public Customer() {
		
	}
	
	public Customer(String nextLine) {
		String[] customerTokens = nextLine.split(";");
		this.customerCode = customerTokens[0];
		this.customerType = customerTokens[1];
		this.primaryContact = DataConverter.findPerson(customerTokens[2], DataConverter.getPersons());
		this.customerName = customerTokens[3];
		this.customerAddress = new Address(customerTokens[4]);
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public String getCustomerType() {
		return customerType;
	}

	public Person getPrimaryContact() {
		return primaryContact;
	}

	public String getCustomerName() {
		return customerName;
	}

	public Address getCustomerAddress() {
		return customerAddress;
	}
	
	public String toString() {
		return this.customerCode + ";" + this.customerType + ";" + this.primaryContact.getPersonCode() + ";" + this.customerName + ";" + this.customerAddress.toString();
	}
	
	

}

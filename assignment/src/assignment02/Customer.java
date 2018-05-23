package assignment02;

import assignment02.DataConverter;

public class Customer {
	
	private String customerCode;
	protected String customerType;
	private Person primaryContact;
	private String customerName;
	private Address customerAddress;
	
	// default constructor
	public Customer() {
		
	}
	
	public Customer(String customerCode, String personCode, String customerType, String customerName, Address customerAddress) {
		this.customerCode = customerCode;
		//input validation customer type.
		if(customerType == "S" || customerType == "G") {
			this.customerType = customerType;
		}
		this.primaryContact = DataConverter.findPerson(personCode, DataConverter.getPersons());
		this.customerName = customerName;
		this.customerAddress = customerAddress;
	}
	
	/**
	 * This function set customer attribute
	 * @param line
	 */
	public void setAttribute(String line) {
		String[] customerTokens = line.split(";");
		this.customerCode = customerTokens[0];
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
	
	

}// end customer class

class Student extends Customer{
	public Student() {
		this.customerType = "S";
	}
}// end student class

class General extends Customer{
	public General() {
		this.customerType = "G";
	}
}// end General class 
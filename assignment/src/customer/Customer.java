package customer;

import ims.Address;
import ims.DataConverter;
import ims.Person;

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

		this.setCustomerType(customerType);
		this.setPrimaryContact(personCode);
		this.setCustomerName(customerName);
		this.setCustomerAddress(customerAddress);
	}
	
	/**
	 * This function set customer attribute
	 * @param line
	 */
	public void setAttribute(String line) {
		String[] customerTokens = line.split(";");
		setCustomerCode(customerTokens[0]);
		setPrimaryContact(customerTokens[2]);
		setCustomerName(customerTokens[3]);
		setCustomerAddress(new Address(customerTokens[4]));
		
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
	
	public void setCustomerCode(String customerCode) {
		if(customerCode != null && customerCode.length() > 0) {
			this.customerCode = customerCode;
		}else{
			this.customerCode = "N.A";
		}
	}

	public void setPrimaryContact(String personCode) {
		this.primaryContact = DataConverter.findPerson(personCode, DataConverter.getPersons());
	}

	public void setCustomerName(String customerName) {
		if(customerCode != null && customerCode.length() > 0) {
			this.customerName = customerName;
		}else{
			this.customerCode = "N.A";
		}
	}

	public void setCustomerAddress(Address customerAddress) {
		this.customerAddress = customerAddress;
	}

	public void setCustomerType(String customerType) {
		//input validation customer type.
		if(customerType.equals("S")) {
			this.customerType = "Student";
		}else if(customerType.equals("G")){
			this.customerType = "General";
		}else {
			this.customerType = "N.A";
		}
	}
	
	

	public String toString() {
		return this.customerCode + ";" + this.customerType + ";" + this.primaryContact.getPersonCode() + ";" + this.customerName + ";" + this.customerAddress.toString();
	}
	
	public String toInfo() {
		String type = "";
		String result = String.format("%s(%s)\n-%s\n%s-\n%s\n",
				this.getCustomerName(),this.getCustomerCode(),this.getCustomerType(),this.getPrimaryContact().getName(),this.getCustomerAddress());
		return result;
	}

	
	

}// end customer class



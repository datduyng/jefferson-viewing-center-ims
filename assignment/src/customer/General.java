package customer;
//test

import ims.Address;

public class General extends Customer{
	
	public General() {
		super();
		this.customerType = "General";
	}
	
	public General(String customerCode, String customerName, String personCode, Address customerAddress) {
		super(customerCode,customerName,personCode,customerAddress);
		this.customerType = "General";
	}
	
	@Override
	public String toString() {
		return "General";
	}
	
}// end General class 
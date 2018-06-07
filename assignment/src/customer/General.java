package customer;
//test

public class General extends Customer{
	
	public General() {
		super();
		this.customerType = "General";
	}
	
	@Override
	public String toString() {
		return "General";
	}
	
}// end General class 
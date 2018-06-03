package customer;

public class Student extends Customer{
	public static final double discountRate = .08 ;
	public static final double additionalFee = 6.75;
	public Student() {
		super();
		this.customerType = "S";
	}
	
	@Override 
	public String toString() {
		return "Student";
	}
}// end student class


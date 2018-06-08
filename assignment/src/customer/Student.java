package customer;

public class Student extends Customer{
	public static final double discountRate = .08 ;
	public static final double additionalFee = 6.75;
	public Student() {
		super();
		this.customerType = "Student";
	}
	/**
	* returns a formatted string to represent the constant, additionalFee
	*/
	public static String additionalFeeToString() {
		return String.format("%3.2f", Student.additionalFee);
	}
	
	@Override 
	public String toString() {
		return "Student";
	}
}// end student class


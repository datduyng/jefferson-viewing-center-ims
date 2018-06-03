package customer;

public class Student extends Customer{
	private static double discountRate = .08 ; 
	public Student() {
		super();
		this.customerType = "S";
	}
	
	@Override 
	public String toString() {
		return "Student";
	}
}// end student class


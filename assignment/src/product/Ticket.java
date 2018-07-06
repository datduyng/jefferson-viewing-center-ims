package product;

public abstract class Ticket extends Product{
	
	public static final double saleTaxRate = .06;
	
	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ticket(String productCode, String productType) {
		super(productCode, productType);
	}
	
	@Override 
	public String toString() {
		return this.getProductCode();
	}
	
}

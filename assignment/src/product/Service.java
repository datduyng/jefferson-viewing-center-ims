package product;

public abstract class Service extends Product{
	
	
	public final static double saleTaxRate = 0.04;
	
	public Service() {
		super();
	}
	
	public Service(String productCode, String productType) {
		super(productCode, productType);
	}
}

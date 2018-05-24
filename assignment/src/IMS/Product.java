package IMS;


public class Product {

	private String productCode;
	public Product() {
		
	}
	
	public Product(String productCode, String productType) {
		setProductCode(productCode);
	}

	public String getProductCode() {
		return productCode;
	}

	
	public void setProductCode(String productCode) {
		if(productCode != null && productCode.length() > 0) {
			this.productCode = productCode;
		}else {
			this.productCode = "na";
		}
		
	}


	/**
	 * @Override
	 */
	public String toString() {
		return this.productCode;
	}
	
	
}
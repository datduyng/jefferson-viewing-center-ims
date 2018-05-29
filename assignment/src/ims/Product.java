package ims;


public class Product {

	private String productCode;
	private static String productType; 
	
	public Product() {
		
	}
	
	public Product(String productCode, String productType) {
		setProductCode(productCode);
		setProductType(productType);
	}

	public String getProductCode() {
		return productCode;
	}
	
	public String getProductType() {
		return productType;
	}


	
	public void setProductCode(String productCode) {
		if(productCode != null && productCode.length() > 0) {
			this.productCode = productCode;
		}else {
			this.productCode = "na";
		}
		
	}
	
	public void setProductType(String productType) {
		if(productType != null && productType.length() > 0) {
			Product.productType = productType;
		}else {
			Product.productType = "na";
		}
	}


	/**
	 * @Override
	 */
	public String toString() {
		return this.productCode + ";" + Product.productType;
	}
	
	
}
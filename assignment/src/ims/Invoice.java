package ims;

import java.util.HashMap;

public class Invoice {

	private String invoiceCode;
	private String customerCode;
	private String salesPersonCode;
	private String invoiceDate;
	private HashMap<Product,Integer> productList = new HashMap<Product,Integer>();
	
	public Invoice() {
		// default constructor 
	}
	
	public Invoice(String invoiceCode) {
		
	}
	
	public void setAttribute(String line) {
		String[] token = line.split(";");
		
		setInvoiceCode(token[0]);
		setCustomerCode(token[1]);
		setSalesPersonCode(token[2]);
		setInvoiceDate(token[3]);
		
		String[] productToken = token[4].split(",");
		for(String p : productToken) {
			String [] pToken = p.split(":");
			productList.put( DataConverter.findProduct(pToken[0],DataConverter.getProducts()),
					Integer.parseInt(pToken[1]));
			System.out.println(productList);
		}
	}
	
	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getSalesPersonCode() {
		return salesPersonCode;
	}

	public void setSalesPersonCode(String salesPersonCode) {
		this.salesPersonCode = salesPersonCode;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public HashMap<Product, Integer> getProductList() {
		return productList;
	}

	public void setProductList(HashMap<Product, Integer> productList) {
		this.productList = productList;
	}
	


	@Override 
	public String toString() {
		// productLIst to string 
		String result = String.format("%s;%s;%s;%s;%s", invoiceCode,customerCode,
				salesPersonCode,invoiceDate,productList);
		return result;
	}

}// end class Invoice


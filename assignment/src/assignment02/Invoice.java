package assignment02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Invoice {
	
	private String invoiceCode;
	private Customer customer;
	private Person salesperson;
	private String invoiceDate;
	private Map<Product, Integer> productList;
	
	public Invoice(){
	
	}
	
	public Invoice(String nextLine) {
		String[] invoiceTokens = nextLine.split(";");
		this.invoiceCode = invoiceTokens[0];
		this.customer = DataConverter.findCustomer(invoiceTokens[1], DataConverter.getCustomers());
		this.salesperson = DataConverter.findPerson(invoiceTokens[2], DataConverter.getPersons());
		this.invoiceDate = invoiceTokens[3];
		this.productList = new HashMap<Product, Integer>();
		String[] products = invoiceTokens[4].split(",");
		for(String product : products) {
			String[] productTokens = product.split(":");
			Product p;
			p = DataConverter.findProduct(productTokens[0], DataConverter.getProducts());
			this.productList.put(p, Integer.parseInt(productTokens[1]));
			// movie-ticket, season pass, or refreshment
			if(productTokens.length == 2) {
				
			// TODO: how to handle associating movie ticket w/ parking pass 
			} else if(productTokens.length == 3) {
	
			}
		}
	}
	
	public Invoice(String invoiceCode, Customer customer, Person salesperson, String invoiceDate, Map<Product, Integer> productList){
		this.invoiceCode = invoiceCode;
		this.customer = customer;
		this.salesperson = salesperson;
		this.invoiceDate = invoiceDate;
		this.productList = productList;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Person getSalesperson() {
		return salesperson;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public Map<Product, Integer> getProductList() {
		return productList;
	}

}

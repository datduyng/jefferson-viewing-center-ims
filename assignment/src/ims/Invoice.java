package ims;

import java.util.HashMap;

public class Invoice {

	private String invoiceCode;
	private Customer customer;
	//private String customerCode;
	private Person salesPerson;
	private String invoiceDate;
	private HashMap<Product,Integer> productList = new HashMap<Product,Integer>();
	
	public Invoice() {
		// default constructor 
	}
	
	public Invoice(String invoiceCode) {
		
	}
	
	public void setAttribute(String line) {
		String[] token = line.split(";");
		
		this.setInvoiceCode(token[0]);
		this.setCustomer(DataConverter.findCustomer(token[1], DataConverter.getCustomers()));
		this.setSalesPerson(DataConverter.findPerson(token[2], DataConverter.getPersons()));
		this.setInvoiceDate(token[3]);
		
		String[] productToken = token[4].split(",");
		for(String aProduct : productToken) {
			String [] pToken = aProduct.split(":");
		
			Product p;
			p =  DataConverter.findProduct(pToken[0],DataConverter.getProducts());
			
			// movie-ticket, season pass, or refreshment
			if(pToken.length == 2) {
				this.productList.put(p,Integer.parseInt(pToken[1]));
			// TODO: how to handle associating movie ticket w/ parking pass 
			} else if(pToken.length == 3 && p instanceof ParkingPass) { 
				
				Product ticket =  DataConverter.findProduct(pToken[2],DataConverter.getProducts());
				if(ticket instanceof MovieTicket) {
					MovieTicket m = null;
					m = (MovieTicket) ticket;
					((ParkingPass)p).setMovieTicket(m);
				}else if(ticket instanceof SeasonPass) {
					SeasonPass s = null;
					s = (SeasonPass) ticket;
					((ParkingPass)p).setSeasonPass(s);
				}
				this.productList.put(p,Integer.parseInt(pToken[1]));

			}
		}
	}
	
	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Person getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(Person salesPerson) {
		this.salesPerson = salesPerson;
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
		String result = String.format("%s;%s;%s;%s;%s", this.invoiceCode, this.customer.getCustomerCode(),
				this.salesPerson.getPersonCode(),this.invoiceDate ,this.productList);
		return result;

	}
}// end class Invoice


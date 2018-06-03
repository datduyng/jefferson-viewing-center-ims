package ims;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import customer.Customer;
import product.MovieTicket;
import product.ParkingPass;
import product.Product;
import product.Refreshment;
import product.SeasonPass;
import product.Ticket;

public class Invoice {

	private String invoiceCode;
	private Customer customer;
	private Person salesPerson;
	private String invoiceDate;
	private  Map<Product,Integer> productList = new LinkedHashMap<Product,Integer>();
	
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
			} else if(pToken.length == 3 && p instanceof ParkingPass) { 
				// find associated ticket with parkingpass.
				Product associatedTicket =  DataConverter.findProduct(pToken[2],DataConverter.getProducts());
				if(associatedTicket instanceof MovieTicket) {
					// deep copy
					p = new ParkingPass((ParkingPass)p);
					MovieTicket m = null;
					m = (MovieTicket) associatedTicket;
					((ParkingPass)p).setTicket(m);				
				}else if(associatedTicket instanceof SeasonPass) {
					// deep copy
					p = new ParkingPass((ParkingPass)p);
					SeasonPass s = null;
					s = (SeasonPass) associatedTicket;
					((ParkingPass)p).setTicket(s);
				}

			}
			
			// hash Product to hash table.
			int unit = Integer.parseInt(pToken[1]);
			this.productList.put(p,unit);
		}// end for loop
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

	public LinkedHashMap<Product, Integer> getProductList() {
		return (LinkedHashMap<Product, Integer>) productList;
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


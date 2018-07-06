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
import customer.Student;
import product.MovieTicket;
import product.ParkingPass;
import product.Product;
import product.Refreshment;
import product.SeasonPass;
import product.Service;
import product.Ticket;

public class Invoice implements Comparable{

	private String invoiceCode;
	private Customer customer;
	private Person salesPerson;
	private String invoiceDate;
	private  Map<Product,Integer> productList = new LinkedHashMap<Product,Integer>();
	private double finalTotal; 
	private double totalFee; 
	private double totalDiscount;
	private double totalTax; 
	private double totalSubTotal;
	private double totalTotal;
	
	public double getFinalTotal() {
		return finalTotal;
	}

	public void setTotal(double finalTotal) {
		this.finalTotal = finalTotal;
	}

	public void setProductList(Map<Product, Integer> productList) {
		this.productList = productList;
	}

	public Invoice() {
		// default constructor 
	}
	
	public Invoice(String invoiceCode,String customerCode,String salePersonCode,String invoiceDate,HashMap<Product,Integer> productList) {
		this.setInvoiceCode(invoiceCode);
		this.setCustomer(DataConverter.findCustomer(customerCode, DataConverter.getCustomers()));
		this.setSalesPerson(DataConverter.findPerson(salePersonCode, DataConverter.getPersons()));
		this.setInvoiceDate(invoiceDate);
		this.setProductList(productList);
		
		// this method set the total fee,discount,total, final total,
		this.calculateTotal();
	}
	
	/**
	 * Parses the next line and sets the appropriate values of
	 * the Invoice object.
	 * @param line scanned line from flat file to be parsed
	 */
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
	
	/**
	 * Comparater implementation
	 */
	public int compareTo(Object i) {
		return Double.compare(this.finalTotal, ((Invoice) i).getFinalTotal());
	}
	
	
	public void calculateTotal() {
		double totalSubTotal = 0.0, totalTax = 0.0, totalTotal = 0.0, finalTotal = 0.0;
		double totalFee = 0.0;
		
		// iterate to each product and calculate finalTotal 
		for(Entry<Product, Integer> p : this.getProductList().entrySet()) {
			
			// get key and value
			Product key =  p.getKey();
			int value = p.getValue();	
			
			//get subtotal for product based on the invoice date and quantity of said product bought 
			double subTotal = key.calculateSubTotal(value, this.getInvoiceDate(), this.getProductList());
			
			// calculate tax. 
			double tax = 0.0;
			if(key instanceof Ticket) {
				tax = subTotal * Ticket.saleTaxRate;
			}else {
				tax = subTotal * Service.saleTaxRate;
			}
			
			// calculate finalTotal for 1 product 
			double total = subTotal + tax;
			
			// set aatribute 
			this.totalSubTotal += subTotal;	
			this.totalTax += tax;
			this.totalTotal += total;
			
		}	// end p forloop
		
		
		// finalTotal discount 
		if(this.getCustomer() instanceof Student) {
			
			this.totalDiscount = (this.totalSubTotal * Student.discountRate) + this.totalTax;
			this.totalFee = Student.additionalFee;
			finalTotal = this.totalTotal - this.totalDiscount + Student.additionalFee;
		}else {
			finalTotal = this.totalTotal ;
		}
		// set attriebute
		this.finalTotal = finalTotal;
		
	}
	
	
	
	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public double getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public double getTotalSubTotal() {
		return totalSubTotal;
	}

	public void setTotalSubTotal(double totalSubTotal) {
		this.totalSubTotal = totalSubTotal;
	}

	public double getTotalTotal() {
		return totalTotal;
	}

	public void setTotalTotal(double totalTotal) {
		this.totalTotal = totalTotal;
	}

	public void setFinalTotal(double finalTotal) {
		this.finalTotal = finalTotal;
	}
	
	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		if(invoiceCode == null) {
			this.invoiceCode = "N.A";
		}else {
			this.invoiceCode = invoiceCode;
		}
		
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		if(customer == null) {
			System.out.println("couldnot find customer");
		}else {
			this.customer = customer;
		}
	}

	public Person getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(Person salesPerson) {
		if(salesPerson == null) {
			System.out.println("couldnot find salesPerson");
		}else {
			this.salesPerson = salesPerson;
		}
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		if(invoiceDate == null) {
			this.invoiceDate = "0000-00-00";
		}else {
			this.invoiceDate = invoiceDate;
		}
	}

	public LinkedHashMap<Product, Integer> getProductList() {
		return (LinkedHashMap<Product, Integer>) productList;
	}

	public void setProductList(HashMap<Product, Integer> productList) {
		if(productList == null) {
			System.out.println("NULL prductList");
		}else {
			this.productList = productList;
		}
	}
	@Override 
	public String toString() {
		String result = String.format("%s;%s;%s;%s;%s", this.invoiceCode, this.customer.getCustomerCode(),
				this.salesPerson.getPersonCode(),this.invoiceDate ,this.productList);
		return result;

	}



	
	
}// end class Invoice


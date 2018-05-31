package ims;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Invoice {

	private String invoiceCode;
	private Customer customer;
	//private String customerCode;
	private Person salesPerson;
	private String invoiceDate;
	private  HashMap<Product,Integer> productList = new HashMap<Product,Integer>();
	
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
				// find associated ticket with parkingpass.
				Product associatedTicket =  DataConverter.findProduct(pToken[2],DataConverter.getProducts());
				
				if(associatedTicket instanceof MovieTicket) {
					System.out.println("DEBUG movie");
					MovieTicket m = null;
					m = (MovieTicket) associatedTicket;
					((ParkingPass)p).setTicket(m);
				}else if(associatedTicket instanceof SeasonPass) {
					//System.out.println("DEBUG season");
					SeasonPass s = null;
					s = (SeasonPass) associatedTicket;
					((ParkingPass)p).setTicket(s);
				}
				
				int unit = Integer.parseInt(pToken[1]);
				this.productList.put(p,unit);
				

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
	
	//TODO: add comment
	public HashMap<Product,Double> getSubTotalList() {
		
		HashMap<Product,Double> result = new HashMap<Product,Double>();
		
		double subTotal = 0.0;
		boolean haveTicket = false;
		// count number of ticket 
		for(Entry<Product, Integer> p : this.productList.entrySet()) {
			Product key =  p.getKey();
			if (key instanceof Ticket) {
				haveTicket = true;
				break;
			}
		}
		
		for(Entry<Product, Integer> p : this.productList.entrySet()) {
			
			// get key and valu
			Product key =  p.getKey();
			int value = p.getValue();
			
			

			if(key.getProductType().equals("M")) {
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = (Date) formatter.parse(this.invoiceDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				//get day of week
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				
				if(dayOfWeek == 3 || dayOfWeek == 5) {
					subTotal = ((MovieTicket)key).getPricePerUnit() * (double)value * (1-MovieTicket.discountRate);
					result.put(key, subTotal);
				}
				
				subTotal = ((MovieTicket)key).getPricePerUnit() * (double)value;
				result.put(key, subTotal);
			}else if(key.getProductType().equals("S")) {
				subTotal = (double)value * ( SeasonPass.seasonCost *  ( ((SeasonPass)key).getSeasonDayLeft(this.invoiceDate)/
						( (SeasonPass)key).getTotalDays() ) + SeasonPass.convenienceFee) ;
				result.put(key, subTotal);
			}else if(key.getProductType().equals("R")) {
				if(haveTicket == true) {
					subTotal = (1-Refreshment.discountRate) * (double)value * ((Refreshment)key).getCost();
					result.put(key, subTotal);
				}else {
					subTotal = (double)value * ((Refreshment)key).getCost();
					result.put(key, subTotal);
				}
				
			}else if(key.getProductType().equals("P")) {
				
				// if there is not a corresponding parking pass.
				if(((ParkingPass)key).getTicket() == null){
					subTotal= ((ParkingPass)key).getParkingFee() * (double)value;
					result.put(key, subTotal);
				}else {
					System.out.println("DEBUG prduct code");
					System.out.println(((ParkingPass)key).getTicket().getProductCode());
					int freeUnit = getNumOfTicketAssociated(((ParkingPass)key).getTicket().getProductCode());
					System.out.println("FREE"+freeUnit);
					if(freeUnit >= value) {
						subTotal = 0.0;
						result.put(key, subTotal);
					}else {
						subTotal = ((ParkingPass)key).getParkingFee() * (double)(value - freeUnit);
						result.put(key, subTotal);
					}
					
				}
			}
			
		}

		return result;
		
	}
	
	
	
	@Override 
	public String toString() {
		String result = String.format("%s;%s;%s;%s;%s", this.invoiceCode, this.customer.getCustomerCode(),
				this.salesPerson.getPersonCode(),this.invoiceDate ,this.productList);
		return result;

	}
	
	public int getNumOfTicketAssociated(String productCode) {
		for(Entry<Product, Integer> p : this.productList.entrySet()) {
			// get key and valu
			Product key =  p.getKey();
			int value = p.getValue();
			
			if(key.getProductCode().equals(productCode)) {
				return value;
			}
			
		}
		
		return -1;
	}
	
}// end class Invoice


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
	//private String customerCode;
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
			
			

			// if ticket is a movie ticket
			if(key.getProductType().equals("M")) {
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = (Date) formatter.parse(((MovieTicket)key).getDateTime());// get date of movie
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				//get day of week
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				
				// if date of movie is tuesday and thursday. then give discount 
				// no discount otherwise
				if(dayOfWeek == 3 || dayOfWeek == 5) {
					subTotal = ((MovieTicket)key).getPricePerUnit() * (double)value * (1.0-MovieTicket.discountRate);
					result.put(key, subTotal);
				}else {
					subTotal = ((MovieTicket)key).getPricePerUnit() * (double)value;
					result.put(key, subTotal);
				}
			
		    // if ticket is a season pass.
			}else if(key.getProductType().equals("S")) {
				// dayleft /totalDay.
				double prorated = ( ((SeasonPass)key).getSeasonDayLeft(this.invoiceDate)/( (SeasonPass)key).getTotalDays() );
				subTotal = (double)value * (((SeasonPass)key).getCost()  + SeasonPass.convenienceFee)  * prorated ;
				result.put(key, subTotal);
				
		    // if product is a Refreshment.
			}else if(key.getProductType().equals("R")) {
				
				// give discount is there is a ticket.
				if(haveTicket == true) {
					subTotal = (1-Refreshment.discountRate) * (double)value * ((Refreshment)key).getCost();
					result.put(key, subTotal);
				}else {
					subTotal = (double)value * ((Refreshment)key).getCost();
					result.put(key, subTotal);
				}
			// if product is a Parking pass.
			}else if(key.getProductType().equals("P")) {
				
				// if there is not a corresponding parking pass.
				if(((ParkingPass)key).getTicket() == null){
					subTotal= ((ParkingPass)key).getParkingFee() * (double)value;
					result.put(key, subTotal);
				}else {
					int freeUnit = getNumOfTicketAssociated(((ParkingPass)key).getTicket().getProductCode());
					System.out.println("DEBUG");
					System.out.println("ticketAssociate:"+((ParkingPass)key).getTicket().getProductCode());
					System.out.println("FREE:"+freeUnit);
					if(freeUnit >= value) {
						subTotal = 0.0;
						result.put(key, subTotal);
					}else{
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
		
		return 0;
	}
	
	
}// end class Invoice


package product;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import java.util.HashMap;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class SeasonPass extends Ticket {
	
	public static final double convenienceFee = 8.0;
	
	private String name;
	private String startDate;
	private String endDate;
	private double cost;
	
	//static
	private static int quantity;
	private static double proratedPercent;
	
	
	public static double getProratedPercent() {
		return proratedPercent;
	}

	public static void setProratedPercent(double proratedPercent) {
		SeasonPass.proratedPercent = proratedPercent;
	}

	public static int getQuantity() {
		return quantity;
	}

	public static void setQuantity(int quantity) {
		SeasonPass.quantity = quantity;
	}

	public SeasonPass(String[] nextLineTokens) {
		super(nextLineTokens[0], nextLineTokens[1]);
		this.name = nextLineTokens[2];
		this.startDate = nextLineTokens[3];
		this.endDate = nextLineTokens[4];
		// input validation cost 
		if(Double.parseDouble(nextLineTokens[5]) > 0.0) {
			this.cost = Double.parseDouble(nextLineTokens[5]);
		}
	}

	public String getName() {
		return name;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public double getCost() {
		return cost;
	}
	
	/**
	* @return the total days in the season pass based on the start and end date.
	*/
	public double getTotalDays() {
		DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		
		DateTime startDateDT = dateFormatter.parseDateTime(this.startDate);
		DateTime endDateDT = dateFormatter.parseDateTime(this.endDate);
		
		return (double) Days.daysBetween(startDateDT , endDateDT).getDays() ;
	}
	
	/**
	* @return the total days left in the season pass based on the invoice date
	*/
	public double getSeasonDayLeft(String invoiceDate) {
		DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		
		DateTime invoiceDateDT = dateFormatter.parseDateTime(invoiceDate);
		DateTime endDateDT = dateFormatter.parseDateTime(this.endDate);
		
		return (double) Days.daysBetween(invoiceDateDT , endDateDT).getDays() ;
	}
	
	/**
	 * Defined abstract method from parent class.  
	 * Calculates the subtotal for this specific product.
	 * @param quantity the amount of the product bought 
	 * @param invoiceDate the date of the invoice
	 * @param productList list of products associated with the invoice
	 * @return subtotal
	 */
	public double calculateSubTotal(int quantity, String invoiceDate, HashMap<Product,Integer> productList) {
		double subTotal = 0.0;
		double totalDays = this.getTotalDays();
		double seasonDaysLeft = this.getSeasonDayLeft(invoiceDate);
		
		// SET QUANTITY 
		SeasonPass.setQuantity(quantity);
		
		// Invoice is before start of season, charge full amount
		if(seasonDaysLeft >= totalDays) {
			SeasonPass.setProratedPercent(0.0);
			subTotal = (double)quantity * (this.cost + SeasonPass.convenienceFee);
		} else {
			SeasonPass.setProratedPercent((this.getSeasonDayLeft(invoiceDate)/this.getTotalDays()));
			subTotal = (double)quantity * ((this.cost * SeasonPass.getProratedPercent()) + SeasonPass.convenienceFee);
		}
		return subTotal;
	}
	
	/**
	 * Converts product details to a formatted string for the invoice report.
	 * @return formatted string
	 */
	public String toInvoiceFormat() {
		String ifProrated = "";
		
		if(SeasonPass.getProratedPercent() > 0.1) {
			ifProrated = "("+SeasonPass.getProratedPercent() + " prorated)";
		}
		return String.format("SeasonPass %s (%d units @$ %s %s+ $8.00 fee)",this.getName(),SeasonPass.getQuantity(),this.getCost(),ifProrated);
	}
	
	/**
	 * @Override
	 */
	public String toString() {
		return this.getProductCode() + ";" + "S" + ";" + this.name + ";" + this.startDate +
				";" + this.endDate + ";" + this.cost + ";" + this.getTotalDays();
	}

}

package ims;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class SeasonPass extends Ticket {
	
	public static final double seasonCost = 120.0;
	public static final double convenienceFee = 8.0;
	
	private String name;
	private String startDate;
	private String endDate;
	private double cost;
	
	public SeasonPass(){
		
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
	
	public double getTotalDays() {
		DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		
		DateTime startDateDT = dateFormatter.parseDateTime(this.startDate);
		DateTime endDateDT = dateFormatter.parseDateTime(this.endDate);
		
		return (double) Days.daysBetween(startDateDT , endDateDT).getDays() ;
	}
	
	public double getSeasonDayLeft(String invoiceDate) {
		DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		
		DateTime invoiceDateDT = dateFormatter.parseDateTime(invoiceDate);
		DateTime endDateDT = dateFormatter.parseDateTime(this.endDate);
		
		return (double) Days.daysBetween(invoiceDateDT , endDateDT).getDays() ;
	}
	
	
	
	
	/**
	 * @Override
	 */
	public String toString() {
		return this.getProductCode() + ";" + "S" + ";" + this.name + ";" + this.startDate +
				";" + this.endDate + ";" + this.cost + ";" + this.getTotalDays();
	}

}
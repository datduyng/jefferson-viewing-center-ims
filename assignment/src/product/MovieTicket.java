package product;

import ims.Address;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MovieTicket extends Ticket {
	
	// constant
	public static final double discountRate = 0.07;
	
	private String dateTime;
	private String movieName;
	private Address theaterAddress;
	private String screenNumber;
	private double pricePerUnit;
	
	
	public MovieTicket(String[] token) {
		super(token[0], token[1]);
		setDateTime(token[2]);
		setMovieName(token[3]);
		setTheaterAddress(new Address(token[4]));
		setScreenNumber(token[5]);
		setPricePerUnit(Double.parseDouble(token[6]));
	}

	public String getDateTime() {
		return dateTime;
	}

	public String getMovieName() {
		return movieName;
	}

	public Address getTheaterAddress() {
		return theaterAddress;
	}

	public String getScreenNumber() {
		return screenNumber;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}
	
	public void setDateTime(String dateTime) {
		if(dateTime != null) {
			this.dateTime = dateTime;
		}else{
			this.dateTime = "N.A";
		}
	}

	public void setMovieName(String movieName) {
		if(movieName != null && movieName.length() > 0) {
			this.movieName = movieName;
		}else{
			this.movieName = "N.A";
		}
	}

	public void setTheaterAddress(Address theaterAddress) {
		if(theaterAddress != null) {
			this.theaterAddress = theaterAddress;
		}
	}

	public void setScreenNumber(String screenNumber) {
		if(screenNumber != null && screenNumber.length() > 0) {
			this.screenNumber = screenNumber;
		}else{
			this.screenNumber = "NA";
		}
	}

	public void setPricePerUnit(double pricePerUnit) {
		//input validation priceperUnit
		if(pricePerUnit >= 0.0) {
			this.pricePerUnit = pricePerUnit;
		}else{
			this.pricePerUnit = -1.0;
		}
	}

	public double calculateSubTotal(int quantity, String invoiceDate, HashMap<Product,Integer> productList) {
		
		double subTotal = 0.0;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		
		try {
			date = (Date) formatter.parse(this.dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//get day of week
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		System.out.println("Day of Week" + dayOfWeek);
		
		if(dayOfWeek == 3 || dayOfWeek == 5) {
			subTotal = this.getPricePerUnit() * (double)quantity * (1-MovieTicket.discountRate);
		} else {
			subTotal = this.getPricePerUnit() * (double)quantity;
		}
		return subTotal;
	}
	
	
	/**
	 * @Override
	 */
	public String toString() {
		return this.getProductCode() + ";" + "M" + ";" + this.dateTime + ";" + this.movieName + ";" + this.theaterAddress.toString() + this.screenNumber + ";" + this.pricePerUnit;
	}
	

}
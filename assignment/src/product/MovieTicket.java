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
	private boolean isDiscountDay;
	
	//static
	private static int quantity;
	
	/**
	 * Parses the next line and sets the appropriate values of
	 * the MovieTicket object.
	 * @param token tokens from parsed line from flat file
	 */
	public MovieTicket(String[] token) {
		super(token[0], token[1]);
		this.setDateTime(token[2]);
		this.setMovieName(token[3]);
		this.setTheaterAddress(new Address(token[4]));
		this.setScreenNumber(token[5]);
		this.setPricePerUnit(Double.parseDouble(token[6]));
		
		MovieTicket.setQuantity(0);// init with 0 when creating an instance of this product.
		
		// set isDiscountDay if tuesday or thursday 
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
		if(dayOfWeek == 3 || dayOfWeek == 5) {
			this.isDiscountDay = true;
		} else {
			this.isDiscountDay = false;
		}
	}
	
	/**
	 * Boolean set to true if it's a Tuesday/Thursday
	 * @return a boolean value 
	 */
	public boolean isDiscountDay() {
		return isDiscountDay;
	}

	public void setDiscountDay(boolean isDiscountDay) {
		this.isDiscountDay = isDiscountDay;
	}

	public static int getQuantity() {
		return MovieTicket.quantity;
	}

	public static void setQuantity(int quantity) {
		MovieTicket.quantity = quantity;
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

		// set quantity of this product.
		MovieTicket.setQuantity(quantity);
		
		if(this.isDiscountDay == true) {
			subTotal = this.getPricePerUnit() * (double)quantity * (1-MovieTicket.discountRate);
		} else {
			subTotal = this.getPricePerUnit() * (double)quantity;
		}
		return subTotal;
	}
	
	
	/**
	 * Converts product details to a formatted string for the invoice report.
	 * @param subTotal product subtotal
	 * @param tax product tax
	 * @param total product total w/ tax
	 * @return formatted string
	 */
	public String toInvoiceFormat(double subTotal, double tax, double total) {
		String ifDiscountDay = "";
		if(this.isDiscountDay == true) {
			ifDiscountDay = "- Tue/Thu 7% off";
		}
		String firstLine = String.format("MovieTicket '%2.30s' @%20.70s", this.getMovieName(),this.getTheaterAddress().toString());
		String subTotalInStr = String.format("%3.2f", subTotal);
		String taxInStr = String.format("%3.2f", tax);
		String totalInStr = String.format("%3.2f", total);
		return String.format("%-96s$%10s  $%10s  $%10s\n\t%s (%d units @$%.2f/unit %s\n",firstLine, subTotalInStr,taxInStr,totalInStr,
				this.getDateTime(),MovieTicket.getQuantity(),this.getPricePerUnit(),ifDiscountDay);
	}
	
	
	/**
	 * @Override
	 */
	public String toString() {
		return this.getProductCode() + ";" + "M" + ";" + this.dateTime + ";" + this.movieName + ";" + this.theaterAddress.toString() + this.screenNumber + ";" + this.pricePerUnit;
	}
	

}

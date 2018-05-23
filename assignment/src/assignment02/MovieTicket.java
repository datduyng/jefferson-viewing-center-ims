package assignment02;

public class MovieTicket extends Product {
	
	private String dateTime;
	private String movieName;
	private Address theaterAddress;
	private String screenNumber;
	private double pricePerUnit;
	
	public MovieTicket() {
		
	}
	
	public MovieTicket(String[] token) {
		super(token[0], token[1]);
		this.dateTime = token[2];
		this.movieName = token[3];
		this.theaterAddress = new Address(token[4]);
		this.screenNumber = token[5];
		//input validation priceperUnit
		if(Double.parseDouble(token[6]) > 0) {
			this.pricePerUnit = Double.parseDouble(token[6]);
		}	
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
	
	/**
	 * @Override
	 */
	public String toString() {
		return this.getProductCode() + ";" + this.getProductType() + ";" + this.dateTime + ";" + this.movieName + ";" + this.theaterAddress.toString() + this.screenNumber + ";" + this.pricePerUnit;
	}
	

}
package IMS;

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

	/**
	 * @Override
	 */
	public String toString() {
		return this.getProductCode() + ";" + "M" + ";" + this.dateTime + ";" + this.movieName + ";" + this.theaterAddress.toString() + this.screenNumber + ";" + this.pricePerUnit;
	}
	

}
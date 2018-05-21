package assignment02;

public class MovieTicket extends Product {
	
	private String dateTime;
	private String movieName;
	private Address theaterAddress;
	private String screenNumber;
	private double pricePerUnit;
	
	public MovieTicket() {
		
	}
	
	public MovieTicket(String[] nextLineTokens) {
		super(nextLineTokens[0], nextLineTokens[1]);
		this.dateTime = nextLineTokens[2];
		this.movieName = nextLineTokens[3];
		this.theaterAddress = new Address(nextLineTokens[4]);
		this.screenNumber = nextLineTokens[5];
		this.pricePerUnit = Double.parseDouble(nextLineTokens[6]);
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
	

}

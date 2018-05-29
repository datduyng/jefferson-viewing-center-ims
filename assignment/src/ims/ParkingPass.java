package ims;

public class ParkingPass extends Product {

	private double parkingFee;
	private MovieTicket movieTicket;
	private SeasonPass seasonPass;
	//private Object ticket;
	
	public ParkingPass() {
		
	}
	
	public ParkingPass(String[] nextLineTokens) {
		super(nextLineTokens[0], nextLineTokens[1]);
		this.parkingFee = Double.parseDouble(nextLineTokens[2]);
	}

	public double getParkingFee() {
		return parkingFee;
	}
	
	public MovieTicket getMovieTicket() {
		return movieTicket;
	}

	public void setMovieTicket(MovieTicket movieTicket) {
		this.movieTicket = movieTicket;
	}
	
	public SeasonPass getSeasonPass() {
		return seasonPass;
	}

	public void setSeasonPass(SeasonPass seasonPass) {
		this.seasonPass = seasonPass;
	}
	
	/**
	 * @Override 
	 */
	public String toString() {
		return this.getProductCode() + ";" + "P" + ";" + this.getParkingFee();
	}




}

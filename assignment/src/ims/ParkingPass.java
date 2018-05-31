package ims;

public class ParkingPass extends Ticket {

	private double parkingFee;
	private Ticket ticket = null; 
	
	public ParkingPass() {
		
	}
	
	public ParkingPass(String[] nextLineTokens) {
		super(nextLineTokens[0], nextLineTokens[1]);
		this.parkingFee = Double.parseDouble(nextLineTokens[2]);
	}

	public double getParkingFee() {
		return parkingFee;
	}
	
	
	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public void setParkingFee(double parkingFee) {
		this.parkingFee = parkingFee;
	}

	/**
	 * @Override 
	 */
	public String toString() {
		return this.getProductCode() + ";" + "P" + ";" + this.getParkingFee();
	}




}

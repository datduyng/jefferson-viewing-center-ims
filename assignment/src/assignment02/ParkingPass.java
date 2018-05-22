package assignment02;

public class ParkingPass extends Product {

	private double parkingFee;
	
	public ParkingPass() {
		
	}
	
	public ParkingPass(String[] nextLineTokens) {
		super(nextLineTokens[0], nextLineTokens[1]);
		this.parkingFee = Double.parseDouble(nextLineTokens[2]);
	}

	public double getParkingFee() {
		return parkingFee;
	}
	
	/**
	 * @Override 
	 */
	public String toString() {
		return this.getProductCode() + ";" + this.getProductType() + ";" + this.getParkingFee();
	}
}
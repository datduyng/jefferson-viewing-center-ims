package product;

import java.util.HashMap;
import java.util.Map.Entry;

public class ParkingPass extends Service {

	private double parkingFee;
	private Ticket ticket = null; 
	
	public ParkingPass(ParkingPass p) {
		super(p.getProductCode(), p.getProductType());
		this.parkingFee = p.getParkingFee();
		this.ticket = p.getTicket();
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
	
	public double calculateSubTotal(int quantity, String invoiceDate, HashMap<Product, Integer> productList) {
		double subTotal = 0.0;
		// if there is not a corresponding parking pass.
		if(this.getTicket() == null){
			subTotal= this.getParkingFee() * (double)quantity;
		}else {
			int freeUnit = getNumOfTicketAssociated(productList);
			if (freeUnit == -1) {
				System.out.println("Error: Associated Ticket Not Found!");
				subTotal = this.getParkingFee() * (double)quantity;
			}
			else if(freeUnit >= quantity) {
				subTotal = 0.0;
			}else {
				subTotal = this.getParkingFee() * (double)(quantity - freeUnit);
			}
		}
		return subTotal;
	}
	
	public int getNumOfTicketAssociated(HashMap<Product, Integer> productList) {
		for(Entry<Product, Integer> p : productList.entrySet()) {
			// get key and value
			Product key =  p.getKey();
			int value = p.getValue();
			
			if(key.getProductCode().equals(this.getTicket().getProductCode())) {
				return value;
			}
			
		}
		
		return -1;
	}

	/**
	 * @Override 
	 */
	public String toString() {
		return this.getProductCode() + ";" + "P" + ";" + this.getParkingFee();
	}




}
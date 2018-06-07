package product;

import java.util.HashMap;
import java.util.Map.Entry;

public class ParkingPass extends Service {

	private double parkingFee;
	private Ticket ticket = null; 
<<<<<<< HEAD
	
	//static
	private static int quantity;
	private static int numOfAssociatedTicket;
	
	//copy constructor.
=======
	
>>>>>>> 7b2885f749c23d5bbe3799578cd5d3fea2889bdd
	public ParkingPass(ParkingPass p) {
		super(p.getProductCode(), p.getProductType());
		this.parkingFee = p.getParkingFee();
		this.ticket = p.getTicket();
	}
	
	public ParkingPass(String[] nextLineTokens) {
		super(nextLineTokens[0], nextLineTokens[1]);
		this.parkingFee = Double.parseDouble(nextLineTokens[2]);
		ParkingPass.numOfAssociatedTicket = 0;
		ParkingPass.quantity = 0;
	}

	public int getQuantity() {
		return quantity;
	}

	public static void setQuantity(int quantity) {
		ParkingPass.quantity = quantity;
	}

	public static int getNumOfAssociatedTicket() {
		return numOfAssociatedTicket;
	}

	public void setNumOfAssociatedTicket(int numOfAssociatedTicket) {
		ParkingPass.numOfAssociatedTicket = numOfAssociatedTicket;
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
<<<<<<< HEAD
		
		// set quantity of this product.
		ParkingPass.setQuantity(quantity);
		
=======
>>>>>>> 7b2885f749c23d5bbe3799578cd5d3fea2889bdd
		// if there is not a corresponding parking pass.
		if(this.getTicket() == null){
			subTotal= this.getParkingFee() * (double)quantity;
		}else {
<<<<<<< HEAD
			ParkingPass.numOfAssociatedTicket = getNumOfTicketAssociated(productList);
			if(ParkingPass.numOfAssociatedTicket  >= quantity) {
				subTotal = 0.0;
			}else {
				subTotal = this.getParkingFee() * (double)(quantity - ParkingPass.numOfAssociatedTicket );
=======
			int freeUnit = getNumOfTicketAssociated(productList);
			if (freeUnit == -1) {
				System.out.println("Error: Associated Ticket Not Found!");
				subTotal = this.getParkingFee() * (double)quantity;
			}
			else if(freeUnit >= quantity) {
				subTotal = 0.0;
			}else {
				subTotal = this.getParkingFee() * (double)(quantity - freeUnit);
>>>>>>> 7b2885f749c23d5bbe3799578cd5d3fea2889bdd
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

	public String toInvoiceFormat() {
		String ifHaveTicket = "";
		String ifHaveDiscount = ")";
		
		if(ticket != null) {
			ifHaveTicket = this.getTicket().getProductCode();
			ifHaveDiscount = String.format("/w %d free)", ParkingPass.numOfAssociatedTicket);
		}
		return String.format("ParkingPass %s (%d units @$%.2f/unit %s",ifHaveTicket,this.getQuantity(),this.getParkingFee()
				,ifHaveDiscount);
	}
	
	/**
	 * @Override 
	 */
	public String toString() {
		return this.getProductCode() + ";" + "P" + ";" + this.getParkingFee();
	}




}
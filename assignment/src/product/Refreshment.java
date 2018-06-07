package product;

import java.util.HashMap;
import java.util.Map.Entry;

public class Refreshment extends Service {
	
	public static final double discountRate = .05;
	private String name;
	private double cost;
	
<<<<<<< HEAD
	//static variable
	private static int quantity;
	private static boolean haveTicket;
	
=======
>>>>>>> 7b2885f749c23d5bbe3799578cd5d3fea2889bdd
	public Refreshment(String[] nextLineTokens) {
		super(nextLineTokens[0], nextLineTokens[1]);
		this.name = nextLineTokens[2];
		if(Double.parseDouble(nextLineTokens[3]) > 0.0) {
			this.cost = Double.parseDouble(nextLineTokens[3]);
		}
		Refreshment.setQuantity(0);
		Refreshment.setHaveTicket(false);
	}

	public boolean isHaveTicket() {
		return haveTicket;
	}

	public static void setHaveTicket(boolean haveTicket) {
		Refreshment.haveTicket = haveTicket;
	}

	public static int getQuantity() {
		return quantity;
	}

	public static void setQuantity(int quantity) {
		Refreshment.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public double getCost() {
		return cost;
	}
	
	public double calculateSubTotal(int quantity, String invoiceDate, HashMap<Product,Integer> productList) {
		double subTotal = 0.0;
<<<<<<< HEAD
		Refreshment.haveTicket = false;
		// SET QUANTITY 
		Refreshment.setQuantity(quantity);
		for(Entry<Product, Integer> p : productList.entrySet()) {
			Product key =  p.getKey();
			if (key instanceof Ticket) {
				Refreshment.haveTicket = true;
=======
		boolean haveTicket = false;
		for(Entry<Product, Integer> p : productList.entrySet()) {
			Product key =  p.getKey();
			if (key instanceof Ticket) {
				haveTicket = true;
>>>>>>> 7b2885f749c23d5bbe3799578cd5d3fea2889bdd
				break;
			}
		}
		
		if(haveTicket == true) {
			subTotal = (1.0 -Refreshment.discountRate) * (double)quantity * this.getCost();
		}else {
			subTotal = (double)quantity * this.getCost();
		}
		return subTotal;
	}
	
<<<<<<< HEAD
	public String toInvoiceFormat() {
		String ifHaveDiscount = ")";
		
		if(haveTicket == true) {
			ifHaveDiscount = "/w 5% off)";
		}
		return String.format("Refreshment (%d units @ $%.2f/unit%s",Refreshment.getQuantity(),this.getCost(),ifHaveDiscount);
	}
	
=======
>>>>>>> 7b2885f749c23d5bbe3799578cd5d3fea2889bdd
	/**
	 * @Override
	 */
	public String toString() {
		return this.getProductCode() + ";" + "R" + ";" + this.getName() + ";" + this.getCost();
	}

}
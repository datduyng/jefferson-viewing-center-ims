package product;

import java.util.HashMap;
import java.util.Map.Entry;

public class Refreshment extends Service {
	
	public static final double discountRate = .05;
	private String name;
	private double cost;
	
	public Refreshment(String[] nextLineTokens) {
		super(nextLineTokens[0], nextLineTokens[1]);
		this.name = nextLineTokens[2];
		if(Double.parseDouble(nextLineTokens[3]) > 0.0) {
			this.cost = Double.parseDouble(nextLineTokens[3]);
		}
	}

	public String getName() {
		return name;
	}

	public double getCost() {
		return cost;
	}
	
	public double calculateSubTotal(int quantity, String invoiceDate, HashMap<Product,Integer> productList) {
		double subTotal = 0.0;
		boolean haveTicket = false;
		for(Entry<Product, Integer> p : productList.entrySet()) {
			Product key =  p.getKey();
			if (key instanceof Ticket) {
				haveTicket = true;
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
	
	/**
	 * @Override
	 */
	public String toString() {
		return this.getProductCode() + ";" + "R" + ";" + this.getName() + ";" + this.getCost();
	}

}
package ims;

public class Refreshment extends Product {
	
	public static final double discountRate = .05;
	private String name;
	private double cost;
	
	public Refreshment(){
		
	}
	
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
	
	/**
	 * @Override
	 */
	public String toString() {
		return this.getProductCode() + ";" + "R" + ";" + this.getName() + ";" + this.getCost();
	}

}
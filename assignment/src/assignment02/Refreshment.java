package assignment02;

public class Refreshment extends Product {
	
	private String name;
	private double cost;
	
	public Refreshment(){
		
	}
	
	public Refreshment(String[] nextLineTokens) {
		super(nextLineTokens[0], nextLineTokens[1]);
		this.name = nextLineTokens[2];
		this.cost = Double.parseDouble(nextLineTokens[3]);
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
		return this.getProductCode() + ";" + this.getProductType() + ";" + this.getName() + ";" + this.getCost();
	}

}
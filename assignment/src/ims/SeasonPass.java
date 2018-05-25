package ims;

public class SeasonPass extends Product {
	
	private String name;
	private String startDate;
	private String endDate;
	private double cost;
	
	public SeasonPass(){
		
	}
	
	public SeasonPass(String[] nextLineTokens) {
		super(nextLineTokens[0], nextLineTokens[1]);
		this.name = nextLineTokens[2];
		this.startDate = nextLineTokens[3];
		this.endDate = nextLineTokens[4];
		// input validation cost 
		if(Double.parseDouble(nextLineTokens[5]) > 0.0) {
			this.cost = Double.parseDouble(nextLineTokens[5]);
		}
	}

	public String getName() {
		return name;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public double getCost() {
		return cost;
	}
	
	/**
	 * @Override
	 */
	public String toString() {
		return this.getProductCode() + ";" + "S" + ";" + this.name + ";" + this.startDate + ";" + this.endDate + ";" + this.cost;
	}

}
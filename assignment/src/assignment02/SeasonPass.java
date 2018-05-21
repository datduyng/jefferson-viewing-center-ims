package assignment02;

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
		this.cost = Double.parseDouble(nextLineTokens[5]);
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

}

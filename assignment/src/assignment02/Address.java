package assignment02;

public class Address {
	
	private String street;
	private String city;
	private String state; 
	private String zipCode;
	private String country;
	
	public Address() {
		
	}
	
	public Address(String address) {
		
		String[] addressTokens = address.split(",");
		for(int i = 0; i < addressTokens.length; i++) {
			this.street = addressTokens[0];
			this.city = addressTokens[1];
			this.state = addressTokens[2];
			this.zipCode = addressTokens[3];
			this.country = addressTokens[4];
		}
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getCountry() {
		return country;
	}
	
	
}

package ims;

public class Address {
	
	private String street;
	private String city;
	private String state; 
	private String zipCode;
	private String country;
	
	public Address(String street, String city, String state, String country,String zipcode) {
		this.setCity(city);
		this.setStreet(street);
		this.setState(state);
		this.setCountry(country);
		this.setZipCode(zipcode);
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

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setCountry(String country) {
		this.country = country;
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
	
	
	/**@Override 
	 * 
	 */
	public String toString(){
		//return this.street + ",";
		return this.street + "," + this.city + "," + this.state + "," + this.zipCode + "," + this.country + ";";
		
	}
	
	
}
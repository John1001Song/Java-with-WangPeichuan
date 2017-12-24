package hotelapp;

/** The class that represents the address of a hotel in USA. Stores the following data about the address:
 * city, state, street address, latitude and longitude. 
 */
public class Address {
	// FILL IN CODE: add instance variables mentioned above
	private String city;
	private String state;
	private String streetAddress;
	private double lat;
	private double lon;
	/** 
	 * Constructor that takes city, state, streetAddress, latitude and longitude
	 */
	public Address(String city, String state, String streetAddress, double lat, double lon) {
		// FILL IN CODE
		this.city = city;
		this.state = state;
		this.streetAddress = streetAddress;
		this.lat = lat;
		this.lon = lon;
	}

	// FILL IN CODE: add getters for city, state, streedAddress, latitude and longitude


	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}


	/** Return the string representing the address in the following format:
	 * street address on the first line,
	 * city, state on the second line.
	 * Example:
	 17 Green st.
	 San Francisco, CA
	 * @return string representing the address of the hotel
	 */
	public String toString() {
		String res = "";
		// FILL IN CODE
		res = res + this.getStreetAddress() + System.lineSeparator() + this.getCity() + ", " + this.getState();

		return res;
		
	}
}


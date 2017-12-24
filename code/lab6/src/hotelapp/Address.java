package hotelapp;

/** The class that represents the address of a hotel in USA. Stores the following data about the address:
 * city, state, street address, latitude and longitude. 
 */
public class Address {
	private String city;
	private String state;
	private String streetAddress;
	private double lat;
	private double lon;
	/** 
	 * Constructor that takes city, state, streetAddress, latitude and longitude
	 *
	 * @param city
	 * 			- the city where the hotel locates in
	 * @param state
	 * 			- the state where the city locates in
	 * @param streetAddress
	 * 			- the hotel street address
	 * @param lat
	 * 			- hotel's latitude
	 * @param lon
	 * 			- hotel's longitude
	 */
	public Address(String city, String state, String streetAddress, double lat, double lon) {
		this.city = city;
		this.state = state;
		this.streetAddress = streetAddress;
		this.lat = lat;
		this.lon = lon;
	}

	/**
	 * Method gets the hotel's city
	 *
	 * @return city */
	public String getCity() {
		return city;
	}

	/**
	 * Method gets the hotel's state
	 *
	 * @return state */
	public String getState() {
		return state;
	}

	/**
	 * Method gets the hotel's street address
	 *
	 * @return streetAddress */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * Method gets the hotel's latitude
	 *
	 * @return latitude */
	public double getLat() {
		return lat;
	}

	/**
	 * Method gets the hotel's longitude
	 *
	 * @return longitude */
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
		res = res + this.getStreetAddress() + System.lineSeparator() + this.getCity() + ", " + this.getState();
		return res;
	}
}


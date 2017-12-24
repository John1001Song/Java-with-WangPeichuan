package hotelapp;
/** A class that represents a hotel. Stores hotelId, name, address, and averageRating.
 * Implements Comparable - the hotels are compared based on the hotel names. If the names are the same, hotels
 * are compared based on the hotelId.
 * @author okarpenko
 */
public class Hotel implements Comparable<Hotel>{

	// FILL IN CODE: declare instance variables
	private String hotelId;
	private String hotelName;
	private Address hotelAddress;

	/**
	 * Constructor
	 * @param hId - the id of the hotel
	 * @param name - the name of the hotel
	 * address should be set to null.
	 */
	public Hotel(String hId, String name) {
		// FILL IN CODE
		this.hotelId = hId;
		this.hotelName = name;
	}

	/**
	 * Constructor
	 * @param hId - the id of the hotel
	 * @param name - the name of the hotel
	 * @param address - the address of the hotel
	 */
	public Hotel(String hId, String name, Address address) {
		// FILL IN CODE
		this.hotelId = hId;
		this.hotelName = name;
		this.hotelAddress = address;
	}

	// FILL IN CODE: add getters for hotelId, name and address


	public String getHotelId() {
		return hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public Address getHotelAddress() {
		return hotelAddress;
	}

	/** Compare hotels based on the name (alphabetically). May use compareTo method in class String.
	 * If the names are the same, compare based on the hotel ids. */
	@Override
	public int compareTo(Hotel o) {
		// FILL IN CODE
		if (this.getHotelName().compareTo(o.getHotelName()) == 0){
			if (this.getHotelId().compareTo(o.getHotelId()) == 0){
				return 0;
			}else if (this.getHotelId().compareTo(o.getHotelId()) < 0) {
				return -1;
			} else return 1;
		}else if (this.getHotelName().compareTo(o.getHotelName()) < 0){
			return -1;
		}else return 1;
	}
	
	/** 
	 * Returns the string representation of the hotel in the following format:
	 * hotelName: hotelID
	 * streetAddress
	 * city, state
	 * 
	 * Example: Travelodge Central San Francisco: 40682 
				1707 Market St
				San Francisco, CA
	 * 
	 * Does not include information about the reviews.
	 */
	public String toString() {
		String res = ""; // change
		// FILL IN CODE
		res = res + this.getHotelName() + ": " + this.getHotelId() + System.lineSeparator()
				+ this.getHotelAddress().getStreetAddress() + System.lineSeparator()
				+ this.getHotelAddress().getCity() + ", " + this.getHotelAddress().getState();

		return res;

	}
	
}

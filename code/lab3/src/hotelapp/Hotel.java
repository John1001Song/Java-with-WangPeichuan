package hotelapp;

/** A class that represents a hotel. Stores hotelId, name, address, and averageRating.
 * Implements Comparable - the hotels are compared based on the hotel names. If the names are the same, hotels
 * are compared based on the hotelId.
 * @author okarpenko
 */
public class Hotel implements Comparable<Hotel> {

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
		this.hotelId = hId;
		this.hotelName = name;
		this.hotelAddress = null;
	}

	/**
	 * Constructor
	 * @param hId - the id of the hotel
	 * @param name - the name of the hotel
	 * @param address - the address of the hotel
	 */
	public Hotel(String hId, String name, Address address) {
		this.hotelId = hId;
		this.hotelName = name;
		this.hotelAddress = address;
	}

	/**
	 * Method gets the hotel's hotelID
	 *
	 * @return hotelId */
	public String getHotelId() {
		return hotelId;
	}

	/**
	 * Method gets the hotel's name
	 *
	 * @return hotelName */
	public String getHotelName() {
		return hotelName;
	}

	/**
	 * Method gets the hotel's full address
	 *
	 * @return hotelAddress */
	public Address getHotelAddress() {
		return hotelAddress;
	}

	/**
	 * The method compares hotels based on the name (alphabetically).
	 * May use compareTo method in class String.
	 * If the names are the same, compare based on the hotel ids.
	 *
	 * @param anotherHotel
	 *
	 * @return -1, 0, 1 based on the alphabet order*/
	@Override
	public int compareTo(Hotel anotherHotel) {
		
		if (this.getHotelName().compareTo(anotherHotel.getHotelName()) == 0){
			if (this.getHotelId().compareTo(anotherHotel.getHotelId()) == 0){
				return 0;
			}else if (this.getHotelId().compareTo(anotherHotel.getHotelId()) < 0) {
				return -1;
			} else return 1;
		}else if (this.getHotelName().compareTo(anotherHotel.getHotelName()) < 0){
			return -1;
		}else return 1;
	}
	
	/** 
	 * The method returns the string representation of the hotel in the following format:
	 * hotelName: hotelID
	 * streetAddress
	 * city, state
	 * 
	 * Example: Travelodge Central San Francisco: 40682 
				1707 Market St
				San Francisco, CA
	 * 
	 * Does not include information about the reviews.
	 *
	 * @return hotel full address
	 */
	public String toString() {
		String res = "";
		res = res + this.getHotelName() + ": " + this.getHotelId() + System.lineSeparator()
				+ this.getHotelAddress().getStreetAddress() + System.lineSeparator()
				+ this.getHotelAddress().getCity() + ", " + this.getHotelAddress().getState();

		return res;

	}
	
}

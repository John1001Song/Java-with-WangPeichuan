package hotelapp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import concurrent.ReentrantReadWriteLock;
import concurrent.WorkQueue;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Class ThreadSafeHotelData - extends class HotelData.
 * Thread-safe, uses ReentrantReadWriteLock to synchronize access to all data structures.
 */
public class ThreadSafeHotelData extends HotelData {

	private ReentrantReadWriteLock lock = null;
	private final static Logger log = LogManager.getRootLogger();

	/**
	 * Default constructor.
	 */
	public ThreadSafeHotelData() {
		// call parent's constructor and initialize the lock
		super();
		lock = new ReentrantReadWriteLock();
	}


	/**
	 * Overrides addHotel method from HotelData class to make it thread-safe; uses the lock.
	 * Create a Hotel given the parameters, and add it to the appropriate data
	 * structure(s).
	 * 
	 * @param hotelId
	 *            - the id of the hotel
	 * @param hotelName
	 *            - the name of the hotel
	 * @param city
	 *            - the city where the hotel is located
	 * @param state
	 *            - the state where the hotel is located.
	 * @param streetAddress
	 *            - the building number and the street
	 * @param lat
	 * @param lon
	 */
	@Override
	public void addHotel(String hotelId, String hotelName, String city, String state, String streetAddress, double lat,
			double lon) {
			lock.lockWrite();
			try {
				super.addHotel(hotelId, hotelName, city, state, streetAddress, lat,lon);
			}finally {
				lock.unlockWrite();
			}
	}

	/**
	 * Overrides addReview method from HotelData class to make it thread-safe; uses the lock.
	 *
	 * @param hotelId
	 *            - the id of the hotel reviewed
	 * @param reviewId
	 *            - the id of the review
	 * @param rating
	 *            - integer rating 1-5.
	 * @param reviewTitle
	 *            - the title of the review
	 * @param review
	 *            - text of the review
	 * @param isRecom
	 *            - whether the user recommends it or not
	 * @param date
	 *            - date of the review in the format yyyy-MM-dd, e.g.
	 *            2016-08-29.
	 * @param username
	 *            - the nickname of the user writing the review.
	 * @return true if successful, false if unsuccessful because of invalid date
	 *         or rating. Needs to catch and handle the following exceptions:
	 *         ParseException if the date is invalid InvalidRatingException if
	 *         the rating is out of range
	 */
	@Override
	public boolean addReview(String hotelId, String reviewId, int rating, String reviewTitle, String review,
			boolean isRecom, String date, String username) {
		lock.lockWrite();
		try{
			return super.addReview(hotelId, reviewId, rating, reviewTitle, review, isRecom, date, username);
		}finally {
			lock.unlockWrite();
		}
	}

	/** Overrides toString method of class HotelData to make it thread-safe.
	 * Returns a string representing information about the hotel with the given
	 * id, including all the reviews for this hotel separated by --------------------
	 * Format of the string:
	 * HotelName: hotelId
	 * streetAddress city, state
	 * --------------------
	 * Review by username:
	 * rating
	 * ReviewTitle
	 * ReviewText
	 * --------------------
	 * Review by username:
	 * rating
	 * ReviewTitle
	 * ReviewText ...
	 * 
	 * @param hotelId
	 * @return - output string.
	 */
	@Override
	public String toString(String hotelId) {
		lock.lockRead();
		try {
			return super.toString(hotelId);
		}finally {
			lock.unlockRead();
		}
	}

	/**
	 * Overrides the method printToFile of the parent class to make it thread-safe.
	 * Save the string representation of the hotel data to the file specified by
	 * filename in the following format: an empty line A line of 20 asterisks
	 * ******************** on the next line information for each hotel, printed
	 * in the format described in the toString method of this class.
	 * 
	 * The hotels should be sorted by hotel ids
	 * 
	 * @param filename
	 *            - Path specifying where to save the output.
	 */
	@Override
	public void printToFile(Path filename) {
		lock.lockRead();
		try {
			super.printToFile(filename);
		} finally {
			lock.unlockRead();
		}
	}

	/**
	 * Overrides a method of the parent class to make it thread-safe.
	 * Return an alphabetized list of the ids of all hotels
	 * 
	 * @return the list of hotels' ids
	 */
	@Override
	public List<String> getHotels() {
		lock.lockRead();
		List<String> res = new ArrayList<>();
		try {
			res = super.getHotels();
		} finally {
			lock.unlockRead();
		}
		log.debug("Hotel list: " + res);
		return res;
	}

	/**
	 * Overrides the super mergeHotelData
	 **/
	@Override
	public void mergeHotelData(String hotelId, HotelData localHotelData){
		lock.lockWrite();
		try {
			super.mergeHotelData(hotelId, localHotelData);
		} finally {
			lock.unlockWrite();
		}
	}

	/**
	 * Override the super handleAReviewFile
	 *
	 * @param jsonFileName
	 * 	 				- json file name with its dir in string
	 *
	 * @return a hotel id which is same to the hotelID in the reviewMap and the reviews in the json file
	 * */
	@Override
	public String handleAReviewFile(String jsonFileName) {
		lock.lockRead();
		try {
			return super.handleAReviewFile(jsonFileName);
		} finally {
			lock.unlockRead();
		}
	}

	/**
	 * Override the super getOneHotelCity.
	 *
	 * @param hotelID
	 *
	 * @return city
	 * */
	@Override
	public String getOneHotelCity(String hotelID) {
		lock.lockRead();
		try {
			return super.getOneHotelCity(hotelID);
		} finally {
			lock.unlockRead();
		}
	}

	/**
	 * Override the super getHotelLon.
	 *
	 * @param hotelID
	 *
	 * @return longitude
	 * */
	@Override
	public Double getHotelLon(String hotelID) {
		lock.lockRead();
		try {
			return super.getHotelLon(hotelID);
		} finally {
			lock.unlockRead();
		}
	}

	/**
	 * Override the super getHotelLat.
	 *
	 * @param hotelID
	 *
	 * @return latitude
	 * */
	@Override
	public Double getHotelLat(String hotelID) {
		lock.lockRead();
		try {
			return super.getHotelLat(hotelID);
		} finally {
			lock.unlockRead();
		}
	}

	/**
	 * Override the super getHotelName
	 *
	 * @param hotelID
	 *
	 * @return hotelName
	 * */
	@Override
	public String getHotelName(String hotelID) {
		lock.lockRead();
		try {
			return super.getHotelName(hotelID);
		} finally {
			lock.unlockRead();
		}
	}

	}

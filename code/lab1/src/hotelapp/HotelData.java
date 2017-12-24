package hotelapp;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xalan.internal.xsltc.dom.ArrayNodeListIterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class HotelData - a data structure that stores information about hotels and
 * hotel reviews.
 *
 * Allows to quickly lookup a Hotel given the hotel id. (use TreeMap).
 * Allows to efficiently find hotel reviews for a given hotelID (use a TreeMap,
 * where for each hotelId, the value is a TreeSet).
 *
 * Reviews for a given hotel id are sorted by 1.date from most recent to oldest;
 * if the dates are the same, the reviews are sorted by 2.user nickname,
 * and the user nicknames are the same, by the 3.reviewId.
 *
 * You may NOT modify the signatures of methods in the starter code.
 *
 */
public class HotelData {

	// FILL IN CODE:
	// Add two data structures to store hotel data:
	// The first map should allow to lookup a hotel given the hotel id. (use TreeMap).
	// The second map should allow to efficiently find all hotel reviews for a given hotelID (use a TreeMap,
 	// where for each hotelId, the value is a TreeSet of Review-s).

	// first map
	private TreeMap<String, Hotel> hotelTreeMap;

	// second map
	//private TreeSet<Review> reviewTreeSet;
	private TreeMap<String, TreeSet<Review>> reviewTreeMap;

	/**
	 * Default constructor.
	 */
	public HotelData() {
		// Initialize all data structures.
		// FILL IN CODE

		// first map <hotelId, hotelName>
		this.hotelTreeMap = new TreeMap<String, Hotel>();

		// second map
		this.reviewTreeMap = new TreeMap<String, TreeSet<Review>>();

	}

	/**
	 * Create a Hotel given the parameters, and add it to the appropriate data
	 * structure.
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
	 * @param lat latitude
	 * @param lon longitude
	 */
	public void addHotel(String hotelId, String hotelName, String city, String state, String streetAddress, double lat,
			double lon) {

		// FILL IN CODE

		Address thisAddress = new Address(city, state, streetAddress, lat, lon);

		Hotel thisHotel = new Hotel(hotelId, hotelName, thisAddress);

		hotelTreeMap.put(hotelId, thisHotel);

	}

	/**
	 * Add a new hotel review. Add it to the map (to the TreeSet of reviews for a given key=hotelId).
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
	 *            - date of the review in the format yyyy-MM-ddThh:mm:ss, e.g. "2016-06-29T17:50:37"
	 * @param username
	 *            - the nickname of the user writing the review.
	 * @return true if successful, false if unsuccessful because of invalid hotelId, invalid date
	 *         or rating. Needs to catch and handle the following exceptions:
	 *         ParseException if the date is invalid
	 *         InvalidRatingException if the rating is out of range.
	 */
	public boolean addReview(String hotelId, String reviewId, int rating, String reviewTitle, String review,
			boolean isRecom, String date, String username) {

		// FILL IN CODE


		// check if the hotel id is in the hotel tree map. if not, return false
		if (!hotelTreeMap.containsKey(hotelId)) return false;
		if (hotelId.equals("")) return false;

		try {
			Review thisReview = new Review(hotelId, reviewId, rating, reviewTitle, review, isRecom, date, username);
			if (reviewTreeMap.containsKey(hotelId)){
				TreeSet<Review> reviewTreeSet = reviewTreeMap.get(hotelId);
				reviewTreeSet.add(thisReview);
				reviewTreeMap.put(hotelId, reviewTreeSet);
			}else {
				TreeSet<Review> reviewTreeSet = new TreeSet<>();
				reviewTreeSet.add(thisReview);
				reviewTreeMap.put(hotelId, reviewTreeSet);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		} catch (InvalidRatingException e) {
			e.printStackTrace();
			return false;
		}

		return true; // change it as needed
	}

	/**
	 * Returns a string representing information about the hotel with the given
	 * id, including all the reviews for this hotel separated by --------------------
	 * Format of the string:
	 * HotelName: hotelId
	 * streetAddress
	 * city, state
	 * -------------------- 
	 * Review by username on date
	 * Rating: rating
	 * ReviewTitle
	 * ReviewText
	 * -------------------- 
	 * Review by username on date
	 * Rating: rating
	 * ReviewTitle
	 * ReviewText ...
	 * 
	 * @param hotelId
	 * @return - output string.
	 */
	public String toString(String hotelId) {
		StringBuilder sb = new StringBuilder();
		// FILL IN CODE: append strings to sb
		TreeSet<Review> thisReviewsSet = reviewTreeMap.get(hotelId);
		//Collections.sort(thisReviewsSet);

		Hotel thisHotel = hotelTreeMap.get(hotelId);

		if (thisHotel==null) return sb.toString();

		// hotel part
		sb.append(thisHotel.toString());

		//  ---------
		// review part
		// iterator's toString method is not the toString methond defined in the Hotel.java and Review.java
		// use another method

		// check if the there is no review for the hotel id, then just add a line separator
		if (thisReviewsSet == null){
			sb.append(System.lineSeparator());
			return sb.toString();
		}

		// reviews exist
		for (Review r : thisReviewsSet) {
			sb.append(System.lineSeparator() + "--------------------" + System.lineSeparator());
			sb.append(r.toString());
		}

		return sb.toString();
	}


	/**
	 * Return a list of hotel ids, in alphabetical order of hotelIds
	 * 
	 * @return
	 */
	public List<String> getHotels() {
		List<String> hotelIdList = new ArrayList<String>();
		// FILL IN CODE

		Set<String> hotelIdSet = hotelTreeMap.keySet();
		for(String id: hotelIdSet){
			hotelIdList.add(id);
		}

		return hotelIdList;
	}

	/**
	 * Return the average rating for the given hotelId.
	 * 
	 * @param hotelId-
	 *            the id of the hotel
	 * @return average rating or 0 if no ratings for the hotel
	 */
	public double getRating(String hotelId) {
		// FILL IN CODE
		double averageRating = 0;

		// if the hotel does not exist, return 0
		if (reviewTreeMap.containsKey(hotelId) == false) return 0;

		TreeSet<Review> wantedReviews = reviewTreeMap.get(hotelId);
		for (Review r : wantedReviews) {
			averageRating += r.getRating();
		}
		averageRating = averageRating / wantedReviews.size();

		return averageRating; // change it
	}


	/**
	 * Read the given json file with information about the hotels (check hotels.json to see the expected format)
	 * and load it into the appropriate data structure(s).
	 * Do not hardcode the name of the file! the could should work on any json file in the same format.
	 * You may use JSONSimple library for parsing a JSON file.
	 * 
	 */
	public void loadHotelInfo(String jsonFilename) {
		// FILL IN CODE (use JSONParser class from JSON Simple library)
		JSONParser parser = new JSONParser();
		String hotelId;
		String hotelName;
		Address address;
		String city;
		String state;
		String streeAddress;
		double lat, lon;

		try {
			Object object = parser.parse(new FileReader(jsonFilename));
			JSONObject jsonObject = (JSONObject) object;

			JSONArray hotelsArray = (JSONArray) jsonObject.get("sr");

			for (int i = 0; i < hotelsArray.size(); i++){
				JSONObject thisHotel = (JSONObject) hotelsArray.get(i);
				// insert elements to Address
				JSONObject ll = (JSONObject) thisHotel.get("ll");
				lat = Double.parseDouble(ll.get("lat").toString());
				lon = Double.parseDouble(ll.get("lng").toString());

				// insert elements to Hotel
				hotelId = thisHotel.get("id").toString();
				hotelName = thisHotel.get("f").toString();

				city = thisHotel.get("ci").toString();
				state = thisHotel.get("pr").toString();
				streeAddress = thisHotel.get("ad").toString();

				addHotel(hotelId, hotelName, city, state, streeAddress, lat, lon);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}


	}

	public void handleAReviewFile(String jsonFileName){
		JSONParser parser = new JSONParser();
		String hotelId;
		String reviewId;
		int rating;
		String reviewTitle;
		String reviewContent;
		boolean isRecom;
		String date;
		String username;

		try {
			JSONObject reviews = (JSONObject)parser.parse(new FileReader(jsonFileName));

			JSONObject reviewDetails = (JSONObject) reviews.get("reviewDetails");
			JSONObject reviewCollection = (JSONObject) reviewDetails.get("reviewCollection");
			JSONArray review = (JSONArray) reviewCollection.get("review");

			// use the iterator method shown by the JSONSimpleExample instead of counting the review size
			Iterator<JSONObject> iterator = review.iterator();
			while (iterator.hasNext()){
				JSONObject currentReview = iterator.next();
				hotelId = (String) currentReview.get("hotelId");
				reviewId = currentReview.get("reviewId").toString();
				rating = Integer.parseInt(currentReview.get("ratingOverall").toString());
				reviewTitle = currentReview.get("title").toString();
				reviewContent = currentReview.get("reviewText").toString();
				isRecom = currentReview.get("isRecommended").equals("YES");
				date = currentReview.get("reviewSubmissionTime").toString();
				username = currentReview.get("userNickname").toString();
				addReview(hotelId, reviewId, rating, reviewTitle, reviewContent, isRecom, date, username);
			}
		}

		catch (IOException | org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

	}



	public void findAllFiles(Path path) {

		try {
			DirectoryStream<Path> filesList = Files.newDirectoryStream(path);

			for (Path file : filesList) {
				//System.out.println(file.toString() + "-----" + path.getFileName().toString());
				if (file.toFile().isDirectory()) {
					findAllFiles(file);
				}else {
					handleAReviewFile(file.toString());
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Find all review files in the given path (including in subfolders and subsubfolders etc),
	 * read them, parse them using JSONSimple library, and
	 * load review info to the TreeMap that contains a TreeSet of Review-s for each hotel id (you should
	 * have defined this instance variable above)
	 * @param path
	 */
	public void loadReviews(Path path) {
		// FILL IN CODE
		// Feel free to write helper methods if needed
		findAllFiles(path);
	}

	/**
	 * Save the string representation of the hotel data to the file specified by
	 * filename in the following format (see "expectedOutput" in the test folder):
	 * an empty line
	 * A line of 20 asterisks ********************
	 * on the next line information for each hotel, printed
	 * in the format described in the toString method of this class.
	 *
	 * The hotels in the file should be sorted by hotel ids
	 *
	 * @param filename
	 *            - Path specifying where to save the output.
	 */
	public void printToFile(Path filename) {
		// FILL IN CODE
		int flag = 1;
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(filename.toString()));

			for (String thisHotelID: hotelTreeMap.keySet()){
				//System.out.println("thisHotelID is: ------- " + thisHotelID);
				//System.out.println("reviewTreeMap: ------" + reviewTreeMap.get(thisHotelID).toString());

				writer.print(System.lineSeparator());
				writer.print("********************");
				writer.print(System.lineSeparator());
				writer.print(toString(thisHotelID));
				if (!(reviewTreeMap.get(thisHotelID)==null)){
					writer.print(System.lineSeparator());
				}

			}
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}


}
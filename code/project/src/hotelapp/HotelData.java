package hotelapp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
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

	// Add two data structures to store hotel data:
	// The first map should allow to lookup a hotel given the hotel id. (use TreeMap).
	// The second map should allow to efficiently find all hotel reviews for a given hotelID (use a TreeMap,
 	// where for each hotelId, the value is a TreeSet of Review-s).

	// first map
	private TreeMap<String, Hotel> hotelTreeMap;

	// second map
	private TreeMap<String, TreeSet<Review>> reviewTreeMap;

	/**
	 * Default constructor.
	 */
	public HotelData() {
		// Initialize all data structures.

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
	 * @param lat
	 * 			  - latitude
	 * @param lon
	 * 			  - longitude
	 */
	public void addHotel(String hotelId, String hotelName, String city, String state, String streetAddress, double lat,
			double lon) {

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

		//System.out.println("addReview head");
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
		//System.out.println("addReview tail");
		return true;
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
	 * 			   - hotel ID
	 * @return Hotel.toString and reviews.toString with the same hotelID.
	 */
	public String toString(String hotelId) {
		StringBuilder sb = new StringBuilder();
		TreeSet<Review> thisReviewsSet = reviewTreeMap.get(hotelId);
		Hotel thisHotel = hotelTreeMap.get(hotelId);

		if (thisHotel==null) return sb.toString();

		// hotel part
		sb.append(thisHotel.toString());

		//  ---------
		// review part

		// iterator's toString method is not the toString methond defined in the Hotel.java and Review.java
		// use another method

		// check if the there is no review for the hotel id, then just add a line separator
		if (thisReviewsSet == null) {
			sb.append(System.lineSeparator());
			return sb.toString();
		}

		// when reviews exist
		for (Review r : thisReviewsSet) {
			sb.append(System.lineSeparator() + "--------------------" + System.lineSeparator());
			sb.append(r.toString());
		}

		return sb.toString();
	}

	/**
	 * This method returns a list of hotel ids, in alphabetical order of hotelIds
	 * 
	 * @return a list of hotelID, in alphabetical order of hotelIds
	 */
	public List<String> getHotels() {
		List<String> hotelIdList = new ArrayList<String>();

		Set<String> hotelIdSet = hotelTreeMap.keySet();
		for(String id: hotelIdSet) {
			hotelIdList.add(id);
		}

		return hotelIdList;
	}

	/**
	 * This method returns hotels existing in the review map.
	 *
	 * @return a list of hotelId, which appears in the review map
	 * */
	public List<String> getHotelsInReviewsMap() {
		List<String> hotelIdList = new ArrayList<>();

		Set<String> hotelIdSet = reviewTreeMap.keySet();
		for (String id: hotelIdSet) {
			hotelIdList.add(id);
		}

		return hotelIdList;
	}

	/**
	 * Get the hotel name
	 *
	 * @param hotelID
	 *
	 * @return hotelName
	 * */
	public String getHotelName(String hotelID) {

		return hotelTreeMap.get(hotelID).getHotelName();
	}

	/**
	 * Get the hotel street address
	 *
	 * @param hotelID
	 *
	 * @return hotelStressAdress
	 * */
	public String getHotelStressAddress(String hotelID) {
		String streetAddress = hotelTreeMap.get(hotelID).getHotelAddress().getStreetAddress();
		return streetAddress;
	}

	/**
	 * This method returns the city of one hotel.
	 *
	 * @param hotelID
	 * 			- hotel id
	 *
	 * @return city
	 * */
	public String getOneHotelCity(String hotelID) {
		String city = hotelTreeMap.get(hotelID).getHotelAddress().getCity();
		return city;
	}

	/**
	 * This method returns the state of the hotel's city.
	 *
	 * @param hotelID
	 * 			- hotel id
	 *
	 * @return state
	 * */
	public String getOneHotelState(String hotelID) {
		String state = hotelTreeMap.get(hotelID).getHotelAddress().getState();
		return state;
	}

	/**
	 * This method returns longitude of the hotel
	 *
	 * @param hotelID
	 *			- hotel id
	 *
	 * @return longitude
	 * */
	public Double getHotelLon(String hotelID) {
		double lon = hotelTreeMap.get(hotelID).getHotelAddress().getLon();

		return lon;
	}

	/**
	 * This method returns latitude of the hotel
	 *
	 * @param hotelID
	 * 			- hotel id
	 *
	 * @return latitude
	 * */
	public Double getHotelLat(String hotelID) {
		double lat = hotelTreeMap.get(hotelID).getHotelAddress().getLat();
		return lat;
	}

	/**
	 * Return the full address of the hotel in String type.
	 *
	 * @param hotelId
	 *
	 * @return hotel full address in string
	 * */
	public String getHotelFullAddress(String hotelId) {
		return hotelTreeMap.get(hotelId).getHotelAddress().toString();
	}

	/**
	 * Return the average rating for the given hotelId.
	 * 
	 * @param hotelId-
	 *            the id of the hotel
	 * @return average rating or 0 if no ratings for the hotel
	 */
	public double getRating(String hotelId) {
		double averageRating = 0;

		// if the hotel does not exist, return 0
		if (reviewTreeMap.containsKey(hotelId) == false) return 0;

		TreeSet<Review> wantedReviews = reviewTreeMap.get(hotelId);
		for (Review r : wantedReviews) {
			averageRating += r.getRating();
		}
		averageRating = averageRating / wantedReviews.size();

		return averageRating;
	}

	/**
	 * The method reads the given json file with information about the hotels
	 * (check hotels.json to see the expected format)
	 * and load it into the appropriate data structure(s).
	 * Do not hardcode the name of the file! the could should work on any json file in the same format.
	 * You may use JSONSimple library for parsing a JSON file.
	 *
	 * @param jsonFilename
	 *
	 */
	public void loadHotelInfo(String jsonFilename) {
		// (use JSONParser class from JSON Simple library)
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

			for (int i = 0; i < hotelsArray.size(); i++) {
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

	/**
	 * This method reads one json file. Capture all reviews and call addReview().
	 * At the same time, the hotel ID is returned for identifying which hotel the method is adding,
	 * which contributes to the multi-thread part
	 *
	 * @param jsonFileName
	 * 					- json file name with its dir in string
	 *
	 * @return a hotel id which is same to the hotelID in the reviewMap and the reviews in the json file
	 * */
	public String handleAReviewFile(Path jsonFileName) {
		JSONParser parser = new JSONParser();
		String hotelId = "";
		String reviewId;
		int rating;
		String reviewTitle;
		String reviewContent;
		boolean isRecom;
		String date;
		String username;

		try {
			JSONObject reviews = (JSONObject)parser.parse(new FileReader(jsonFileName.toFile()));

			System.out.println(jsonFileName);

			JSONObject reviewDetails = (JSONObject) reviews.get("reviewDetails");
			JSONObject reviewCollection = (JSONObject) reviewDetails.get("reviewCollection");
			JSONArray review = (JSONArray) reviewCollection.get("review");

			// use the iterator method shown by the JSONSimpleExample instead of counting the review size
			Iterator<JSONObject> iterator = review.iterator();
			while (iterator.hasNext()) {
				JSONObject currentReview = iterator.next();
				hotelId = (String) currentReview.get("hotelId");
				System.out.println(hotelId);
				reviewId = currentReview.get("reviewId").toString();
				rating = Integer.parseInt(currentReview.get("ratingOverall").toString());
				reviewTitle = currentReview.get("title").toString();
				reviewContent = currentReview.get("reviewText").toString();
				isRecom = currentReview.get("isRecommended").equals("YES");
				date = currentReview.get("reviewSubmissionTime").toString();
				username = currentReview.get("userNickname").toString();
				System.out.println(username);
				boolean check = addReview(hotelId, reviewId, rating, reviewTitle, reviewContent, isRecom, date, username);
				System.out.println(check);
			}
		}

		catch (IOException | org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

		System.out.println(hotelId);

		return hotelId;

	}


	/**
	 * This method traverses all folders and find the json file, which will be called by handleAReviewFile()*/
	public void findAllFiles(Path path) {

		try {
			DirectoryStream<Path> filesList = Files.newDirectoryStream(path);

			for (Path file : filesList) {
				if (file.toFile().isDirectory()) {
					findAllFiles(file);
				} else {
					if (!file.toString().contains(".DS_Store")) {
						System.out.println(file.toString());
						handleAReviewFile(file);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Find all review files in the given path (including in subfolders and subsubfolders etc),
	 * read them, parse them using JSONSimple library, and
	 * load review info to the TreeMap that contains a TreeSet of Review-s for each hotel id (you should
	 * have defined this instance variable above)
	 * @param path
	 * 			- the path contains the json files
	 */
	public void loadReviews(Path path) {
		findAllFiles(path);
	}

	/**
	 * Check the reviews size of the wanted hotel
	 *
	 * @param hotelID
	 *
	 * @return the reviews size of the wanted hotel
	 * */
	public int getReviewsSizeOfOneHotel(String hotelID) {
		int size = reviewTreeMap.get(hotelID).size();
		return size;
	}

	/**
	 * Get a certain number of reviews based on the required number num,
	 * and the required hotelID
	 *
	 * @param hotelID
	 * 			- find the reviews based on the hotel ID
	 * @param num
	 * 			- the wanted number of reviews
	 * @return reviews in JSON Array
	 * */
	public JSONArray getReviewsOfOneHotel(String hotelID, int num) {
		int counter = num;

		JSONArray reviewsArray = new JSONArray();

		TreeSet currentReviewSet = reviewTreeMap.get(hotelID);

		Iterator<Review> iterator = currentReviewSet.iterator();
		while (iterator.hasNext() && counter > 0) {
			Review currentReview = iterator.next();
			JSONObject oneReview = new JSONObject();

			oneReview.put("reviewId", currentReview.getReviewID());
			oneReview.put("title", currentReview.getReviewTitle());
			oneReview.put("user", currentReview.getUserName());
			oneReview.put("reviewText", currentReview.getReviewText());
			oneReview.put("date", currentReview.getDate().toString());

			reviewsArray.add(oneReview);
			counter--;
		}
		return reviewsArray;
	}

	/**
	 * Return all reviews of the wanted hotel.
	 *
	 * @param hotelId
	 *
	 * @return reviews in review tree set
	 * */
	public TreeSet<Review> getReviewSetOfOneHotel(String hotelId) {

		try {

			TreeSet<Review> currentReviewSet = reviewTreeMap.get(hotelId);

			// generate a new tree set to store the wanted review date and protect the original date
			TreeSet<Review> result = new TreeSet<>();
			System.out.println("review tree map content: =======>" + reviewTreeMap.toString());
			System.out.println("hotel id is : ===========>" + hotelId);
			System.out.println("review Tree map contain this hotel id? ======>" + reviewTreeMap.containsKey(hotelId));
			System.out.printf("review set is empty?: =========>" + currentReviewSet.isEmpty());
			result.addAll(currentReviewSet);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	 *              - Path specifying where to save the output.
	 */
	public void printToFile(Path filename) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(filename.toString()));

			for (String thisHotelID: hotelTreeMap.keySet()) {
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

	/**
	 * mergeHotelData merges all local hotel data into the global one
	 *
	 * @param localHotelId
	 * 			    - the local Hotel id which will is the key for merging into the global reviewTreeMap
	 * @param localHotelData
	 * 				- the local hotel data contains all the local reviews for one hotel
	 * 				  and it is loaded from one json file
	 *
	 * */
	public void mergeHotelData(String localHotelId, HotelData localHotelData){
		reviewTreeMap.put(localHotelId, localHotelData.reviewTreeMap.get(localHotelId));
	}

}

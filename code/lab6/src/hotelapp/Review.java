package hotelapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** The class stores information about one hotel review.
 *  Stores
 *  	the id of the review,
 *  	the id of the corresponding hotel,
 *  	the rating,
 *  	the title of the review,
 *  	the text of the review,
 *  	the date
 * 	when the review was posted in the following format: yyyy-MM-ddThh:mm:ss
 *  Also stores
 *  	the nickname of the user who submitted this review,
 *  and whether the user recommends the hotel to others or not.
 *
 *  Implements Comparable - reviews should be compared based on the date
 *  (more recent review is considered "less" that the older one).
 *  If the dates are the same, compares reviews based on the user nicknames alphabetically.
 *  If the user nicknames are the same, compares based on the review id.
 * @author okarpenko
 *
 */
public class Review implements Comparable<Review> {

	public static final double MINREVIEW = 1;
	public static final double MAXREVIEW = 5;

	// add instance variables:
	private String reviewID;
	private String hotelID;
	private int rating;
	private String reviewTitle;
	private String reviewText;
	private Date date;
	private boolean isRecom;
	private String userName;
	public DateFormat format; // the date format

	/**
	 * Default constructor.
	 */
	public Review() {
		this.reviewID = null;
		this.hotelID = null;
		this.rating = 0;
		this.reviewTitle = null;
		this.reviewText = null;
		this.date = new Date();
		this.isRecom = true;
		format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	}

	/**
	 * Constructor
	 * 
	 * @param hotelId
	 *            - the id of the hotel that is being reviewed
	 * @param reviewId
	 *            = the id of the review
	 * By default, the hotel is recommended.
	 */
	public Review(String hotelId, String reviewId) {
		this.hotelID = hotelId;
		this.reviewID = reviewId;
		this.isRecom = true;
		format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	}

	/**
	 * Constructor
	 * 
	 * @param hotelId
	 *            - id of the hotel that is being reviewed
	 * @param reviewId
	 *            - id of the review
	 * @param rating
	 *            - integer rating from 1 to 5
	 * @param reviewTitle
	 *            - the title of the review
	 * @param review
	 *            - text of the review.
	 * @param isRecom
	 *            - boolean, whether the user recommends it or not
	 * @param date
	 *            - date of the review in the format yyyy-MM-ddThh:mm:ss
	 * @param username
	 *            - the nickname of the user writing the review. If empty, save it as  "Anonymous"
	 * @throws ParseException
	 *             - If date is not valid.
	 * @throws InvalidRatingException
	 * 			   - If the rating is out of the correct range from MINREVIEW TO MAXREVIEW
	 */
	public Review(String hotelId, String reviewId, int rating, String reviewTitle, String review, boolean isRecom,
			String date, String username) throws ParseException, InvalidRatingException {
		// FILL IN CODE
		this.hotelID = hotelId;
		this.reviewID = reviewId;

		if (rating > MAXREVIEW || rating < MINREVIEW){
			throw new InvalidRatingException("Rating out of the correct range.");
		}


		this.rating = rating;
		this.reviewTitle = reviewTitle;
		this.reviewText = review;
		this.isRecom = true;
		//set date type from String to Date
		format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		//format.setLenient(false);
		this.date = format.parse(date);

		if (username == null || username.equals("")){
			this.userName = "Anonymous";
		}else this.userName = username;
	}

	// add getters for all instance variables

	public String getHotelID(){
		return this.hotelID;
	}

	public String getReviewID() {
		return reviewID;
	}

	public String getReviewTitle(){
		return reviewTitle;
	}

	public String getReviewText() {
		return reviewText;
	}

	public String getUserName() {
		return userName;
	}

	public int getRating() {
		return rating;
	}

	public boolean getIsRecom(){
		return isRecom;
	}

	public Date getDate() {
		return date;
	}

	/** Compares this review with the review passed as a parameter based on
	 *  the dates (more recent date is "less" than older date).
	 *  If the dates are equal, it compares reviews based on the user nicknames, alphabetically.
	 *  If user nicknames are the same, it compares based on the review ids.
	 *  Note that we only care about comparing reviews for the same hotel id.
	 *  @param other review to compare this one with
	 *  @return 
	 *  	-1 if this review is "less than" the argument,
	 *       0 if equal
	 *  	 1 if this review is "greater" than the other one
	 */
	@Override
	public int compareTo(Review other) {
		// compare date
		// ((Jan 1 1818).compareTo( Jan 1 1811)) = 1
		if (this.getDate().compareTo(other.getDate()) == 0) {
			if (this.getUserName().compareTo(other.getUserName()) == 0) {
				return this.getReviewID().compareTo(other.getReviewID());
				}
			else return this.getUserName().compareTo(other.getUserName());
		}else if (this.getDate().compareTo(other.getDate()) > 0) return -1;
			else return 1;
	}

	/** Return a string representation of this review. Use StringBuilder for efficiency.
	 * @return A string in the following format: 
	 				Review by username on date
					Rating: rating
	 				reviewTitle
	 				textOfReview
	 * Example:
	 				Review by Ben on Tue Aug 16 18:38:29 PDT 2016
					Rating: 2
					Very bad experience 
					Awaken by noises from top floor at 5AM. Lots of mosquitos too.
					
	 * If the username is null or empty, print "Anonymous" instead of the username
	 */
	public String toString() {

		StringBuilder sb= new StringBuilder();
		// append strings to sb for efficiency
		String tempUserName;
		String tempRating;

		if (this.getUserName() == null || this.getUserName() == "") {
			tempUserName = "Anonymous";
		}else tempUserName = this.getUserName();

		sb.append("Review by " + tempUserName + " on " + date.toString());

		sb.append(System.lineSeparator());

		tempRating = String.valueOf(this.getRating());
		sb.append("Rating: " + tempRating + System.lineSeparator());

		sb.append(this.getReviewTitle());

		sb.append(System.lineSeparator());

		sb.append(this.getReviewText());

		return sb.toString();
	}
}

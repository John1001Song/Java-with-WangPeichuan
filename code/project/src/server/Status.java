package server;


/**
 * Creates a Status enum type for tracking errors. Each Status enum type
 * will use the ordinal as the error code, and store a message describing
 * the error.
 * Example of Prof. Engle
 * @see StatusTester
 */
public enum Status {

	/*
	 * Creates several Status enum types. The Status name and message is
	 * given in the NAME(message) format below. The Status ordinal is
	 * determined by its position in the list. (For example, OK is the
	 * first element, and will have ordinal 0.)
	 */

	OK("No errors occured."),
	ERROR("Unknown error occurred."),
	MISSING_CONFIG("Unable to find configuration file."),
	MISSING_VALUES("Missing values in configuration file."),
	CONNECTION_FAILED("Failed to establish a database connection."),
	CREATE_FAILED("Failed to create necessary tables."),
	INVALID_LOGIN("Invalid username and/or password."),
	INVALID_USER("User does not exist."),
	DUPLICATE_USER("User with that username already exists."),
	SQL_EXCEPTION("Unable to execute SQL statement."),
	UPDATE_HOTEL_AVGRATING_FAILED("Failed to update the hotel avg rating."),

	SUBMIT_FAILED("Failed to submit a review"),
	DUPLICATE_REVIEWID("Review ID already exists"),
	INVALID_REVIEW("Invalid review info and/or empty info."),
	UPDATE_REVIEW_FAILED("Failed to update the review."),
	DELETE_REVIEW_FAILED("Failed to delete the review.");

	private final String message;

	private Status(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}

	@Override
	public String toString() {
		return this.message;
	}
}
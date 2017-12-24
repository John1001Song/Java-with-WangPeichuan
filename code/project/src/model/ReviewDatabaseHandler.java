package model;

import org.omg.CORBA.PUBLIC_MEMBER;
import server.DatabaseConnector;
import server.Status;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ReviewDatabaseHandler {

    /** Makes sure only one database handler is instantiated. */
    private static ReviewDatabaseHandler singleton = new ReviewDatabaseHandler();

    /** Used to configure connection to database. */
    private DatabaseConnector db;

    /** Used to get sorted reviews in one hotel ordered by rating. */
    private static final String GET_SORTED_REVIEWS_IN_ONE_HOTEL_SQL =
            "SELECT * FROM reviews WHERE hotelId=?  order by rating DESC;";

    /** Used to count how many users like the review.*/
    private static final String COUNT_LIKED_REVIEW_SQL =
            "SELECT COUNT(reviewId) FROM likedReviews WHERE reviewId=?";

    /** Used to insert who likes the review into likedReviews table.*/
    private static final String INSERT_LIKED_REVIEW_SQL =
            "INSERT INTO likedReviews (userName, reviewId) VALUES (?, ?);";

    /** Used to get all reviews of one hotel. */
    private static final String ONE_REVIEW_SET_SQL =
            "SELECT * FROM reviews WHERE hotelId = ?";

    /** Used to insert a review to the reviews table*/
    private static final String INSERT_REVIEW_SQL =
            "INSERT INTO reviews (reviewId, hotelId, userName, title, content, rating, isRecomd)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?);";

    /** Used to get all reviews written by the user (userName). */
    private static final String REVIEWS_OF_USER_SQL =
            "SELECT * FROM reviews WHERE userName = ?";

    /** Used to determine if a review already exists. */
    private static final String REVIEWID_SQL =
            "SELECT reviewId FROM reviews WHERE reviewId = ?";

    /** Used to get the wanted review. */
    private static final String GET_ONE_REVIEW_SQL =
            "SELECT * FROM reviews WHERE reviewId = ?";

    /** Used to update the wanted review. */
    private static final String UPDATE_A_REVIEW_SQL =
            "UPDATE reviews SET hotelId=?, title=?, content=?, rating=?, isRecomd=? WHERE reviewId = ?";

    /** Used to delete a review based on the provided reviewId. */
    private static final String DELETE_REVIEW_SQL =
            "DELETE FROM reviews WHERE reviewId=?";

    /** Used to get hotelId based on the reviewId. */
    private static final String GET_HOTELID_FROM_REVIEWID =
            "SELECT hotelId FROM reviews WHERE reviewid=?";

    /** Used to get the hotel rating from all reviews of the hotel. */
    private static final String HOTEL_RATING_FROM_REVIEWS_SQL =
            "SELECT rating FROM reviews WHERE hotelId=?";

    /** Used to update the rating after each submit or deletion operation. */
    private static final String UPATE_HOTEL_RATING_SQL =
            // id  | name  | address | avgRating |
            "UPDATE hotels SET avgRating=? WHERE id=?";

    /** Used to generate reviewId. */
    private Random random;

    /** Used to limit characters in the random result. */
    private static final String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    /**
     * Initializes a database handler for the Login example. Private constructor
     * forces all other classes to use singleton.
     */
    private ReviewDatabaseHandler() {
        Status status = Status.OK;

        try {
            // TODO Change to "database.properties" or whatever your file is called
            db = new DatabaseConnector("database.properties");
            status = db.testConnection() ? Status.OK : Status.CONNECTION_FAILED;
        }
        catch (FileNotFoundException e) {
            status = Status.MISSING_CONFIG;
        }
        catch (IOException e) {
            status = Status.MISSING_VALUES;
        }

    }

    /**
     * Gets the single instance of the database handler.
     *
     * @return instance of the database handler
     */
    public static ReviewDatabaseHandler getInstance() {
        return singleton;
    }

    /**
     * Checks to see if a String is null or empty.
     * @param text - String to check
     * @return true if non-null and non-empty
     */
    public static boolean isBlank(String text) {
        return (text == null) || text.trim().isEmpty();
    }

    /**
     * Get all the hotel info.
     *
     * @return all hotel info in ResultSet
     * */
    public Map getSortedReviewsInOneHotel(String hotelId) {
        ResultSet resultSet = null;
        ArrayList<String> reviewList = new ArrayList<>();
        Map<String, String> review;
        Map<String, Map> reviewsMap = new LinkedHashMap<>();

        // a new structure makes sure the output is pure data without HTML format, easy for further edit
        // ArrayList<TreeMap<String, String>> reviewList
        // TreeMap<String, String> currentReview
        // "reviewId", dadkjs131wsafda...

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_SORTED_REVIEWS_IN_ONE_HOTEL_SQL);) {

            statement.setString(1, hotelId);
            resultSet = statement.executeQuery();

            try {
                while (resultSet.next()) {
                    // reviews (reviewId, hotelId, userName, title, content, rating, isRecomd)
                    String thisReview = "";
                    review = new TreeMap<>();
                    String reviewId = resultSet.getString("reviewId");
                    String userName = resultSet.getString("userName");
                    String content = resultSet.getString("content");
                    String rating = Double.toString(resultSet.getDouble("rating"));
                    String title = resultSet.getString("title");
                    String isRecomd = resultSet.getString("isRecomd");

                    review.put("reviewId", reviewId);
                    review.put("hotelId", hotelId);
                    review.put("userName", userName);
                    review.put("title", title);
                    review.put("content", content);
                    review.put("rating", rating);
                    review.put("isRecomd", isRecomd);

                    reviewsMap.put(reviewId, review);
//                    thisReview = "<p>" + reviewId + ", " + hotelId + ", " + userName + "<br>"
//                                       + title + "<br>"
//                                       + content + "<br>"
//                                       + avgRating + ", " + isRecomd + "</p>";
//                    reviewList.add(thisReview);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(reviewList);
        return reviewsMap;
    }

    /**
     * This method count how many users liked the review.
     * */
    public String countLikedReview(String reviewId) {
        String counter = "";

        try (Connection connection = db.getConnection();
            PreparedStatement statement = connection.prepareStatement(COUNT_LIKED_REVIEW_SQL)) {

            statement.setString(1, reviewId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                counter = resultSet.getString("count(reviewId)");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return counter;
    }

    /**
     * This method insert userName and reviewId to likedReviews table.
     * */
    public Status insertLikedReview(String userName, String reviewId) {
        Status status = Status.ERROR;

        try (Connection connection = db.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_LIKED_REVIEW_SQL)) {

            statement.setString(1, userName);
            statement.setString(2, reviewId);
            statement.executeUpdate();

            status = Status.OK;

        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.ERROR;
        }

        return status;
    }

    /**
     * This method returns hotelId from the reviewId. */
    private String getGetHotelidFromReviewid(String reviewId) {
        String hotelId = "";

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_HOTELID_FROM_REVIEWID)) {

            statement.setString(1,reviewId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                hotelId = resultSet.getString("hotelId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hotelId;
    }

    /**
     * This method deletes a review based on the provided reviewId.
     * */
    public Status deleteReview(String reviewId) {
        Status status = Status.INVALID_REVIEW;
        String hotelId = "";

        hotelId = getGetHotelidFromReviewid(reviewId);

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_REVIEW_SQL);) {

            statement.setString(1, reviewId);
            statement.executeUpdate();
            status = Status.OK;

        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.DELETE_REVIEW_FAILED;
        }

        double avgRating = calculateHotelRating(hotelId);
        updateHotelAvgRating(hotelId, String.valueOf(avgRating));
        System.out.println(String.valueOf(avgRating));

        return status;
    }

    /**
     * This method updates the edited review into the database.
     *
     * @param reviewId
     * @param hotelId
     * @param title
     * @param content
     * @param rating
     * @param isRecomd
     *
     * @return Status about if the review is updated successfully
     * */
    public Status updateReview(String reviewId, String hotelId, String title, String content, String rating, String isRecomd) {
        Status status = Status.INVALID_REVIEW;

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_A_REVIEW_SQL)) {

            // UPDATE reviews SET hotelId=?, title=?, content=?, rating=?, isRecomd=? WHERE reviewId = ?
            statement.setString(1, hotelId);
            statement.setString(2,title);
            statement.setString(3, content);
            statement.setString(4, rating);
            statement.setString(5, isRecomd);
            statement.setString(6, reviewId);
            statement.executeUpdate();
            status = Status.OK;

        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.UPDATE_REVIEW_FAILED;
        }

        double avgRating = calculateHotelRating(hotelId);
        updateHotelAvgRating(hotelId, String.valueOf(avgRating));
        System.out.println(String.valueOf(avgRating));

        return status;
    }

    /**
     * This method gets the wanted review info based on the provided review ID.
     *
     * @param reviewId
     *
     * @return the wanted review info
     * */
    public TreeMap getOneReviewSql(String reviewId) {
        TreeMap<String, String> thisReview = new TreeMap<>();

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ONE_REVIEW_SQL)) {

            statement.setString(1, reviewId);
            ResultSet resultSet = statement.executeQuery();

            try {
                while (resultSet.next()) {
                    thisReview.put("reviewId", reviewId);
                    thisReview.put("hotelId", resultSet.getString("hotelId"));
                    thisReview.put("userName", resultSet.getString("userName"));
                    thisReview.put("title", resultSet.getString("title"));
                    thisReview.put("content", resultSet.getString("content"));
                    thisReview.put("rating", Double.toString(resultSet.getDouble("rating")));
                    thisReview.put("isRecomd", resultSet.getString("isRecomd"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return thisReview;
    }

    /**
     * Get all the hotel info.
     *
     * @return all hotel info in ResultSet
     * */
    public Map displayReviewsOfWantedHotel(String hotelId) {
        ResultSet resultSet = null;
        ArrayList<String> reviewList = new ArrayList<>();
        Map<String, String> review;
        Map<String, Map> reviewsMap = new TreeMap<>();

        // a new structure makes sure the output is pure data without HTML format, easy for further edit
        // ArrayList<TreeMap<String, String>> reviewList
        // TreeMap<String, String> currentReview
        // "reviewId", dadkjs131wsafda...

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(ONE_REVIEW_SET_SQL);) {

            statement.setString(1, hotelId);
            resultSet = statement.executeQuery();

            try {
                while (resultSet.next()) {
                    // reviews (reviewId, hotelId, userName, title, content, rating, isRecomd)
                    String thisReview = "";
                    review = new TreeMap<>();
                    String reviewId = resultSet.getString("reviewId");
                    String userName = resultSet.getString("userName");
                    String content = resultSet.getString("content");
                    String rating = Double.toString(resultSet.getDouble("rating"));
                    String title = resultSet.getString("title");
                    String isRecomd = resultSet.getString("isRecomd");

                    review.put("reviewId", reviewId);
                    review.put("hotelId", hotelId);
                    review.put("userName", userName);
                    review.put("title", title);
                    review.put("content", content);
                    review.put("rating", rating);
                    review.put("isRecomd", isRecomd);

                    reviewsMap.put(reviewId, review);
//                    thisReview = "<p>" + reviewId + ", " + hotelId + ", " + userName + "<br>"
//                                       + title + "<br>"
//                                       + content + "<br>"
//                                       + avgRating + ", " + isRecomd + "</p>";
//                    reviewList.add(thisReview);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(reviewList);
        return reviewsMap;
    }

    /**
     * Get all the reviews written by the user.
     * Map_ReviewsMap<reviewId, Map_ReviewContent>
     * @return all reviews related to the user in ResultSet
     * */
    public Map displayReviewsOfUser(String userName) {
        ResultSet resultSet = null;
        Map<String, String> reviewContent;
        Map<String, Map<String, String>> reviewsMap = new TreeMap<>();

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(REVIEWS_OF_USER_SQL);) {

            statement.setString(1, userName);
            resultSet = statement.executeQuery();

            try {
                while (resultSet.next()) {
                    // reviews (reviewId, hotelId, userName, title, content, rating, isRecomd)
                    String thisReview = "";
                    reviewContent = new TreeMap<>();
                    String reviewId = resultSet.getString("reviewId");
                    String hotelId = resultSet.getString("hotelId");
                    String content = resultSet.getString("content");
                    String avgRating = Double.toString(resultSet.getDouble("rating"));
                    String title = resultSet.getString("title");
                    String isRecomd = resultSet.getString("isRecomd");

                    reviewContent.put("reviewId", reviewId);
                    reviewContent.put("hotelId", hotelId);
                    reviewContent.put("content", content);
                    reviewContent.put("avgRating", avgRating);
                    reviewContent.put("title", title);
                    reviewContent.put("isRecomd", isRecomd);
                    reviewContent.put("userName", userName);

                    reviewsMap.put(reviewId, reviewContent);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(reviewsMap);
        return reviewsMap;
    }

    /**
     * This method checks if the reviewId is unique.
     *
     * @param reviewId
     * @param connection
     *
     * @return status result of if the review id is unique.
     * */
    private Status dupilcateReviewId(Connection connection, String reviewId) {
        assert connection != null;
        assert reviewId != null;

        Status status = Status.ERROR;

        try (
                PreparedStatement statement = connection.prepareStatement(REVIEWID_SQL);
        ) {
            statement.setString(1, reviewId);

            ResultSet results = statement.executeQuery();
            status = results.next() ? Status.DUPLICATE_REVIEWID : Status.OK;
        }
        catch (SQLException e) {
            e.printStackTrace();
            status = Status.SQL_EXCEPTION;
        }

        return status;
    }

    /**
     * Tests if a user already exists in the database.
     *
     * @param reviewId - reviewId to check
     * @return Status.OK if user does not exist in database
     */
    public Status duplicateReviewId(String reviewId) {
        Status status = Status.ERROR;

        try (
                Connection connection = db.getConnection();
        ) {
            status = dupilcateReviewId(connection, reviewId);
            System.out.println(status.message());
        }
        catch (SQLException e) {
            status = Status.DUPLICATE_REVIEWID;
            e.printStackTrace();
        }

        return status;
    }

    /**
     *
     * @param candidateChars
     *            the candidate chars
     * @param length
     *            the number of random chars to be generated
     *
     * @return
     */
    public static String generateRandomChars(String candidateChars, int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }

        return sb.toString();
    }


    /**
     * This method generates a random reviewId.
     *
     * @return reviewId in String
     * */
    private String createReviewId() {

        Status status = Status.DUPLICATE_REVIEWID;
        String newReviewId;

//        byte[] reviewId = new byte[4];
//        random.nextBytes(reviewId);
        //newReviewId = new String(reviewId);
        newReviewId = generateRandomChars(candidateChars, 20);
        status = duplicateReviewId(newReviewId);

        while (status == Status.DUPLICATE_REVIEWID) {
//            reviewId = new byte[4];
//            random.nextBytes(reviewId);
//            newReviewId = new String(reviewId);
            newReviewId = generateRandomChars(candidateChars, 20);
            status = duplicateReviewId(newReviewId);
        }

        return newReviewId;
    }

    /**
     * This method allows the user to insert a review.
     *
     * @param hotelId
     * @param userName
     * @param title
     * @param content
     * @param rating
     * @param isRecomd
     *
     * @return Status about if the review is submitted correctly.
     * */
    private Status submitReview(Connection connection, String hotelId, String userName,
                               String title, String content, String rating, String isRecomd) {
        Status status = Status.ERROR;

        String reviewId = createReviewId();

        try (PreparedStatement statement = connection.prepareStatement(INSERT_REVIEW_SQL)) {
            // INSERT INTO reviews (reviewId, hotelId, userName, title, content, rating, isRecomd)
            statement.setString(1, reviewId);
            statement.setString(2, hotelId);
            statement.setString(3, userName);
            statement.setString(4,title);
            statement.setString(5,content);
            statement.setString(6,rating);
            statement.setString(7,isRecomd);

            statement.executeUpdate();

            System.out.println(reviewId);
            System.out.println(statement.toString());
            status = Status.OK;
            System.out.println("submit review level 2");
        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.SUBMIT_FAILED;
        }

        System.out.println(status.message());
        return status;
    }


    /**
     * This method allows the user to insert a review.
     *
     * @param hotelId
     * @param userName
     * @param title
     * @param content
     * @param rating
     * @param isRecomd
     *
     * @return Status about if the review is submitted correctly.
     * */
    public Status submitReview(String hotelId, String userName,
                               String title, String content, String rating, String isRecomd) {
        Status status = Status.ERROR;

        // make sure we have non-null and non-emtpy values for submitting a review
        if (isBlank(hotelId) || isBlank(userName)
                || isBlank(title) || isBlank(content) || isBlank(rating) || isBlank(isRecomd)) {
            status = Status.INVALID_REVIEW;
            return status;
        }

        System.out.println(db);

        try (Connection connection = db.getConnection();) {

            status = submitReview(connection, hotelId, userName, title, content, rating, isRecomd);

            System.out.println("submit review level 1");
        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.SUBMIT_FAILED;
        }

        double avgRating = calculateHotelRating(hotelId);
        updateHotelAvgRating(hotelId, String.valueOf(avgRating));
        System.out.println(String.valueOf(avgRating));

        System.out.println(status.message());
        return status;
    }

    /**
     * This method updates the hotel rating after submit or deletion. */
    public Status updateHotelAvgRating(String hotelId, String avgRating) {
        Status status = Status.ERROR;

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPATE_HOTEL_RATING_SQL)) {

            statement.setString(1, avgRating);
            statement.setString(2, hotelId);

            statement.executeUpdate();
            status = Status.OK;
            System.out.println(hotelId + " hotel avg rating update ok");

        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.UPDATE_HOTEL_AVGRATING_FAILED;
        }

        System.out.println(status.message());
        return status;
    }

    /**
     * Calculate the average rating of the given hotel.
     * */
    private double calculateHotelRating(String hotelID) {
        double avgRating = 0.0;
        ResultSet resultSet;
        double totalRating = 0.0;
        int reviewCounter = 0;

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(HOTEL_RATING_FROM_REVIEWS_SQL)) {

            statement.setString(1, hotelID);
            resultSet = statement.executeQuery();

            try {
                while (resultSet.next()) {
                    totalRating += Double.valueOf(resultSet.getString(1));
                    reviewCounter++;
                    System.out.println(resultSet.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (reviewCounter != 0) {
                avgRating = totalRating / reviewCounter;
            } else {
                avgRating = 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return avgRating;
    }

}

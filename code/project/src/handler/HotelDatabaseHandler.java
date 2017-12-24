package handler;

import server.DatabaseConnector;
import server.LoginDatabaseHandler;
import server.Status;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class HotelDatabaseHandler {

    /** Makes sure only one database handler is instantiated. */
    private static HotelDatabaseHandler singleton = new HotelDatabaseHandler();

    /** Used to determine if necessary tables are provided. */
    private static final String TABLES_SQL =
            "SHOW TABLES LIKE 'hotels';";

    /** Used to get a hotel info if it exists. */
    private static final String THISHOTEL_SQL =
            "SELECT * FROM hotels WHERE id = ?";

    /** Used to get all hotels' info from database. */
    private static final String ALLHOTELS_SQL =
            "SELECT * FROM hotels";

    /** Used to get the hotel rating from all reviews of the hotel.*/
    private static final String HOTEL_RATING_FROM_REVIEWS_SQL =
            "SELECT rating FROM reviews WHERE hotelId = ?";

    /** Used to configure connection to database. */
    private DatabaseConnector db;


    /**
     * Initializes a database handler for the Login example. Private constructor
     * forces all other classes to use singleton.
     */
    private HotelDatabaseHandler() {
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
    public static HotelDatabaseHandler getInstance() {
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

    // Here is an example for the link
    // https://www.expedia.com/San-Francisco-Hotels-Serrano-Hotel.h12493.Hotel-Information?
    private String generateLink(String hotelCity, String hotelName, String hotelId) {
        String link = "https://www.expedia.com/";

        String[] cityParts = hotelCity.split(" ");
        for (int i = 0; i < cityParts.length; i++) {
            link += cityParts[i] + "-";
        }

        link += "Hotels";

        String[] nameParts = hotelName.split(" ");
        for (int i = 0; i < nameParts.length; i++) {
            link += "-" + nameParts[i];
        }

        link += ".h" + hotelId + ".Hotel-Information?";

        return link;
    }

    /**
     * Get all the hotel info, including a link for expedia.
     *
     * @return all hotel info in ResultSet
     * */
    public Map displayAllHotels() {
        ResultSet resultSet = null;
        Map<String, String> hotelMap = new TreeMap<>();

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(ALLHOTELS_SQL);) {

            resultSet = statement.executeQuery();

            try {
                while (resultSet.next()) {
                    String thisHotel = "";
                    String link = "";
                    String hotelId = resultSet.getString("id");
                    String hotelName = resultSet.getString("name");
                    String hotelStreet = resultSet.getString("street");
                    String hotelCity = resultSet.getString("city");
                    String hotelState = resultSet.getString("state");
                    String avgRating = Double.toString(resultSet.getDouble("avgRating"));
                    thisHotel = hotelId + ", " + hotelName + ", " + hotelStreet + ", " + hotelCity + " " + hotelState + ", " + avgRating;
                    link = generateLink(hotelCity, hotelName, hotelId);
                    hotelMap.put(thisHotel, link);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hotelMap;
    }

    public String displayOneHotel(String hotelId) {

        return "";
    }



}

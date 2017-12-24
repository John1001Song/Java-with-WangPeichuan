package model;

import server.DatabaseConnector;
import server.Status;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class HotelDatabaseHandler {

    /** Makes sure only one database handler is instantiated. */
    private static HotelDatabaseHandler singleton = new HotelDatabaseHandler();

    /** Used to determine if necessary tables are provided. */
    private static final String SAVE_HOTEL_SQL =
            "INSERT INTO savedHotels  (userName, hotelId) VALUES (?, ?)";

    /** Used to determine if necessary tables are provided. */
    private static final String TABLES_SQL =
            "SHOW TABLES LIKE 'hotels';";

    /** Used to get a hotel info if it exists. */
    private static final String THISHOTEL_SQL =
            "SELECT * FROM hotels WHERE id = ?";

    /** Used to clear the saved hotels*/
    private static final String CLEAR_SAVED_HOTELS_SQL =
            "DELETE from savedHotels WHERE userName=?";

    /** Used to get a hotel name for the saved Hotels. */
    private static final String GETHOTELNAME_SQL =
            "SELECT hotels.name FROM hotels, savedHotels " +
                    "WHERE savedHotels.hotelId=hotels.id AND savedHotels.userName=?";

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

    /**
     * Clear the saved hotels.
     * */
    public Status clearHotels(String userName) {
        Status status = Status.ERROR;

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(CLEAR_SAVED_HOTELS_SQL)) {

            statement.setString(1, userName);
            statement.executeUpdate();
            status = Status.OK;

        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.ERROR;
        }

        return status;
    }

    /**
     * Save the selected hotels.
     * */
    public Status saveHotel(String userName, String hotelId) {
        Status status = Status.ERROR;

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_HOTEL_SQL)) {

            statement.setString(1, userName);
            statement.setString(2, hotelId);
            statement.executeUpdate();
            status = Status.OK;

        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.ERROR;
        }

        return status;
    }

    /**
     * Get a hotel name based on the provided userName.
     * */
    public ArrayList getHotelName(String userName) {
       ArrayList<String> savedHotelNameList = new ArrayList<>();

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(GETHOTELNAME_SQL);) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String hotelName = resultSet.getString("name");
                hotelName += "<br><br>";
                savedHotelNameList.add(hotelName);
            }

       } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("hotelName in the getHotelName_SQL: " + savedHotelNameList.toString());
        return savedHotelNameList;
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
        Map<String, String> hotelInfo;
        Map<String, Map> hotelsMap = new TreeMap<>();

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(ALLHOTELS_SQL);) {

            resultSet = statement.executeQuery();

            try {
                while (resultSet.next()) {
                    String link = "";
                    hotelInfo = new TreeMap<>();

                    String hotelId = resultSet.getString("id");
                    String hotelName = resultSet.getString("name");
                    String hotelStreet = resultSet.getString("street");
                    String hotelCity = resultSet.getString("city");
                    String hotelState = resultSet.getString("state");
                    String avgRating = Double.toString(resultSet.getDouble("avgRating"));
                    link = generateLink(hotelCity, hotelName, hotelId);

                    hotelInfo.put("hotelId", hotelId);
                    hotelInfo.put("hotelName", hotelName);
                    hotelInfo.put("hotelStreet", hotelStreet);
                    hotelInfo.put("hotelCity", hotelCity);
                    hotelInfo.put("hotelState", hotelState);
                    hotelInfo.put("avgRating", avgRating);
                    hotelInfo.put("link", link);

                    hotelsMap.put(hotelId, hotelInfo);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hotelsMap;
    }

    public String displayOneHotel(String hotelId) {

        return "";
    }



}

package server;

import hotelapp.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Paths;
import java.util.*;

import com.mysql.jdbc.ResultSetMetaData;

public class InsertHotelAndReviewToDatabase {
    /** Used to configure connection to database. */
    private DatabaseConnector db;

    // load hotel info and reviews
    private static final String hotelDir = "input/hotels.json";
    private static final String reviewDir = "input/reviews";

    ThreadSafeHotelData hotelData;

    public String uri = "";

    /**
     * Attempts to load properties file with database configuration. Must
     * include username, password, database, and hostname.
     *
     * @param configPath
     *            path to database properties file
     * @return database properties
     * @throws IOException
     *             if unable to properly parse properties file
     * @throws FileNotFoundException
     *             if properties file not found
     */
    private Properties loadConfig(String configPath) throws FileNotFoundException, IOException {

        // Load properties file
        Properties config = new Properties();
        config.load(new FileReader(configPath));

        return config;
    }

    /**
     * Initializes a database handler for the Login example.
     */
    public InsertHotelAndReviewToDatabase() {
        hotelData = new ThreadSafeHotelData();
        hotelData.loadHotelInfo(hotelDir);
        hotelData.loadReviews(Paths.get(reviewDir));
        try {
            Properties config = loadConfig("database.properties");

            // Create database URI in proper format
            String uri = String.format("jdbc:mysql://%s/%s", config.getProperty("hostname"),
                    config.getProperty("database"));

            System.out.println("uri = " + uri);

            PreparedStatement sql;
            PreparedStatement reviewSql;

            try (Connection dbConnection = DriverManager.getConnection(uri, config.getProperty("username"), config.getProperty("password"))) {
                sql = dbConnection.prepareStatement("INSERT INTO hotels (id, name, street, city, state, lat, lon, avgRating) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");

//                 insert hotel info
                List<String> hotelIdList = hotelData.getHotels();
                for (String currentHotelId : hotelIdList) {
                    sql.setString(1, currentHotelId);
                    sql.setString(2, hotelData.getHotelName(currentHotelId));
                    sql.setString(3, hotelData.getHotelStressAddress(currentHotelId));
                    sql.setString(4, hotelData.getOneHotelCity(currentHotelId));
                    sql.setString(5, hotelData.getOneHotelState(currentHotelId));
                    sql.setString(6, String.valueOf(hotelData.getHotelLat(currentHotelId)));
                    sql.setString(7, String.valueOf(hotelData.getHotelLon(currentHotelId)));
                    sql.setDouble(8, hotelData.getRating(currentHotelId));
                    sql.executeUpdate();
                    System.out.println(sql.toString());
                }

                // insert reviews
//                reviewSql = dbConnection.prepareStatement("INSERT INTO reviews (reviewId, hotelId, userName, title, content, rating, isRecomd)" +
//                                                                    " VALUES (?, ?, ?, ?, ?, ?, ?);");
//                List<String> availableHotelsList = hotelData.getHotelsInReviewsMap();
//                for (String currentHotelId : availableHotelsList) {
//                        TreeSet<Review> reviewTreeSet = hotelData.getReviewSetOfOneHotel(currentHotelId);
//                        for (Review currentReview : reviewTreeSet) {
//                            reviewSql.setString(1, currentReview.getReviewID());
//                            reviewSql.setString(2, currentHotelId);
//                            reviewSql.setString(3, currentReview.getUserName());
//                            reviewSql.setString(4, currentReview.getReviewTitle());
//                            reviewSql.setString(5, currentReview.getReviewText());
//                            reviewSql.setInt(6, currentReview.getRating());
//                            reviewSql.setString(7, Boolean.toString(currentReview.getIsRecom()));
//                            reviewSql.executeUpdate();
//                            System.out.println(sql.toString());
//                        }
//                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

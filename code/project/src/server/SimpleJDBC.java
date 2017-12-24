package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class SimpleJDBC {

    /**
     * URI to use when connecting to database. Should be in the format:
     * jdbc:subprotocol://hostname/database
     */
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

    /** Connect to the database and send a simple query. Print the results. */
    public void connectToDataBase() {
        try {
            Properties config = loadConfig("database.properties");

            // Create database URI in proper format
            String uri = String.format("jdbc:mysql://%s/%s", config.getProperty("hostname"),
                                        config.getProperty("database"));
            System.out.printf("uri = " + uri);

            PreparedStatement sql; // prepared statement
            try (Connection dbConnection = DriverManager.getConnection(uri, config.getProperty("username"),
                                                                        config.getProperty("password"))){
                sql = dbConnection.prepareStatement("select rating from reviews where hotelid = '1003'");
//                sql.setInt(1, 2);
//                sql.setDouble(2, 3.9);

                ResultSet resultSet = sql.executeQuery();
                // check the number of columns
                ResultSetMetaData resultSetMetaData = (ResultSetMetaData) resultSet.getMetaData();
                int columnsNumber = resultSetMetaData.getColumnCount();
                while (resultSet.next()) { // go along rows using the iterator
                    // iterate along columns
                    for (int i = 0; i < columnsNumber; i++) {
                        System.out.printf(resultSet.getString(i + 1) + " ");
                    }
                    System.out.printf("\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Unable to connect properly to database.");
                System.err.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SimpleJDBC test = new SimpleJDBC();
        test.connectToDataBase();
    }
}

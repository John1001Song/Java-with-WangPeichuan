package JDBCExamples;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Set;

import java.sql.ResultSetMetaData;
/**
 * This class is designed to test your database configuration. You need to have
 * a database.properties file with username, password, database, and hostname.
 * You must also have the tunnel to stargate.cs.usfca.edu running if you are
 * off-campus.
 */
public clasJDBCExample {

	/**
	 * URI to use when connecting to database. Should be in the format:
	 * jdbc:subprotocol://hostname/database
	 */
	public String uri = "";

	/** Properties with username and password for connecting to database. */
	private Properties login = null;

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

		// Specify which keys must be in properties file
		Set<String> required = new HashSet<>();
		required.add("username");
		required.add("password");
		required.add("database");
		required.add("hostname");

		// Load properties file
		Properties config = new Properties();
		config.load(new FileReader(configPath));

		// Check that required keys are present
		if (!config.keySet().containsAll(required)) {
			String error = "Must provide the following in properties file: ";
			throw new InvalidPropertiesFormatException(error + required);
		}

		return config;
	}

	public void connectToDatabase() {
		try {

			Properties config = loadConfig("database.properties");

			// Create database URI in proper format
			String uri = String.format("jdbc:mysql://%s/%s", config.getProperty("hostname"),
					config.getProperty("database"));
			System.out.println("uri = " + uri);
			// Create database login properties
			Properties login = new Properties();
			login.put("user", config.getProperty("username"));
			login.put("password", config.getProperty("password"));
			PreparedStatement sql; // prepared statement
			try (Connection dbConnection = DriverManager.getConnection(uri, login);) {
				sql = dbConnection.prepareStatement("select * from students where id>? and GPA >= ?");

				sql.setInt(1, 2);
				sql.setDouble(2, 3.9);

				ResultSet results = sql.executeQuery();
				// check the number of columns
				ResultSetMetaData rsmd = (ResultSetMetaData) results.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				while (results.next()) { // go along rows using the iterator
					// iterate along columns
					for (int i = 0; i < columnsNumber; i++)
						System.out.print(results.getString(i + 1) + " ");
					System.out.println();
				}
			}
		} catch (Exception e) {
			System.err.println("Unable to connect properly to database.");
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Connects to the mysql database using the database.properties config
	 * information. and executes a simple query. Prints the results to the
	 * console.
	 * 
	 * @param args
	 *            unused
	 */
	public static void main(String[] args) {
		JDBCExample test = new JDBCExample();
		test.connectToDatabase();
	}
}

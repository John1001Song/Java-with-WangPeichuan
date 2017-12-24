package model;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.DatabaseConnector;
import server.Status;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Handles all database-related actions. Uses singleton design pattern.
 * Example of Prof. Engle
 *
 *
 */
public class LoginDatabaseHandler {

	/** A {@link org.apache.log4j.Logger log4j} logger for debugging. */
	private static Logger log = LogManager.getLogger();

	/** Makes sure only one database handler is instantiated. */
	private static LoginDatabaseHandler singleton = new LoginDatabaseHandler();

	/** Used to check if the user exists in the visitRecord table. */
	private static final String CHECK_USER_IN_THIS_LOGIN_DATE_SQL =
			"SELECT count(userName) FROM thisLoginDate WHERE username=?";

	/** Used to check if the user exists in the visitRecord table. */
	private static final String CHECK_USER_IN_LAST_LOGIN_DATE_SQL =
			"SELECT count(userName) FROM lastLoginDate WHERE username=?";

	/** Used to get the user's this login date. */
	private static final String GET_THIS_LOGIN_DATE_SQL =
			"SELECT loginDate FROM thisLoginDate WHERE userName=?";

	/** Used to get the user's visited login date. */
	private static final String GET_LAST_LOGIN_DATE_SQL =
			"SELECT loginDate FROM lastLoginDate WHERE userName=?";

	/** Used to update the user's last login date. */
	private static final String UPDATE_LAST_LOGIN_DATE_SQL =
			"UPDATE lastLoginDate SET loginDate=? WHERE username=?;";

	/** Used to update the user's this login date. */
	private static final String UPDATE_THIS_LOGIN_DATE_SQL =
			"UPDATE thisLoginDate SET loginDate=? WHERE username=?;";

	/** Used to insert user's last login date. */
	private static final String INSERT_LAST_LOGIN_DATE_SQL =
			"INSERT INTO lastLoginDate (userName, loginDate) VALUES (?, ?);";

	/** Used to insert user's this login date. */
	private static final String INSERT_THIS_LOGIN_DATE_SQL =
			"INSERT INTO thisLoginDate (userName, loginDate) VALUES (?, ?);";

	/** Used to determine if necessary tables are provided. */
	private static final String TABLES_SQL =
	        "SHOW TABLES LIKE 'login_users';";

	/** Used to create necessary tables for this example. */
	private static final String CREATE_SQL =
			"CREATE TABLE login_users (" +
			"userid INTEGER AUTO_INCREMENT PRIMARY KEY, " +
			"username VARCHAR(32) NOT NULL UNIQUE, " +
			"password CHAR(64) NOT NULL, " +
			"usersalt CHAR(32) NOT NULL);";

	/** Used to insert a new user into the database. */
	private static final String REGISTER_SQL =
			"INSERT INTO login_users (username, password, usersalt) " +
			"VALUES (?, ?, ?);";

	/** Used to determine if a username already exists. */
	private static final String USER_SQL =
			"SELECT username FROM login_users WHERE username = ?";

	/** Used to retrieve the salt associated with a specific user. */
	private static final String SALT_SQL =
			"SELECT usersalt FROM login_users WHERE username = ?";

	/** Used to authenticate a user. */
	private static final String AUTH_SQL =
			"SELECT username FROM login_users " +
			"WHERE username = ? AND password = ?";

	/** Used to remove a user from the database. */
	private static final String DELETE_SQL =
			"DELETE FROM login_users WHERE username = ?";

	/** Used to configure connection to database. */
	private DatabaseConnector db;

	/** Used to generate password hash salt for user. */
	private Random random;

	/**
	 * Initializes a database handler for the Login example. Private constructor
	 * forces all other classes to use singleton.
	 */
	private LoginDatabaseHandler() {
		Status status = Status.OK;
		random = new Random(System.currentTimeMillis());

		try {
		    // TODO Change to "database.properties" or whatever your file is called
			db = new DatabaseConnector("database.properties");
			status = db.testConnection() ? setupTables() : Status.CONNECTION_FAILED;
		}
		catch (FileNotFoundException e) {
			status = Status.MISSING_CONFIG;
		}
		catch (IOException e) {
			status = Status.MISSING_VALUES;
		}

		if (status != Status.OK) {
			log.fatal(status.message());
		}
	}

	/**
	 * Gets the single instance of the database handler.
	 *
	 * @return instance of the database handler
	 */
	public static LoginDatabaseHandler getInstance() {
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
	 * Check if the user exists in the thisLoginDate table.
	 * return 1 if exists, 0 if not exists.
	 * */
	private String checkUserInThisLoginDate(String userName) {
		String flag = "1";

		try (Connection connection = db.getConnection();
		PreparedStatement statement = connection.prepareStatement(CHECK_USER_IN_THIS_LOGIN_DATE_SQL)) {
			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				flag = resultSet.getString("count(userName)");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			flag = "0";
		}

		return flag;
	}

	/**
	 * Check if the user exists in the lastLoginDate table.
	 * return 1 if exists, 0 if not exists.
	 * */
	private String checkUserInLastLoginDate(String userName) {
		String flag = "1";

		try (Connection connection = db.getConnection();
			 PreparedStatement statement = connection.prepareStatement(CHECK_USER_IN_LAST_LOGIN_DATE_SQL)) {
			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				flag = resultSet.getString("count(userName)");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			flag = "0";
		}

		return flag;
	}



	/**
	 * Update the last login date from the this login date database,
	 * when users logout.
	 * */
	public Status updateLastLoginDate(String userName) {
		Status status = Status.ERROR;
		String flag = "0";
		String thisLoginDate = getThisLoginDte(userName);
		String lastLoginDate = "";

		try (Connection connection = db.getConnection()) {

			PreparedStatement statement = connection.prepareStatement(UPDATE_LAST_LOGIN_DATE_SQL);

			flag = checkUserInLastLoginDate(userName);

			if (flag.equals("0")) {
				statement = connection.prepareStatement(INSERT_LAST_LOGIN_DATE_SQL);
				lastLoginDate = thisLoginDate;
				statement.setString(1, userName);
				statement.setString(2, lastLoginDate);
				statement.executeUpdate();
			} else {
				statement.setString(1, thisLoginDate);
				statement.setString(2, userName);
				statement.executeUpdate();
			}

			System.out.println("flag in the insertDate func login db handler: " + flag);
			System.out.println("new last login date in lastLoginDate: " + thisLoginDate);


			status = Status.OK;
		} catch (SQLException e) {
			status = Status.ERROR;
			e.printStackTrace();
		}

		return  status;
	}

	/**
	 * Get this user's this login date.
	 * */
	public String getThisLoginDte(String userName) {
		String loginDate = "";

		try (Connection connection = db.getConnection()) {

			PreparedStatement statement = connection.prepareStatement(GET_THIS_LOGIN_DATE_SQL);

			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				loginDate = resultSet.getString("loginDate");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return loginDate;
	}

	/**
	 * Get the user's last login date.
	 * */
	public String getLastLoginDate(String userName) {
		String loginDate = "";

		try (Connection connection = db.getConnection();
			 PreparedStatement statement = connection.prepareStatement(GET_LAST_LOGIN_DATE_SQL);) {

			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				loginDate = resultSet.getString("loginDate");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return loginDate;
	}

	protected String getDate() {
		String format = "hh:mm a 'on' EEE, MMM dd, yyyy";
//		String format = "yyyy-MM-dd";
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(Calendar.getInstance().getTime());
	}

	/**
	 * Insert user's visit date.
	 * */
	public Status insertThisLoginDate(String userName, String visitDate) {
		Status status = Status.ERROR;
		String flag = "0";
		String newVisitDate = "";
		try (Connection connection = db.getConnection()) {

			PreparedStatement statement = connection.prepareStatement(INSERT_THIS_LOGIN_DATE_SQL);

			flag = checkUserInThisLoginDate(userName);

			if (flag.equals("1")) {
				statement = connection.prepareStatement(UPDATE_THIS_LOGIN_DATE_SQL);
				newVisitDate = getDate();
				statement.setString(1, newVisitDate);
				statement.setString(2, userName);
				statement.executeUpdate();
			} else {
				statement.setString(1, userName);
				statement.setString(2, visitDate);
				statement.executeUpdate();
			}

			System.out.println("flag in the insertDate func login db handler: " + flag);
			System.out.println("new login date in thisLoginDate: " + newVisitDate);

			status = Status.OK;
		} catch (SQLException e) {
			e.printStackTrace();
			status = Status.ERROR;
		}

		return status;
	}

	/**
	 * Checks if necessary table exists in database, and if not tries to
	 * create it.
	 *
	 * @return {@link Status.OK} if table exists or create is successful
	 */
	private Status setupTables() {
		Status status = Status.ERROR;

		try (
        		Connection connection = db.getConnection();
        		Statement statement = connection.createStatement();
        ) {
			if (!statement.executeQuery(TABLES_SQL).next()) {
			    // Table missing, must create
				log.debug("Creating tables...");
				statement.executeUpdate(CREATE_SQL);

				// Check if create was successful
				if (!statement.executeQuery(TABLES_SQL).next()) {
				    status = Status.CREATE_FAILED;
				}
				else {
				    status = Status.OK;
				}
			}
			else {
				log.debug("Tables found.");
				status = Status.OK;
			}
		}
		catch (Exception ex) {
			status = Status.CREATE_FAILED;
			log.debug(status, ex);
		}

		return status;
	}

	/**
	 * Tests if a user already exists in the database. Requires an active
	 * database connection.
	 *
	 * @param connection - active database connection
	 * @param user - username to check
	 * @return Status.OK if user does not exist in database
	 * @throws SQLException
	 */
	private Status duplicateUser(Connection connection, String user) {

		assert connection != null;
		assert user != null;

		Status status = Status.ERROR;

		try (
			PreparedStatement statement = connection.prepareStatement(USER_SQL);
        ) {
			statement.setString(1, user);

			ResultSet results = statement.executeQuery();
			status = results.next() ? Status.DUPLICATE_USER : Status.OK;
		}
		catch (SQLException e) {
			log.debug(e.getMessage(), e);
			status = Status.SQL_EXCEPTION;
		}

		return status;
	}

	/**
	 * Tests if a user already exists in the database.
	 *
	 * @see #duplicateUser(Connection, String)
	 * @param user - username to check
	 * @return Status.OK if user does not exist in database
	 */
	public Status duplicateUser(String user) {
		Status status = Status.ERROR;

		try (
			Connection connection = db.getConnection();
		) {
			status = duplicateUser(connection, user);
		}
		catch (SQLException e) {
			status = Status.CONNECTION_FAILED;
			log.debug(e.getMessage(), e);
		}

		return status;
	}

	/**
	 * Returns the hex encoding of a byte array.
	 *
	 * @param bytes - byte array to encode
	 * @param length - desired length of encoding
	 * @return hex encoded byte array
	 */
	public static String encodeHex(byte[] bytes, int length) {
		BigInteger bigint = new BigInteger(1, bytes);
		String hex = String.format("%0" + length + "X", bigint);

		assert hex.length() == length;
		return hex;
	}

	/**
	 * Calculates the hash of a password and salt using SHA-256.
	 *
	 * @param password - password to hash
	 * @param salt - salt associated with user
	 * @return hashed password
	 */
	public static String getHash(String password, String salt) {
		String salted = salt + password;
		String hashed = salted;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salted.getBytes());
			hashed = encodeHex(md.digest(), 64);
		}
		catch (Exception ex) {
			log.debug("Unable to properly hash password.", ex);
		}

		return hashed;
	}

	/**
	 * Registers a new user, placing the username, password hash, and
	 * salt into the database if the username does not already exist.
	 *
	 * @param newuser - username of new user
	 * @param newpass - password of new user
	 * @return {@link Status.OK} if registration successful
	 */
	private Status registerUser(Connection connection, String newuser, String newpass) {

		Status status = Status.ERROR;

		byte[] saltBytes = new byte[16];
		random.nextBytes(saltBytes);

		String usersalt = encodeHex(saltBytes, 32);
		String passhash = getHash(newpass, usersalt);

		try (
			PreparedStatement statement = connection.prepareStatement(REGISTER_SQL);
        ) {
			statement.setString(1, newuser);
			statement.setString(2, passhash);
			statement.setString(3, usersalt);
			statement.executeUpdate();

			status = Status.OK;
		}
		catch (SQLException ex) {
			status = Status.SQL_EXCEPTION;
			log.debug(ex.getMessage(), ex);
		}

		return status;
	}

	/**
	 * Registers a new user, placing the username, password hash, and
	 * salt into the database if the username does not already exist.
	 *
	 * @param newuser - username of new user
	 * @param newpass - password of new user
	 * @return {@link Status.OK} if registration successful
	 */
	public Status registerUser(String newuser, String newpass) {
		Status status = Status.ERROR;
		log.debug("Registering " + newuser + ".");

		// make sure we have non-null and non-emtpy values for login
		if (isBlank(newuser) || isBlank(newpass)) {
			status = Status.INVALID_LOGIN;
			log.debug(status);
			return status;
		}

		// try to connect to database and test for duplicate user
		System.out.println(db);

		try (
			Connection connection = db.getConnection();
		) {
			status = duplicateUser(connection, newuser);

			// if okay so far, try to insert new user
			if (status == Status.OK) {
				status = registerUser(connection, newuser, newpass);
			}
		}
		catch (SQLException ex) {
			status = Status.CONNECTION_FAILED;
			log.debug(status, ex);
		}

		return status;
	}

	/**
	 * Gets the salt for a specific user.
	 *
	 * @param connection - active database connection
	 * @param user - which user to retrieve salt for
	 * @return salt for the specified user or null if user does not exist
	 * @throws SQLException if any issues with database connection
	 */
	private String getSalt(Connection connection, String user) throws SQLException {
		assert connection != null;
		assert user != null;

		String salt = null;

		try (
	        PreparedStatement statement = connection.prepareStatement(SALT_SQL);
        ) {
		    statement.setString(1, user);

		    ResultSet results = statement.executeQuery();

        		if (results.next()) {
        			salt = results.getString("usersalt");
        		}
		}

		return salt;
	}

	/**
	 * Checks if the provided username and password match what is stored
	 * in the database. Requires an active database connection.
	 *
	 * @param username - username to authenticate
	 * @param password - password to authenticate
	 * @return {@link Status.OK} if authentication successful
	 * @throws SQLException
	 */
	private Status authenticateUser(Connection connection, String username,
			String password) throws SQLException {

		Status status = Status.ERROR;

		try (
	        PreparedStatement statement = connection.prepareStatement(AUTH_SQL);
        ) {
			String usersalt = getSalt(connection, username);
			String passhash = getHash(password, usersalt);

			statement.setString(1, username);
			statement.setString(2, passhash);

			ResultSet results = statement.executeQuery();
			status = results.next() ? status = Status.OK : Status.INVALID_LOGIN;
		}
		catch (SQLException e) {
			log.debug(e.getMessage(), e);
			status = Status.SQL_EXCEPTION;
		}

		return status;
	}

	/**
	 * Checks if the provided username and password match what is stored
	 * in the database. Must retrieve the salt and hash the password to
	 * do the comparison.
	 *
	 * @param username - username to authenticate
	 * @param password - password to authenticate
	 * @return {@link Status.OK} if authentication successful
	 */
	public Status authenticateUser(String username, String password) {
		Status status = Status.ERROR;

		log.debug("Authenticating user " + username + ".");

		try (
			Connection connection = db.getConnection();
        ) {
			status = authenticateUser(connection, username, password);
		}
		catch (SQLException ex) {
			status = Status.CONNECTION_FAILED;
			log.debug(status, ex);
		}

		return status;
	}

	/**
	 * Removes a user from the database if the username and password are
	 * provided correctly.
	 *
	 * @param username - username to remove
	 * @param password - password of user
	 * @return {@link Status.OK} if removal successful
	 */
	private Status removeUser(Connection connection, String username, String password) {
		Status status = Status.ERROR;

		try (
			PreparedStatement statement = connection.prepareStatement(DELETE_SQL);
        ) {
			statement.setString(1, username);

			int count = statement.executeUpdate();
			status = (count == 1) ? Status.OK : Status.INVALID_USER;
		}
		catch (SQLException ex) {
			status = Status.SQL_EXCEPTION;
			log.debug(status, ex);
		}

		return status;
	}

	/**
	 * Removes a user from the database if the username and password are
	 * provided correctly.
	 *
	 * @param username - username to remove
	 * @param password - password of user
	 * @return {@link Status.OK} if removal successful
	 */
	public Status removeUser(String username, String password) {
		Status status = Status.ERROR;

		log.debug("Removing user " + username + ".");

		try (
			Connection connection = db.getConnection();
        ) {
			status = authenticateUser(connection, username, password);

			if(status == Status.OK) {
				status = removeUser(connection, username, password);
			}
		}
		catch (Exception ex) {
			status = Status.CONNECTION_FAILED;
			log.debug(status, ex);
		}

		return status;
	}
}

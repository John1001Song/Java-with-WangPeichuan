package server;

import handler.HotelDatabaseHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles display of user information.
 * Example of Prof. Engle
 * @see LoginServer
 */
@SuppressWarnings("serial")
public class LoginWelcomeServlet extends LoginBaseServlet {

	// display the hotels in the welcome page
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String user = getUsername(request);

		if (user != null) {
			prepareResponse("Welcome", response);
			// use hotel database handler to catch all hotel info
			HotelDatabaseHandler hotelDatabaseHandler = HotelDatabaseHandler.getInstance();
//			ArrayList allHotelInfo = hotelDatabaseHandler.displayAllHotels();

			PrintWriter out = response.getWriter();
			out.println("<h2>Hello " + user + "!</h2>");
			out.println("<h2><a href=\"/manageMyReviews?userName=" + user + "\">Manage My Reviews</a></p>");
			out.println("<p><a href=\"/login?logout\">(logout)</a></p>");

			// merge hotels info into html format
//			Iterator iterator = allHotelInfo.iterator();
//			while (iterator.hasNext()) {
//				out.println(iterator.next());
//			}

			finishResponse(response);
		}
		else {
			response.sendRedirect("/login");
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doGet(request, response);
	}
}
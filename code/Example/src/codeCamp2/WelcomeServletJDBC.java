package codeCamp2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.*;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Modified Welcome Servlet that stores the name of the user in the database.
 * Requires database.properties and a tunneling command
 *
 */
public class WelcomeServletJDBC extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		// checkName should be inside the js folder -
		// that should be one level up compared to src folder
		out.println("<script src =\"checkName.js\">");
		out.println("</script>");
		out.println("</head>");

		out.println("<body>");
		out.println("<h1>Please fill out this form </h1>");
		out.println("<form action = \"/welcome\" method = \"POST\"" + ">");// form
		// will
		// be
		// processed
		// by
		// post
		out.println("<input type= \"text\" onkeyup = \"checkName(this);\" name = \"username\" >");

		out.println("<p><input type=\"submit\" value=\"Submit\" ></p>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");

		response.setStatus(HttpServletResponse.SC_OK);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		if (username == null || username.isEmpty())
			username = "anonymous";
		else
			username = StringEscapeUtils.escapeHtml4(username);

		// store in the database
		DatabaseHandler.getInstance().insertNameIntoDatabase(username);

		out.println("Welcome," + username + "!");
		out.println("Your name was stored in the database");

		response.setStatus(HttpServletResponse.SC_OK);
	} // doPost
} // WelcomeServlet class

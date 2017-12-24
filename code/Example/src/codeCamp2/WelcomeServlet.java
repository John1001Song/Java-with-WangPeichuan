package codeCamp2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.*;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Welcome servlet saves the name entered by the user in a cookie.
 * The first time the user visits "/welcome" webpage, the servlet should
 * display an HTML form asking the user for the name;
 *  once the user submits the form, the servlet saves the name
 * in a cookie. Whenever the user comes to the "/welcome" webpage again, the
 * server would take the user directly to the page that prints "Welcome" and the name.
 */
public class WelcomeServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		// check if we have a cookie called "username"
		Cookie[] cookies = request.getCookies();
		String username = "";
		boolean foundUsernameCookie = false;
		int ind = 0;
		while ((cookies != null) && (!foundUsernameCookie) && (ind < cookies.length)) {
			Cookie cookie = cookies[ind];
			if (cookie.getName().equals("username")) {
				username = cookie.getValue();
				foundUsernameCookie = true;
			}
			ind++;
		}
		if (foundUsernameCookie) {
			username = StringEscapeUtils.escapeHtml4(username);
			out.println("Welcome," + username + "!");
		} else {
			out.println("<html>");
			out.println("<head>");

			// checkName should be inside the js folder
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
		}
		response.setStatus(HttpServletResponse.SC_OK);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		if (username == null || username.isEmpty())
			username = "anonymous";
		else
			username = StringEscapeUtils.escapeHtml4(username);
		out.println("Welcome," + username + "!");
		
		// add a cookie to the response
		Cookie cookie = new Cookie("username", username);
		response.addCookie(cookie);
		response.setStatus(HttpServletResponse.SC_OK);
	} // doPost
} // WelcomeServlet class

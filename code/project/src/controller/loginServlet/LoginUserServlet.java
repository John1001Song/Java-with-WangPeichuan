package controller.loginServlet;

import controller.Hotel;
import model.LoginDatabaseHandler;
import org.apache.commons.lang.StringEscapeUtils;
import server.LoginBaseServlet;
import server.Status;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;



/**
 * Handles login requests.
 * Example of Prof. Engle
 */
@SuppressWarnings("serial")
public class LoginUserServlet extends LoginBaseServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out = response.getWriter();

//		String user = request.getParameter("username");
//		String pass = request.getParameter("password");
////		Status status = dbhandler.authenticateUser(user, pass);


//		Cookie[] cookies = request.getCookies();
		Map<String, String> cookies = getCookieMap(request);

		String userNameInCookie = cookies.get("userName");
		String loginStatusInCookie = cookies.get("login");
		String visitDate = cookies.get("visitDate");

//		if (cookies != null) {
//			for (Cookie cookie : cookies) {
//				if (cookie.getName().equals("name")) {
//					userNameInCookie = cookie.getValue();
//				}
//				if (cookie.getName().equals("login")) {
//					loginStatusInCookie = cookie.getValue();
//				}
//				if (cookie.getName().equals("loginDate")) {
//					loginDateLastTime = cookie.getValue();
//				}
//			}
//		}
		System.out.println("userName In Cookie in login user servlet: " + userNameInCookie);
		System.out.println("loginStatus In Cookie: " + loginStatusInCookie);
		System.out.println("visitDate in cookie: " + visitDate);

		if (userNameInCookie != null && loginStatusInCookie.equals("true")
				&& !userNameInCookie.equals("anonymous")) {
			response.sendRedirect(response.encodeRedirectURL("/welcome"));
		}


		// Get the "name" parameter from the GET request
		// The request may look like this: /hotels?name=Ali
//		String name = request.getParameter("username");
//		if (name == null || name.isEmpty() || name.equals(""))
//			name = "anonymous";
//		name = StringEscapeUtils.escapeHtml4(name);

		VelocityEngine ve = (VelocityEngine) request.getServletContext().getAttribute("templateEngine");
		VelocityContext context = new VelocityContext();
		Template template = ve.getTemplate("view/LoginUser.html");
		// Comment the line above and uncomment the line below for a more complex template:
		//Template template = ve.getTemplate("templates/travelAdvisor.html");

//		context.put("name", name);
//		System.out.println("name gotten in login user servlet: " +  name);

		// usually the data would come from some kind of database. Using a
		// simple ArrayList here instead
//		ArrayList<Hotel>  hotels = new ArrayList<>();
//		hotels.add(new Hotel("Sheraton Pier 39"));
//		hotels.add(new Hotel("Best Western SF Downtown"));
//		hotels.add(new Hotel("Marriott SF Airport"));

//		context.put("hotels", hotels);

		//Template template = ve.getTemplate("templates/bootstrap.html");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		out.println(writer.toString());

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String user = request.getParameter("username");
		String pass = request.getParameter("password");
		Status status = dbhandler.authenticateUser(user, pass);
		System.out.println("uerName in doPost login user Servlet: " + user);
		System.out.println("password in doPost login user servlet: " + pass);

//		HttpSession session = request.getSession();
//		String visitDate = (String) session.getAttribute("date");
//		System.out.println("visitDate session: " + visitDate);

		// should set session date here
		// get session date in welcome page; implement later

//		HttpSession session = request.getSession();


		try {
			if (status == Status.OK) {
				// should eventually change this to something more secure
				response.addCookie(new Cookie("login", "true"));

				response.addCookie(new Cookie("userName", user));

//				response.addCookie(new Cookie("loginDate", getDate()));
				LoginDatabaseHandler loginDatabaseHandler = LoginDatabaseHandler.getInstance();
				loginDatabaseHandler.insertThisLoginDate(user, getDate());
				System.out.println("login date in the login servlet doPost: " + getDate());

				response.sendRedirect(response.encodeRedirectURL("/welcome"));
			}
			else {
				response.addCookie(new Cookie("login", "false"));
				response.addCookie(new Cookie("userName", ""));
				response.sendRedirect(response.encodeRedirectURL("/login?error=" + status.ordinal()));
			}
		}
		catch (Exception ex) {
			log.error("Unable to process login form.", ex);
		}
	}

//	@Override
//	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		prepareResponse("Login", response);
//		PrintWriter out = response.getWriter();
//		String error = request.getParameter("error");
//		int code = 0;
//
//		if (error != null) {
//			try {
//				code = Integer.parseInt(error);
//			}
//			catch (Exception ex) {
//				code = -1;
//			}
//
//			String errorMessage = getStatusMessage(code);
//			out.println("<p style=\"color: red;\">" + errorMessage + "</p>");
//		}
//
//		if (request.getParameter("newuser") != null) {
//			out.println("<p>Registration was successful!");
//			out.println("Login with your new username and password below.</p>");
//		}
//
//		if (request.getParameter("logout") != null) {
//			clearCookies(request, response);
//			out.println("<p>Successfully logged out.</p>");
//		}
//
//		printForm(out);
//		finishResponse(response);
//	}
//
//	@Override
//	public void doPost(HttpServletRequest request, HttpServletResponse response) {
//		String user = request.getParameter("user");
//		String pass = request.getParameter("pass");
//
//		Status status = dbhandler.authenticateUser(user, pass);
//
//		try {
//			if (status == Status.OK) {
//				// should eventually change this to something more secure
//				response.addCookie(new Cookie("login", "true"));
//				response.addCookie(new Cookie("name", user));
//				response.sendRedirect(response.encodeRedirectURL("/welcome"));
//			}
//			else {
//				response.addCookie(new Cookie("login", "false"));
//				response.addCookie(new Cookie("name", ""));
//				response.sendRedirect(response.encodeRedirectURL("/login?error=" + status.ordinal()));
//			}
//		}
//		catch (Exception ex) {
//			log.error("Unable to process login form.", ex);
//		}
//	}
//
//	private void printForm(PrintWriter out) {
//		assert out != null;
//
//		out.println("<form action=\"/login\" method=\"post\">");
//		out.println("<table border=\"0\">");
//		out.println("\t<tr>");
//		out.println("\t\t<td>Usename:</td>");
//		out.println("\t\t<td><input type=\"text\" name=\"user\" size=\"30\"></td>");
//		out.println("\t</tr>");
//		out.println("\t<tr>");
//		out.println("\t\t<td>Password:</td>");
//		out.println("\t\t<td><input type=\"password\" name=\"pass\" size=\"30\"></td>");
//		out.println("</tr>");
//		out.println("</table>");
//		out.println("<p><input type=\"submit\" value=\"Login\"></p>");
//		out.println("</form>");
//
//		out.println("<p>(<a href=\"/register\">new user? register here.</a>)</p>");
//	}
}
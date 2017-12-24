package controller.loginServlet;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import server.LoginBaseServlet;
import server.Status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

// Example of Prof. Engle

@SuppressWarnings("serial")
public class LoginRegisterServlet extends LoginBaseServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

//		prepareResponse("Register New User", response);

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out = response.getWriter();

		String error = request.getParameter("error");

		if(error != null) {
			String errorMessage = getStatusMessage(error);
//			out.println("<p style=\"color: red;\">" + errorMessage + "</p>");
		}


		VelocityEngine ve = (VelocityEngine) request.getServletContext().getAttribute("templateEngine");
		VelocityContext context = new VelocityContext();
		Template template = ve.getTemplate("view/LoginRegister.html");

//		printForm(out);
//		finishResponse(response);

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		out.println(writer.toString());

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
//		prepareResponse("Register New User", response);

		String newuser = request.getParameter("user");
		System.out.println("new user: " + newuser);
		String newpass = request.getParameter("pass");
		System.out.println("new password: " + newpass);
		Status status = dbhandler.registerUser(newuser, newpass);

		if(status == Status.OK) {
			response.sendRedirect(response.encodeRedirectURL("/login?newuser=true"));
		}
		else {
			String url = "/register?error=" + status.name();
			url = response.encodeRedirectURL(url);
			response.sendRedirect(url);
		}
	}

	private void printForm(PrintWriter out) {
		assert out != null;

		out.println("<form action=\"/register\" method=\"post\">");
		out.println("<table border=\"0\">");
		out.println("\t<tr>");
		out.println("\t\t<td>Usename:</td>");
		out.println("\t\t<td><input type=\"text\" name=\"user\" size=\"30\"></td>");
		out.println("\t</tr>");
		out.println("\t<tr>");
		out.println("\t\t<td>Password:</td>");
		out.println("\t\t<td><input type=\"password\" name=\"pass\" size=\"30\"></td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<p><input type=\"submit\" value=\"Register\"></p>");
		out.println("</form>");
	}
}
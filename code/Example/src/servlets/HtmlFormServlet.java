package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
//import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/** An example that demonstrates how to process HTML forms with servlets.*/
@SuppressWarnings("serial")
public class HtmlFormServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println();
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out = response.getWriter();
		out.printf("<html>%n%n");
		out.printf("<head><title>%s</title></head>%n", "Form");

		out.printf("<body>%n");
		// display HTML Form
		printForm(request, response);

		out.printf("%n</body>%n");
		out.printf("</html>%n");

		response.setStatus(HttpServletResponse.SC_OK);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// will be called when the user submits the html form
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		String nameParam = request.getParameter("name");
		if (nameParam == null)
			nameParam = "anonymous";
		nameParam = StringEscapeUtils.escapeHtml4(nameParam);

		response.setStatus(HttpServletResponse.SC_OK);
		// Redirect to another url, send name in the get request
		// Going to this url will invoke a welcome servlet
		response.sendRedirect("/welcome?name=" + nameParam);

	}

	private static void printForm(HttpServletRequest request, HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();

		out.printf("<form method=\"post\" action=\"%s\">%n", request.getServletPath());
		out.printf("Enter name:<br><input type=\"text\" name=\"name\"><br>");
		out.printf("<p><input type=\"submit\" value=\"Enter\"></p>\n%n");
		out.printf("</form>\n%n");
	}

}
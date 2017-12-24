package velocity;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

@SuppressWarnings("serial")
public class ShowHotelsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out = response.getWriter();

		// Get the "name" parameter from the GET request
		// The request may look like this: /hotels?name=Ali
		String name = request.getParameter("name");
		if (name == null || name.isEmpty())
			name = "anonymous";
		name = StringEscapeUtils.escapeHtml4(name);

		VelocityEngine ve = (VelocityEngine) request.getServletContext().getAttribute("templateEngine");
		VelocityContext context = new VelocityContext();
		Template template = ve.getTemplate("templates/hotelInfo.html");
		// Comment the line above and uncomment the line below for a more complex template:
		//Template template = ve.getTemplate("templates/travelAdvisor.html");

		context.put("name", name);

		// usually the data would come from some kind of database. Using a
		// simple ArrayList here instead
		ArrayList<Hotel>  hotels = new ArrayList<>();
		hotels.add(new Hotel("Sheraton Pier 39"));
		hotels.add(new Hotel("Best Western SF Downtown"));
		hotels.add(new Hotel("Marriott SF Airport"));

		context.put("hotels", hotels);
		
		//Template template = ve.getTemplate("templates/bootstrap.html");

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		out.println(writer.toString());

	}
}
package JDBCExamples;

/** Demonstrates how to pass an object from the server to a servlet using  ServletContextHandler.
 *  The example is modified from the following example: 
 *  http://stackoverflow.com/questions/39421686/jetty-pass-object-from-main-method-to-servlet/39440430#39440430
 * 
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ObjectPassingExample {
	public static void main(String args[]) throws Exception {
		Server server = new Server(8080);
		Student st = new Student("Jones"); // lets say we want the servlet to
											// access the name of this student
		
		ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");
		// Using ServletContext attribute
		context.setAttribute("student", st);
		context.addServlet(WelcomeServlet.class, "/welcome/*");

		server.setHandler(context);
		server.start();
		server.join();
	}


	@SuppressWarnings("serial")
	public static class WelcomeServlet extends HttpServlet {

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			resp.setContentType("text/plain");
			Student st = (Student) getServletContext().getAttribute("student");
			String name = st.getName();
			name = StringEscapeUtils.escapeHtml4(name); // sanitize the input

			resp.getWriter().println("Welcome, " + name + "!");
		}
	}
}
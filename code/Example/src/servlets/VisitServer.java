package servlets;
// Example is courtesy of Prof. Engle

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * A simple example of using Jetty and servlets to track the number of
 * visitors to a web page, demonstrating that this code is run in a multi-
 * threaded setting.
 */
public class VisitServer {

	public static final int PORT = 8090;

	// Keep in mind our server is multithreaded, so we need to use
	// storage safe for access by multiple threads simultaneously
	private static AtomicInteger visits = new AtomicInteger();

	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(VisitServlet.class, "/");

		server.setHandler(handler);
		server.start();
		server.join();
	}

	@SuppressWarnings("serial")
	public static class VisitServlet extends HttpServlet {

		private static final String TITLE = "Visits";

		@Override
		protected void doGet(
				HttpServletRequest request,
				HttpServletResponse response)
				throws ServletException, IOException {

			if (request.getRequestURI().endsWith("favicon.ico")) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

		    // Output request for debugging (better if use logger)
		    System.out.println(Thread.currentThread().getName() + ": " + request.getRequestURI());

			response.setContentType("text/html");

			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head><title>" + TITLE + "</title></head>");
			out.println("<body>");

			// Safely increment the number of visits to this website
			int current = visits.incrementAndGet();
			out.println("<p>There have been " + current + " visits to this page.");

			// Demonstrate that this servlet is called by different threads
			out.println("<p>This request was handled by thread " + Thread.currentThread().getName() + "</p>");

			out.println("</body>");
			out.println("</html>");

			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
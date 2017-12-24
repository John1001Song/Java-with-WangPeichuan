package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * A simple web server that sends the same response for each GET request (an
 * html page that says Hello, friends!)
 *
 */
public class SimpleHelloServer {

	public static final int PORT = 8081;

	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		ServletHandler handler = new ServletHandler();

		// when the user goes to http://localhost:8080/hello, the get request is
		// going to go to a SimpleHelloServlet
		handler.addServletWithMapping(SimpleHelloServlet.class, "/welcome");
		//handler.addServletWithMapping(SimpleByeServlet.class, "/bye");

		server.setHandler(handler);

		server.start();
		server.join();
	}
}
package velocity;

import org.apache.velocity.app.VelocityEngine;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
  A webserver that shows a list of hotels. Demonstrates how to use Velocity template engine.
  To test in the browser, run and go to localhost:8080/hotels
  Need to add the following to the buildpath:
  velocity-1.7-dep.jar, velocity-1.7.jar, and Jetty, Servlets, ApacheCommonsLang
 *
 */
public class HotelServer {

	public static final int PORT = 8080;

	public static void main(String[] args)  {
		Server server = new Server(8080);
		ServletContextHandler context = new ServletContextHandler();
        context.addServlet(ShowHotelsServlet.class, "/hotels");
       
        // initialize Velocity
        VelocityEngine velocity = new VelocityEngine();
		velocity.init();

		// set velocity as an attribute of the context so that we can access it
		// from servlets
        context.setContextPath("/");
        context.setAttribute("templateEngine", velocity);
		server.setHandler(context);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
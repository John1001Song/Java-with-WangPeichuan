package javascript;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

/** Javascript/ AJAX examples. Run this server first. Then go to localhost:8080.
 * -Open file exampleInputValidation.html  - shows how to do input validation in Javascript.
 * -Open ajaxEx.html. It is the first example that uses AJAX. It makes AJAX calls to CounterSerlet.
 * -Open ajaxDBEx.html. It is the second example that uses AJAX. It makes AJAX calls to the AccessDatabaseServlet,
 *  that retrieves information from the database. This example requires database.properties file in the project folder
 *  (make sure to edit it as needed) and it assumes that you:
 *    - have a "students" table in your database that contains name, graduateDate, GPA
 *    - are running a tunnelling command to connect to mysql
 * Example modified from Prof. Rollins' example  **/
public class JSServer {

	public static void main(String[] args) {


		org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(8080);

		ServletContextHandler servhandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

		// a handler for serving static html pages from the static/ folder
		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(true);
		resource_handler.setResourceBase("static");

		// for ajaxEx example
		servhandler.addServlet(CounterServlet.class, "/counter");
		
		// for the ajaxDBDemo example
		servhandler.addServlet(AccessDatabaseServlet.class, "/students");

		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { resource_handler, servhandler });
		server.setHandler(handlers);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			System.out.println("Exception occured while running the server: " + e);
		}

	}

}

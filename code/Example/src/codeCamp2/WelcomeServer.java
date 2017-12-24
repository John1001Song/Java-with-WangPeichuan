package codeCamp2;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

public class WelcomeServer {
	private static int PORT = 8080;

	public static void main(String[] args) {
		Server server = new Server(PORT);

		ServletHandler handler = new ServletHandler();
		//handler.addServletWithMapping(WelcomeServlet.class, "/welcome");
		handler.addServletWithMapping(WelcomeServletJDBC.class, "/welcome");


		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setResourceBase("js");

		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { resource_handler, handler });
		server.setHandler(handlers);

		try {
			server.start();
			server.join();
		} catch (Exception ex) {
			System.out.println("An exception while running the server " + ex);
		}
	}
}

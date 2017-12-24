package servlets;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * A simple example of using Jetty and servlets to serve files.
 * @author: Prof. Engle
 */
public class SimpleFileServer {

	public static final int PORT = 8080;

	public static void main(String[] args) throws Exception {

		Server server = new Server(PORT);

		// Add static resource holders to web server
		// This indicates where web files are accessible on the file system
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);

		resourceHandler.setResourceBase("src/regex"); // a directory from where we want to serve files
												// The directory needs to exist in the project
		server.setHandler(resourceHandler); 

		
		// optional - if you want to map /files url path to the same resource handler
		/*ContextHandler ctx = new ContextHandler("/files"); // url
		ctx.setHandler(resourceHandler);
		
		// Setup handlers (and handler order)
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { resourceHandler, ctx}); 
		server.setHandler(handlers);
		*/
		
		server.start();
		server.join();

		
	}
}
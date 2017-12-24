package server;

import hotelapp.ThreadSafeHotelData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.apache.velocity.app.VelocityEngine;


import java.nio.file.Paths;

/**
 * Demonstrates simple user registration, login, and session tracking. This
 * is a simplified example, and **NOT** secure.
 * This comprehensive example is provided by Prof. Engle.
 */
public class ProjectServer {
    protected static Logger log = LogManager.getLogger();
    private static int PORT = 8080;
//    private static int PORT = 2000;

    private static final String hotelDir = "input/hotels.json";
    private static final String reviewDir = "input/reviews";

    public static void main(String[] args) {

        // load hotel info and reviews
        ThreadSafeHotelData hotelData = new ThreadSafeHotelData();
        hotelData.loadHotelInfo(hotelDir);
        hotelData.loadReviews(Paths.get(reviewDir));

        // insert hotels info and reviews at the first time to turn on the server
        // later to comment this functions because it is enough to insert once
         InsertHotelAndReviewToDatabase insertHotelAndReviewToDatabase = new InsertHotelAndReviewToDatabase();

        // connect to the database
        try {
            // TODO Change to database.properties (or whatever you named your properties file)!
            DatabaseConnector test = new DatabaseConnector("database.properties");
            System.out.println("Connecting to " + test.uri);

            if (test.testConnection()) {
                System.out.println("Connection to database established.");
            }
            else {
                System.err.println("Unable to connect properly to database.");
            }
        }
        catch (Exception e) {
            System.err.println("Unable to connect properly to database.");
            System.err.println(e.getMessage());
        }

        // turn on the server
        Server server = new Server(PORT);

//        ServletHandler handler = new ServletHandler();
//        server.setHandler(handler);
//
//        handler.addServletWithMapping(LoginUserServlet.class,     "/login");
//        handler.addServletWithMapping(LoginRegisterServlet.class, "/register");
//        handler.addServletWithMapping(LoginWelcomeServlet.class,  "/welcome");
//        handler.addServletWithMapping(LoginRedirectServlet.class, "/*");
//
//        handler.addServletWithMapping(ReviewServlet.class, "/review");
//        handler.addServletWithMapping(ManageMyReviewsServlet.class, "/manageMyReviews");
//        handler.addServletWithMapping(ReviewSubmitServlet.class, "/reviewSubmit");
//        handler.addServletWithMapping(ReviewEditSubmitServlet.class, "/reviewEditSubmit");
//        handler.addServletWithMapping(ReviewDeleteServlet.class, "/reviewDelete");
//        handler.addServletWithMapping(ReviewRedirectServlet.class, "/reviewRedirect");
//
//        handler.addServletWithMapping(TouristAttractionsDisplayServlet.class, "/touristAttractionsDisplay");
//

        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.addServlet(LoginUserServlet.class, "/login");

        // initialize velocity engine
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        // set velocity as an attribute of the context so that we can access it from servlets
        contextHandler.setContextPath("/");
        contextHandler.setAttribute("tempEngine", velocityEngine);

        server.setHandler(contextHandler);



        log.info("Starting server on port " + PORT + "...");

        try {
            server.start();
            server.join();

            log.info("Exiting...");
        }
        catch (Exception ex) {
            log.fatal("Interrupted while running server.", ex);
            System.exit(-1);
        }
    }
}
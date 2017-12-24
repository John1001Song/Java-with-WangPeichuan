package clientAndServer;

import hotelapp.ThreadSafeHotelData;
import jdk.nashorn.internal.objects.Global;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;


public class Main {
    // PORT: 2000, 2050
    public static final int PORT2000 = 2000;
    public static final int PORT2050 = 2050;

    public static final String hotelDir = "input/hotels.json";
    public static final String hotelDir1 = "input/hotels1.json";
    public static final String hotelDir4 = "input/hotels4.json";

    public static final String reviewsDir = "input/reviews";

    public static void startJetty() throws Exception {

        ThreadSafeHotelData hotelData = new ThreadSafeHotelData();

        hotelData.loadHotelInfo(hotelDir);
        hotelData.loadHotelInfo(hotelDir1);
        hotelData.loadHotelInfo(hotelDir4);
        hotelData.loadReviews(Paths.get(reviewsDir));

        Server server = new Server(PORT2000);

        ServletHandler handler = new ServletHandler();

        handler.addServletWithMapping(new ServletHolder(new HotelServlet(hotelData)), "/hotelInfo");
        handler.addServletWithMapping(new ServletHolder(new ReviewServlet(hotelData)), "/reviews");
        handler.addServletWithMapping(new ServletHolder(new AttractionServlet(hotelData)), "/attractions");

        server.setHandler(handler);

        server.start();
        server.join();
    }

    public static void startRaw() throws Exception {

        ThreadSafeHotelData hotelData = new ThreadSafeHotelData();

        hotelData.loadHotelInfo(hotelDir);
        hotelData.loadReviews(Paths.get(reviewsDir));

        MultiThreadWebServer server = new MultiThreadWebServer();
        server.setHotelData(hotelData);
        server.startServer(2050);

    }

    public static void main(String[] args) throws Exception {

        // turn on Jetty Http Server
        //startJetty();

        // turn on Raw Sockets Http Server
        startRaw();

    }

}

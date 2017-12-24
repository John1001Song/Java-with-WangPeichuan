package server;

import hotelapp.ThreadSafeHotelData;
import hotelapp.TouristAttractionFinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TouristAttractionsDisplayServlet extends TouristAttractionsBaseServlet{

    TouristAttractionFinder touristAttractionFinder;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        prepareResponse("Display the Tourist Attractions.", response);
        PrintWriter out = response.getWriter();

        String hotelId = request.getParameter("hotelId");
        int radius = Integer.valueOf(request.getParameter("radius"));

//        out.println("Hotel ID: " + hotelId + ", Radius: " + radius);

        touristAttractionFinder = new TouristAttractionFinder();
        String[] filteredResult = touristAttractionFinder.filter(touristAttractionFinder.fetchAttractions(hotelId, radius));
        out.println(touristAttractionFinder.printFilteredAtrractions(filteredResult, hotelId));
//        out.println("<form action=\"/welcome method=\"get\">");
//        out.println("<p><input type=\"submit\" value=\"Return to My Homepage\"></form></p>");
//        finishResponse(response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}

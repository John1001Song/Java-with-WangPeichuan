package controller.touristAttractionServlet;

import hotelapp.TouristAttractionFinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TouristAttractionsDisplayServlet extends TouristAttractionsBaseServlet {

    TouristAttractionFinder touristAttractionFinder;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        prepareResponse("Display the Tourist Attractions.", response);
        PrintWriter out = response.getWriter();

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html");

        String hotelId = request.getParameter("hotelId");
        int radius = Integer.valueOf(request.getParameter("radius"));

//        out.println("Hotel ID: " + hotelId + ", Radius: " + radius);

        touristAttractionFinder = new TouristAttractionFinder();
        String[] filteredResult = touristAttractionFinder.filter(touristAttractionFinder.fetchAttractions(hotelId, radius));
        String res = touristAttractionFinder.printFilteredAtrractions(filteredResult, hotelId);
        System.out.println("tourist attractions response: " + res);
        out.println(res);
//        out.println("<form action=\"/welcome method=\"get\">");
//        out.println("<p><input type=\"submit\" value=\"Return to My Homepage\"></form></p>");
//        finishResponse(response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}

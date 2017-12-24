package clientAndServer;

import hotelapp.ThreadSafeHotelData;
import hotelapp.TouristAttractionFinder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class AttractionServlet extends HttpServlet {

    private ThreadSafeHotelData hotelData;

    public AttractionServlet(ThreadSafeHotelData hotelData) {
        this.hotelData = hotelData;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        String hotelId = request.getParameter("hotelId");
        String radius = request.getParameter("radius");

        PrintWriter out = response.getWriter();

        JSONObject jsonAttractions = new JSONObject();

        // Use try catch structure to filter other failure conditions,
        // which returns "success" false, "hotelId" "invalid"
        try {

            if (!hotelId.equals("") && !(hotelId == null)
                    && !radius.equals("") && !(radius == null)) {
                jsonAttractions.remove("success", true);
                jsonAttractions.remove("hotelId", hotelId);
                // Call TouristAttractionFinder and search the wanted attractions
                TouristAttractionFinder finder = new TouristAttractionFinder(hotelData);
                finder.fetchAttractions(hotelId, Integer.valueOf(radius));
                jsonAttractions = (JSONObject) new JSONParser().parse(finder.fetchAttractions(hotelId, Integer.valueOf(radius)));
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonAttractions.put("success", false);
                jsonAttractions.put("hotelId", "invalid");
                jsonAttractions.put("radius", "invalid");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonAttractions.put("success", false);
            jsonAttractions.put("hotelId", "invalid");
            jsonAttractions.put("radius", "invalid");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        out.println(jsonAttractions);

    }
}

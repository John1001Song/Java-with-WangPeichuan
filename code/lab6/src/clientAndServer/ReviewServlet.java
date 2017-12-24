package clientAndServer;


import hotelapp.ThreadSafeHotelData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sun.text.normalizer.UTF16;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

@SuppressWarnings("serial")
public class ReviewServlet extends HttpServlet {

    ThreadSafeHotelData hotelData;
    public ReviewServlet(ThreadSafeHotelData hotelData) {
        this.hotelData = hotelData;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        //response.setCharacterEncoding("UTF16");
        response.setStatus(HttpServletResponse.SC_OK);

        String hotelId = request.getParameter("hotelId");
        String num = request.getParameter("num");

        PrintWriter out = response.getWriter();

        JSONObject jsonReviews = new JSONObject();
        jsonReviews.put("success", false);
        jsonReviews.put("hotelId", "invalid");

        //JSONArray jsonReviewsArray = new JSONArray();

        try {

            if (!(hotelId == null) && !hotelId.equals("") && !(num == null) && !num.equals("")) {

                // when the input num is smaller than the available reviews
                if (Integer.valueOf(num) < hotelData.getReviewsSizeOfOneHotel(hotelId)) {
                    System.out.println("1 " + num);
                    jsonReviews.put("success", true);
                    jsonReviews.put("hotelId", hotelId);
                    jsonReviews.put("reviews", hotelData.getReviewsOfOneHotel(hotelId, Integer.valueOf(num)));
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    System.out.println("2 " + num);
                    jsonReviews.put("success", false);
                    jsonReviews.put("hotelId", "invalid");
                    jsonReviews.remove("reviews");
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                System.out.println("3 " + num);
                jsonReviews.put("success", false);
                jsonReviews.put("hotelId", "invalid");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonReviews.put("success", false);
            jsonReviews.put("hotelId", "invalid");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        System.out.println(jsonReviews);
        out.println(jsonReviews.toJSONString());

    }
}

package clientAndServer;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;

import java.io.*;
import java.util.LinkedHashMap;

import hotelapp.ThreadSafeHotelData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


@SuppressWarnings("serial")
public class HotelServlet extends HttpServlet{

    ThreadSafeHotelData wantedHotel;

    public HotelServlet(ThreadSafeHotelData hotelData) {
        wantedHotel = hotelData;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException{
        System.out.println();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();

        String hotelId = request.getParameter("hotelId");

        System.out.println(hotelId);

        JSONObject jsonHotel = new JSONObject();
        jsonHotel.put("success", false);
        jsonHotel.put("hotelId", "invalid");

        try {
            // if the input contain a hotelId
            if (!(hotelId == null) && !hotelId.equals("")) {

                // handle hotel request
                // if the hotel id exists in the hotel database
                if (wantedHotel.getHotels().contains(hotelId)) {
                    // find the hotel info based on the hotel id
                    jsonHotel.put("success", true);
                    jsonHotel.put("hotelId", hotelId);
                    jsonHotel.put("name", wantedHotel.getHotelName(hotelId));
                    jsonHotel.put("addr", wantedHotel.getHotelStressAddress(hotelId));
                    jsonHotel.put("city", wantedHotel.getOneHotelCity(hotelId));
                    jsonHotel.put("state", wantedHotel.getOneHotelState(hotelId));
                    jsonHotel.put("lat", wantedHotel.getHotelLat(hotelId).toString());
                    jsonHotel.put("lng", wantedHotel.getHotelLon(hotelId).toString());
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            } else {
                jsonHotel.remove("name");
                jsonHotel.remove("addr");
                jsonHotel.remove("city");
                jsonHotel.remove("state");
                jsonHotel.remove("lat");
                jsonHotel.remove("lng");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonHotel.put("success", false);
            jsonHotel.put("hotelId", "invalid");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        out.println(jsonHotel);
        out.close();

    }
}

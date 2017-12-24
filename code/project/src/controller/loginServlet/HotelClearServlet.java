package controller.loginServlet;

import model.HotelDatabaseHandler;
import server.Status;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HotelClearServlet extends LoginBaseServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println("In HotelClearServlet");
        // parse the request
        String userName = request.getParameter("userName");
        System.out.println("userName in HotelClearServlet doPost: " + userName);

        HotelDatabaseHandler hotelDatabaseHandler = HotelDatabaseHandler.getInstance();
        Status status = hotelDatabaseHandler.clearHotels(userName);
        System.out.println("saved hotels clear status: " + status.message());

    }
    }

package controller.loginServlet;

import model.HotelDatabaseHandler;
import server.Status;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class HotelSaveServlet extends LoginBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println("In HotelSaveServlet");
        // parse the request
        String userName = request.getParameter("userName");
        System.out.println("userName in HotelSaveServlet: " + userName);

        String hotelId = request.getParameter("hotelId");
        System.out.println("hotelId in HotelSaveServlet: " + hotelId);

        HotelDatabaseHandler hotelDatabaseHandler = HotelDatabaseHandler.getInstance();


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println("In HotelSaveServlet");
        // parse the request
        String userName = request.getParameter("userName");
        System.out.println("userName in HotelSaveServlet doPost: " + userName);

        String hotelId = request.getParameter("hotelId");
        System.out.println("hotelId in HotelSaveServlet doPost: " + hotelId);

        HotelDatabaseHandler hotelDatabaseHandler = HotelDatabaseHandler.getInstance();
        Status status = hotelDatabaseHandler.saveHotel(userName, hotelId);
        ArrayList<String> hotelNameList = hotelDatabaseHandler.getHotelName(userName);

        PrintWriter out = response.getWriter();

        for (String hotelName: hotelNameList) {
            out.println(hotelName);
        }


        if (status == Status.OK) {
            System.out.println(status.message());
        } else {
            System.out.println(status.message());
        }

    }
    }

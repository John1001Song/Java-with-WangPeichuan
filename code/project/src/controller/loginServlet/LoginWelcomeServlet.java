package controller.loginServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.loginServlet.LoginBaseServlet;
import model.HotelDatabaseHandler;
import model.LoginDatabaseHandler;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import model.DatabaseConnector;

@SuppressWarnings("serial")
public class LoginWelcomeServlet extends LoginBaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();

        // Get the "name" parameter from the GET request
        // The request may look like this: /hotels?name=Ali
//        String name = request.getParameter("username");

        Map<String, String> cookieMap = getCookieMap(request);
        String userName = cookieMap.get("userName");
        System.out.println("userName in the cookie map in welcome servlet: " + userName);

//        if (!userName.equals(name) || userName == ""){
//            response.sendRedirect("/login");
//        }

        String visitedDate = "";

        if (userName == null || userName.isEmpty()) {
            userName = "anonymous";
        } else {
            LoginDatabaseHandler loginDatabaseHandler = LoginDatabaseHandler.getInstance();
            visitedDate = loginDatabaseHandler.getLastLoginDate(userName);
            if (visitedDate.equals("") || visitedDate == null) {
                visitedDate = "This is your first time to login.";
            }
        }
        userName = StringEscapeUtils.escapeHtml4(userName);

        VelocityEngine ve = (VelocityEngine) request.getServletContext().getAttribute("templateEngine");
        VelocityContext context = new VelocityContext();
        Template template = ve.getTemplate("view/LoginWelcome.html");
        // Comment the line above and uncomment the line below for a more complex template:
        //Template template = ve.getTemplate("templates/travelAdvisor.html");



        context.put("userName", userName);
        System.out.println("userName in welcome servlet: " + userName);

        context.put("visitedDate", visitedDate);
        System.out.println("visited Date in welcome servlet: " + visitedDate);
        // usually the data would come from some kind of database. Using a
        // simple ArrayList here instead
//        ArrayList<Hotel>  hotels = new ArrayList<>();
//        hotels.add(new Hotel("Sheraton Pier 39"));
//        hotels.add(new Hotel("Best Western SF Downtown"));
//        hotels.add(new Hotel("Marriott SF Airport"));

//        context.put("hotels", hotels);

        Map<String, String> hotelsMap;

        HotelDatabaseHandler hotelDatabaseHandler = HotelDatabaseHandler.getInstance();
        hotelsMap = hotelDatabaseHandler.displayAllHotels();
        context.put("hotelsMap", hotelsMap);



        //Template template = ve.getTemplate("templates/bootstrap.html");

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        out.println(writer.toString());

    }
}
package controller.reviewServlet;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import server.Status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ReviewNewSubmitServlet extends ReviewBaseServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();

        String userName = request.getParameter("userName");
        System.out.println("userName in newSubmit doGet: " + userName);

        String hotelId = request.getParameter("hotelId");
        System.out.println("hotelId in newSubmit doGet: " + hotelId);

        String hotelName = request.getParameter("hotelName");
        System.out.println("hotelName in newSubmit doGet: " + hotelName);


        VelocityEngine ve = (VelocityEngine) request.getServletContext().getAttribute("templateEngine");
        VelocityContext context = new VelocityContext();
        Template template = ve.getTemplate("view/ReviewNewSubmit.html");

        context.put("userName", userName);
        context.put("hotelId", hotelId);
        context.put("hotelName", hotelName);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        out.println(writer.toString());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();

        String hotelId = request.getParameter("hotelId");
        System.out.println("hotelId in newReview: " + hotelId);

        String hotelName = request.getParameter("hotelName");
        System.out.println("hotelName in newReview: " + hotelName);

        String userName = request.getParameter("userName");
        System.out.println("userName in newReview: " + userName);

        String title = request.getParameter("title");
        System.out.println("title in newReview: " + title);

        String content = request.getParameter("content");
        System.out.println("content in newReview: " + content);

        String rating = request.getParameter("rating");
        System.out.println("rating in newReview: " + rating);

        String isRecomd = request.getParameter("isRecomd");
        System.out.println("isRecomd in newReview: " + isRecomd);



        try {
            handler.ReviewDatabaseHandler reviewDatabaseHandler = handler.ReviewDatabaseHandler.getInstance();
            // reviewId | hotelId | userName | title | content | rating | isRecomd |
            Status status = reviewDatabaseHandler.submitReview(hotelId, userName, title, content, rating, isRecomd);
            out.println(status.message());
            if (status == Status.OK) {
                // /reviewInOneHotel?hotelId=$hotel.hotelId&hotelName=$hotel.hotelName&userName=$name
                response.sendRedirect("/reviewInOneHotel?hotelId=" + hotelId + "&hotelName=" + hotelName +"&userName=" + userName);
            } else {
                // hotelId=$hotelId&hotelName=$hotelName&userName=$userName
                response.sendRedirect("/reviewNewSubmit?hotelId=" + hotelId + "&hotelName=" + hotelName + "&userName=" + userName);
            }
            System.out.println(status.message());
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e);
        }

    }

    }

package controller.reviewServlet;

import model.ReviewDatabaseHandler;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import server.Status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

public class ReviewEditServlet extends ReviewBaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        String error = request.getParameter("error");
        String userName = request.getParameter("userName");
        System.out.println("userName in review edit doGet: " + userName);
        String hotelId = request.getParameter("hotelId");
        System.out.println("hotelId in review edit doGet: " + hotelId);

        String reviewId = request.getParameter("reviewId");
        System.out.println("reviewId in review edit doGet: " + reviewId);


        VelocityEngine ve = (VelocityEngine) request.getServletContext().getAttribute("templateEngine");
        VelocityContext context = new VelocityContext();
        Template template = ve.getTemplate("view/ReviewEdit.html");



        try {
            if (request.getParameter("userName") != null) {
//                ReviewDatabaseHandler reviewDatabaseHandler = ReviewDatabaseHandler.getInstance();
//                Map reviewsMap = reviewDatabaseHandler.displayReviewsOfUser(userName);
                context.put("hotelId", hotelId);
                context.put("reviewId", reviewId);
                context.put("userName", userName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        finishResponse(response);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        out.println(writer.toString());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();

        String reviewId = request.getParameter("reviewId");
        System.out.println("reviewId: " + reviewId);

        String hotelId = request.getParameter("hotelId");
        System.out.println("hotelId: " + hotelId);

        String userName = request.getParameter("userName");
        System.out.println("userName: " + userName);

        String title = request.getParameter("title");
        System.out.println("title: " + title);

        String content = request.getParameter("content");
        System.out.println("content: " + content);

        String rating = request.getParameter("rating");
        System.out.println("rating: " + rating);

        String isRecomd = request.getParameter("isRecomd");
        System.out.println("isRecomd: " + isRecomd);



        try {
            handler.ReviewDatabaseHandler reviewDatabaseHandler = handler.ReviewDatabaseHandler.getInstance();
            // reviewId | hotelId | userName | title | content | rating | isRecomd |
            Status status = reviewDatabaseHandler.updateReview(reviewId, hotelId, title, content, rating, isRecomd);
            out.println(status.message());
            if (status == Status.OK) {
                response.sendRedirect("/reviewManage?userName=" + userName);
            } else {
                response.sendRedirect("/reviewEdit?reviewId=" + reviewId + "&userName=" + userName + "&hotelId=" + hotelId);
            }
            System.out.println(status.message());
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e);
        }

//        VelocityEngine ve = (VelocityEngine) request.getServletContext().getAttribute("templateEngine");
//        VelocityContext context = new VelocityContext();
//        Template template = ve.getTemplate("src/view/ReviewEdit.html");

//        String reviewId = request.getParameter("reviewId");
//        String userName = request.getParameter("userName");
//        String hotelId = request.getParameter("hotelId");
//
//        context.put("reviewId", reviewId);
//        context.put("userName", userName);
//        context.put("hotelId", hotelId);
//
//        StringWriter writer = new StringWriter();
//        template.merge(context, writer);
//
//        out.println(writer.toString());
    }
}

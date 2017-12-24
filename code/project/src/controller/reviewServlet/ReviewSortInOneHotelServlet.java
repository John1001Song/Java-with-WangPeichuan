package controller.reviewServlet;

import model.ReviewDatabaseHandler;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

public class ReviewSortInOneHotelServlet extends ReviewBaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();

        String hotelId = request.getParameter("hotelId");
        System.out.println("hotelId in review in one hotel servlet: " + hotelId);

        String hotelName = request.getParameter("hotelName");
        System.out.println("hotelName in review in one hotel servlet: " + hotelName);

        String userName = request.getParameter("userName");
        System.out.println("userName in review in one hotel servlet: " + userName);

        VelocityEngine ve = (VelocityEngine) request.getServletContext().getAttribute("templateEngine");
        VelocityContext context = new VelocityContext();
        Template template = ve.getTemplate("view/ReviewDisplay.html");

        context.put("hotelId", hotelId);
        context.put("hotelName", hotelName);
        context.put("userName", userName);

        ReviewDatabaseHandler reviewDatabaseHandler = ReviewDatabaseHandler.getInstance();
        Map reviewsMap = reviewDatabaseHandler.getSortedReviewsInOneHotel(hotelId);

        context.put("reviewsMap", reviewsMap);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        out.println(writer.toString());
    }

}

package controller.reviewServlet;

import model.ReviewDatabaseHandler;
import server.Status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ReviewDeleteServlet extends ReviewBaseServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String reviewId = request.getParameter("reviewId");
        System.out.println("reviewId in delete servlet doPost: " + reviewId);

        ReviewDatabaseHandler reviewDatabaseHandler = ReviewDatabaseHandler.getInstance();

        Status status = reviewDatabaseHandler.deleteReview(reviewId);
        System.out.println("delete review status in delete servlet: " + status.message());
        out.println(status);
    }
}

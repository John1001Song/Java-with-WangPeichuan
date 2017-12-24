package controller.reviewServlet;

import model.ReviewDatabaseHandler;
import server.Status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ReviewLikeServlet extends ReviewBaseServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        String userName = request.getParameter("userName");
        System.out.println("userName in reviewLike Servlet doPost: " + userName);

        String reviewId = request.getParameter("reviewId");
        System.out.println("reviewId in reviewLike Servlet doPost: " + reviewId);

        ReviewDatabaseHandler reviewDatabaseHandler = ReviewDatabaseHandler.getInstance();

        Status status = reviewDatabaseHandler.insertLikedReview(userName, reviewId);
        System.out.println("status in reviewLike servlet: " + status.message());

        String counter = reviewDatabaseHandler.countLikedReview(reviewId);
        System.out.println("counter in the reviewLike servlet: " + counter);

        PrintWriter out = response.getWriter();
        out.println(counter);
    }
}

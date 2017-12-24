package server;

import handler.ReviewDatabaseHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ReviewDeleteServlet extends ReviewBaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepareResponse("Delete the Review", response);
        PrintWriter out = response.getWriter();

        String reviewId = request.getParameter("reviewId");
        out.println("Deleted ReviewId: " + reviewId);

        try {
            ReviewDatabaseHandler reviewDatabaseHandler = ReviewDatabaseHandler.getInstance();
            Status status = reviewDatabaseHandler.deleteReview(reviewId);
            if (status == Status.OK) {
                out.println("This review is deleted successfully.");
            } else {
                out.println("This review is NOT deleted.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e);
        }

        out.println("<form action=\"/welcome method=\"get\">");
        out.println("<p><input type=\"submit\" value=\"Return to My Homepage\"></p>");

        finishResponse(response);
    }
}
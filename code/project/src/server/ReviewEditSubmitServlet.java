package server;

import handler.ReviewDatabaseHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeMap;

public class ReviewEditSubmitServlet extends ReviewBaseServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepareResponse("Submit the Edited Review", response);
        PrintWriter out = response.getWriter();

        String reviewId = request.getParameter("reviewId");
        String hotelId = request.getParameter("hotelId");
        String userName = request.getParameter("userName");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String rating = request.getParameter("rating");
        String isRecomd = request.getParameter("isRecomd");

        out.println("Review ID: " + reviewId);
        out.println("User Name: " + userName);

        try {
            ReviewDatabaseHandler reviewDatabaseHandler = ReviewDatabaseHandler.getInstance();
            // reviewId | hotelId | userName | title | content | rating | isRecomd |
            Status status = reviewDatabaseHandler.updateReview(reviewId, hotelId, title, content, rating, isRecomd);
            out.println(status.message());
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e);
        }


//        out.println(hotelId + ", " + userName + ", " + title + ", " + content + ", " + rating + ", " + isRecomd);
        out.println("<form action=\"/welcome method=\"get\">");
//        response.sendRedirect(response.encodeRedirectURL("/welcome"));
        out.println("<p><input type=\"submit\" value=\"Return to My Homepage\"></p>");
        out.println("</form>");
        finishResponse(response);
    }

}

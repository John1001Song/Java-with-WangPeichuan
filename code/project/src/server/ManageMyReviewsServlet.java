package server;

import handler.ReviewDatabaseHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SplittableRandom;
import java.util.TreeMap;

public class ManageMyReviewsServlet extends ReviewBaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepareResponse("Manage My Reviews", response);
        PrintWriter out = response.getWriter();
        String error = request.getParameter("error");
        String userName = request.getParameter("userName");

        int code = 0;

        if (error != null) {
            try {
                code = Integer.parseInt(error);
            }
            catch (Exception ex) {
                code = -1;
            }

            String errorMessage = getStatusMessage(code);
            out.println("<p style=\"color: red;\">" + errorMessage + "</p>");
        }

        try {
            if (request.getParameter("userName") != null) {
                ReviewDatabaseHandler reviewDatabaseHandler = ReviewDatabaseHandler.getInstance();
                ArrayList reviewList = reviewDatabaseHandler.displayReviewsOfUser(userName);
                Iterator iterator = reviewList.iterator();
                out.println("<table border=\"10\">");
                while (iterator.hasNext()) {
                    out.println("\t<tr>");
                    out.println(iterator.next());
                    out.println("\t</tr>");
                }
                out.println("</table>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finishResponse(response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepareResponse("Edit a Review", response);
        PrintWriter out = response.getWriter();

        String reviewId = request.getParameter("reviewId");
        out.println("Review ID: " + reviewId);

        try {
            ReviewDatabaseHandler reviewDatabaseHandler = ReviewDatabaseHandler.getInstance();
            TreeMap theEditedReview = reviewDatabaseHandler.getOneReviewSql(reviewId);
            // reviewId | hotelId | userName | title | content | rating | isRecomd |
            String hotelId = String.valueOf(theEditedReview.get("hotelId"));
            String userName = theEditedReview.get("userName").toString();
            String title = theEditedReview.get("title").toString();
            String content = theEditedReview.get("content").toString();
            String rating = theEditedReview.get("rating").toString();
            String isRecomd = theEditedReview.get("isRecomd").toString();

            printReviewEditForm(reviewId, hotelId, userName, title, content, rating, isRecomd, out);

        } catch (Exception e) {
            e.printStackTrace();
            out.println(e);
        }

        finishResponse(response);
    }

    private void printReviewEditForm(String reviewId, String hotelId, String userName, String title,
                                     String content, String rating, String isRecomd, PrintWriter out) {
        assert out != null;

        // reviews (reviewId, hotelId, userName, title, content, rating, isRecomd)
        out.println("<form action=\"/reviewEditSubmit?reviewId="+ reviewId + "\" method=\"post\">");
        out.println("<table border=\"0\">");
        out.println("\t<tr>");
        out.println("\t\t<td>Please confirm HotelID: </td>");
        out.println("\t\t<td>" + hotelId + "<input type=\"checkbox\" name=\"hotelId\" value=" + hotelId + "></td>");
        out.println("\t</tr>");

        out.println("\t\t<td>Usename:</td>");
        out.println("\t\t<td><input type=\"text\" name=\"userName\" value=\"" + userName + "\" size=\"30\"></td>");
        out.println("\t</tr>");

        out.println("\t<tr>");
        out.println("\t\t<td>Title:</td>");
        out.println("\t\t<td><input type=\"text\" name=\"title\" value=\"" + title + "\" size=\"30\"></td>");
        out.println("</tr>");

        out.println("\t<tr>");
        out.println("\t\t<td>Content:</td>");
        out.println("\t\t<td><textarea rows=\"4\" cols=\"50\" name=\"content\">" + content + "</textarea></td>");
        out.println("</tr>");

        out.println("\t<tr>");
        out.println("\t\t<td>Rating:</td>");
        out.println("\t\t<td><input type=\"radio\" name=\"rating\" value=\"1\">1");
        out.println("\t\t<input type=\"radio\" name=\"rating\" value=\"2\">2");
        out.println("\t\t<input type=\"radio\" name=\"rating\" value=\"3\">3");
        out.println("\t\t<input type=\"radio\" name=\"rating\" value=\"4\">4");
        out.println("\t\t<input type=\"radio\" name=\"rating\" value=\"5\" checked=\"checked\">5</td>");
        out.println("</tr>");

        out.println("\t<tr>");
        out.println("\t\t<td>isRecomd:</td>");
        out.println("\t\t<td><input type=\"radio\" name=\"isRecomd\" value=\"true\">true");
        out.println("\t\t<input type=\"radio\" name=\"isRecomd\" value=\"false\">false</td>");
        out.println("</tr>");

        out.println("</table>");
        out.println("<p><input type=\"submit\" value=\"Submit\"></p>");
        out.println("</form>");

    }

}

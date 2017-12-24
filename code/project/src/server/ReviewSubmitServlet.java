package server;

import handler.ReviewDatabaseHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class ReviewSubmitServlet extends ReviewBaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepareResponse("Review Submit", response);
        PrintWriter out = response.getWriter();
        String error = request.getParameter("error");
        String hotelId = request.getParameter("hotelId");

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

        printReviewSubmitForm(hotelId, out);
        finishResponse(response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepareResponse("Submit a New Review", response);
        PrintWriter out = response.getWriter();

        String hotelId = request.getParameter("hotelId");
        String userName = request.getParameter("userName");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String rating = request.getParameter("rating");
        String isRecomd = request.getParameter("isRecomd");

        try {
            ReviewDatabaseHandler reviewDatabaseHandler = ReviewDatabaseHandler.getInstance();
            Status status = reviewDatabaseHandler.submitReview(hotelId, userName, title, content, rating, isRecomd);
            out.println(status.message());
            out.println("<br>Review submitted successfully.<br>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e);
        }

        out.println("Hotel ID: " + hotelId + "<br>"
                  + "User Name: " + userName + "<br>"
                  + "Title: " + title + "<br>"
                  + "Content: " + content + "<br>"
                  + "Rating: " + rating + "<br>"
                  + "isRecomd: " + isRecomd);
        out.println("<form action=\"/welcome method=\"get\">");
        out.println("<p><input type=\"submit\" value=\"Return to My Homepage\"></form></p>");
        finishResponse(response);
    }

    private void printReviewSubmitForm(String hotelId, PrintWriter out) {
        assert out != null;

        // reviews (reviewId, hotelId, userName, title, content, rating, isRecomd)
        out.println("<form action=\"/reviewSubmit\" method=\"post\">");
        out.println("<table border=\"0\">");
        out.println("\t<tr>");
        out.println("\t\t<td>Please confirm HotelID: </td>");
        out.println("\t\t<td>" + hotelId + "<input type=\"checkbox\" name=\"hotelId\" value=" + hotelId + "></td>");
        out.println("\t</tr>");

        out.println("\t\t<td>Usename:</td>");
        out.println("\t\t<td><input type=\"text\" name=\"userName\" size=\"30\"></td>");
        out.println("\t</tr>");

        out.println("\t<tr>");
        out.println("\t\t<td>Title:</td>");
        out.println("\t\t<td><input type=\"text\" name=\"title\" size=\"30\"></td>");
        out.println("</tr>");

        out.println("\t<tr>");
        out.println("\t\t<td>Content:</td>");
        out.println("\t\t<td><textarea rows=\"4\" cols=\"50\" name=\"content\"></textarea></td>");
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

package server;

import handler.ReviewDatabaseHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class ReviewServlet extends ReviewBaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepareResponse("review", response);
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

        if (request.getParameter("hotelId") != null) {
            out.println("<p><a href=\"reviewSubmit?hotelId=" + hotelId + "\">Submit a review about this hotel.</a></p>");
            out.println("<p>" + "Display the tourist attractions near hotelID: " +
                    "<form action=\"/touristAttractionsDisplay\" method=\"get\">" +
                    "<input type=\"text\" name=\"hotelId\" value=\"" + hotelId + "\">" +
                    "in <input type=\"text\" name=\"radius\">miles" +
                    "<input type=\"submit\" value=\"Search\"></form></p>");
            ReviewDatabaseHandler reviewDatabaseHandler = ReviewDatabaseHandler.getInstance();
            ArrayList reviewList = reviewDatabaseHandler.displayReviewsOfWantedHotel(hotelId);

            Iterator iterator = reviewList.iterator();
            while (iterator.hasNext()) {
                out.println(iterator.next());
            }
        }

        finishResponse(response);
    }

    private void printForm(PrintWriter out) {
        assert out != null;

        out.println("<form action=\"/login\" method=\"post\">");
        out.println("<table border=\"0\">");
        out.println("\t<tr>");
        out.println("\t\t<td>Usename:</td>");
        out.println("\t\t<td><input type=\"text\" name=\"user\" size=\"30\"></td>");
        out.println("\t</tr>");
        out.println("\t<tr>");
        out.println("\t\t<td>Password:</td>");
        out.println("\t\t<td><input type=\"password\" name=\"pass\" size=\"30\"></td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("<p><input type=\"submit\" value=\"Login\"></p>");
        out.println("</form>");

        out.println("<p>(<a href=\"/register\">new user? register here.</a>)</p>");
    }

}

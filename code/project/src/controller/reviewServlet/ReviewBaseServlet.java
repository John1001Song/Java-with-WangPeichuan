package controller.reviewServlet;

import handler.ReviewDatabaseHandler;
import server.Status;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReviewBaseServlet extends HttpServlet {

    protected static final ReviewDatabaseHandler reviewDatabaseHandler = ReviewDatabaseHandler.getInstance();

    protected void prepareResponse(String title, HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();

            writer.printf("<!DOCTYPE html>%n%n");
            writer.printf("<html lang=\"en\">%n%n");
            writer.printf("<head>%n");
            writer.printf("\t<title>%s</title>%n", title);
            writer.printf("\t<meta charset=\"utf-8\">%n");
            writer.printf("</head>%n%n");
            writer.printf("<body>%n%n");
        }
        catch (IOException ex) {
            System.out.println("Unable to prepare HTTP response.");
            ex.printStackTrace();
            return;
        }
    }

    protected void finishResponse(HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();

            writer.printf("%n");
            writer.printf("<p style=\"font-size: 10pt; font-style: italic;\">");
            writer.printf("Last updated at %s.", getDate());
            writer.printf("</p>%n%n");

            writer.printf("</body>%n");
            writer.printf("</html>%n");

            writer.flush();

            response.setStatus(HttpServletResponse.SC_OK);
            response.flushBuffer();
        }
        catch (IOException ex) {
            System.out.println("Unable to finish HTTP response.");
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
    }

    protected String getDate() {
        String format = "hh:mm a 'on' EEE, MMM dd, yyyy";
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    protected Map<String, String> getCookieMap(HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<String, String>();

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                map.put(cookie.getName(), cookie.getValue());
            }
        }

        return map;
    }

    protected void clearCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        if(cookies == null) {
            return;
        }

        for(Cookie cookie : cookies) {
            cookie.setValue("");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    protected void clearCookie(String cookieName, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    protected void debugCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if(cookies == null) {
            System.out.println("Saved Cookies: []");
        }
        else {
            String[] names = new String[cookies.length];

            for(int i = 0; i < names.length; i++) {
                names[i] = String.format("(%s, %s, %d)",
                        cookies[i].getName(),
                        cookies[i].getValue(),
                        cookies[i].getMaxAge());
            }

            System.out.println("Saved Cookies: " + Arrays.toString(names));
        }
    }


    protected String getStatusMessage(String errorName) {
        Status status = null;

        try {
            status = Status.valueOf(errorName);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            status = Status.ERROR;
        }

        return status.toString();
    }

    protected String getStatusMessage(int code) {
        Status status = null;

        try {
            status = Status.values()[code];
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            status = Status.ERROR;
        }

        return status.toString();
    }
}

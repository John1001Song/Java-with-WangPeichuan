package controller.loginServlet;

import model.LoginDatabaseHandler;
import server.Status;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class LogoutServlet extends LoginBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String> cookieMap = getCookieMap(request);
        String userName = cookieMap.get("userName");
        System.out.println("userName in the cookie map in logout servlet: " + userName);

        LoginDatabaseHandler loginDatabaseHandler = LoginDatabaseHandler.getInstance();
        Status status = loginDatabaseHandler.updateLastLoginDate(userName);
        System.out.println("update last login date status in logout servlet: " + status);

        clearCookies(request, response);
        response.sendRedirect(response.encodeRedirectURL("/login"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    }

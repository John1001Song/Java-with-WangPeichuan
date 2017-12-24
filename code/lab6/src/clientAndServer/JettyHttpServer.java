package clientAndServer;

/**
 * This server uses Jetty&servlets and it can handle Http GET requests from clients.
 * If a client sends any other type of Http request, the server will return the response code: 405 Method not allowed.
 * */
public class JettyHttpServer {
    // available Port: 2000 and 2050
    public static final int PORT = 2000;

}

package clientAndServer;
import com.sun.xml.internal.ws.addressing.WsaTubeHelperImpl;

import java.io.*;
import java.net.*;

/**
 *
 * SimpleServer is modified from the example code SimpleServer.java
 *
 * Opens a welcoming socket, listens for client requests.
 * Once the client asks to connect, creates a new "connection socket" for the client.
 * Read messages from the client from the input stream.
 * Prints them to the console.
 *
 * */
public class RawSocketsHttpServer {
    // available Port: 2050
    public static final int PORT = 2050;
    public static final String EOT = "EOT";
    public static final String EXIT = "SHUTDOWN";

    // whether the server is alive or shutdown
    private boolean alive;

    public RawSocketsHttpServer() {
        alive = true;
    }

    public void run() {
        ServerSocket welcomingSocket = null;
        Socket connectionSocket = null;
        try {
            // for listening for connection requests from clients
            welcomingSocket = new ServerSocket(PORT);
            while (alive) {
                System.out.println("Server: Waiting for connection...");
                // wait for a client to connect,
                // create a new connection socket for talking to this client
                connectionSocket = welcomingSocket.accept();
                System.out.println("Server: Client connected.");
                // welcomingSocket will continue listening for connections from other clients after it is done talking
                // to the client.
                // We should create a new runnable task for each client request

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                // the server now can read lines sent by the client using BufferReader
                String input;
                while (!connectionSocket.isClosed()) {
                    input = bufferedReader.readLine();
                    // echo the same string to the console
                    System.out.println("Server received: " + input);

                    if (input.equals(EOT)) {
                        System.out.println("Server: Closing socket.");
                        connectionSocket.close();
                    } else if (input.equals(EXIT)) {
                        alive = false;
                        System.out.println("Server: shutting down.");
                        connectionSocket.close();
                        welcomingSocket.close();
                    }
                }
                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception occured while using the socket: " + e);
        } finally {
            try {
                if (welcomingSocket != null && !welcomingSocket.isClosed())
                    welcomingSocket.close();;
                if (connectionSocket != null && !connectionSocket.isClosed())
                    connectionSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Could not close the socket");
            }
        }
    }


}

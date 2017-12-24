package codeCamp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientForServer {
    public void communicate() {
        try(Socket socket = new Socket("localhost", Server.PORT)) {
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw.println("GET /index.html HTTP/1.1"); // part of the GET request
            pw.flush();
            String line = br.readLine();
            System.out.println("Received from the server: " + line);
        }
        catch (IOException e) {
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        ClientForServer client  = new ClientForServer();
        client.communicate();
    }
}

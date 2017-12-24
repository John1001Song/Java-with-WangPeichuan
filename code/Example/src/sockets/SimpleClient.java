package sockets;
import java.io.*;
import java.net.*;

/**
 * SimpleClient: Reads input from the keyboard and sends it to the server via
 * the socket. File is modified from the code of Prof. Engle
 *
 */
public class SimpleClient extends Thread {
	public void run() {
		try {
			System.out.println("Client: Started...");
			// Sends a connection request to the server that is running on
			// a given host, "listening" on the given port
			//Socket socket = new Socket("g1212.cs.usfca.edu", SimpleServer.PORT); // use on campus

			Socket socket = new Socket("localhost", 2050); // running on the local machine

			// For reading user keyboard input from the console
			// (has nothing to do with sockets!)

			// For writing to the socket (so that the server could get client messages)
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

			String input = new String();

				writer.println("GET /hotelInfo?hotelId=10323 HTTP/1.1"); // send the message to the server via th
				writer.println("");
										// socket
				writer.flush();

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((input = in.readLine()) != null) {
				System.out.println(input);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SimpleClient client = new SimpleClient();
		client.start();
		try {
			client.join();
		} catch (InterruptedException e) {
			System.out.println("The thread got interrupted " + e);
		}
	}
}
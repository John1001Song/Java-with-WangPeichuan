package clientAndServer;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import hotelapp.ThreadSafeHotelData;
import hotelapp.TouristAttractionFinder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadWebServer{

    public static final String EOT = "EOT";
    public static final String EXIT = "SHUTDOWN";
    protected int PORT = 2050;
    private volatile boolean isShutdown = false;
    private ThreadSafeHotelData hotelData;

    public void startServer(int PORT) {
        final ExecutorService threads = Executors.newFixedThreadPool(10);
        this.PORT = PORT;

        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket welcomingSocket = new ServerSocket(PORT);
                    System.out.println("Waiting for clients to connect...");
                    while (!isShutdown) {
                        Socket clientSocket = welcomingSocket.accept();
                        threads.submit(new ClientTask(clientSocket));
                    }
                    if (isShutdown) {
                        welcomingSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Unable to process client request");
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

    private class ClientTask implements Runnable {
        private final Socket connectionSocket;
        private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        private ClientTask(Socket connectionSocket) {
            this.connectionSocket = connectionSocket;
        }

        public void doGet(String request) {
            try {
                PrintWriter out = new PrintWriter(connectionSocket.getOutputStream());
                JSONObject responseContent = returnResponse(request);
                System.out.println("responseContent is: " + responseContent);
                Date date = new Date();

//                out.println("HTTP/1.1 200 OK"
//                        + System.lineSeparator() + "Content-Type: application/json"
//                        + System.lineSeparator() + dateFormat.format(date)
//                        + System.lineSeparator() + responseContent);

                if (responseContent != null) {
                    out.println("HTTP/1.1 200 OK");
                    out.println("Date: 2017-05-12");
                    out.println("Server: test");
                    out.println("Content-Type: application/json");
                    out.println("MIME-version: 1.0");
                    out.println( System.lineSeparator() + responseContent);
                    out.flush();
                } else {
                    out.println("HTTP/1.1 404 Not Found");
                    out.flush();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() {
            System.out.println("A client connected.");
            try {
                String input = "";
                String methodLine = "";

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

                InputStream inputStream = connectionSocket.getInputStream();
                byte[] byteBuffer = new byte[1000];


//                    System.out.println("------------------");
//
//                    inputStream.read(byteBuffer);
//                    for (byte a : byteBuffer)
//                        System.out.print((char)a);
//
//                    System.out.println("------------------");
//
                    while ((input = bufferedReader.readLine()) != null && (!input.isEmpty())) {
                        System.out.println("----------");
                        System.out.println(input);
                        if (input.contains("HTTP/1.1")) {
                            methodLine = input;
                            System.out.println(methodLine);
                        }
                    }
                    System.out.println("method line is: " + methodLine);

//
//                    input = bufferedReader.readLine();
//                    System.out.println(input);
//                    if (input.substring(0, 3).equals("GET")) {
//                        methodLine = input;
//                    }
//                    // echo the same string to the console
//                    System.out.println("Server received: " + methodLine);

                    if (methodLine.startsWith("GET")) {
                        doGet(methodLine);
                        methodLine = "";
                    } else {
                        PrintWriter out = new PrintWriter(connectionSocket.getOutputStream(), true);
                        Date date = new Date();
                        out.println("HTTP/1.1" + " 405 Method not allowed");
                    }
                    // Based on the input, we will figure out the client's request is GET or not
//                    String requestMethodLine = requestContentList.get(0);
//                    String method = analyzeRequestMethod(requestMethodLine);
//                    System.out.println(method);
//                    if (method.equals("GET")) {
//                        doGet(requestContentList);
//                    } else {
//                        PrintWriter out = new PrintWriter(connectionSocket.getOutputStream(), true);
//                        out.println(method);
//                    }


                    // save for future use
//                    switch (method) {
//                        case "GET":
//                            System.out.println("Use doGet");
//                            //doGet();
//                            break;
//
//                        case "POST":
//                            break;
//                    }

                bufferedReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (connectionSocket != null)
                        connectionSocket.close();
                } catch (IOException e) {
                    System.out.println("Can't close the connection socket: " + e);
                    e.printStackTrace();
                }
            }
            System.out.println("connection is closed: " + connectionSocket.isClosed());
        }
    }

    public void setHotelData(ThreadSafeHotelData hotelData) {
        this.hotelData = hotelData;
        System.out.println(hotelData.getHotels().toString());
    }

    public String getQueryParameter(String query, String parameter) {
        String wantedValue = "";
        System.out.println("query is: " + query);

        if (!query.contains("?")) {
            return wantedValue = "";
        }

        int indexOfParameter = query.indexOf("?");
        System.out.println(indexOfParameter);

        query = query.substring(indexOfParameter+1, query.length());
        System.out.println("parameter is: " + query);

        query = query.substring(0, query.indexOf(" "));
        System.out.println("parameter without tail is: " + query);

        String[] params = query.split("&");
        for (String param : params) {
            System.out.println("para is: " + param);
            String name = param.split("=")[0];
            System.out.println("name: " + name);
            String value = param.split("=")[1];
            System.out.println("value: " + value);
            if (name.equals(parameter))
                wantedValue = value;
        }
        System.out.println("wantedValue: " + wantedValue);
        return wantedValue;
    }

    public String getPath(String request) {

        if (request.contains("hotelInfo")) {
            return "hotelInfo";
        } else if (request.contains("reviews")) {
            return "reviews";
        } else if (request.contains("attractions")) {
            return "attractions";
        } else
        return "";
    }

    // Similar with servlets, they handle the info and returns json
    public JSONObject returnHotelInfo(String parametersLine) {

        System.out.println("This is HotelInfo part");
        // get the hotelID
        String hotelId = getQueryParameter(parametersLine, "hotelId");
        System.out.println("hotelId is: " + hotelId);

        JSONObject jsonHotel = new JSONObject();
        jsonHotel.put("success", false);
        jsonHotel.put("hotelId", "invalid");


        // if the input contain a hotelId
        if (!(hotelId == null) && !hotelId.equals("")) {

            // handle hotel request
            // if the hotel id exists in the hotel database
            System.out.println("=========================");
            System.out.println(hotelId);
            System.out.println(hotelData.getHotels().contains(hotelId));
            if (hotelData.getHotels().contains(hotelId)) {
                // find the hotel info based on the hotel id
                jsonHotel.put("success", true);
                jsonHotel.put("hotelId", hotelId);
                jsonHotel.put("name", hotelData.getHotelName(hotelId));
                jsonHotel.put("addr", hotelData.getHotelStressAddress(hotelId));
                jsonHotel.put("city", hotelData.getOneHotelCity(hotelId));
                jsonHotel.put("state", hotelData.getOneHotelState(hotelId));
                jsonHotel.put("lat", hotelData.getHotelLat(hotelId).toString());
                jsonHotel.put("lng", hotelData.getHotelLon(hotelId).toString());
            }
        } else {
            jsonHotel.remove("name");
            jsonHotel.remove("addr");
            jsonHotel.remove("city");
            jsonHotel.remove("state");
            jsonHotel.remove("lat");
            jsonHotel.remove("lng");
        }

        return jsonHotel;
    }

    public JSONObject returnReviews(String parametersLine) {
        System.out.println("This is reviews part");
        String hotelId = getQueryParameter(parametersLine, "hotelId");
        String num = getQueryParameter(parametersLine, "num");

        JSONObject jsonReviews = new JSONObject();
        jsonReviews.put("success", false);
        jsonReviews.put("hotelId", "invalid");

        JSONArray jsonReviewsArray = new JSONArray();

        if (!(hotelId == null) && !hotelId.equals("") && !(num == null) && !num.equals("")) {

            // check if the hotelId is valid
            if (!hotelData.getHotels().contains(hotelId)) {
                jsonReviews.put("success", false);
                jsonReviews.put("hotelId", "invalid");
                jsonReviews.remove("reviews");
                return jsonReviews;
            }

            // when the input num is smaller than the available reviews
            if (Integer.valueOf(num) < hotelData.getReviewsSizeOfOneHotel(hotelId)) {
                System.out.println("1 "+num);
                jsonReviews.put("success", true);
                jsonReviews.put("hotelId", hotelId);
                jsonReviews.put("reviews", hotelData.getReviewsOfOneHotel(hotelId, Integer.valueOf(num)));
            } else {
                System.out.println("2 "+num);
                jsonReviews.put("success", false);
                jsonReviews.put("hotelId", "invalid");
                jsonReviews.remove("reviews");
            }
        } else {
            System.out.println("3 "+num);
            jsonReviews.put("success", false);
            jsonReviews.put("hotelId", "invalid");
        }

        return jsonReviews;
    }

    public JSONObject returnAttractions(String parametersLine) {
        System.out.println("This is attractions part");
        String hotelId = getQueryParameter(parametersLine, "hotelId");
        String radius = getQueryParameter(parametersLine, "radius");

        JSONObject jsonAttractions = new JSONObject();

        // Use try catch structure to filter other failure conditions,
        // which returns "success" false, "hotelId" "invalid"
        try {

            if (!(hotelId == null) && !hotelId.equals("")
                    && !(radius == null) && !radius.equals("")  && (Long.valueOf(radius) >= 0)) {
                // Call TouristAttractionFinder and search the wanted attractions
                TouristAttractionFinder finder = new TouristAttractionFinder(hotelData);
                jsonAttractions = (JSONObject) new JSONParser().parse(finder.fetchAttractions(hotelId, Integer.valueOf(radius)));

            } else {
                jsonAttractions.put("success", false);
                jsonAttractions.put("hotelId", "invalid");
                jsonAttractions.put("radius", "invalid");

            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonAttractions.put("success", false);
            jsonAttractions.put("hotelId", "invalid");
            jsonAttractions.put("radius", "invalid");

        }
        return jsonAttractions;
    }

    /**
     * This method analyze the client request (GET or not) and decide to call which method listed above.
     **/
    public String analyzeRequestMethod(String request) {
        System.out.println("This is analyzeRequestMethod");
        if (request.substring(0, 3).equals("GET")) {
            return "GET";
        } else {
            return "405 Method not allowed";
        }

        // save for latter use
//        if (request.substring(0, 4).equals("POST")) {
//            return "POST";
//        }

    }

    /**
     * Based on what the client want, call returnHotelInfo, returnReviews, or returnAttractions.
     * Return the response to the connectionSocket and let connectionSocket send back to the client
     * */
    public JSONObject returnResponse(String request) {
        JSONObject response = new JSONObject();

        // check the first line, hotelInfo, reviews or attractions
        String path = getPath(request);

        // check path
        if (request.contains("/hotelInfo")) {
            System.out.println("path is hotelInfo");
            response = returnHotelInfo(request);
        } else if (request.contains("/reviews")) {
            System.out.println("path is reviews");
            response = returnReviews(request);
        } else if (request.contains("/attractions")) {
            System.out.println("path is attractions");
            response = returnAttractions(request);
        } else {
            response = null;
        }

        return response;
    }
}

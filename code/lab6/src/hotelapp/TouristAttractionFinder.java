package hotelapp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.net.Socket;
import java.nio.file.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.*;
import java.util.TreeMap;
import java.util.regex.*;


import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TouristAttractionFinder {

    // google API key
    // AIzaSyBkX76hAC4Jdg6ITA80ULgfXioMrQaq12A
    // AIzaSyCpKR5zdWnAfvegfOBczFZ80VwzOHTutbo
    // AIzaSyAWbnxKzEdyAYPJ59JLsVY_Xd2Xe5RoEqk

    private static final String host = "maps.googleapis.com";
    private static final String path = "/maps/api/place/textsearch/json";

    // hotel data saves hotel information when loading hotel json files
    private ThreadSafeHotelData hdata;

    // map stores the attractions close to the hotel
    private Map<String, String[]> allAttractions;

    // add data structures to store attractions
    // Alternatively, you can store these data structures in ThreadSafeHotelData

    /** Constructor for TouristAttractionFinder
     *
     * @param hdata
     */
    public TouristAttractionFinder(ThreadSafeHotelData hdata) {
        this.hdata = hdata;
        allAttractions = new TreeMap<>();
    }


    /**
     * The filter receives the response from fetchAttractions.
     * It removes header first. Then, it catches the attractions' names and their address.
     * Also, the attributions will be stored into the TreeMap allAttractions.
     * 
     * @param response
     *              - It is the response received from the google, which stores tourist attributions in json style.
     * 
     * @return attributionArray
     * */
    public String[] filter(String response) {
        String attributionAddress;
        String attributionName;

        // remove the header
        String result = response;
        // header is the content before the {
        // remove it
        int index = result.indexOf("{");
        result = result.substring(index);

        // parse the result
        JSONParser parser = new JSONParser();
        String[] attributionArray = new String[0];
        try {
            JSONObject object = (JSONObject) parser.parse(result);
            JSONArray attributionJSArray = (JSONArray) object.get("results");
            attributionArray = new String[attributionJSArray.size()];

            for (int i = 0; i < attributionJSArray.size(); i++) {
                JSONObject currentAttribution = (JSONObject) attributionJSArray.get(i);
                attributionAddress = currentAttribution.get("formatted_address").toString();
                attributionName = currentAttribution.get("name").toString();
                attributionArray[i] = attributionName + "; " + attributionAddress;
            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        return attributionArray;
    }


    /**
     * Creates a secure socket to communicate with googleapi's server that
     * provides Places API, sends a GET request (to find attractions close to
     * the hotel within a given radius), and gets a response as a string.
     * Removes headers from the response string and parses the remaining json to
     * get Attractions info. Adds attractions to the ThreadSafeHotelData.
     *
     * @param radiusInMiles
     *              - the range that the attractions will be searched
     */
    public String fetchAttractions(String hotelID, int radiusInMiles) {
        // This method should call getRequest method
        String result = "";
        PrintWriter out = null;
        BufferedReader in = null;
        SSLSocket sslSocket = null;

        // Change radiusInMiles to meters as required
        double meters = radiusInMiles * 1609.34;

        // Get a list of the keys, which are hotelIDs
        List<String> hotelIdList = hdata.getHotels();

        // Get an iterator, which is a hotelID

            String city = hdata.getOneHotelCity(hotelID);
            // replace space between city name; San Fran => San%20Fran
            city = city.replaceAll("\\s+", "%20");
            String lat = Double.toString(hdata.getHotelLat(hotelID));
            String lon = Double.toString(hdata.getHotelLon(hotelID));

            try {
                SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

                // HTTPS uses port 443
                sslSocket = (SSLSocket) factory.createSocket(host, 443);

                // output stream for the secure socket
                out = new PrintWriter(new OutputStreamWriter(sslSocket.getOutputStream()));

                String query = "query=tourist%20attractions+in+"+ city
                        + "&location=" + lat + "," + lon
                        + "&radius=" + Double.toString(meters)
                        + "&key=AIzaSyBkX76hAC4Jdg6ITA80ULgfXioMrQaq12A";
                String request = getRequest(host, path + "?" + query);
                //System.out.println("Request: " + request);

                // send a request to the server
                out.println(request);
                out.flush();

                // input stream for the secure socket.
                in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

                // use input stream to read server's response
                String line;
                StringBuffer stringBuffer = new StringBuffer();
                while ((line = in.readLine()) != null) {
                    // debug and display the response
                    System.out.println(line);
                    stringBuffer.append(line);
                }
                result = stringBuffer.toString();

                // put current results into the map
                return result.replaceAll(result.split("\\{")[0], "");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    // close the streams and the socket
                    out.close();
                    in.close();
                    sslSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("An exception occured while trying to close the streams or the socket: " + e);
                }
            }
            return result;
    }

    /**
     * Return attractions near a particular hotel in a string (json file), which is required in Lab6.
     *
     * @param hotelID
     *
     * @return attractions in String
     * */
    public String returnAttractions(String hotelID) {
        String result = "";

        // Get a set of entries
        List<String> hotelIdList = hdata.getHotels();

        if (hotelIdList.contains(hotelID)) {
            String hotelName = hdata.getHotelName(hotelID);

            result += "Attractions near ";
            result += hotelName + ", " + hotelID;
            result += System.lineSeparator();

            String[] currentTouristAttraction = allAttractions.get(hotelID);
            for (int i = 0; i < currentTouristAttraction.length; i++) {
                result += currentTouristAttraction[i];
                result += System.lineSeparator() + System.lineSeparator();
            }
            result += "++++++++++++++++++++";

        }

        return result;
    }

    /** Print attractions near the hotels to a file.
     * The format is described in the lab description.
     *
     * @param filename
     *              - the location where the result will be stored
     */
    public void printAttractions(Path filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename.toString()));

            //TreeMap currentHotelTreeMap = hdata.getHotelTreeMap();

            // Get a set of the entries
            List<String> hotelIdList = hdata.getHotels();

            // Get an iterator
            Iterator<String> iterator = hotelIdList.iterator();
            // Set a flag to control if a new line will be added
            int flag = 0;
            while (iterator.hasNext()){
                String hotelID = iterator.next();
                String hotelName = hdata.getHotelName(hotelID);

                if (flag > 0) {
                    writer.print(System.lineSeparator());
                }
                writer.print("Attractions near ");
                writer.print(hotelName + ", " + hotelID);
                writer.print(System.lineSeparator());

                String[] currentTouristAttraction = allAttractions.get(hotelID);
                for (int i = 0; i < currentTouristAttraction.length; i++) {
                    writer.print(currentTouristAttraction[i]);
                    writer.print(System.lineSeparator()+System.lineSeparator());
                }
                writer.print("++++++++++++++++++++");

                flag++;
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set up the request, which will be sent to the server
     *
     * @param host
     *          - host address
     * @param pathResourceQuery
     *          - path of the resource and search method
     *
     * @return request
     * */
    private static String getRequest(String host, String pathResourceQuery) {
        String request = "GET " + pathResourceQuery + " HTTP/1.1" + System.lineSeparator() // GET
                // request
                + "Host: " + host + System.lineSeparator() // Host header required for HTTP/1.1
                + "Connection: close" + System.lineSeparator() // make sure the server closes the
                // connection after we fetch one page
                + System.lineSeparator();
        return request;
    }
}

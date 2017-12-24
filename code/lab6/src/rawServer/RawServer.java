package rawServer;

import hotelapp.ThreadSafeHotelData;

import java.nio.file.Paths;

public class RawServer {
    private int PORT;
    private ThreadSafeHotelData hotelData;

    /**
     * Constructor initializes the raw server and uses the given port number.
     *
     * @param PortNumber
     *
     * */
    public RawServer(int PortNumber) {
        this.PORT = PortNumber;
    }

    /**
     * This method starts the server and loads the hotels and reviews information from input file
     * */
    public void startCurrentServer() {
        hotelData.loadHotelInfo("input/hotels.json");
        hotelData.loadReviews(Paths.get("input/reviews"));
    }



    public void addMapping(String path, HttpHandler handler) {

    }
}

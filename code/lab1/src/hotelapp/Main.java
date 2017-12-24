package hotelapp;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.text.ParseException;

public class Main {
    public static void main(String args[]) {
        //addressTest();
        //hotelTest();
        reviewTest();
        //hotelDataTest();

    }

    public static void addressTest(){
        Address address = new Address("SF", "CA", "55 Fulton", 1.0, 2.0);
        address.getState();
        System.out.println(address.toString());
    }

    public static void hotelTest(){
        Address address = new Address("SF", "CA", "55 Fulton", 1.0, 2.0);
        Hotel hotel = new Hotel("12345", "jjHotel", address);
        hotel.toString();
        System.out.println(hotel.toString());
    }


    public static void reviewTest(){
        Address address = new Address("SF", "CA", "55 Fulton", 1.0, 2.0);
        Hotel hotel = new Hotel("12345", "jjHotel", address);
        try {
            Review review = new Review("12345", "review12345", 3,
                                            "reviewTitle", "reviewContent",
                                                false, "2017-01-01T17:50:37", "xiaojiji");
            Review review1 = new Review("12345", "review12346", 3,
                    "reviewTitle", "reviewContent",
                    false, "2017-01-01T17:50:37", "xiaojiji");

            System.out.println("\n"+"date test: " + review1.getDate());

            System.out.println("\n"+review1.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InvalidRatingException e) {
            e.printStackTrace();
        }

    }


    public static void hotelDataTest() {

        HotelData hd = new HotelData();

        hd.addHotel("12345", "jj", "SF", "CA", "55 Fulton", 1.0, 2.0);
        //hd.toString("12345");
        //System.out.println("\nhotal data test: \n" + hd.toString("12345"));

        hd.addHotel("54321", "JJJ", "SF2", "CA", "66 Fulton", 1.1, 2.1);
        //hd.toString("12345");
        //hd.toString("54321");

        hd.addHotel("25622", "Hilton San Francisco Union Square", "San Francisco", "CA", "55 Cyril Magnin St", 37.78,
                -122.4);
        hd.toString("25622").trim();


    }

    public static void reviewLoadTest(){

    }

}



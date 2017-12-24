package hotelapp;

public class TouristAttraction {
    // FILL IN CODE: add instance variables to store
    // name, rating, address, id

    String id;
    String name;
    double rating;
    String address;

    /** Constructor for TouristAttraction
     *
     * @param id
     * @param name
     * @param rating
     * @param address
     */
    public TouristAttraction(String id, String name, double rating, String address) {
        // FILL IN CODE
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getAddress() {
        return address;
    }

    // FILL IN CODE: add getters as needed

    /** toString() method
     * @return a String representing this
     * TouristAttraction
     */
    @Override
    public String toString() {
        // FILL IN CODE
        String res;
        res = getName() + "; " + getAddress();
        return res; // do not forget to change this
    }
}

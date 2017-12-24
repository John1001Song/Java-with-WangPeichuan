package codeCamp1;

public class Car {
    private String make;
    private String model;
    private double price;

    public Car(String model, String make, double price) {
        this.model = model;
        this.make = make;
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public String toString() {
        return model + ", " + make + ", " + price;
    }




}

package codeCamp1;

import java.util.Comparator;
import java.util.TreeSet;

public class CarTest {
    public static void main(String[] args) {
        Car car1 = new Car("Ford", "Fusion", 22300);
        Car car2 = new Car("Honda", "Civic", 14100);
        Car car3 = new Car("Mazda", "6", 15000);

        TreeSet<Car> carSet;
        // create carSet passing the comparator.

        carSet = new TreeSet<>(new Comparator<Car>() {
            @Override
            public int compare(Car c1, Car c2) {
                double p1 = c1.getPrice();
                double p2 = c2.getPrice();
                if (Math.abs(p1 - p2) < 0.01)
                    return 0;
                else if (p1 < p2)
                    return -1;
                else
                    return 1;
            }
        });

        // Add car1, car2, car3 to carSet
        carSet.add(car1);
        carSet.add(car2);
        carSet.add(car3);
        for (Car car: carSet) {
            System.out.println(car);
        }

    }

}

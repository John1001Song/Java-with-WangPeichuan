import org.junit.Assert;
import org.junit.Test;
import shapes.Circle;

/** An class that represents a circle. Stores a radius. A subclass of class Shape2D. */
public class CircleTest {

    Circle circle = new Circle(5);

    @Test
    public void testArea(){
        String testName = "testArea";
        double expectArea = 25.0 * Math.PI;
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectArea, circle.area(), 0.001);

    }

    @Test
    public void testPerimeter(){
        String testName = "testPerimeter";
        double expectArea = 10.0 * Math.PI;
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectArea, circle.perimeter(), 0.001);

    }
}

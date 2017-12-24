import org.junit.Assert;
import org.junit.Test;
import shapes.Sphere;

public class SphereTest {

    Sphere sphere = new Sphere(5.0);

    @Test
    public void testArea(){
        String testName = "testArea";
        double expectArea = 4.0 * Math.PI * Math.pow(5.0, 2.0);
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectArea, sphere.area(), 0.001);
    }

    @Test
    public void testVolume(){
        String testName = "testVolume";
        double expectArea = 4.0/3 * Math.PI * Math.pow(5.0, 3.0);
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectArea, sphere.volume(), 0.001);
    }
}

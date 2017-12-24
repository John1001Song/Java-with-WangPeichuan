import org.junit.Assert;
import org.junit.Test;
import shapes.ConvexRegularPolygon;

public class ConvexRegularPolygonTest {

    ConvexRegularPolygon convexRegularPolygon = new ConvexRegularPolygon(3,1);
    @Test
    public void testArea(){
        String testName = "testArea";
        double expectArea = Math.sqrt(3.0)/4;
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectArea, convexRegularPolygon.area(), 0.001);
    }

    @Test
    public void testPerimeter(){
        String testName = "testPerimeter";
        double expectPerimeter = 3.0;
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectPerimeter, convexRegularPolygon.perimeter(), 0.001);
    }
}

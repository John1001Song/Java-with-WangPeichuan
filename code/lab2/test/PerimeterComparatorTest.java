import comparators.PerimeterComparator;
import org.junit.Assert;
import org.junit.Test;
import shapes.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerimeterComparatorTest {
    @Test
    public void tesPerimeterComparator(){
        Circle circle1 = new Circle(4.0);
        Circle circle2 = new Circle(2.5);
        Circle circle3 = new Circle(1.0);
        ConvexRegularPolygon convexRegularPolygon1 = new ConvexRegularPolygon(5, 1.0);
        ConvexRegularPolygon convexRegularPolygon2 = new ConvexRegularPolygon(3, 3.0);
        ConvexRegularPolygon convexRegularPolygon3 = new ConvexRegularPolygon(7, 2.0);

        String testName = "testNameComparator";
        List<Shape> acutalShapeList = new ArrayList<>();
        acutalShapeList.add(convexRegularPolygon1);
        acutalShapeList.add(circle3);
        acutalShapeList.add(circle1);
        acutalShapeList.add(circle2);
        acutalShapeList.add(convexRegularPolygon2);
        acutalShapeList.add(convexRegularPolygon3);

        Collections.sort(acutalShapeList, new PerimeterComparator());

        List<Shape> expectShapeList = new ArrayList<>();
        expectShapeList.add(convexRegularPolygon1);
        expectShapeList.add(circle3);
        expectShapeList.add(convexRegularPolygon2);
        expectShapeList.add(convexRegularPolygon3);
        expectShapeList.add(circle2);
        expectShapeList.add(circle1);

        for (int i = 0; i < acutalShapeList.size(); i++){
            Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectShapeList.get(i).toString(), acutalShapeList.get(i).toString());
        }

    }

}

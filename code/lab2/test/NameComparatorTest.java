import comparators.NameComparator;
import org.junit.Assert;
import org.junit.Test;
import shapes.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NameComparatorTest {

    @Test
    public void testNameComparator(){
        Circle circle1 = new Circle(4.0);
        Circle circle2 = new Circle(2.5);
        Circle circle3 = new Circle(1.0);
        ConvexRegularPolygon convexRegularPolygon1 = new ConvexRegularPolygon(5, 1.0);
        Sphere sphere1 = new Sphere(5.0);
        PlatonicSolid platonicSolid0 = new PlatonicSolid(3, 5, 2.5);

        String testName = "testNameComparator";
        List<Shape> acutalShapeList = new ArrayList<>();
        acutalShapeList.add(platonicSolid0);
        acutalShapeList.add(sphere1);
        acutalShapeList.add(convexRegularPolygon1);
        acutalShapeList.add(circle3);
        acutalShapeList.add(circle2);
        acutalShapeList.add(circle1);

        Collections.sort(acutalShapeList, new NameComparator());

        List<Shape> expectShapeList = new ArrayList<>();
        expectShapeList.add(circle3);
        expectShapeList.add(circle2);
        expectShapeList.add(circle1);
        expectShapeList.add(convexRegularPolygon1);
        expectShapeList.add(platonicSolid0);
        expectShapeList.add(sphere1);

        for (int i = 0; i < acutalShapeList.size(); i++){
            Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectShapeList.get(i).toString(), acutalShapeList.get(i).toString());
        }

    }
}

import comparators.VolumeComparator;
import org.junit.Assert;
import org.junit.Test;
import shapes.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VolumeComparatorTest {
    @Test
    public void testVolumeComparator(){
        Sphere sphere1 = new Sphere(1.5);
        Sphere sphere2 = new Sphere(4.0);
        Sphere sphere3 = new Sphere(5.0);

        PlatonicSolid platonicSolid1 = new PlatonicSolid(4, 3, 1.0);
        PlatonicSolid platonicSolid2 = new PlatonicSolid(3, 3, 1.0);
        PlatonicSolid platonicSolid3 = new PlatonicSolid(3, 4, 1.0);


        String testName = "testVolumeComparator";
        List<Shape> acutalShapeList = new ArrayList<>();
        acutalShapeList.add(sphere3);
        acutalShapeList.add(sphere2);
        acutalShapeList.add(sphere1);
        acutalShapeList.add(platonicSolid1);
        acutalShapeList.add(platonicSolid2);
        acutalShapeList.add(platonicSolid3);

        Collections.sort(acutalShapeList, new VolumeComparator());

        List<Shape> expectShapeList = new ArrayList<>();
        expectShapeList.add(platonicSolid2);
        expectShapeList.add(platonicSolid3);
        expectShapeList.add(platonicSolid1);
        expectShapeList.add(sphere1);
        expectShapeList.add(sphere2);
        expectShapeList.add(sphere3);

        for (int i = 0; i < acutalShapeList.size(); i++){
            Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectShapeList.get(i).toString(), acutalShapeList.get(i).toString());
        }

    }

}

import org.junit.Assert;
import org.junit.Test;
import shapes.PlatonicSolid;

public class PlatonicSolidTest {

    PlatonicSolid tetrahedron = new PlatonicSolid(3,3,2.0);
    PlatonicSolid cube = new PlatonicSolid(4, 3, 2.0);
    PlatonicSolid octahedron = new PlatonicSolid(3, 4, 2.0);
    PlatonicSolid dodecahedron = new PlatonicSolid(5, 3, 2.0);
    PlatonicSolid icosahedron = new PlatonicSolid(3, 5, 2.0);

    @Test
    public void testTetrahedronArea() {
        String testName = "testTetrahedronArea";
        double expectArea = 4.0 * Math.sqrt(3);
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectArea, tetrahedron.area(), 0.001);
    }

    @Test
    public void testCubeArea() {
        String testName = "testCubeArea";
        double expectArea = 24.0;
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectArea, cube.area(), 0.001);
    }

    @Test
    public void testOctahedronArea(){
        String testName = "testOctahedronArea";
        double expectArea = 8.0 * Math.sqrt(3);
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectArea, octahedron.area(), 0.001);
    }

    @Test
    public void testDodecahedronArea(){
        String testName = "testDodecahedronArea";
        double expectArea = 12.0 * Math.sqrt(25 + 10*Math.sqrt(5.0));
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectArea, dodecahedron.area(), 0.001);
    }

    @Test
    public void testIcosahedronArea(){
        String testName = "testIcosahedronArea";
        double expectArea = 20.0 * Math.sqrt(3);
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectArea, icosahedron.area(), 0.001);
    }



    // volume test
    @Test
    public void testTetrahedronVolume(){
        String testName = "testTetrahedronVolume";
        double expectArea = Math.sqrt(8.0)/3;
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectArea, tetrahedron.volume(), 0.001);
    }

    @Test
    public void testCubeVolume(){
        String testName = "testCubeVolume";
        double expectArea = 8.0;
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectArea, cube.volume(), 0.001);
    }

    @Test
    public void testOctahedronVolume(){
        String testName = "testOctahedronVolume";
        double expectArea = Math.sqrt(128.0)/3;
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectArea, octahedron.volume(), 0.001);
    }

    @Test
    public void testDodecahedronVolume(){
        String testName = "testDodecahedronVolume";
        double expectArea = 61.304952;
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectArea, dodecahedron.volume(), 0.001);
    }

    @Test
    public void testIcosahedronVolume(){
        String testName = "testIcosahedronVolume";
        double expectArea = 17.453560;
        Assert.assertEquals(String.format("%n" + "Test Case: %s%n", testName), expectArea, icosahedron.volume(), 0.001);
    }
}

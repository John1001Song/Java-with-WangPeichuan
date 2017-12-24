import shapes.*;
import comparators.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import org.junit.Assert;
import org.junit.Test;

public class ShapeSorterTest {

	@Test
	public void testPrintToFile() {
		Circle c = new Circle(4.5);
		String testName = "testPrintToFile";
		ShapeSorter sorter = new ShapeSorter();
		String inputShapeFile = TestUtils.INPUT_DIR + File.separator + "shapesFile.txt";
		sorter.loadShapes(inputShapeFile);	
		String studentOutput = TestUtils.OUTPUT_DIR + File.separator +"studentOutputUnsorted";
		Path actual = Paths.get(studentOutput);
		// Delete the previous output before printing to a file
		if (Files.exists(actual)) {
			try {
				Files.delete(actual);
			} catch (IOException e) {
				System.out.println("Could not delete an old studentOutput file.");
			}
		}
		sorter.printToFile(studentOutput);
		
		Path expected = Paths.get(TestUtils.OUTPUT_DIR + File.separator + "expectedOutputUnsorted"); 											// output
		// compare files
		int count = 0;
		try {
			count = TestUtils.checkFiles(expected, actual);
			// System.out.println(count);
		} catch (IOException e) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" + " File check failed: %s%n", testName, e.getMessage()));
		}

		if (count <= 0) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" + " Mismatched Line: %d%n", testName, -count));
		}
	}
	

	/** This test will call the loadShapes method first, then will call sortShapes 
	 *  3 times to sort three lists of shapes by area. 
	 *  Then it will print all three lists to the file and compare with the expected output.
	 */
	@Test
	public void testSortByArea() {
		String testName = "testSortByArea";
		ShapeSorter sorter = new ShapeSorter();
		String inputShapeFile = TestUtils.INPUT_DIR + File.separator + "shapesFile.txt";

		sorter.loadShapes(inputShapeFile);
		sorter.sortShapes("all", new AreaComparator());
		sorter.sortShapes("2D", new AreaComparator());
		sorter.sortShapes("3D", new AreaComparator());
		String studentOutput = TestUtils.OUTPUT_DIR + File.separator + "studentOutputByArea";
		Path actual = Paths.get(studentOutput);
		// Delete the previous output before printing to a file
		if (Files.exists(actual)) {
			try {
				Files.delete(actual);
			} catch (IOException e) {
				System.out.println("Could not delete an old studentOutput file.");
			}
		}
		sorter.printToFile(studentOutput);

		Path expected = Paths.get(TestUtils.OUTPUT_DIR + File.separator +"expectedOutputByArea");
		int count = 0;
		try {
			count = TestUtils.checkFiles(expected, actual);
			//System.out.println(count);
		} catch (IOException e) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" + " File check failed: %s%n", testName, e.getMessage()));
		}

		if (count <= 0) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" + " Mismatched Line: %d%n", testName, -count));
		}

	}
	
	/** This test will call the loadShapes method first, then will call sortShapes 
	 *  3 times to sort three lists of shapes by name. 
	 *  Then it will print all three lists to the file and compare with the expected output.
	 */
	@Test
	public void testSortByName() {
		String testName = "testSortByName";
		ShapeSorter sorter = new ShapeSorter();
		String inputShapeFile = TestUtils.INPUT_DIR + File.separator + "shapesFile.txt";

		sorter.loadShapes(inputShapeFile);
		sorter.sortShapes("all", new NameComparator());
		sorter.sortShapes("2D", new NameComparator());
		sorter.sortShapes("3D", new NameComparator());
		String studentOutput = TestUtils.OUTPUT_DIR + File.separator +"studentOutputByName";
		Path actual = Paths.get(studentOutput); // your result
		// Delete the previous output before printing to a file
		if (Files.exists(actual)) {
			try {
				Files.delete(actual);
			} catch (IOException e) {
				System.out.println("Could not delete an old studentOutput file.");
			}
		}
		sorter.printToFile(studentOutput);

		Path expected = Paths.get(TestUtils.OUTPUT_DIR + File.separator +"expectedOutputByName");
		int count = 0;
		try {
			count = TestUtils.checkFiles(expected, actual);
			//System.out.println(count);
		} catch (IOException e) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" + " File check failed: %s%n", testName, e.getMessage()));
		}

		if (count <= 0) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" + " Mismatched Line: %d%n", testName, -count));
		}

	}
	
	/** This test will call the loadShapes method first, then will call sortShapes 
	 *  to sort a list of 2d shapes by perimeter. 
	 *  Note that other two lists will NOT be sorted.
	 *  Then it will print all three lists to the file and compare with the expected output.
	 *  Note that in the file, only the list of 2D shapes will be sorted by perimeter.
	 */
	@Test
	public void testSortByPerimeter() {
		String testName = "testSortByPerimeter";
		ShapeSorter sorter = new ShapeSorter();
		String inputShapeFile = TestUtils.INPUT_DIR + File.separator + "shapesFile.txt";

		sorter.loadShapes(inputShapeFile);
		sorter.sortShapes("2D", new PerimeterComparator());
		String studentOutput = TestUtils.OUTPUT_DIR + File.separator + "studentOutputByPerimeter";
		Path actual = Paths.get(studentOutput); // your result
		// Delete the previous output before printing to a file
		if (Files.exists(actual)) {
			try {
				Files.delete(actual);
			} catch (IOException e) {
				System.out.println("Could not delete an old studentOutput file.");
			}
		}
		sorter.printToFile(studentOutput);

		Path expected = Paths.get(TestUtils.OUTPUT_DIR + File.separator + "expectedOutputByPerimeter"); // expected result
		int count = 0;
		try {
			count = TestUtils.checkFiles(expected, actual);
			// System.out.println(count);
		} catch (IOException e) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" + " File check failed: %s%n", testName, e.getMessage()));
		}

		if (count <= 0) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" + " Mismatched Line: %d%n", testName, -count));
		}

	}
	
	/** This test will call the loadShapes method first, then will call sortShapes 
	 *  to sort a list of 3d shapes by volume.
	 *  Note that other two lists of shapes will NOT be sorted.
	 *  Then it will print all three lists to the file and compare with the expected output.
	 *  Note that in the file, only the list of 3D shapes will be sorted by volume.
	 */
	@Test
	public void testSortByVolume() {
		String testName = "testSortByVolume";
		ShapeSorter sorter = new ShapeSorter();
		String inputShapeFile = TestUtils.INPUT_DIR + File.separator + "shapesFile.txt";

		sorter.loadShapes(inputShapeFile);
		sorter.sortShapes("3D", new VolumeComparator());
		String studentOutput = TestUtils.OUTPUT_DIR + File.separator + "studentOutputByVolume";
		Path actual = Paths.get(studentOutput); // your result
		// Delete the previous output before printing to a file
		if (Files.exists(actual)) {
			try {
				Files.delete(actual);
			} catch (IOException e) {
				System.out.println("Could not delete an old studentOutput file.");
			}
		}
		sorter.printToFile(studentOutput);

		Path expected = Paths.get(TestUtils.OUTPUT_DIR + File.separator +"expectedOutputByVolume"); // expected result
		int count = 0;
		try {
			count = TestUtils.checkFiles(expected, actual);
			//System.out.println(count);
		} catch (IOException e) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" + " File check failed: %s%n", testName, e.getMessage()));
		}

		if (count <= 0) {
			Assert.fail(String.format("%n" + "Test Case: %s%n" + " Mismatched Line: %d%n", testName, -count));
		}

	} 


}



package shapes;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A class that stores a collection of shapes, and is able to sort shapes based
 * on different criteria. Stores shapes in three lists: allShapes, shapes2D,
 * shapes3D. For general shapes: sorting is by area or by name. For 2d shapes,
 * sorting is by area, by name or by perimeter. For 3d shapes, sorting is by
 * area, by name or by volume.
 * 
 * @author okarpenko
 *
 */
public class ShapeSorter {
	// Store in three data structures: 1) all shapes; 2) all 2D shapes; 3) all
	// 3D shapes
	private List<Shape> shapes;
	private List<Shape> shapes2D;
	private List<Shape> shapes3D;

	/** A default constructor for class ShapeSorter */
	public ShapeSorter() {
		// FILL IN CODE
		// Initialize the lists
		shapes = new ArrayList<Shape>();
		shapes2D = new ArrayList<Shape>();
		shapes3D = new ArrayList<Shape>();

	}

	/**
	 * Read a single line of string and capture the key words. Then, analyze which shape the input is.
	 *
	 * @param line
	 * 			- The current line that contains the raw information about the shape
	 */
	public void addShape(String line){
		String currentShape;
		String[] arr = line.split(" ");
		double radius;
		int numbOfEdge;
		double edgeLength;
		int numbOfFace;

		currentShape = arr[0];

		switch (currentShape) {
			case "Circle:": {
				radius = Double.parseDouble(arr[1]);
				Shape2D Circle = new Circle(radius);
				//System.out.println(radius);
				shapes2D.add(Circle);
				shapes.add(Circle);
				break;
			}
			case "ConvexRegularPolygon:": {
				numbOfEdge = Integer.parseInt(arr[1]);
				edgeLength = Double.parseDouble(arr[2]);
				Shape2D ConvexRegularPolygon = new ConvexRegularPolygon(numbOfEdge, edgeLength);
				shapes2D.add(ConvexRegularPolygon);
				shapes.add(ConvexRegularPolygon);
				break;
			}
			case "Sphere:": {
				radius = Double.parseDouble(arr[1]);
				Shape3D Sphere = new Sphere(radius);
				shapes3D.add(Sphere);
				shapes.add(Sphere);
				break;
			}
			case "PlatonicSolid:": {
				numbOfEdge = Integer.parseInt(arr[1]);
				numbOfFace = Integer.parseInt(arr[2]);
				edgeLength = Double.parseDouble(arr[3]);
				Shape3D PlatonicSolid = new PlatonicSolid(numbOfEdge, numbOfFace, edgeLength);
				shapes3D.add(PlatonicSolid);
				shapes.add(PlatonicSolid);
				break;
			}
		}

	}

	/**
	 * Read a given input file, create different shapes, and add them to the data
	 * structures.
	 * To understand the format of the file, look at the file "shapesFile.txt" in in the input folder.
	 * This method should handle I/O Exceptions.
	 * 
	 * @param filename
	 *            - The name of the file that contains info about the shapes.
	 */
	public void loadShapes(String filename) {
		// FILL IN CODE

		String line = null;

		try {
			// FileReader reads text file
			FileReader fileReader = new FileReader(filename);

			// add FileReader to BufferedReader
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				// add shapes into their lists
				addShape(line);
			}

			bufferedReader.close();

		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}

	}

	/**
	 * Print the list of shapes to a file as following:
	 * First, all 2d shape (one line per shape), followed by an empty line.
	 * Then all 3d shapes, followed by an empty line..
	 * Then all shapes from the list.
	 * Each shape should be printed according to the format specified in the toString()
	 * method of the corresponding parent class.
	 * This method should handle I/O Exceptions.
	 * @param filename
	 * 			the name of the output file
	 */
	public void printToFile(String filename) {
		// FILL IN CODE
		try {
			FileWriter fileWriter = new FileWriter(filename);

			//BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			PrintWriter writer = new PrintWriter(fileWriter);

			// write shape2D
			for (Shape shape: shapes2D){
				writer.print(shape.toString());
				System.out.println(shape.toString());
				writer.print(System.lineSeparator());
			}
			writer.print(System.lineSeparator());

			// write shape3D
			for (Shape shape: shapes3D){
				writer.print(shape.toString());
				writer.print(System.lineSeparator());
			}

			// write shapes
			for (Shape shape: shapes){
				writer.print(System.lineSeparator());
				writer.print(shape.toString());
			}
			writer.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Sort a list of Shape-s
	 * 
	 * @param whichShapes
	 *            - A string, either "all", "2D" or "3D" - specifies which list
	 *            of shapes to sort
	 * @param comparator
	 *            - A Comparator object that tells the method how to sort
	 *            shapes.
	 */
	public void sortShapes(String whichShapes, Comparator<Shape> comparator) {
		// FILL IN CODE
		switch (whichShapes) {
		case "all":
			Collections.sort(shapes, comparator);
			break;
		case "2D":
			Collections.sort(shapes2D, comparator);
			break;
		case "3D":
			Collections.sort(shapes3D, comparator);
			break;
		default:
			System.out.println("Trying to sort invalid type of shape, did not sort.");
		}

	}

}

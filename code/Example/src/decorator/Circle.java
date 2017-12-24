package decorator;

/** Circle class
 *  The example is based on
 *  https://www.tutorialspoint.com/design_pattern/decorator_pattern.htm
 */
public class Circle implements Drawable {
	private double radius;

	Circle() {
		radius = 1.0;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double r) {
		radius = r;
	}

	@Override
	public void draw() {
		System.out.println("Circle:  " + radius);
		// Assume here we have  the code to draw a circle
	}

	

}

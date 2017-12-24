package decorator;
/** Rectangle class
 *  The example is based on
 *  https://www.tutorialspoint.com/design_pattern/decorator_pattern.htm
 */
public class Rectangle implements Drawable {
	private double width, height = 0;
	public Rectangle() { }

	public void setWidth(double w) {
		width = w;
	}
	
	public void setHeight(double h) {
		height = h;
	}

	@Override
	public void draw() {
		System.out.println("Rectangle: " + width + " " + height);
		// code here that draws a rectangle
	}
	
	
	
	
}

package shapes;

import java.text.DecimalFormat;

/** An abstract class Shape. At the top of the shape hierarchy. */
public abstract class Shape {
	protected final DecimalFormat df = new DecimalFormat("#.00");

	/**
	 * An abstract method for computing the area or the surface area of the
	 * shape.
	 * 
	 * @return area of the shape
	 */
	public abstract double area();

	/**
	 * Returns a string representation of the shape in the following format: The
	 * name of the shape, 1 tab, the area (formatted so that it only shows two
	 * digits after the decimal point)
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		String formattedName = this.getClass().getSimpleName() + "\t";
		return formattedName + df.format(area());
	}

}

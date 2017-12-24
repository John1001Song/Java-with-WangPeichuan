package shapes;

/** An abstract class Shape2D. Extends Shape. A direct parent of all two-dimensional shape classes. */
public abstract class Shape2D extends Shape {

    /**
     * An abstract method for computing the perimeter of the shape.
     *
     * @return perimeter of the 2d shape
     */
    public abstract double perimeter();

    /**
     * Overrides toString() from class Shape. Adds information about the perimeter of the shape. Format: The name of the shape, 1 tab, the area (formatted so that it only shows two digits after the decimal point), 1 tab, the perimeter(formatted so that it only shows two digits after the decimal point).
     *
     * @return String
     */
    @Override
    public String toString() {
        return super.toString() + "\t" + super.df.format(perimeter());
    }
}

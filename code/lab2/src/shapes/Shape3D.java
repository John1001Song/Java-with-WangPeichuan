package shapes;

/** An abstract class Shape3D. Extends Shape. A direct parent of all three-dimensional shape classes. */
public abstract class Shape3D extends Shape{

    /**
     * An abstract method for computing the volume of the shape.
     *
     * @return volume of the 3d shape
     */
    public abstract double volume();

    /**
     * Overrides toString() from class Shape.
     * Adds information about the volume of the 3D shape.
     * Format: The name of the shape, 1 tab,
     *         the area (formatted so that it only shows two digits after the decimal point),
     *         1 tab,
     *         the volume (formatted so that it only shows two digits after the decimal point).
     *
     * @return String
     */
    @Override
    // return the string representation of a shape
    public String toString() {

        return super.toString() + "\t" + super.df.format(volume());
    }

}

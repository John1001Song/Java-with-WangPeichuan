package shapes;

/** An class that represents a circle. Stores a radius. A subclass of class Shape2D. */
public class Circle extends Shape2D {

    private double radius;

    public Circle(double radius){
        this.radius = radius;
    }

    public double getRadius(){
        return this.radius;
    }

    /** A method inherited from class Shape,
     *
     * @returns the area
     */
    @Override
    public double area() {
        return Math.PI * Math.pow(radius, 2);
    }

    /**Circle overrides this method, inherited from class Shape2D,
     *
     * @returns the perimeter of the circle. */

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

}

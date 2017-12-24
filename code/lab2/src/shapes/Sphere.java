package shapes;

/** An class that represents a Sphere. Stores a radius. A subclass of class Shape3D. */
public class Sphere extends Shape3D {

    private double radius;

    public Sphere(double radius){
        this.radius = radius;
    }

    /**
     * Class Sphere overrides this method inherited from Shape to return the surface area of the sphere.
     *
     * @return surface area of the sphere
     */
    @Override
    public double area() {
        return 4.0 * Math.PI * Math.pow(radius, 2);
    }

    /**
     * Class Sphere overrides volume method inherited from Shape3D to return the volume of the sphere.
     *
     * @return volume of the sphere
     */
    @Override
    public double volume() {
        return 4.0/3 * Math.PI * Math.pow(radius, 3);
    }
}

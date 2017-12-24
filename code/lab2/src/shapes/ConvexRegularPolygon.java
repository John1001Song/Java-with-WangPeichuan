package shapes;

/** An class that represents a convex regular polygon. Stores a number of sides and the length of the side. A subclass of class Shape2D. */
public class ConvexRegularPolygon extends Shape2D{

    private int numbOfEdge;
    private double edgeLength;

    public ConvexRegularPolygon(int numbOfEdge, double edgeLength){
        this.numbOfEdge = numbOfEdge;
        this.edgeLength = edgeLength;
    }

    /** ConvexRegularPolygon overrides this method, inherited from class Shape.
     *
     * @return area of the convex regular polygon
     * */
     @Override
    public double area() {
        return 1.0/4 * numbOfEdge * Math.pow(edgeLength, 2) * 1/Math.tan((Math.PI)/numbOfEdge);
    }

    /** ConvexRegularPolygon overrides this method, inherited from class Shape2D.
     *
     * @return perimeter of the convex regular polygon
     */
     @Override
    public double perimeter() {
        return numbOfEdge * edgeLength;
    }
}

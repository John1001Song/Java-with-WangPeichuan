package comparators;

import shapes.Shape;
import shapes.Shape2D;

import java.util.Comparator;

/** A custom comparator that compares 2d shapes by perimeter. */
 public class PerimeterComparator implements Comparator<Shape> {
    final double EPS = 0.001;

    /** Compare 2d shapes based on the perimeter. Downcast o1 and o2 to Shape2D before calling the perimeter method. */
     @Override
    public int compare(Shape o1, Shape o2){
        if (Math.abs(((Shape2D)o1).perimeter() - ((Shape2D)o2).perimeter()) < EPS) {
            return 0;
        }else if (((Shape2D)o1).perimeter() > ((Shape2D)o2).perimeter()){
            return 1;
        }else return -1;
    }
}

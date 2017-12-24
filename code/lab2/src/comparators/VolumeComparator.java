package comparators;

import shapes.Shape;
import shapes.Shape3D;
import java.util.Comparator;

/** A custom comparator that compares 3d shapes by volume. */
public class VolumeComparator implements Comparator<Shape> {

    final double EPS = 0.001;

    /** Compare 3d shapes based on the volume. Downcast o1 and o2 to Shape3D before calling the volume method. */
    @Override
    public int compare(Shape o1, Shape o2){
        if (Math.abs(((Shape3D)o1).volume() - ((Shape3D)o2).volume()) < EPS){
            return 0;
        }else if (((Shape3D)o1).volume() > ((Shape3D)o2).volume()) {
            return 1;
        }else return -1;
    }
}

package comparators;

import shapes.Shape;

import java.util.Comparator;

/** A custom comparator that compares shapes by area (or surface area). */
public class AreaComparator implements Comparator<Shape>{

    final double EPS = 0.001;

    /**
     * Compare shapes based on the area
     */
    @Override
    public int compare(Shape o1, Shape o2){
        if (Math.abs(o1.area() - o2.area()) < EPS){
            return 0;
        }else if (o1.area() > o2.area()){
            return 1;
        }else return -1;

    }
}

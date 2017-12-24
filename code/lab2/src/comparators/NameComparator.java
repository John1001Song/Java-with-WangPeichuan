package comparators;

import shapes.Shape;

import java.util.Comparator;

/** A custom comparator that compares shapes by name alphabetically. */
public class NameComparator implements Comparator<Shape>{

	/** Compare shapes based on the name */
	 @Override
	public int compare(Shape o1, Shape o2) {
		return o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
	}

}

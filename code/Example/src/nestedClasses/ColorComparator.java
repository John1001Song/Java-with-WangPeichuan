package nestedClasses;

import java.util.Comparator;
/** A class that implements Comparator. Used in ColorComparatorWithAnonymousClass example */
public class ColorComparator implements Comparator<Color> {
	private boolean ascending;
	public ColorComparator( boolean ascending) {
		this.ascending = ascending;
	}

	@Override
	public int compare(Color o1, Color o2) {
		if (ascending)
			return o1.getColor().compareTo(o2.getColor());
		else
			return o2.getColor().compareTo(o1.getColor());
	}

	public boolean getAscending() {
		return ascending;
	}

}

package nestedClasses;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author O. Karpenko 
 * In this class, we first create an instance of an
 * anonymous class that implements Comparator<Color> and overrides the
 * compare method. The object is then passed to the constructor of the
 * TreeSet, to specify the order of elements in the TreeSet.
 *
 * The second method shows how to create an instance of an anonymous class that extends class ColorComparator
 * @see Color , ColorComparator
 */
public class ColorComparatorWithAnonymousClass {

	/** Shows how to create a TreeSet where the order of elements is defined by the comparator */
	public static void createTreeSetOfColors1() {

		// comp is a reference to an instance of an anonymous class that
		// implements Comparator<Color> and
		// overrides the compare method.
		Comparator<Color> comp = new Comparator<Color>() { // defining an anonymous class here
			@Override
			public int compare(Color s1, Color s2) {
				return s1.getColor().compareTo(s2.getColor());
			}

		};

		// create a TreeSet, pass comparator object as a parameter
		// so that java knows how to sort colors in the tree set
		Set<Color> colors = new TreeSet<Color>(comp);
		colors.add(new Color("Red"));
		colors.add(new Color("green"));
		colors.add(new Color("Blue"));
		colors.add(new Color("Yellow"));

		System.out.println("In createTreeSetOfColors1(): comparison is case sensitive");
		System.out.println(colors + System.lineSeparator());


	}

	/** Shows how to create a TreeSet of Color(s),  where the order of elements is defined by the comparator 
	 *  object that extends an existing ColorComparator class */
	public static void createTreeSetOfColors2() {

		Comparator<Color> colorComp = new ColorComparator(true) {  // passing param to the constructor of the super class

			@Override
			public int compare(Color s1, Color s2) { // case insensitive
				if (getAscending()) { // get the value of ascending from the super class
					return s1.getColor().compareToIgnoreCase(s2.getColor()); // ignores the case so that "ReD" and "red" are the same
				}
				else
					return s2.getColor().compareToIgnoreCase(s1.getColor());
			}

		};

		Set<Color> colors = new TreeSet<Color>(colorComp);
		colors.add(new Color("Red"));
		colors.add(new Color("green"));
		colors.add(new Color("Blue"));
		colors.add(new Color("Yellow"));

		System.out.println("In createTreeSetOfColors2(): comparison is case-insensitive:");
		System.out.println(colors);
	}

	public static void main(String[] args) {
		ColorComparatorWithAnonymousClass.createTreeSetOfColors1();
		ColorComparatorWithAnonymousClass.createTreeSetOfColors2();

	}
}

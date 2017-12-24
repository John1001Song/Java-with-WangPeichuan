import java.util.*;

public static void main(String[] args) {
	
	rectangleInClass r1 = new rectangleInClass(3, 5);
	rectangleInClass r2 = new rectangleInClass(6,6);

	Comparator<rectangleInClass> comp1 = new ComparaterArea();
	Comparator<rectangleInClass> comp2 = new ComparatorPerimeter();

	Set<rectangleInClass> rec = new TreeSet<rectangleInClass>(comp1);
	rec.add(r1);
	rec.add(r2);
	System.out.println(rec);
}
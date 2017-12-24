import java.util.*;

public class ComparatorPerimeter implements Comparator<rectangleInClass>{
	public int compare(rectangleInClass r1, rectangleInClass r2){
		if (r1.perimeter() < r2.perimeter()) {
			return 0;
		}
		else if (r1.perimeter() == r2.perimeter()) {
			return 1;
		}else return -1;
	}
}
import java.util.*;

public class ComparaterArea implements Comparator<rectangleInClass>{

	public int compare(rectangleInClass r1, rectangleInClass r2){
		if (r1.area() < r2.area()) {
			return 0;
		}
		else if (r1.area() == r2.area()) {
			return 1;
		}else return -1;
	}
	
}
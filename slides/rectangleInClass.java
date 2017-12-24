import java.util.*;

public class rectangleInClass{
	
	public int width;
	public int height;

	public rectangleInClass(int width, int height){
		this.width = width;
		this.height = height;
	}

	public int area(){

		return width * height;

	}

	public int perimeter(){

		return (width+height)*2;

	}
}
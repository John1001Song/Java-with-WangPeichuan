package adapterPattern;
/** Modified from https://sourcemaking.com/design_patterns/adapter/java/1 */

public class ShapesTest {
	public static void main(String[] args) {

		// Without the adapter:
		/*Object[] shapes = { new Line(), new Rectangle() };
		int x1 = 10, y1 = 20;
		int x2 = 30, y2 = 60;
		for (int i = 0; i < shapes.length; ++i)
			if (shapes[i].getClass().getSimpleName().equals("Line"))
				((Line) shapes[i]).draw(x1, y1, x2, y2);
			else if (shapes[i].getClass().getSimpleName().equals("Rectangle"))
				((Rectangle) shapes[i]).draw(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
		*/
		
		// After using adapter pattern:
		Shape[] shapes = { new LineShape(), new RectangleShape() };
		// A begin and end point from a graphical editor
		int x1 = 10, y1 = 20;
		int x2 = 30, y2 = 60;
		for (int i = 0; i < shapes.length; ++i)
			shapes[i].draw(x1, y1, x2, y2);
	}

}

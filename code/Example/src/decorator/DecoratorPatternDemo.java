package decorator;
/** Demonstrates Decorator Pattern
 *  Allows to add some functionality to an object of a class at runtime
 *  The example is modifying from:
 *  https://www.tutorialspoint.com/design_pattern/decorator_pattern.htm
 */
public class DecoratorPatternDemo {
	   public static void main(String[] args) {

		  // Create a "plain" circle and "draw" it
	      Drawable circle = new Circle();
	      circle.draw();
	      System.out.println();

	      // Decorate it to make a circle with border, and "draw" it
	      Drawable circleWithBorder = new BorderDecorator(circle);
	      System.out.println("Circle with border");
	      circleWithBorder.draw();
	      System.out.println();

	      // Decorate the circle with border and color decorators.
		  // Can easily wrap object in multiple decorators using chaining:
	      new ColorDecorator(new BorderDecorator(circle), "blue").draw();
	      System.out.println();

	      // Creating a rectangle:
	      Rectangle rectangle = new Rectangle();
	      rectangle.setWidth(5);
	      rectangle.setHeight(3);
		  // Decorating it with Border:
	      Drawable rectangleWithBorder = new BorderDecorator(rectangle);
	      System.out.println("Rectangle with border");
	      rectangleWithBorder.draw();
	   }
	}
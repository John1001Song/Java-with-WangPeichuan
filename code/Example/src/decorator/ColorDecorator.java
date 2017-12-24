package decorator;

/** ColorDecorator class
 *  The example is based on
 *  https://www.tutorialspoint.com/design_pattern/decorator_pattern.htm
 */
public class ColorDecorator extends ShapeDecorator {
	private String color;
	public ColorDecorator(Drawable decoratedShape, String color) {
	      super(decoratedShape);
	      this.color = color;
	   }

	   @Override
	   public void draw() {
	      getDecoratedShape().draw();
	      fillColor(getDecoratedShape());
	   }

	   private void fillColor(Drawable decoratedShape){
		  System.out.println("Fill shape with " + color + " color.");
	   }
}

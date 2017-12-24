package decorator;

/** BorderDecorator class
 *  The example is based on
 *  https://www.tutorialspoint.com/design_pattern/decorator_pattern.htm
 */
public class BorderDecorator extends ShapeDecorator {
	public BorderDecorator(Drawable decoratedShape) {
	      super(decoratedShape);		
	   }

	   @Override
	   public void draw() {
	      getDecoratedShape().draw();
	      addBorder(getDecoratedShape());
	   }

	   private void addBorder(Drawable decoratedShape){
			System.out.println("Add a Border around the shape. ");
	   }
}

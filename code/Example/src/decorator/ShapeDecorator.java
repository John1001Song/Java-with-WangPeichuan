package decorator;

/** Abstract class that all decorators will implement. Note that it implements
 *  Drawable and stores decoratedShape as instance variable
 *  The example is based on
 *  https://www.tutorialspoint.com/design_pattern/decorator_pattern.htm
 */
public abstract class ShapeDecorator implements Drawable {
	private Drawable decoratedShape;

	public ShapeDecorator(Drawable decoratedShape){
	      this.decoratedShape = decoratedShape;
	   }

	public void draw(){
	      decoratedShape.draw();
	   }

	protected Drawable getDecoratedShape() {
		return decoratedShape;
	}
}

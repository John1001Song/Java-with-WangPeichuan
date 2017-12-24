package decoratorBeveragesExample;

/** The decorator: adds Milk to the Beverage
 *  The code is modified from Head First Design Pattern book.
 *
 */
public class BeverageWithMilk extends CondimentDecorator {
	private Beverage beverage;

	public BeverageWithMilk(Beverage beverage) {
		this.beverage = beverage;
	}

	public String getDescription() {
		return beverage.getDescription() + ", Milk";
	}

	public double cost() {
		return .10 + beverage.cost();
	}
}

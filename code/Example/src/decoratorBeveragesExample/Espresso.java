package decoratorBeveragesExample;

/* The code is from Head First Design Pattern book */
public class Espresso extends Beverage {
  
	public Espresso() {
		setDescription("Espresso");
	}
  
	public double cost() {
		return 1.99;
	}
}


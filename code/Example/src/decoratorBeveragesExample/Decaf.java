package decoratorBeveragesExample;

/* The code is from Head First Design Pattern book */
public class Decaf extends Beverage {
	public Decaf() {
		setDescription("Decaf Coffee");
	}
 
	public double cost() {
		return 1.05;
	}
}


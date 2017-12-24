package factoryMethod;
/** From http://www.newthinktank.com/2012/09/factory-design-pattern-tutorial/ */
// Subclasses in this example are not very interesting. They are used just to demo the idea of the factory.
public class BigUFOEnemyShip extends UFOEnemyShip {
	
	public BigUFOEnemyShip(){
		
		setName("Big UFO Enemy Ship");
		
		setDamage(40.0);
		
	}
	
}
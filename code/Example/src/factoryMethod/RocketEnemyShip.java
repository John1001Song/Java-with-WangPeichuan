package factoryMethod;

/** 
 * From http://www.newthinktank.com/2012/09/factory-design-pattern-tutorial
 */
public class RocketEnemyShip extends EnemyShip {
	public RocketEnemyShip() {
		setName("Rocket Enemy Ship");
		setDamage(10.0);
	}

}

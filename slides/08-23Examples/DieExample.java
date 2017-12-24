/** The Driver for the Die class. 
 *  Creates two dice, rolls them and prints their face values. 
 */
public class DieExample {
	
	public static void main(String[] args) {

		// Create two dice using two different constructors
		Die die1  = new Die();
		Die die2 = new Die(6);

		// roll the dice
		die1.roll();
		die2.roll();

		// print info about the dice
		System.out.println("Die1: " + die1);
		System.out.println("Die2: " + die2);

	}
}
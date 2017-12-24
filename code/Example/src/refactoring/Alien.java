package refactoring;


/** From the book : Java 1.4 Game programming.
 *  Represents an Alien.
 *  Exercise: use IntelliJ to pull up getGreeting() to the superclass level */

public class Alien extends Creature {

	private String planet; // the name of the planet
	private static int numAliensFromMars = 0; // the number of aliens from Mars

	public Alien(String greeting, String planet) {
		super(greeting);
		this.planet = planet;
		if (planet.equals("Mars"))
			numAliensFromMars++;
	}

	// Use IntelliJ to pull up getGreeting() from Alien class to Creature class
	public String getGreeting() {
		return greeting;
	}


	public void speak() {

		System.out.print("Alien from planet " + planet + " says: " +  getGreeting() + ". ");
	}
	
	public void fight() {
		// If this alien is from Mars and the total number of aliens from Mars is greater than 3, then 
		// print "Let's fight!");
		if ((planet.equals("Mars"))&&(numAliensFromMars >=3)) {
			System.out.println("Let's fight!");
		}
		
	}
}

package refactoring;

/**
 * From the book : Java 1.4 Game programming. Represents a Human, a subclass of
 * Creature. Overrides speak() method to say: Human says <getGreeting()> ! *
 */
public class Human extends Creature {
        private String firstName;

	public Human(String firstName, String greeting) {
		super(greeting);
		this.firstName = firstName;
	}

	// Need to pull up this method to the super class
   public String getGreeting() {
		return greeting;
   }
	public void speak() {

		System.out.println("Human " + firstName + " says: " + getGreeting());
	}

}

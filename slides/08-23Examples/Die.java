/** A class that represents a simple die that can be rolled. 
 *  The example is from the book: Java Software Solutions, by Lewis & Loftus.
 */
public class Die {
	private final int NUM_SIDES = 6; 
	private int faceValue; // current value of the face

	/** Constructor: sets the initial value of the face to 1 */
	public Die() {
		this.faceValue = 1;
	}	

	/** Constructor: sets the initial value of the face to whatever is passed as a parameter */
	public Die(int faceValue) {
		this.faceValue = faceValue;
	}

	/** A getter for class Die */
	public int getFaceValue() {
		return faceValue;
	}

    /** A method that rolls the die: generates a random int from 1 to NUM_SIDES */
	public void roll() {
		this.faceValue = (int)(NUM_SIDES*Math.random())  + 1;
	}

	/** Returns a string representation of the Die */
	public String toString() {
		return "Face: " + faceValue;
	}

}
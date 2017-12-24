//package polymorphism;
//
//import java.util.*;
//
///**
// * The OuterClass that demonstrates overriding and polymorphism. Requires classes Creature, Human and
// * Alien.
// */
//
//public class CreatureAlienExample {
//	public static void main(String[] args) {
//
//		ArrayList<Creature> creatures = new ArrayList<>();
//		// 1) Add 3 aliens and 2 humans to the creatures ArrayList.
//		creatures.add(new Alien("vpavpa", "Mars"));
//		creatures.add(new Human("Leon", "Hello"));
//		creatures.add(new Alien("hohohohoho", "Mars"));
//		creatures.add(new Alien("grrrgrrr", "Mars"));
//		creatures.add(new Human("Maria", "Hola!"));
//
//
//		// 2) Iterate over the ArrayList, call speak() method for
//		// each creature. Call fight() for each Alien
//		for (Creature cr: creatures) {
//			cr.speak(); // behaves polymorphically -
//
//			// To determine, if it's an Alien, use instanceof keyword
//			if (cr instanceof Alien)
//				((Alien)cr).fight(); // need to downcast, since fight() is not in Creature class
//
//		}
//
//		Creature c1 = new MarsAlien("HI");
//		MarsAlien fromMars = (MarsAlien)c1;
//		fromMars.speak();
//
//	}
//
//}

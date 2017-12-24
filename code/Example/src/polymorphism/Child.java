package polymorphism;
/**
 * OuterClass shows how static methods can not be overriden, just hidden.
 * Modified from :
 * http://stackoverflow.com/questions/2223386/why-doesnt-java-allow-overriding-
 * of-static-methods
 */

public class Child extends Base {
       public static void staticPrintValue() {

       		System.out.println("  Called static Child method.");
		}

       public void nonStatPrintValue() {

       		System.out.println("  Called non-static Child method.");
		}

       public void nonStatCallingStatMethodOverridenInChild(){
			System.out.print("  Child:  Non-static calls own static:");
			staticPrintValue();
			//super.staticPrintValue(); // will call Base's method
	}
}

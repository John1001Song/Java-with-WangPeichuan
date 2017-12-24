package polymorphism;

/**
 * OuterClass shows how static methods can not be overriden, just hidden
 * Modified from :
 * http://stackoverflow.com/questions/2223386/why-doesnt-java-allow-overriding-
 * of-static-methods
 */
public class Base {
	public static void staticPrintValue() {

			System.out.println("  Called static Base method.");
	}

	public void nonStatPrintValue() {

			System.out.println("  Called non-static Base method.");
	}

	public void nonStatCallingStatMethod() {
		System.out.print("  Non-static parent method calls static: ");
		staticPrintValue();
	}
	
	void nonStatCallingStatMethodOverridenInChild() {
		System.out.print("  Base:  Non-static parent method overriden in child calls static: ");
		staticPrintValue();
	}
	
}

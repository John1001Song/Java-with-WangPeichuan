package polymorphism;
/**
 * OuterClass shows how static methods can not be overriden, just hidden.
 * Uses classes Base and Child
 * Modified from :
 * http://stackoverflow.com/questions/2223386/why-doesnt-java-allow-overriding-
 * of-static-methods
 */

public class StaticExample {
	public static void main(String[] args) {

		System.out.println("Object: static type Base; runtime type Base:");
		Base base1 = new Base();
		base1.nonStatPrintValue();
		base1.staticPrintValue();
		base1.nonStatCallingStatMethod();
		base1.nonStatCallingStatMethodOverridenInChild();
		
		System.out.println(System.lineSeparator() + "--------------------------------");
		System.out.println("Object: static type Base; runtime type Child:");
		Base baseChild = new Child();
		baseChild.nonStatPrintValue();
		baseChild.staticPrintValue();
		baseChild.nonStatCallingStatMethod(); // this method does not exist in Child
		baseChild.nonStatCallingStatMethodOverridenInChild();
		// When calling a static method from within an object method of a class, 
		// the static method chosen is the one accessible from the class itself 
		
		System.out.println(System.lineSeparator() + "--------------------------------");
		System.out.println("Object: static type Child; runtime type Child:");
		Child child = new Child();
		child.nonStatPrintValue();
		child.staticPrintValue();
		child.nonStatCallingStatMethod(); // this method does not exist in Child
		child.nonStatCallingStatMethodOverridenInChild();
	}
}

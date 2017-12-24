package nestedClasses;

/** Example of an Inner Class. Demonstrates shadowing. */
public class MyOuter {
    private int x = 2;

    private class MyInner {
        int x = 6;
        private void printX() {
            int x = 8;
            System.out.println( x ); // 8  referring to the local variable x
            System.out.println( this.x ); //6  referring to the instance variable of MyInner
            System.out.println( MyOuter.this.x ); //2  referring to the instance variable of MyOuter
        }
    }

    public void printX() {
        System.out.println("Outer method printX: " + x);
    }

    public static void main(String[] args) {
        MyOuter outer = new MyOuter();
        outer.printX();
        MyOuter.MyInner inner  = outer.new MyInner();
        // MyInner inner  = outer.new MyInner(); // since this code is inside MyOuter, this would also work
        inner.printX();
    }
}


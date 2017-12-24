package exceptions;

/** Demonstrates what happens when an Arithmetic exception occurs (first in the method where it is not caught,
 * then in the method that has try/catch
 */
public class ArithmeticExceptionDemo {

    public static double divide(int a, int b) {
        return a / b;
    }

    public static double divideHandleException(int a, int b) {
        try {
            return a / b;
        } catch (ArithmeticException e) {
            System.out.println("Can't divide by 0.");
            return Double.MAX_VALUE;
        } finally {
            System.out.println("Executed the finally block.");
        }
    }

    public static void main(String[] args) {

        //divide(5, 0); // the program will crash
        divideHandleException(5, 0); // uncomment this one instead to see that the program will not crash

    }
}

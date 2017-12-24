package inheritance;

/** The class that demonstrates how to create a custom exception. InsufficientFundsException is
 * thrown by the withdraw method in the BankAccount class.
 * The example is modified from the following example:
 * https://www.ibm.com/developerworks/community/blogs/738b7897-cd38-4f24-9f05-48dd69116837/entry/declare_your_own_java_exceptions */
public class InsufficientFundsException extends Exception {

    private double amount = 0;
    public InsufficientFundsException(double amount, String str) {
        super(str);
        this.amount = amount;
    }
    public double getAmount() {
        return amount;
    }

    public String toString() {
        String s = super.toString();
        return s + " The amount is " + amount;
    }
}

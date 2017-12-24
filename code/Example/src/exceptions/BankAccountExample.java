package exceptions;

/** A class tha demonstrates the use of custom exceptions. */
public class BankAccountExample {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(300.0, "Paresh");
        try {
            // since InsufficientFundsException is a checked exception (this class extends Extension), we need to handle it
            account.withdraw(10000);
        }
        catch (InsufficientFundsException e) {
            System.out.println(e + ", your balance is " + account.getBalance());
        }
    }
}

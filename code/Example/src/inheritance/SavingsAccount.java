package inheritance;

/** Demonstrates inheritance. Class SavingsAccount extends class BankAccount */
public class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(double balance, String owner, double interestRate) {
        super(balance, owner); // calling parent's constructor
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        setBalance(getBalance() + interestRate * getBalance()); // note that we cannot access balance directly
    }

    @Override
    public String toString() {
        return super.toString() + " Interest: " + interestRate;
        // super.toString() calls the toString() method of the parent class
    }
}

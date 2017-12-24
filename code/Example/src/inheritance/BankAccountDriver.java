package inheritance;

public class BankAccountDriver {
    public static void main(String[] args) {
        SavingsAccount ac = new SavingsAccount(200.0, "Jones", 0.5);
        ac.deposit(400);
        try {
            ac.withdraw(10);
        }
        catch (InsufficientFundsException e) {
            System.out.println(e);
        }


    }
}

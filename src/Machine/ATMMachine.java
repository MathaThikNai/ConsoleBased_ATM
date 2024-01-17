package Machine;

public class ATMMachine {
    private double balance;

    public ATMMachine(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }
}

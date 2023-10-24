package User;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final int accountNumber;
    private int PIN;
    private final double accountBalance;
    private final List<String> transactionHistory;
    private boolean loggedIn = false;

    public User(int accountNumber, int PIN, double accountBalance) {
        this.accountNumber = accountNumber;
        this.PIN = PIN;
        this.accountBalance = accountBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUsername() {
        return "User " + accountNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getPIN() {
        return PIN;
    }

    public void changePin(int currentPin, int newPin){
        if(currentPin == PIN){
            PIN = newPin;
        }

        System.out.println("Pin changed successfully!");
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean state) {
        loggedIn = state;
    }

}

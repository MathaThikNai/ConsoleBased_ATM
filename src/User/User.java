package User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    private final int accountNumber;
    private int PIN;
    public double accountBalance;
    public double fixedDepositBalance;
    public double fdWithInterest;
    private final List<String> transactionHistory;
    private boolean loggedIn = false;

    public User(int accountNumber, int PIN) {
        this.accountNumber = accountNumber;
        this.PIN = PIN;
        this.accountBalance = 1000;
        this.transactionHistory = new ArrayList<>();
    }

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

    public void applyForLoan() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter amount for Loan: ");
        double amount = scanner.nextDouble();
        System.out.println("Enter time for Loan (Months): ");
        double time = scanner.nextDouble();

        System.out.println("Loan amount: " + amount);
        System.out.println("Loan time: " + time);

        System.out.println("Your application is under review. Please wait for a response.");
        System.out.println("Thank you for choosing us!");
        System.out.println("-----------------------------------");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("loan_applications.txt", true))) {
            writer.write("User Account Number: " + getAccountNumber());
            writer.newLine();
            writer.write("Loan Amount: " + amount);
            writer.newLine();
            writer.write("Loan Time (Months): " + time);
            writer.newLine();
            writer.write("------------------------------");
            writer.newLine();
            System.out.println("Loan details written to file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing loan details to file: " + e.getMessage());
        }
    }

    public void setAccountBalance(double v) {
        accountBalance = v;
    }

    public int getPin() {
        return PIN;
    }

}

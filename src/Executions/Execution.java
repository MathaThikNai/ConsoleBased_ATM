package Executions;

import Account.Account;
import Account.AccountInformationService;
import Account.UserAccountManager;
import Exchange.ExchangeClass;
import User.User;

import java.util.Scanner;

public class Execution {

    public void showMenu() {
        System.out.println("Welcome to the ATM!");
        System.out.println("Please select an option:");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("Input Option:");
    }

    public void showUserOperations() {
        System.out.println("What would you like to do?");
        System.out.println("1. View account information");
        System.out.println("2. Check balance");
        System.out.println("3. View transaction history");
        System.out.println("4. Savings Account");
        System.out.println("5. Fixed Deposit Account");
        System.out.println("6. Exchange currency");
        System.out.println("7. Apply for Loan");
        System.out.println("8. Change PIN");
        System.out.println("9. Log out");
        System.out.println("Input Option: ");
    }

    public void showExchangeOptions() {
        System.out.println("Select an option: ");
        System.out.println("1. BDT to USD");
        System.out.println("2. BDT to EUR");
        System.out.println("Input Option: ");
    }


    public void showSavingCurrentOptions(){
        System.out.println("Select an option: ");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("Input Option: ");
    }

    public void fixedDepositOptions() {
        System.out.println("Select an option: ");
        System.out.println("1. Fixed Deposit");
        System.out.println("Input Option: ");
    }

    public User executeLogin(Scanner scanner, UserAccountManager userAccountManager) {
        System.out.println("Please enter your account number: ");
        int accountNumber = scanner.nextInt();
        System.out.println("Please enter your PIN: ");
        int PIN = scanner.nextInt();

        User loggedInUser = userAccountManager.login(accountNumber, PIN);

        if (loggedInUser != null) {
            loggedInUser.setLoggedIn(true);
            System.out.println("Login successful! Welcome, " + loggedInUser.getUsername() + "!");
            return loggedInUser;
        } else {
            System.out.println("Login failed!");
            return null;
        }
    }

    public User executeRegister(Scanner scanner, UserAccountManager userAccountManager) {
        System.out.println("Please enter your account number: ");
        int accountNumber = scanner.nextInt();
        System.out.println("Please enter your PIN: ");
        int PIN = scanner.nextInt();
        System.out.println("Please enter your initial balance: ");
        double initialBalance = scanner.nextDouble();

        User newUser = userAccountManager.registerUser(accountNumber, PIN, initialBalance);

        if (newUser != null) {
            newUser.setLoggedIn(true);
            System.out.println("Registration successful! Welcome, " + newUser.getUsername() + "!");
            return newUser;
        } else {
            System.out.println("Registration failed!");
            return null;
        }
    }

    public void handleUserOperations(User loggedInUser, Scanner scanner, UserAccountManager userAccountManager) {

        while (loggedInUser.isLoggedIn()) {
            showUserOperations();
            int input = scanner.nextInt();

            if (input == 1) {
                Account.readAccountInfoFromFile("account.txt");
            } else if (input == 2) {
                System.out.println("Current balance : " + AccountInformationService.getAccountBalance(loggedInUser));
            } else if (input == 3) {
                // Withdraw
            } else if (input == 4) {
                this.showSavingCurrentOptions();
                int option = scanner.nextInt();
            } else if (input == 5) {
                this.fixedDepositOptions();
                int option = scanner.nextInt();
            } else if (input == 6) {
                this.showExchangeOptions();
                int option = scanner.nextInt();
                if (option == 1) {
                    System.out.println("Enter amount in BDT: ");
                    double amount = scanner.nextDouble();
                    System.out.println("Amount in USD: " + ExchangeClass.convertBDTtoUSD(amount));
                    System.out.println("-----------------------------------");
                } else if (option == 2) {
                    System.out.println("Enter amount in BDT: ");
                    double amount = scanner.nextDouble();
                    System.out.println("Amount in EUR: " + ExchangeClass.convertBDTtoEUR(amount));
                    System.out.println("-----------------------------------");
                } else {
                    System.out.println("Invalid input");
                }
            } else if (input == 7) {
                System.out.println("Enter amount for Loan: ");
                double amount = scanner.nextDouble();
                System.out.println("Enter time for Loan (Months): ");
                double time = scanner.nextDouble();

                System.out.println("Loan amount: " + amount);
                System.out.println("Loan time: " + time);

                System.out.println("Your application is under review. Please wait for a response.");
                System.out.println("Thank you for choosing us!");
                System.out.println("-----------------------------------");

            } else if (input == 8) {
                System.out.println("Enter current PIN: ");
                int currentPin = scanner.nextInt();
                System.out.println("Enter new PIN: ");
                int newPin = scanner.nextInt();
                userAccountManager.updatePin(loggedInUser, currentPin, newPin);
            } else if (input == 9) {
                userAccountManager.logout(loggedInUser);
                System.out.println("Logging out...");
            }
            else {
                System.out.println("Invalid input");
            }
        }
    }
}

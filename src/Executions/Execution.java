package Executions;

import Account.Account;
import Account.AccountInformationService;
import Account.UserAccountManager;
import User.User;

import java.util.Map;
import java.util.Scanner;

import static Account.Account.readTransactionsFromFile;

public class Execution {

    public void showMenu() {
        System.out.println("Welcome to the ATM!");
        System.out.println("Please select an option:");
        System.out.println("1. Login");
//        System.out.println("2. Register");
        System.out.println("2. Exit");
        System.out.println("Input Option:");
    }

    public void showUserOperations() {
        System.out.println("What would you like to do?");
        System.out.println("1. View account information");
        System.out.println("2. Check balance");
        System.out.println("3. View transaction history");
        System.out.println("4. Savings Account");
        System.out.println("5. Fixed Deposit Account");
        System.out.println("6. Apply for Loan");
        System.out.println("7. Change PIN");
        System.out.println("8. Log out");
        System.out.println("Input Option: ");
    }

    public void showSavingCurrentOptions(){
        System.out.println("Select an option: ");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.println("Input Option: ");
    }

    public User executeLogin(Scanner scanner, UserAccountManager userAccountManager) {
        System.out.println("Please enter your account number: ");
        int accountNumber = scanner.nextInt();
        System.out.println("Please enter PIN (4-Digit): ");
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
        System.out.println("Please enter PIN (4-Digit): ");
        int PIN = scanner.nextInt();
        System.out.println("Please enter your initial balance: ");
        double initialBalance = scanner.nextDouble();

        scanner.nextLine();

        System.out.println("Please enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Please enter your date of birth (YYYY-MM-DD): ");
        String DOB = scanner.nextLine();

        User newUser = userAccountManager.registerUser(accountNumber, PIN, initialBalance);
        Account account = new Account(accountNumber, name, DOB);
        userAccountManager.saveAccountInfoToFile("accounts.txt", account, newUser.getAccountBalance());

        if (newUser != null) {
            newUser.setLoggedIn(true);
            System.out.println("Registration successful! Welcome, " + newUser.getUsername() + "!");
            return newUser;
        } else {
            System.out.println("Registration failed! Please check your input.");
            return null;
        }
    }


    public void handleUserOperations(User loggedInUser, Scanner scanner, UserAccountManager userAccountManager) {

        while (loggedInUser.isLoggedIn()) {
            showUserOperations();
            int input = scanner.nextInt();

            if (input == 1) {
                int accountNumber = loggedInUser.getAccountNumber();
                Account cAccount = userAccountManager.readAccountFromFile("accounts.txt", accountNumber);
                if (cAccount != null) {
                    System.out.println("Account Number: " + cAccount.getAccountNumber());
                    System.out.println("Name: " + cAccount.getName());
                    System.out.println("Date of Birth: " + cAccount.getDOB());
                } else {
                    System.out.println("Account not found or error reading from file.");
                }

            } else if (input == 2) {
                System.out.println("Current balance : " + AccountInformationService.getAccountBalance(loggedInUser));
                System.out.println("-----------------------------------");
                System.out.println("Fixed Deposit balance : " + loggedInUser.fixedDepositBalance);
                System.out.println("-----------------------------------");
                System.out.println("Fixed Deposit with Interest : " + loggedInUser.fdWithInterest);
                System.out.println("-----------------------------------");
            } else if (input == 3) {
                Map<Integer, StringBuilder> accountTransactions = readTransactionsFromFile("transactions.txt");

                int targetAccountNumber = loggedInUser.getAccountNumber();
                if (accountTransactions.containsKey(targetAccountNumber)) {
                    System.out.println("Transactions for Account Number " + targetAccountNumber + ":");
                    System.out.println(accountTransactions.get(targetAccountNumber));
                } else {
                    System.out.println("No transactions found for Account Number " + targetAccountNumber);
                }
            } else if (input == 4) {
                showSavingCurrentOptions();
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        Account.deposit(loggedInUser, depositAmount, "Deposit");
                        break;
                    case 2:
                        System.out.println("Enter withdrawal amount: ");
                        double withdrawalAmount = scanner.nextDouble();
                        Account.withdraw(loggedInUser, withdrawalAmount, "Withdrawal");
                        break;
                    case 3:
                        System.out.println("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();
                        System.out.println("Enter target account number: ");
                        int targetAccountNumber = scanner.nextInt();
                        User targetAccount = Account.getUserByAccountNumber(targetAccountNumber);

                        Account.transfer(loggedInUser, targetAccount, transferAmount, "Transfer");
                        break;
                    default:
                        System.out.println("Invalid option. Please enter a valid choice.");
                }
            } else if (input == 5) {
                System.out.print("Enter deposit amount: ");
                double depositAmount = scanner.nextDouble();

                System.out.print("Enter deposit term in months: ");
                int depositTerm = scanner.nextInt();

                System.out.println("Fixed interest rate 2%");

                System.out.print("Enter description: ");
                scanner.nextLine();
                String description = scanner.nextLine();

                double currentBalance = loggedInUser.getAccountBalance();

                Account.fixedDeposit(depositAmount, depositTerm, currentBalance);
                loggedInUser.fixedDepositBalance += depositAmount;
                double fdWithInterest = Account.calculateMaturityAmount(depositAmount,depositTerm,2);
                loggedInUser.fdWithInterest += fdWithInterest;
            }
            else if (input == 6) {
                loggedInUser.applyForLoan();
            } else if (input == 7) {
                System.out.println("Enter current PIN: ");
                int currentPin = scanner.nextInt();
                System.out.println("Enter new PIN: ");
                int newPin = scanner.nextInt();
                userAccountManager.updatePin(loggedInUser, currentPin, newPin);
            } else if (input == 8) {
                userAccountManager.logout(loggedInUser);
                System.out.println("Logging out...");
            }
            else {
                System.out.println("Invalid input");
            }
        }
    }
}

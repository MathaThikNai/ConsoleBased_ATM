package Account;

import Transaction.Transaction;
import User.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {
    private int accountNumber;
    private final String name;
    private final String DOB;
    private static final List<Transaction> transactions = new ArrayList<Transaction>();

    public Account(int accountNumber, String name, String DOB) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.DOB = DOB;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public String getDOB() {
        return DOB;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public static boolean deposit(User user, double amount, String description) {
        if (amount > 0) {
            user.accountBalance += amount;
            updateBalanceInFile(user.getAccountNumber(), user.getAccountBalance());
            transactions.add(new Transaction("Deposit", amount, description));
            writeTransactionToFile(user.getAccountNumber(), new Transaction("Deposit", amount, description));
            System.out.println("Deposit Successful");
            return true;
        }
        return false;
    }

    public static boolean withdraw(User user, double amount, String description) {
        if (amount > 0 && amount <= user.getAccountBalance()) {
            user.accountBalance -= amount;
            updateBalanceInFile(user.getAccountNumber(), user.getAccountBalance());
            transactions.add(new Transaction("Withdrawal", amount, description));
            writeTransactionToFile(user.getAccountNumber(), new Transaction("Withdrawal", amount, description));
            System.out.println("Withdraw Successful");
            return true;
        }
        System.out.println("Insufficient Balance");
        return false;
    }

    public static boolean transfer(User sender, User receiver, double amount, String description) {
        if (amount > 0 && amount <= sender.getAccountBalance()) {
            sender.accountBalance -= amount;
            receiver.accountBalance += amount;

            updateBalanceInFile(sender.getAccountNumber(), sender.getAccountBalance());
            updateBalanceInFile(receiver.getAccountNumber(), receiver.getAccountBalance());

            transactions.add(new Transaction("Transfer to " + receiver.getUsername(), amount, description));
            writeTransactionToFile(sender.getAccountNumber(), new Transaction("Transfer to " + receiver.getUsername(), amount, description));

            transactions.add(new Transaction("Transfer from " + sender.getUsername(), amount, description));
            writeTransactionToFile(receiver.getAccountNumber(), new Transaction("Received from " + sender.getUsername(), amount, description));

            System.out.println("Transfer Successful");
            return true;
        }
        System.out.println("Insufficient Balance");
        return false;
    }

    public static User getUserByAccountNumber(int accountNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int currentAccountNumber = Integer.parseInt(parts[0]);
                if (currentAccountNumber == accountNumber) {
                    int pin = Integer.parseInt(parts[1]);
                    double balance = Double.parseDouble(parts[2]);

                    return new User(accountNumber, pin, balance);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error retrieving user by account number: " + e.getMessage());
        }
        return null;
    }



    private static void updateBalanceInFile(int accountNumber, double newBalance) {
        try {
            File file = new File("users.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int currentAccountNumber = Integer.parseInt(parts[0]);
                if (currentAccountNumber == accountNumber) {
                    parts[2] = Double.toString(newBalance);
                    line = String.join(",", parts);
                }
                lines.add(line);
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void writeTransactionToFile(int accountNumber, Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            writer.write(accountNumber + "," + transaction.getTransactionType() + "," + transaction.getAmount() + "," + transaction.getDescription());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing transaction to file: " + e.getMessage());
        }
    }

    public static Map<Integer, StringBuilder> readTransactionsFromFile(String filePath) {
        Map<Integer, StringBuilder> accountTransactions = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    int accountNumber = Integer.parseInt(data[0]);
                    String type = data[1];
                    double amount = Double.parseDouble(data[2]);
                    String description = data[3];

                    StringBuilder transactionDetails = new StringBuilder();
                    transactionDetails.append(type).append(": ").append(amount).append(" - ").append(description);
                    transactionDetails.append("\n");

                    accountTransactions.computeIfAbsent(accountNumber, k -> new StringBuilder()).append(transactionDetails);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading transactions from file: " + e.getMessage());
        }

        return accountTransactions;
    }

    public static void viewTransactionHistory() {
        if (!transactions.isEmpty()) {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        } else {
            System.out.println("No transactions found.");
        }
    }

    public static boolean fixedDeposit(double amount, int months, double balance) {
        if (amount > 0 && months > 0) {
            double maturityAmount = calculateMaturityAmount(amount, months, 2);

            if (maturityAmount > 0) {
                System.out.println("Fixed deposit successful! Maturity Amount: " + maturityAmount);
                return true;
            }
        }
        System.out.println("Failed to create fixed deposit. Please check your inputs.");
        return false;
    }

    public static double calculateMaturityAmount(double principal, int months, double interestRate) {
        double simpleInterest = (principal * interestRate * months) / 1200;
        return principal + simpleInterest;
    }

    public static void readAccountInfoFromFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            reader.close();

            String[] data = line.split(",");
            if (data.length == 10) {
                int accountNumber = Integer.parseInt(data[0]);
                double initialBalance = Double.parseDouble(data[1]);
                String transactionFile = data[2];
                String name = data[3];
                String address = data[4];
                String parentsName = data[5];
                int age = Integer.parseInt(data[6]);
                String gender = data[7];
                String nationality = data[8];
                String DOB = data[9];

                System.out.println("Account Number: " + accountNumber);
                System.out.println("Initial Balance: " + initialBalance);
                System.out.println("Name: " + name);
                System.out.println("Address: " + address);
                System.out.println("Parent's Name: " + parentsName);
                System.out.println("Age: " + age);
                System.out.println("Gender: " + gender);
                System.out.println("Nationality: " + nationality);
                System.out.println("Date of Birth: " + DOB);
                System.out.println("-----------------------------------");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

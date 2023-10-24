package Account;

import Transaction.Transaction;
import Transaction.TransactionFileHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private final int accountNumber;
    private final String name;
    private final String address;
    private final String parentsName;
    private final int age;
    private final String gender;
    private final String nationality;
    private final String DOB;
    private double balance;
    private List<Transaction> transactions;
    private final TransactionFileHandler fileHandler;


    public Account(int accountNumber, double initialBalance, String transactionFile, String name, String address, String parentsName, int age, String gender, String nationality, String DOB) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.fileHandler = new TransactionFileHandler(transactionFile);
        transactions = fileHandler.readTransactionsFromFile();
        this.name = name;
        this.address = address;
        this.parentsName = parentsName;
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
        this.DOB = DOB;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getInitialBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getParentsName() {
        return parentsName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getNationality() {
        return nationality;
    }

    public String getDOB() {
        return DOB;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public boolean deposit(double amount, String description) {
        if (amount > 0) {
            balance += amount;
            transactions.add(new Transaction("Deposit", amount, description));
            fileHandler.writeTransactionsToFile(transactions);
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount, String description) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction("Withdrawal", amount, description));
            fileHandler.writeTransactionsToFile(transactions);
            return true;
        }
        return false;
    }

    public boolean transfer(Account targetAccount, double amount) {
        if (targetAccount != null && amount > 0 && amount <= balance) {
            balance -= amount;
            targetAccount.deposit(amount, "Transfer from Account " + accountNumber);
            transactions.add(new Transaction("Transfer", amount, "To Account " + targetAccount.getAccountNumber()));
            fileHandler.writeTransactionsToFile(transactions);
            return true;
        }
        return false;
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
            e.printStackTrace();
        }
    }
}

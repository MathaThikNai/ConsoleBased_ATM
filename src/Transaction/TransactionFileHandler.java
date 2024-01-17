package Transaction;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionFileHandler {
    private final String fileName;

    public TransactionFileHandler(String fileName) {
        this.fileName = fileName;
    }

    public List<Transaction> readTransactionsFromFile() {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String transactionType = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    String description = parts[2];
                    String time = parts[3];
                    Transaction transaction = new Transaction(transactionType, amount, description);
                    transaction.setTime(time);
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading transactions from file: " + e.getMessage());
        }
        return transactions;
    }

    public void writeTransactionsToFile(List<Transaction> transactions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Transaction transaction : transactions) {
                String transactionRecord = transaction.getTransactionType() + ","
                        + transaction.getAmount() + ","
                        + transaction.getDescription() + ","
                        + LocalTime.now();
                writer.write(transactionRecord);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing transactions to file: " + e.getMessage());
        }
    }
}

package Transaction;

import java.util.List;

public class TransactionManager {
    private final List<Transaction> transactions;
    private final TransactionFileHandler fileHandler;

    public TransactionManager(String transactionFile) {
        this.fileHandler = new TransactionFileHandler(transactionFile);
        transactions = fileHandler.readTransactionsFromFile();
    }

    public void recordDeposit(double amount, String description) {
        Transaction deposit = new Transaction("Deposit", amount, description);
        transactions.add(deposit);
        fileHandler.writeTransactionsToFile(transactions);
    }

    public void recordWithdrawal(double amount, String description) {
        Transaction withdrawal = new Transaction("Withdrawal", amount, description);
        transactions.add(withdrawal);
        fileHandler.writeTransactionsToFile(transactions);
    }

    public void recordTransfer(double amount, String sourceAccount, String targetAccount) {
        String description = "From Account " + sourceAccount + " to Account " + targetAccount;
        Transaction transfer = new Transaction("Transfer", amount, description);
        transactions.add(transfer);
        fileHandler.writeTransactionsToFile(transactions);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}

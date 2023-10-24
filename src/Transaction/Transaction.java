package Transaction;

import java.util.Date;

public class Transaction {
    private final String transactionType;
    private final double amount;
    private final String description;
    private final Date date;

    public Transaction(String transactionType, double amount, String description) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.date = new Date();
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }
}

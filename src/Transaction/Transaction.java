package Transaction;

public class Transaction {
    private final String transactionType;
    private final double amount;
    private final String description;
    private String time;

    public Transaction(String transactionType, double amount, String description) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
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

    public String getTime() {
        return time;
    }
    public void setTime(String time) { time = time;}
}

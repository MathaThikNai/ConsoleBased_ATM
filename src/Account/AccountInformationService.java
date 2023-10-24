package Account;

import User.User;

import java.util.List;

public class AccountInformationService {
    public static double getAccountBalance(User user) {
        return user.getAccountBalance();
    }

    public static int getAccountNumber(User user) {
        return user.getAccountNumber();
    }

    public static List<String> getTransactionHistory(User user) {
        return user.getTransactionHistory();
    }

}

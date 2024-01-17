package Account;

import User.User;

import java.io.*;
import java.util.List;

public class AccountInformationService {
    public static double getAccountBalance(User user) {
        try {
            File file = new File("users.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int currentAccountNumber = Integer.parseInt(parts[0]);

                if (currentAccountNumber == user.getAccountNumber()) {
                    double currentBalance = Double.parseDouble(parts[2]);
                    user.setAccountBalance(currentBalance);
                    return currentBalance;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }


    public static int getAccountNumber(User user) {
        return user.getAccountNumber();
    }

    public static List<String> getTransactionHistory(User user) {
        return user.getTransactionHistory();
    }

}

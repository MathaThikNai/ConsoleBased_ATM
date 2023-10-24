package Authentication;

import User.User;

import java.util.List;

public class AuthenticationService {
    public static User authenticateUser(List<User> users, int accountNumber, int PIN) {
        for (User user : users) {
            if (user.getAccountNumber() == accountNumber && user.getPIN() == PIN) {
                return user;
            }
        }
        return null;
    }
}

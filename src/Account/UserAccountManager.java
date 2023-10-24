package Account;

import Authentication.AuthenticationService;
import User.User;
import User.UserFileHandler;

import java.util.List;

public class UserAccountManager {
    private final UserFileHandler fileHandler;
    private final List<User> users;

    public UserAccountManager(String fileName) {
        fileHandler = new UserFileHandler(fileName);
        if (fileHandler.fileExists() && fileHandler.fileHasContent()) {
            users = fileHandler.readUsersFromFile();
        } else {
            users = fileHandler.createFile();
        }
    }

    public User registerUser(int accountNumber, int PIN, double initialBalance) {
        User newUser = new User(accountNumber, PIN, initialBalance);
        users.add(newUser);
        fileHandler.writeUsersToFile(users);
        return newUser;
    }

    public User login(int accountNumber, int PIN) {
        return AuthenticationService.authenticateUser(users, accountNumber, PIN);
    }

    public void logout(User user) {
        user.setLoggedIn(false);
    }

    public void updatePinToFile(User loggedInUser, int pin) {
        fileHandler.updatePinToFile(loggedInUser, pin);
    }

    public void updatePin(User loggedInUser, int currentPin, int newPin) {
        if (currentPin == loggedInUser.getPIN()) {
            loggedInUser.changePin(currentPin, newPin);
            updatePinToFile(loggedInUser, newPin);
        } else {
            System.out.println("Incorrect PIN");
        }
    }
}

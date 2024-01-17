package Account;

import Authentication.AuthenticationService;
import User.User;
import User.UserFileHandler;

import java.io.*;
import java.util.List;
import java.util.Scanner;

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

    public void saveAccountInfoToFile(String filePath, Account account, double balance) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String accountInfo = String.format("%d,%.2f,%s,%s",
                    account.getAccountNumber(), balance, account.getName(), account.getDOB());
            writer.write(accountInfo);
            writer.newLine();
            System.out.println("Account information appended to file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error appending account information to file: " + e.getMessage());
        }
    }


    public Account readAccountFromFile(String filePath, int accountNumber) {
        try (Scanner scanner = new Scanner(new FileReader(filePath))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length == 4) {
                    int currentAccountNumber = Integer.parseInt(data[0]);
                    if (currentAccountNumber == accountNumber) {
                        double balance = Double.parseDouble(data[1]);
                        String name = data[2];
                        String DOB = data[3];

                        return new Account(accountNumber, name, DOB);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading account information from file: " + e.getMessage());
        }
        System.out.println("Account not found or error reading from file.");
        return null;
    }


    public void activateUsers() {
        try {
            File existingUsersFile = new File("existingUsers.txt");
            File activatedUsersFile = new File("activatedUsers.txt");

            BufferedReader reader = new BufferedReader(new FileReader(existingUsersFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(activatedUsersFile, true));

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Account Number: ");
            int inputAccountNumber = scanner.nextInt();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int currentAccountNumber = Integer.parseInt(parts[0]);

                if (currentAccountNumber == inputAccountNumber) {
                    System.out.print("Enter PIN: ");
                    int inputPin = scanner.nextInt();
                    int storedPin = Integer.parseInt(parts[1]);

                    if (inputPin == storedPin) {
                        writer.write(line);
                        writer.newLine();
                        System.out.println("Account activated successfully.");
                    } else {
                        System.out.println("Invalid PIN. Account activation failed.");
                    }

                    break;
                }
            }

            reader.close();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public User login() {
        try {
            File activatedUsersFile = new File("activatedUsers.txt");
            BufferedReader reader = new BufferedReader(new FileReader(activatedUsersFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int currentAccountNumber = Integer.parseInt(parts[0]);
                int storedPin = Integer.parseInt(parts[1]);

                System.out.print("Enter Account Number: ");
                Scanner scanner = new Scanner(System.in);
                int inputAccountNumber = scanner.nextInt();

                System.out.print("Enter PIN: ");
                int inputPin = scanner.nextInt();

                if (currentAccountNumber == inputAccountNumber && storedPin == inputPin) {
                    return new User(currentAccountNumber, storedPin);
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


}

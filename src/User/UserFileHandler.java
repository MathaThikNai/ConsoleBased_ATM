package User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserFileHandler {
    private final String fileName;

    public UserFileHandler(String fileName) {
        this.fileName = fileName;
    }

    public List<User> readUsersFromFile() {
        List<User> users = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int accountNumber = Integer.parseInt(parts[0]);
                    int PIN = Integer.parseInt(parts[1]);
                    double accountBalance = Double.parseDouble(parts[2]);
                    users.add(new User(accountNumber, PIN, accountBalance));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading user data: " + e.getMessage());
        }
        return users;
    }

    public void writeUsersToFile(List<User> users) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (User user : users) {
                String userString = user.getAccountNumber() + "," + user.getPIN() + "," + user.getAccountBalance();
                writer.write(userString);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing user data: " + e.getMessage());
        }
    }


    public void updatePinToFile(User user, int newPin) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            List<String> fileContent = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int accountNumber = Integer.parseInt(parts[0]);
                    int PIN = Integer.parseInt(parts[1]);
                    double accountBalance = Double.parseDouble(parts[2]);
                    if (accountNumber == user.getAccountNumber()) {
                        PIN = newPin;
                    }
                    fileContent.add(accountNumber + "," + PIN + "," + accountBalance);
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (String lineToWrite : fileContent) {
                writer.write(lineToWrite);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error updating user data: " + e.getMessage());
        }
    }

    public boolean fileExists() {
        File file = new File(fileName);
        return file.exists();
    }

    public boolean fileHasContent() {
        File file = new File(fileName);
        return file.length() > 0;
    }

    public List<User> createFile() {
        List<User> users = new ArrayList<>();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.close();
        } catch (IOException e) {
            System.err.println("Error creating user data file: " + e.getMessage());
        }
        return users;
    }
}

import Account.UserAccountManager;
import Executions.Execution;
import User.User;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Execution execution = new Execution();

        UserAccountManager userAccountManager = new UserAccountManager("users.txt");

        boolean session = true;
        User loggedInUser = null;
        Scanner scanner = new Scanner(System.in);

        while(session){

            execution.showMenu();
            int input = scanner.nextInt();

            if(input == 1){
                loggedInUser = execution.executeLogin(scanner, userAccountManager);
                //loggedInUser = userAccountManager.login();
                execution.handleUserOperations(loggedInUser, scanner, userAccountManager);
            } else if(input == 2){
                loggedInUser = execution.executeRegister(scanner, userAccountManager);
                execution.handleUserOperations(loggedInUser, scanner, userAccountManager);
                //userAccountManager.activateUsers();
            } else if(input == 3){
                System.out.println("Exiting...");
                session = false;
            } else {
                System.out.println("Invalid input");
            }
        }

    }
}
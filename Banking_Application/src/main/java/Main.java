import dao.AccountDAO;
import Model.Account;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountDAO dao = new AccountDAO();

        while (true) {
            System.out.println("\n===============================");
            System.out.println("     WELCOME TO BSI BANK      ");
            System.out.println("===============================");
            System.out.println("1. Create New Account");
            System.out.println("2. Login / Account Access");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Full Name: ");
                    scanner.nextLine(); // Clear buffer
                    String name = scanner.nextLine();
                    System.out.print("Set 4-Digit PIN: ");
                    String pin = scanner.next();
                    System.out.print("Initial Deposit Amount: ");
                    double deposit = scanner.nextDouble();


                    Account newAcc = new Account(0, name, pin, deposit);
                    dao.createAccount(newAcc);
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    int accNum = scanner.nextInt();
                    System.out.print("Enter PIN: ");
                    String userPin = scanner.next();

                    Account userAccount = dao.authenticate(accNum, userPin);

                    if (userAccount != null) {
                        System.out.println("✅ Login Successful! Welcome, " + userAccount.getHolderName() + "!");
                        userSession(scanner, dao, userAccount);
                    } else {
                        System.out.println("❌ Invalid Account Number or PIN!");
                    }
                    break;

                case 3:
                    System.out.println("Thank you for using BSI Bank. Goodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    private static void userSession(Scanner scanner, AccountDAO dao, Account userAcc) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n--- ACCOUNT DASHBOARD (" + userAcc.getHolderName() + ") ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Transfer Money");
            System.out.println("3. Logout");
            System.out.print("Choose action: ");

            int action = scanner.nextInt();
            switch (action) {
                case 1:

                    System.out.printf("💰 Current Balance: $%.2f\n", userAcc.getBalance());
                    break;

                case 2:
                    System.out.print("Enter Receiver's Account Number: ");
                    int targetAcc = scanner.nextInt();
                    System.out.print("Enter Transfer Amount: ");
                    double amount = scanner.nextDouble();

                    dao.transferMoney(userAcc, targetAcc, amount);
                    break;

                case 3:
                    System.out.println("Logged out successfully.");
                    sessionActive = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
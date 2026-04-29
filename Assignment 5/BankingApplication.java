import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BankingApplication {
    public static void main(String[] args) {
        SQLiteConnection sqliteConnection = new SQLiteConnection();
        BankingRepository repository = new BankingRepository(sqliteConnection);

        repository.initializeDatabase();
        if (!repository.hasAnyCustomers()) {
            seedSampleData(repository);
        }

        runMenu(repository);

        sqliteConnection.disconnect();
    }

    private static void runMenu(BankingRepository repository) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== Banking Menu =====");
            System.out.println("1. View Consolidated Customer Accounts");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number from 1 to 4.");
                continue;
            }

            switch (choice) {
                case 1 -> printConsolidatedInfo(repository.getConsolidatedCustomerAccounts());
                case 2 -> processTransaction(repository, scanner, true);
                case 3 -> processTransaction(repository, scanner, false);
                case 4 -> {
                    running = false;
                    System.out.println("Exiting application.");
                }
                default -> System.out.println("Invalid choice. Please enter a number from 1 to 4.");
            }
        }
        scanner.close();
    }

    private static void processTransaction(BankingRepository repository, Scanner scanner, boolean isDeposit) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine().trim();

        Account account = repository.getAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter amount: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
            return;
        }

        try {
            if (isDeposit) {
                account.deposit(amount);
            } else {
                account.withdraw(amount);
            }

            repository.updateAccountBalance(account.getAccountNumber(), account.getBalance());
            System.out.println("Transaction successful.");
            System.out.println("Updated: " + account.getAccountDetails());
        } catch (IllegalArgumentException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }

    private static void seedSampleData(BankingRepository repository) {
        Customer c1 = new Customer(1, "Aarav Sharma", "aarav@example.com");
        Customer c2 = new Customer(2, "Riya Patel", "riya@example.com");
        Customer c3 = new Customer(3, "Neha Singh", "neha@example.com");

        repository.insertCustomer(c1);
        repository.insertCustomer(c2);
        repository.insertCustomer(c3);

        SavingsAccount s1 = new SavingsAccount(1, 1, "SAV-1001", 12000, 3.5);
        SavingsAccount s2 = new SavingsAccount(2, 2, "SAV-2001", 8500, 4.0);
        LoanAccount l1 = new LoanAccount(3, 1, "LOAN-1001", 25000, 100000);
        LoanAccount l2 = new LoanAccount(4, 3, "LOAN-3001", 10000, 60000);

        s1.deposit(1000);
        s2.withdraw(500);
        l1.withdraw(5000);
        l2.deposit(2000);

        repository.insertAccount(s1);
        repository.insertAccount(s2);
        repository.insertAccount(l1);
        repository.insertAccount(l2);
    }

    private static void printConsolidatedInfo(Map<Customer, List<Account>> consolidatedInfo) {
        System.out.println("\n========== Consolidated Customer Account Information ==========");
        for (Map.Entry<Customer, List<Account>> entry : consolidatedInfo.entrySet()) {
            Customer customer = entry.getKey();
            List<Account> accounts = entry.getValue();

            System.out.println("\nCustomer ID: " + customer.getId());
            System.out.println("Name       : " + customer.getName());
            System.out.println("Email      : " + customer.getEmail());

            if (accounts.isEmpty()) {
                System.out.println("Accounts   : No accounts found.");
                continue;
            }

            System.out.println("Accounts   :");
            for (Account account : accounts) {
                System.out.println("  - " + account.getAccountDetails());
            }
        }
        System.out.println("==============================================================");
    }
}

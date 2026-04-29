import java.util.Scanner;

public class ArithmeticOperations {

    // Method to display menu
    public static void displayMenu() {
        System.out.println("\n===== Arithmetic Operations Menu =====");
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Division");
        System.out.println("5. Exit");
        System.out.print("Enter your choice (1-5): ");
    }

    // Method to get valid double input
    public static double getValidNumber(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a valid number: ");
            scanner.next(); // discard invalid input
        }
        return scanner.nextDouble();
    }

    // Arithmetic methods
    public static double add(double a, double b) {
        return a + b;
    }

    public static double subtract(double a, double b) {
        return a - b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }

    public static Double divide(double a, double b) {
        if (b == 0) {
            return null; // indicate division by zero
        }
        return a / b;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            displayMenu();

            // Validate menu choice
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid choice. Please enter a number between 1 and 5: ");
                scanner.next();
            }

            choice = scanner.nextInt();

            if (choice == 5) {
                System.out.println("Exiting program. Thank you!");
                break;
            }

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please select between 1 and 5.");
                continue;
            }

            System.out.print("Enter first number: ");
            double num1 = getValidNumber(scanner);

            System.out.print("Enter second number: ");
            double num2 = getValidNumber(scanner);

            switch (choice) {
                case 1:
                    System.out.println("Result: " + add(num1, num2));
                    break;

                case 2:
                    System.out.println("Result: " + subtract(num1, num2));
                    break;

                case 3:
                    System.out.println("Result: " + multiply(num1, num2));
                    break;

                case 4:
                    Double result = divide(num1, num2);
                    if (result == null) {
                        System.out.println("Error: Division by zero is not allowed.");
                    } else {
                        System.out.println("Result: " + result);
                    }
                    break;
            }
        }

        scanner.close();
    }
}

import java.util.Scanner;

public class VectorCalc {
    private static Vector readVector(Scanner scanner, String name) throws VectorException {
        System.out.print("Enter dimension for " + name + " (2 or 3): ");
        int dimension = scanner.nextInt();

        double[] elements = new double[dimension];
        System.out.println("Enter " + dimension + " elements for " + name + ":");
        for (int i = 0; i < dimension; i++) {
            System.out.print("Element " + (i + 1) + ": ");
            elements[i] = scanner.nextDouble();
        }

        return new Vector(elements);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Vector a = readVector(scanner, "Vector A");
            Vector b = readVector(scanner, "Vector B");

            Vector sum = a.add(b);
            Vector difference = a.subtract(b);
            double dot = a.dotProduct(b);

            System.out.print("Addition Result: ");
            sum.display();
            System.out.print("Subtraction Result: ");
            difference.display();
            System.out.println("Dot Product Result: " + dot);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Please enter valid numeric input.");
        } catch (VectorException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

import java.util.Arrays;
import java.util.Scanner;

public class Array{
    public static void main(String[] args) {
        //Array1(args);
        //Array2(args);
        //Array3(args);
        //Array4(args);
        //Array5(args);
        //ArrayEquals(args);
        //ArraySort(args);
        //ArrayFill(args);
        ArrayVowelDetection(args);
    }


    public static void Array1(String[] args) {
        int[] iArr; // Declaration of an integer array
        iArr = new int[5]; // Instantiation of the array with size 5
        iArr[0] = 10;
        iArr[1] = 20;
        iArr[2] = 30;
        iArr[3] = 40;
        iArr[4] = 50;

        for (int i = 0; i < iArr.length; i++) {
            System.out.println("Element at index " + i + ": " + iArr[i]);
        }
    }

    public static void Array2(String[] args) {
        int[] iArr = { 5, 10, 15, 20, 25 }; // Declaration and instantiation with initialization

        for (int i = 0; i < iArr.length; i++) {
            System.out.println("Element at index " + i + ": " + iArr[i]);
        }
    }
    public static void Array3(String[] args) {
        int[] iArr = new int[5]; // Declaration and instantiation

        iArr[0] = 1;
        iArr[1] = 2;
        iArr[2] = 3;
        iArr[3] = 4;
        iArr[4] = 5;

        for (int i = 0; i < iArr.length; i++) {
            System.out.println("Element at index " + i + ": " + iArr[i]);
        }

        for ( double d : iArr) { // Enhanced for loop to iterate through the array
            System.out.println("Squared value: " + (d * d));
        }
    }

    public static void Array4(String[] args) {
        char[] letters = { 'A', 'B', 'C', 'D', 'E' }; // Declaration and initialization of a char array
        for (char letter : letters) {
            System.out.println("Letter: " + letter);
        }
    }

    public static void Array5(String[] args) {
        int[] numbers = { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5 }; // Declaration and initialization of an integer array
        int[] numbersCopy = new int[numbers.length]; // Create a new array to hold the copy

        System.arraycopy(numbers, 0, numbersCopy, 0, numbers.length); // Copying the array

        for (int num : numbersCopy) {
            System.out.println("Copied number: " + num);
        }

        int[] numbersCopy2 = Arrays.copyOf(numbers, numbers.length); // Using Arrays.copyOf to copy the array

        for (int num : numbersCopy2) {
            System.out.println("Copied number using Arrays.copyOf: " + num);
        }
    }

    public static void ArrayEquals(String[] args) {
        int[] array1 = { 1, 2, 3, 4, 5 };
        int[] array2 = { 1, 2, 3, 4, 5 };
        int[] array3 = { 5, 4, 3, 2, 1 };

        boolean isEqual1 = Arrays.equals(array1, array2); // true
        boolean isEqual2 = Arrays.equals(array1, array3); // false

        System.out.println("array1 and array2 are equal: " + isEqual1);
        System.out.println("array1 and array3 are equal: " + isEqual2);
    }

    public static void ArraySort(String[] args) {
        int[] numbers = { 5, 2, 8, 1, 3 };

        System.out.println("Before sorting: " + Arrays.toString(numbers));

        Arrays.sort(numbers); // Sorting the array

        System.out.println("After sorting: " + Arrays.toString(numbers));
    }

    public static void ArrayFill(String[] args) {
        int[] numbers = new int[10];

        Arrays.fill(numbers, 7); // Filling the array with the value 7

        for (int num : numbers) {
            System.out.println("Filled number: " + num);
        }
    }

    public static boolean isVowel(char c) {
        char[] vowel = "aeiouAEIOU".toCharArray();
        for (char i: vowel) {
            if (c == i) {
                return true;
            }
        }
        return false;
    }

    public static void ArrayVowelDetection(String[] args) {
        String[] words = { "apple", "banana", "cherry", "date" };

        for (String word : words) {
            if (isVowel(word.charAt(0))){
                System.out.println("The word \"" + word + "\" starts with a vowel.");
            }
            else {
                System.out.println("The word \"" + word + "\" does not start with a vowel.");
            }
        }
    }
}
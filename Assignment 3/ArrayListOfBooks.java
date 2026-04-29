import java.util.ArrayList;
import java.time.LocalDate;

public class ArrayListOfBooks {
    
    private ArrayList<Book> books;
    
    // Constructor
    public ArrayListOfBooks() {
        books = new ArrayList<>();
    }
    
    // Method to add a book to the list
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.name);
    }

    // Method to add a book with proper exception handling
    public boolean addBookWithExceptionHandling(String name, String authorName, double price, 
                                                String publisherName, String genre, String ISBN, LocalDate dateOfPublishing) {
        try {
            Book book = new Book(name, authorName, price, publisherName, genre, ISBN, dateOfPublishing);
            addBook(book);
            return true;
        } catch (InvalidPriceException e) {
            System.out.println("Error adding book: " + e.getMessage());
            return false;
        } catch (InvalidGenreException e) {
            System.out.println("Error adding book: " + e.getMessage());
            return false;
        }
    }
    
    // Method to remove a book from the list
    public void removeBook(int index) {
        if (index >= 0 && index < books.size()) {
            books.remove(index);
            System.out.println("Book removed");
        } else {
            System.out.println("Invalid index");
        }
    }
    
    // Method to display all books
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the list");
            return;
        }
        
        System.out.println("=== All Books ===");
        for (int i = 0; i < books.size(); i++) {
            System.out.println("\nBook " + (i + 1) + ":");
            books.get(i).displayDetails();
        }
    }
    
    // Method to get total number of books
    public int getTotalBooks() {
        return books.size();
    }

    // Method to calculate average price of books in the list
    public double getAveragePrice() {
        if (books.isEmpty()) {
            System.out.println("No books in the list to calculate average price");
            return 0.0;
        }
        
        double totalPrice = 0.0;
        for (Book book : books) {
            totalPrice += book.price;
        }
        return totalPrice / books.size();
    }

    // Method to print books of a particular genre
    public void printBooksByGenre(String genre) {
        ArrayList<Book> genreBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.genre.equalsIgnoreCase(genre)) {
                genreBooks.add(book);
            }
        }
        
        if (genreBooks.isEmpty()) {
            System.out.println("No books found with genre: " + genre);
            return;
        }
        
        System.out.println("=== Books of Genre: " + genre + " ===");
        for (int i = 0; i < genreBooks.size(); i++) {
            System.out.println("\nBook " + (i + 1) + ":");
            genreBooks.get(i).displayDetails();
        }
    }
    
    // Method to get a book by index
    public Book getBook(int index) {
        if (index >= 0 && index < books.size()) {
            return books.get(index);
        }
        return null;
    }
    
    // Main method
    public static void main(String[] args) throws InvalidPriceException {
        ArrayListOfBooks bookList = new ArrayListOfBooks();
        
        // Create and add books using exception handling
        System.out.println("=== Adding Books ===");
        bookList.addBookWithExceptionHandling("The Great Gatsby", "F. Scott Fitzgerald", 10.99, "Scribner", "Fiction", "978-0743273565", LocalDate.of(1925, 4, 10));
        bookList.addBookWithExceptionHandling("1984", "George Orwell", 13.99, "Penguin", "Fiction", "978-0451524935", LocalDate.of(1949, 6, 8));
        bookList.addBookWithExceptionHandling("To Kill a Mockingbird", "Harper Lee", -18.99, "Lippincott", "Fiction", "978-0061120084", LocalDate.of(1960, 7, 11));
        bookList.addBookWithExceptionHandling("The Hobbit", "J.R.R. Tolkien", 8.99, "Allen & Unwin", "Fantasy", "978-0547928227", LocalDate.of(1937, 9, 21));
        bookList.addBookWithExceptionHandling("A Brief History of Time", "Stephen Hawking", 15.99, "Bantam", "Science", "978-0553380163", LocalDate.of(1988, 4, 1));
        
        System.out.println("\nTotal books: " + bookList.getTotalBooks());
        
        // Display all books
        bookList.displayAllBooks();
        
        // Calculate and display average price
        System.out.println("\n--- Average Price of Books ---");
        System.out.printf("Average Price: $%.2f%n", bookList.getAveragePrice());
        
        // Print books of a particular genre
        System.out.println("\n--- Books by Genre ---");
        bookList.printBooksByGenre("Fiction");
        
        System.out.println();
        bookList.printBooksByGenre("Fantasy");
        
        System.out.println();
        bookList.printBooksByGenre("Science");
        
        System.out.println();
        bookList.printBooksByGenre("Mystery");
        
        // Remove a book
        System.out.println("\n--- Removing book at index 1 ---");
        bookList.removeBook(1);
        
        System.out.println("\nTotal books after removal: " + bookList.getTotalBooks());
        System.out.printf("New Average Price: $%.2f%n", bookList.getAveragePrice());
    }
}

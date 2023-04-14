import java.util.*;
import java.sql.*;

public class LibraryManagementSystem {
    static Scanner scanner = new Scanner(System.in);
    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    
    public static void main(String[] args) {
        try {
            // Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_system", "root", "");
            
            // Display the login page
            loginPage();
        } catch (Exception ex) {
            System.out.println("An error occurred: " + ex.getMessage());
        }
    }
    
    public static void loginPage() throws SQLException {
        System.out.println("Welcome to the Library Management System");
        System.out.println("Please enter your username and password");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        // Check if the user exists in the database
        stmt = conn.createStatement();
        String sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        rs = stmt.executeQuery(sql);
        
        if (rs.next()) {
            // User is authenticated, display the main menu
            if (rs.getString("role").equals("admin")) {
                adminMainMenu();
            } else {
                userMainMenu();
            }
        } else {
            // User is not authenticated, display an error message
            System.out.println("Invalid username or password");
            loginPage();
        }
    }
    
    public static void adminMainMenu() throws SQLException {
        System.out.println("Welcome to the Admin Panel");
        System.out.println("1. Add Book");
        System.out.println("2. Remove Book");
        System.out.println("3. Update Book");
        System.out.println("4. Add Member");
        System.out.println("5. Remove Member");
        System.out.println("6. Update Member");
        System.out.println("7. Generate Reports");
        System.out.println("8. Logout");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume the newline character
        
        switch (choice) {
            case 1:
                addBookPage();
                break;
            case 2:
                removeBookPage();
                break;
            case 3:
                updateBookPage();
                break;
            case 4:
                addMemberPage();
                break;
            case 5:
                removeMemberPage();
                break;
            case 6:
                updateMemberPage();
                break;
            case 7:
                generateReportsPage();
                break;
            case 8:
                logoutPage();
                break;
            default:
                System.out.println("Invalid choice");
                adminMainMenu();
                break;
        }
    }
    
    public static void addBookPage() throws SQLException {
        System.out.println("Add Book");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Category: ");
        String category = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        
        // Add the book to the database
        stmt = conn.createStatement();
        String sql = "INSERT INTO books (title, author, category, isbn) VALUES ('" + title + "', '" + author + "', '" + category + "', '" + isbn + "')";
        stmt.executeUpdate(sql);
        
        System.out.println("Book added successfully");
        adminMainMenu();
    }
    
    public static void removeBookPage() throws
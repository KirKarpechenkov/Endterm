import java.sql.*;
import java.util.*;

public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/database";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Aa123456";
    public static void main(String[] args){
        System.out.println("Welcome to AITU FM!");
        System.out.println("Connecting to database...");
        try { // using try-catch to handle exceptions
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection successful!");
            LoginMenu.main();
        }
        catch (SQLException e) {
            e.printStackTrace(); // print the stack trace
            System.out.println("Connection error!"); // print the error message
        }
    }
}
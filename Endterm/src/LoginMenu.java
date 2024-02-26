import java.sql.*;
import java.util.*;

public class LoginMenu {
    private static final String URL = "jdbc:postgresql://localhost:5432/database";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Aa123456";
    public static void main(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void register() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            System.out.print("Are you a music creator? (yes/no): ");
            String creator = scanner.nextLine();
            int isCreator = creator.equalsIgnoreCase("yes") ? 1 : 0;

            String sql = "INSERT INTO musicusers (username, password, creator) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, isCreator);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Registration successful!");
                login();
            }
        } catch (SQLException e) {
            System.out.println("Error while registering.");
            e.printStackTrace();
        }
    }

    public static void login() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            if (username.equals("admin") && password.equals("admin")) {
                AdminMenu.main(null);
                return;
            }
            String sql = "SELECT * FROM musicusers WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Login successful!");
                username = resultSet.getString("username");
                if (resultSet.getInt("creator") == 0) {
                    MainMenu.main(new String[]{username});
                } else if (resultSet.getInt("creator") == 1) {
                    CreatorMenu.main(new String[]{username});
                } else {
                    AdminMenu.main(new String[]{username});
                }
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            System.out.println("Error while logging in.");
            e.printStackTrace();
        }
    }

    public static void logout() {
        System.out.println("You have been logged out.");
    }
}

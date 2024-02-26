import java.sql.*;
import java.util.*;

public class AdminMenu{
    private static final String URL = "jdbc:postgresql://localhost:5432/database";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Aa123456";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Show Users");
            System.out.println("2. Add User");
            System.out.println("3. Delete User");
            System.out.println("4. Search Track");
            System.out.println("5. Add Track");
            System.out.println("6. Delete Track");
            System.out.println("7. Search Album");
            System.out.println("8. Add Album");
            System.out.println("9. Delete Album");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    showUsers();
                    break;
                case 2:
                    addUser();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    MainMenu.searchTrack();
                    break;
                case 5:
                    addTrack();
                    break;
                case 6:
                    deleteTrack();
                    break;
                case 7:
                    MainMenu.searchAlbum();
                    break;
                case 8:
                    addAlbum();
                    break;
                case 9:
                    deleteAlbum();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showUsers(){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM musicusers";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            StringBuilder users = new StringBuilder();// Create a StringBuilder to store the usernames
            while (resultSet.next()) {
                users.append(resultSet.getString("username")).append(", "); // Append each username to the StringBuilder
            }
            if (users.length() > 0) {
                users.setLength(users.length() - 2); // Remove the last comma and space
            }
            System.out.println("Users: " + users.toString()); // Print the usernames in one row
        } catch (SQLException e) {
            System.out.println("Error while searching for users.");
            e.printStackTrace();
        }
    }

    private static void addUser(){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the username: ");
            String username = scanner.nextLine();
            System.out.print("Enter the password: ");
            String password = scanner.nextLine();
            System.out.println("Is User Creator? (yes/no)");
            String isCreator = scanner.nextLine();
            String sql = "INSERT INTO musicusers (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User was added successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding user.");
            e.printStackTrace();
        }
    }

    private static void deleteUser() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the username: ");
            String username = scanner.nextLine();
            String sql = "DELETE FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User was deleted successfully!");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting user.");
            e.printStackTrace();
        }
    }
    
    private static void addTrack() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the author of the music: ");
            String musicAuthor = scanner.nextLine();
            System.out.print("Enter the track of the music: ");
            String musicTrack = scanner.nextLine();
            System.out.print("Enter the album of the music: ");
            String musicAlbum = scanner.nextLine();
            String sql = "INSERT INTO music (author, track, album) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, musicAuthor);
            statement.setString(2, musicTrack);
            statement.setString(3, musicAlbum);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Music was added successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding music.");
            e.printStackTrace();
        }
    }

    public static void deleteTrack() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the name of the track: ");
            String musicTrack = scanner.nextLine();
            String sql = "DELETE FROM music WHERE track = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, musicTrack);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Music was deleted successfully!");
            } else {
                System.out.println("Music not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting music.");
            e.printStackTrace();
        }
    }

    public static void addAlbum() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the author of the album: ");
            String albumAuthor = scanner.nextLine();
            System.out.print("Enter the album: ");
            String albumName = scanner.nextLine();
            System.out.print("Enter the tracklist (In commas): ");
            String albumTracklist = scanner.nextLine();
            String[] tracks = albumTracklist.split(","); // split the tracklist by commas
            String sql = "INSERT INTO music (author, album, track) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            for (String track : tracks) { // loop over the array of tracks
                statement.setString(1, albumAuthor);
                statement.setString(2, albumName);
                statement.setString(3, track.trim()); // trim to remove any leading or trailing spaces
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Track '" + track.trim() + "' was added to the album successfully!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while adding album.");
            e.printStackTrace();
        }
    }

    public static void deleteAlbum() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the name of the album: ");
            String albumName = scanner.nextLine();
            String sql = "DELETE FROM music WHERE album = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Album was deleted successfully!");
            } else {
                System.out.println("Album not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting album.");
            e.printStackTrace();
        }
    }
}

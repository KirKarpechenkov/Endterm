import java.sql.*;
import java.util.*;

public class MainMenu {
    private static final String URL = "jdbc:postgresql://localhost:5432/database";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Aa123456";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Search Track");
            System.out.println("2. Search Album");
            System.out.println("3. Search Author");
            System.out.println("4. Show your favorites");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    searchTrack();
                    break;
                case 2:
                    searchAlbum();
                    break;
                case 3:
                    searchAuthor();
                    break;
                case 4:
                    showFavorites();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void searchTrack() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the name of the track: ");
            String trackName = scanner.nextLine();
            String sql = "SELECT * FROM music WHERE track = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, trackName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Track found:");
                System.out.println("Name: " + resultSet.getString("track"));
                System.out.println("Author: " + resultSet.getString("author"));
                System.out.println("Album: " + resultSet.getString("album"));
                System.out.println("Add this track to your favorit? (yes/no)");
                String choice = scanner.nextLine();
                if (choice.equals("yes")) {
                    String sql2 = "INSERT INTO favorites (username, track) VALUES (?, ?)";
                    PreparedStatement statement2 = connection.prepareStatement(sql2);
                    statement2.setString(1, "user");
                    statement2.setString(2, trackName);
                    statement2.executeUpdate();
                    System.out.println("Track added to favorites.");
                }
            } else {
                System.out.println("Track not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for track.");
            e.printStackTrace();
        }
    }

    public static void searchAuthor() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the name of the author: ");
            String authorName = scanner.nextLine();
            String sql = "SELECT * FROM music WHERE author = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, authorName);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Track: " + resultSet.getString("track"));
                System.out.println("Album: " + resultSet.getString("album"));
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for author.");
            e.printStackTrace();
        }
    }

    public static void searchAlbum() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the name of the album: ");
            String albumName = scanner.nextLine();

            String sql = "SELECT * FROM music WHERE album = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Album found:");
                System.out.println("Name: " + resultSet.getString("album"));
                System.out.println("Author: " + resultSet.getString("author"));
                System.out.println("Tracklist: ");
                do {
                    System.out.println(resultSet.getString("track"));
                } while (resultSet.next());
            } else {
                System.out.println("Album not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for album.");
            e.printStackTrace();
        }
    }

    public static void showFavorites(){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM favorites WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "user");
            ResultSet resultSet = statement.executeQuery();
            StringBuilder tracks = new StringBuilder();// Create a StringBuilder to store the track names
            while (resultSet.next()) {
                tracks.append(resultSet.getString("track")).append(", "); // Append each track name to the StringBuilder
            }
            if (tracks.length() > 0) {
                tracks.setLength(tracks.length() - 2); // Remove the last comma and space
            }
            System.out.println("Favorits: " + tracks.toString()); // Print the track names in one row
        } catch (SQLException e) {
            System.out.println("Error while searching for favorites.");
            e.printStackTrace();
        }
    }
}

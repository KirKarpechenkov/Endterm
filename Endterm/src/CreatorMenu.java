import java.sql.*;
import java.util.*;

public class CreatorMenu {
    private static String username;
    private static final String URL = "jdbc:postgresql://localhost:5432/database";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Aa123456";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        username = args[0]; // retrieve the username
        while (true) {
            System.out.println("Hello, " + username + "!"); // use the username
            System.out.println("1. Show Your Tracks");
            System.out.println("2. Show Your Albums");
            System.out.println("3. Add Your Track");
            System.out.println("4. Add Your Album");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    showMyTracks();
                    break;
                case 2:
                    showMyAlbums();
                    break;
                case 3:
                    addMyTrack();
                    break;
                case 4:
//                    addMyAlbum();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void showMyTracks() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM music WHERE author = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username); // use the username variable
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Track: " + resultSet.getString("track"));
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for track.");
            e.printStackTrace();
        }
    }

    public static void showMyAlbums() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM music WHERE author = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username); // use the username variable
            ResultSet resultSet = statement.executeQuery();
            Set<String> albumNames = new HashSet<>(); // Create a HashSet to store the album names
            while (resultSet.next()) {
                albumNames.add(resultSet.getString("album")); // Add each album name to the HashSet
            }
            for (String albumName : albumNames) { // Print each unique album name
                System.out.println("Albums: " + albumName);
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for author.");
            e.printStackTrace();
        }
    }

    public static void addMyTrack() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the track: ");
            String musicTrack = scanner.nextLine();
            System.out.print("Enter the album: ");
            String musicAlbum = scanner.nextLine();
            String sql = "INSERT INTO music (author, track, album) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, musicTrack);
            statement.setString(3, musicAlbum);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Track was added successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for album.");
            e.printStackTrace();
        }
    }

    public static void addMyAlbum() {
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
                System.out.println("Tracklist: " + resultSet.getString("track"));
            } else {
                System.out.println("Album not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error while searching for album.");
            e.printStackTrace();
        }
    }
}
package dataaccess;


import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserDAO {

    public static boolean userExists(String username) throws DataAccessException {
        String query = "SELECT username FROM UserTable WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // If there's a result, user exists
        } catch (SQLException e) {
            throw new DataAccessException("Database error: " + e.getMessage());
        }
    }

    public static void createUser(String username, String password, String email) throws DataAccessException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());//hashes password before inserting into DB
        String query = "INSERT INTO UserTable (username, passwordHash, email) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, email);
            stmt.executeUpdate();
        }catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new DataAccessException("Error: Username already taken");
            } else {
                throw new DataAccessException("Database error: " + e.getMessage());
            }
        }
    }

    public static boolean verifyPassword(String username, String password) throws DataAccessException{
        String query = "SELECT passwordHash FROM UserTable WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("passwordHash");
                return BCrypt.checkpw(password, storedHash);
            }
        }catch (SQLException e) {
            throw new DataAccessException("Database error: " + e.getMessage());
        }
        return false;
    }

    //Extra from before?
    public static UserData getUser(String username) throws DataAccessException {
        String query = "SELECT username, passwordHash, email FROM UserTable WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserData(rs.getString("username"), rs.getString("passwordHash"), rs.getString("email"));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Database error: " + e.getMessage());
        }
        return null;
    }

    public static void clear() throws DataAccessException {
        String query = "DELETE FROM UserTable";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error in UserDAO.clear(): " + e.getMessage());
            throw new DataAccessException("Database error: " + e.getMessage());
        }
    }


}


//private static final Map<String, UserData> USERS = new HashMap<>();
//
//public static boolean userExists(String username) {
//    return USERS.containsKey(username);
//}
//
//public static void createUser(String username, String password, String email) {
//    USERS.put(username, new UserData(username, password, email));
//}
//
//public static UserData getUser(String username) {
//    return USERS.get(username);
//}
//
//public static void clear(){
//    USERS.clear();
//}
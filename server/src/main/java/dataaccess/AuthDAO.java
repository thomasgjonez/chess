package dataaccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class AuthDAO {

    public static void createAuth(String username, String authToken) throws DataAccessException{
        String query = "INSERT INTO AuthTable (authToken, username) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, authToken);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Database error: " + e.getMessage());
        }
    }

    public static String getUsername(String authToken) throws DataAccessException{
        String query = "SELECT username FROM AuthTable WHERE authToken = ?";

        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,authToken);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("username");
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Database error: " + e.getMessage());
        }
    }

    public static void deleteAuth(String authToken) throws DataAccessException{
        String query = "DELETE FROM AuthTable WHERE authToken = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, authToken);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Database error: " + e.getMessage());
        }
    }

    public static boolean isValidAuth(String authToken) throws DataAccessException{
        return getUsername(authToken) != null;
    }

    public static void clear() throws DataAccessException {
        String query = "DELETE FROM AuthTable";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error in AuthDAO.clear(): " + e.getMessage());
            throw new DataAccessException("Database error: " + e.getMessage());
        }
    }

//    private static final Map<String, AuthData> AUTH_TOKENS = new HashMap<>();

//    public static void createAuth(String username, String authToken) throws DataAccessException {
//        AUTH_TOKENS.put(authToken, new AuthData(username, authToken));
//    }

//    public static String getUsername(String authToken) {
//        AuthData authData = AUTH_TOKENS.get(authToken);
//        return (authData != null) ? authData.username() : null;
//    }


//    public static void deleteAuth(String authToken) {
//        AUTH_TOKENS.remove(authToken);
//    }

//    public static boolean isValidAuth(String authToken) {
//        return AUTH_TOKENS.containsKey(authToken);
//    }

//    public static void clear(){
//        AUTH_TOKENS.clear();
//    }
}

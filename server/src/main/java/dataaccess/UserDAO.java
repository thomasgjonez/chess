package dataaccess;


import model.UserData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private final Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean userExists(String username) throws SQLException {
        String query = "SELECT username FROM UserTable WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // If there's a result, user exists
        }
    }

    public void createUser(String username, String password, String email) throws SQLException {
        String query = "INSERT INTO UserTable (username, passwordHash, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);  // Need to hash password, before I do this.
            stmt.setString(3, email);
            stmt.executeUpdate();
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
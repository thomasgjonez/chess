package dataaccess;

import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private static final Map<String, String> users = new HashMap<>();

    public static boolean userExists(String username) {
        return users.containsKey(username);
    }

    public static void createUser(String username, String password) {
        users.put(username, password);
    }
}
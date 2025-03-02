package dataaccess;


import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private static final Map<String, UserData> users = new HashMap<>();

    public static boolean userExists(String username) {
        return users.containsKey(username);
    }

    public static void createUser(String username, String password, String email) {
        users.put(username, new UserData(username, password, email));
    }

    public static void clear(){
        users.clear();
    }

}
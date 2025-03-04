package dataaccess;


import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private static final Map<String, UserData> USERS = new HashMap<>();

    public static boolean userExists(String username) {
        return USERS.containsKey(username);
    }

    public static void createUser(String username, String password, String email) {
        USERS.put(username, new UserData(username, password, email));
    }

    public static UserData getUser(String username) {
        return USERS.get(username);
    }

    public static void clear(){
        USERS.clear();
    }

}
package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;


public class AuthDAO {
    private static final Map<String, AuthData> AUTH_TOKENS = new HashMap<>();

    public static void createAuth(String username, String authToken) {
        AUTH_TOKENS.put(authToken, new AuthData(username, authToken));
    }

    public static String getUsername(String authToken) {
        AuthData authData = AUTH_TOKENS.get(authToken);
        return (authData != null) ? authData.username() : null;
    }


    public static void deleteAuth(String authToken) {
        AUTH_TOKENS.remove(authToken);
    }

    public static boolean isValidAuth(String authToken) {
        return AUTH_TOKENS.containsKey(authToken);
    }

    public static void clear(){
        AUTH_TOKENS.clear();
    }
}

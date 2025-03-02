package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;


public class AuthDAO {
    private static final Map<String, AuthData> authTokens = new HashMap<>();

    public static void createAuth(String username, String authToken) {
        authTokens.put(authToken, new AuthData(authToken, username));
    }

    public static String getUsername(String authToken) {
        AuthData authData = authTokens.get(authToken);
        return (authData != null) ? authData.username() : null;
    }


    public static void deleteAuth(String authToken) {
        authTokens.remove(authToken);
    }

    public static boolean isValidAuth(String authToken) {
        return authTokens.containsKey(authToken);
    }

    public static void clear(){
        authTokens.clear();
    }
}

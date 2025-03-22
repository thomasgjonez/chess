package net;

import model.AuthData;
import model.UserData;

public class ServerFacade {
    private final HTTPConnection http;

    public ServerFacade(String serverUrl) {
        this.http = new HTTPConnection(serverUrl);
    }
    //Auth Section
    public AuthData register(String username, String password, String email) throws ResponseException {
        return http.makeRequest("POST", "/user", new UserData(username,password,email), AuthData.class);
    }
}

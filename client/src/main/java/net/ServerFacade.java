package net;

import model.AuthData;
import model.UserData;

public class ServerFacade {
    private final HTTPConnection http;

    public ServerFacade(String serverUrl) {
        this.http = new HTTPConnection(serverUrl);
    }
    //Auth Section
    public AuthData register(String username, String password, String email) {
        System.out.println("register in Server Facade called");
        return http.sendRequest("/register", "POST", new UserData(username,password,email), AuthData.class);

    }
}

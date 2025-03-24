package net;

import chess.ChessGame;
import model.*;

import java.util.Objects;

public class ServerFacade {
    private final HTTPConnection http;

    public ServerFacade(String serverUrl) {
        this.http = new HTTPConnection(serverUrl);
    }

    public AuthData register(String username, String password, String email) throws ResponseException {
        return http.makeRequest("POST", "/user", new UserData(username,password,email), AuthData.class);
    }

    public AuthData login(String username, String password) throws ResponseException{
        return http.makeRequest("POST", "/session", new LoginRequest(username, password), AuthData.class);
    }

    public CreateResult createGame(String gameName) throws ResponseException{
        return http.makeRequest("POST", "/game", new CreateRequest(gameName), CreateResult.class);
    }

    public ListGamesResult listGames() throws ResponseException{
        //double check ListGamesRequest
        return http.makeRequest("GET", "/game", "", ListGamesResult.class);
    }

    public GameData joinGame(String playerColor, String gameID) throws ResponseException{
        ChessGame.TeamColor color = (Objects.equals(playerColor, "WHITE")) ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;

        return http.makeRequest("PUT", "/game", new JoinGameRequest(color, gameID), GameData.class);
    }

    public void logout(String authToken) throws ResponseException{
        http.makeRequest("DELETE", "/session", null, null);
    }
}

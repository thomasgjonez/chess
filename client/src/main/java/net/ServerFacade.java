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
        return http.makeRequest("POST", "/user", new UserData(username,password,email), AuthData.class, null);
    }

    public AuthData login(String username, String password) throws ResponseException{
        return http.makeRequest("POST", "/session", new LoginRequest(username, password), AuthData.class, null);
    }

    public CreateResult createGame(String gameName, String authToken) throws ResponseException{
        return http.makeRequest("POST", "/game", new CreateRequest(gameName), CreateResult.class, authToken);
    }

    public ListGamesResult listGames(String authToken) throws ResponseException{
        //double check ListGamesRequest
        return http.makeRequest("GET", "/game", "", ListGamesResult.class, authToken);
    }

    public GameData joinGame(String playerColor, String gameID, String authToken) throws ResponseException{
        ChessGame.TeamColor color = (Objects.equals(playerColor, "WHITE")) ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;

        return http.makeRequest("PUT", "/game", new JoinGameRequest(color, gameID), GameData.class, authToken);
    }

    public void logout(String authToken) throws ResponseException{
        http.makeRequest("DELETE", "/session", null, null, authToken);
    }
}

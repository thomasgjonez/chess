package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.GameData;
import model.JoinGameRequest;
import model.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JoinGameServiceTest {
    private JoinGameService joinGameService;
    private int gameID;
    private ClearService clearService;

    @BeforeEach
    public void setup() throws DataAccessException {
        clearService = new ClearService();
        clearService.clear();
        UserDAO.createUser("testUser","test123", "test@gmail.com");
        AuthDAO.createAuth("testUser", "auth123");
        gameID = GameDAO.createGame("testGame");
        joinGameService = new JoinGameService();
    }

    @Test
    public void testJoinGameAsWhite() throws DataAccessException {
        JoinGameRequest request = new JoinGameRequest(ChessGame.TeamColor.WHITE, String.valueOf(gameID));
        ApiResponse result = joinGameService.joinGame("auth123", request);

        assertNull(result.message(), "Join request should succeed and return an empty response");

        GameData updatedGame = GameDAO.getGame(gameID);

        assertEquals("testUser", updatedGame.whiteUsername(), "White player should be set correctly");
        assertNull(updatedGame.blackUsername(), "Black player should still be null");
    }

    @Test
    public void testJoinGameThatDoesNotExist() {
        JoinGameRequest request = new JoinGameRequest(ChessGame.TeamColor.WHITE, "9999");
        ApiResponse result = joinGameService.joinGame("auth123", request);

        assertEquals("Error: bad request", result.message(), "Should return bad request error");
    }
}

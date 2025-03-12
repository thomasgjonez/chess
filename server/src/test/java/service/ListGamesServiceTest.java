package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ListGamesServiceTest {
    private ListGamesService listGamesService;
    private ClearService clearService;

    @BeforeEach
    public void setup() throws DataAccessException {
        clearService = new ClearService();
        clearService.clear();
        UserDAO.createUser("testUser","test123", "test@gmail.com");
        AuthDAO.createAuth("testUser","auth123");
        listGamesService = new ListGamesService();
    }

    @Test
    public void normalListGames() throws DataAccessException {
        int gameID = GameDAO.createGame("testGame");
        GameData expectedGame = new GameData(1000, null, null, "testGame", null);

        ListGamesResult result = listGamesService.listGames("auth123");

        assertEquals(1, result.games().size(), "Should return exactly one game");
        assertEquals(expectedGame.gameName(), result.games().getFirst().gameName(), "Game names should match");
        assertEquals(gameID, result.games().getFirst().gameID(), "Game IDs should match");
        assertNull(result.games().getFirst().whiteUsername(), "White player should be null");
        assertNull(result.games().getFirst().blackUsername(), "Black player should be null");
    }

    @Test
    public void listGamesWithUnauthorizedAuthToken() {
        ListGamesResult result = listGamesService.listGames("invalidAuth");

        assertNull(result, "should return null when an unauthorized auth token is provided");
    }


}

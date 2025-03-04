package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ListGamesServiceTest {
    private ListGamesService listGamesService;

    @BeforeEach
    public void Setup(){
        AuthDAO.createAuth("testUser","auth123");
        listGamesService = new ListGamesService();
    }

    @Test
    public void normalListGames(){
        GameDAO.createGame("testGame");
        GameData expectedGame = new GameData(1000, null, null, "testGame", null);

        ListGamesResult result = listGamesService.listGames("auth123");

        assertEquals(1, result.games().size(), "Should return exactly one game");
        assertEquals(expectedGame.gameName(), result.games().get(0).gameName(), "Game names should match");
        assertEquals(expectedGame.gameID(), result.games().get(0).gameID(), "Game IDs should match");
        assertNull(result.games().get(0).whiteUsername(), "White player should be null");
        assertNull(result.games().get(0).blackUsername(), "Black player should be null");
    }

    @Test
    public void ListGameswithUnauthorizedAuthToken(){
        ListGamesResult result = listGamesService.listGames("invalidAuth");

        assertNull(result, "should return null when an unauthorized auth token is provided");
    }


}

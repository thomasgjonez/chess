package dataaccess;

import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

import java.sql.SQLDataException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameDAOTest {
    private ClearService clearService;

    @BeforeEach
    public void setup() throws DataAccessException{
        clearService = new ClearService();
        clearService.clear();
        UserDAO.createUser("testUser","test123","test@gmail.com");
        AuthDAO.createAuth("testUser","12345");

    }

    @Test
    public void createGameNormal() throws DataAccessException{
        int gameId = GameDAO.createGame("gameName");

        assertEquals(Integer.class, ((Object) gameId).getClass(), "gameId should be an int, which indicates a game was successfully made");


    }

    @Test
    public void createGameWithNullGameName() {
        assertThrows(DataAccessException.class, () -> GameDAO.createGame(null), "Creating a game with a null name should throw a DataAccessException");
    }

    @Test
    public void getGameWithRealGameID() throws DataAccessException{
        int gameId = GameDAO.createGame("gameName");

        assertDoesNotThrow( () -> GameDAO.getGame(gameId), "no error thrown means success");

    }

    @Test
    public void getGameWithWrongGameID() throws DataAccessException{
        GameData gameData = GameDAO.getGame(10000);

        assertNull(gameData, "gameData will be null if the gameID is not in DB");
    }

    @Test
    public void listGamesSuccess() throws DataAccessException {
        GameDAO.createGame("TestGame1");
        GameDAO.createGame("TestGame2");

        List<GameData> games = GameDAO.listGames();

        assertNotNull(games, "The returned game list should not be null");
        assertEquals("TestGame1", games.get(0).gameName(), "First game name should match");
        assertEquals("TestGame2", games.get(1).gameName(), "Second game name should match");
    }

    @Test
    public void listGamesWithNoGamesInDB() throws DataAccessException{
        GameDAO.clear();

        List<GameData> games = GameDAO.listGames();

        assertNotNull(games,"Game list should not be be null");
        assertTrue(games.isEmpty(),"Game list should be empty tho");
    }

    @Test
    public void UpdateGameNormal()throws DataAccessException {
        UserDAO.createUser("playerWhite","test123","test@gmail.com");
        UserDAO.createUser("playerBlack","test123","test@gmail.com");
        int gameID = GameDAO.createGame("testGame");

        GameData updatedGame = new GameData(gameID, "playerWhite", "playerBlack", "NonExistentGame", null);

        assertDoesNotThrow( () -> GameDAO.updateGame(updatedGame), "Updating should not throw a DataAccessException");
    }

    @Test
    public void UpdateGameWithUsersNotInDB()throws DataAccessException{
        int gameID = GameDAO.createGame("testGame");

        GameData updatedGame = new GameData(gameID, "playerWhite", "playerBlack", "NonExistentGame", null);

        assertThrows(DataAccessException.class, () -> GameDAO.updateGame(updatedGame), "Updating should DataAccessExceptioin");

    }

    @Test
    public void clearNormal() throws DataAccessException{
        GameDAO.createGame("testGame");

        assertDoesNotThrow( () -> GameDAO.clear(),"Lack of error indicates clear was successful");

    }

}

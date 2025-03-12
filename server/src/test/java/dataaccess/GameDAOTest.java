package dataaccess;

import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

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

        assertDoesNotThrow( () -> GameDAO.getGame(gameId));

    }

    @Test
    public void getGameWithWrongGameID() throws DataAccessException{
        GameData gameData = GameDAO.getGame(10000);

        assertNull(gameData, "gameData will be null if the gameID is not in DB");
    }

}

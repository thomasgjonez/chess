package client;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.AuthData;
import net.ResponseException;
import org.junit.jupiter.api.*;
import server.Server;
import net.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);

        var serverUrl = "http://localhost:8080";
        serverFacade = new ServerFacade(serverUrl);
    }

    @BeforeEach
    public void clear() throws DataAccessException {
        GameDAO.clear();
        AuthDAO.clear();
        UserDAO.clear();
    }

    @Test
    void register() throws Exception {
        AuthData authData = serverFacade.register("player1", "password", "p1@email.com");
        assertTrue(authData.authToken().length() > 10);
    }

    @Test
    void registerWithNoUsername() throws Exception{
        ResponseException exception = assertThrows(ResponseException.class, () -> {
            serverFacade.register(null, "password", "email@example.com");
        });

        assertTrue(exception.getMessage().contains("bad request"));

    }

    @Test
    void login() throws Exception {
        serverFacade.register("player2", "password","p2@gmail.com");
        AuthData authData = serverFacade.login("player2", "password");

        assertTrue(authData.authToken().length() > 10);
    }

    @Test
    void loginWithWrongPassword() throws Exception {
        serverFacade.register("player2", "password","p2@gmail.com");
        ResponseException exception = assertThrows(ResponseException.class, () -> {
            serverFacade.login("player2", "wrongPassword");
        });

        assertTrue(exception.getMessage().contains("Error: unauthorized"));
    }

    @Test
    void logout() throws Exception {
        AuthData authData = serverFacade.register("player1", "password", "p1@gamil.com");

        assertDoesNotThrow(() -> serverFacade.logout(authData.authToken()));
    }


    @Test
    void logoutWithInvalidAuthToken() throws Exception{
        String invalidToken = "InvalidToken";

        ResponseException exception = assertThrows(ResponseException.class, () -> {
            serverFacade.logout(invalidToken);
        });

        assertTrue(exception.getMessage().contains("Error: unauthorized"));
    }

    @Test
    void createGame() throws Exception {
        AuthData authData = serverFacade.register("player1", "password", "p1@gamil.com");

        assertDoesNotThrow(() -> serverFacade.createGame("gameName",authData.authToken()));
    }

    @Test
    void createGameWithNullForName() throws Exception{
        AuthData authData = serverFacade.register("player1", "password", "p1@gamil.com");

        ResponseException exception = assertThrows(ResponseException.class, () -> {
            serverFacade.createGame(null, authData.authToken());
        });

        assertTrue(exception.getMessage().contains("bad request"));


    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        assertTrue(true);
    }

}

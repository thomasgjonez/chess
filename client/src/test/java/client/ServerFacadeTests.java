package client;

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

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        assertTrue(true);
    }

}

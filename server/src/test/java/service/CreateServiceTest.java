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

public class CreateServiceTest {
    private CreateService createGameService;
    private ClearService clearService;

    @BeforeEach
    public void setup() throws DataAccessException {
        clearService = new ClearService();
        clearService.clear();
        UserDAO.createUser("testUser","test123", "test@gmail.com");
        AuthDAO.createAuth("testUser","auth123");
        createGameService = new CreateService();
    }

    @Test
    public void normalCreateGame(){
        CreateRequest req = new CreateRequest("gameName");
        CreateResult result = createGameService.create("auth123", req);

        assertNotNull(result.gameID(), "returning not null indicates a successful game creation");
    }

    @Test
    public void creatGameWithInvalidAuthToken(){
        CreateRequest req = new CreateRequest("gameName");
        CreateResult result = createGameService.create("InvalidAuth", req);

        assertNull(result.gameID(), "returning null indicates a failed game creation");
    }
}

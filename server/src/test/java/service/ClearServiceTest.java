package service;


import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.ApiResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {
    private ClearService clearService;

    @BeforeEach
    public void Setup(){
        clearService = new ClearService();
    }

    @Test
    public void clearAllDatabases(){
        UserDAO.createUser("testUser","password123","test@example.com");
        AuthDAO.createAuth("testUser", "12345678910");
        GameDAO.createGame("randomgame");

        ApiResponse result = clearService.clear();

        assertNull(result.message(), "result should be null which indicates a 200 request");


    }

}

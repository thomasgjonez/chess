package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.ApiResponse;
import model.AuthData;
import model.LoginRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    private LoginService loginService;
    private ClearService clearService;

    @BeforeEach
    public void setup() throws DataAccessException {
        clearService = new ClearService();
        clearService.clear();
        loginService = new LoginService();
    }

    @Test
    public void normalLoginWithUserAlreadyRegistered() throws DataAccessException {
        UserDAO.createUser("testUser","password123","test@example.com");
        LoginRequest loginRequest = new LoginRequest("testUser", "password123");

        AuthData result = loginService.login(loginRequest);

        assertNotNull(result.authToken(), "result should not be null to indicate it returns an AuthToken");


    }
    @Test
    public void loginWithoutUserInDataBase(){
        LoginRequest loginRequest = new LoginRequest("testUser3", "password123");
        AuthData result = loginService.login(loginRequest);

        assertNull(result, "result should be null to indicate that an authToken is not returned since User is unauthorized");


    }

}
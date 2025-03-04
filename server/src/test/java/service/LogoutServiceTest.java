package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.ApiResponse;
import model.AuthData;
import model.LoginRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class LogoutServiceTest {
    private LogoutService logoutService;

    @BeforeEach
    public void setup(){
        logoutService = new LogoutService();
    }

    @Test
    public void normalLogoutWithAuthToken(){
        AuthDAO.createAuth("testUser","auth123");

        ApiResponse result = logoutService.logout("auth123");

        assertNull(result.message(), "null indicates that you were successfully logged out");
    }

    @Test
    public void logoutWithoutVerifiedAuthTokenInDB(){
        ApiResponse result = logoutService.logout("auth123");

        assertNotNull(result.message(), "not null indicates there was an error thrown and you were not logged out properly");
    }


}
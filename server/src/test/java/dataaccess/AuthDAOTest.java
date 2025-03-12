package dataaccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

import static org.junit.jupiter.api.Assertions.*;

public class AuthDAOTest {
    private ClearService clearService;

    @BeforeEach
    public void setup() throws DataAccessException{
        clearService = new ClearService();
        clearService.clear();
        UserDAO.createUser("testUser","test123","test@gmail.com");

    }

    @Test
    public void createAuthNormal(){
        assertDoesNotThrow(() -> AuthDAO.createAuth("testUser","12345"), "No error returned means it was successful");
    }

    @Test
    public void createAuthWithUsernameNotInDBl(){
        assertThrows(DataAccessException.class, () -> AuthDAO.createAuth("user","12345"),
                "Error returns since it will only create AuthTokens to Users that exist in DB");
    }

    @Test void getUsernameWithRealAuthToken() throws DataAccessException{
        AuthDAO.createAuth("testUser","12345");

        String result = AuthDAO.getUsername("12345");
        assertEquals("testUser", result, "result shold be equal to 'testUser'");


    }

    @Test void getUsernameWithFakeAuthToken() throws DataAccessException{
        AuthDAO.createAuth("testUser","12345");

        String result = AuthDAO.getUsername("15");
        assertNull(result,"result should be null if no user is returned.");

    }

    @Test void deleteAuthThatExists() throws DataAccessException{
        AuthDAO.createAuth("testUser","12345");
        assertDoesNotThrow( () -> AuthDAO.deleteAuth("12345"),"no error indicates success");
    }

    @Test
    public void testDeleteAuthWithNonExistentToken() throws DataAccessException {
        assertDoesNotThrow(() -> AuthDAO.deleteAuth("nonExistentToken"),"no error indicates success");
    }

    @Test void testIsValidAuthTokenWithValidAuth() throws DataAccessException{
        AuthDAO.createAuth("testUser","12345");

        boolean result = AuthDAO.isValidAuth("12345");
        assertTrue(result,"result should be true");

    }

    @Test void testIsValidAuthTokenWithFakeAuth() throws DataAccessException{
        AuthDAO.createAuth("testUser","12345");

        boolean result = AuthDAO.isValidAuth("15");
        assertFalse(result,"result should be false");

    }

    @Test
    public void clearNormal() throws DataAccessException{
        AuthDAO.createAuth("testUser","12345");

        assertDoesNotThrow( () -> AuthDAO.clear(),"Lack of error indicates clear was successful");

    }
}

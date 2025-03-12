package dataaccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertDoesNotThrow(() -> AuthDAO.createAuth("testUser","12345"));//No error returned means it was successful
    }

    @Test
    public void createAuthWithUsernameNotInDBl(){
        assertThrows(DataAccessException.class, () -> AuthDAO.createAuth("user","12345"));//Error returns since it will only create AuthTokens to Users that exist in DB
    }


}

package dataaccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private ClearService clearService;

    @BeforeEach
    public void setup(){
        clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void userExistsWithUsernameInDB() throws DataAccessException{
        UserDAO.createUser("testUser","test123","test@gmail.com");

        boolean result = UserDAO.userExists("testUser");
        assertTrue(result,"returns true if User is in Database");
    }

    @Test
    public void userExistsWithoutUsernameInDB() throws DataAccessException{
        boolean result = UserDAO.userExists("testUser");
        assertFalse(result,"returns false if User not in Database");
    }

    @Test
    public void createUsernameNormal(){
        assertDoesNotThrow(() -> UserDAO.createUser("testUser","test123","test@gmail.com"),"No error returned means it was successful");

    }

    @Test
    public void createUsernameWithDuplicate() throws DataAccessException{
        UserDAO.createUser("testUser","test123","test@gmail.com");

        assertThrows(DataAccessException.class, () -> UserDAO.createUser("testUser","test123","test@gmail.com"),"Should return error since testUser already exists in DB");

    }

    @Test
    public void verifyPasswordThatIsTheSame() throws DataAccessException{
        UserDAO.createUser("testUser","test123","test@gmail.com");

        boolean result = UserDAO.verifyPassword("testUser","test123");
        assertTrue(result,"returns true if passwords are the same");
    }

    @Test
    public void verifyPasswordThatIsDifferent() throws DataAccessException{
        UserDAO.createUser("testUser","test123","test@gmail.com");

        boolean result = UserDAO.verifyPassword("testUser","test");
        assertFalse(result,"returns false if passwords are different");
    }

    @Test
    public void clearNormal() throws DataAccessException{
        UserDAO.createUser("testUser","test123","test@gmail.com");
        UserDAO.clear();

        boolean result = UserDAO.userExists("testUser");
        assertFalse(result,"should be false to indicate that testUser doesn't exist and thus clear was successful");

    }

}

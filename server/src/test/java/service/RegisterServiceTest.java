package service;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
    private RegisterService registerService;

    @BeforeEach
    public void setUp() {
        registerService = new RegisterService();
    }

    @Test
    public void testRegisterSuccess() {
        UserData user = new UserData("testUser", "password123", "test@example.com");
        AuthData result = registerService.register(user);

        assertNotNull(result.authToken(), "Auth token should be generated");
        assertEquals("testUser", result.username(), "Username should match");
    }

    @Test
    public void testRegisterDuplicateUser() {
        UserData user = new UserData("duplicateUser", "password123", "duplicate@example.com");
        registerService.register(user); // First registration

        AuthData result = registerService.register(user); // Attempt to register again

        assertNull(result.authToken(), "Auth token should be null for duplicate registration");
    }
}

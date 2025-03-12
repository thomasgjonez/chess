package dataaccess;

import org.junit.jupiter.api.BeforeEach;
import service.ClearService;

public class AuthDAOTest {
    private ClearService clearService;

    @BeforeEach
    public void setup() {
        clearService = new ClearService();
        clearService.clear();

    }
}

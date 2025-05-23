package service;

import dataaccess.UserDAO;
import dataaccess.AuthDAO;
import model.AuthData;
import model.UserData;
import java.sql.Connection;
import dataaccess.DatabaseManager;


public class RegisterService extends BaseService {
    public AuthData register(UserData request) {
        try {
            // Check if the user already exists
            if (UserDAO.userExists(request.username())) {
                return new AuthData(null, null);  // Username is taken
            }

            String authToken = generateAuthToken();

            UserDAO.createUser(request.username(), request.password(), request.email());  // Store user in DB
            AuthDAO.createAuth(request.username(), authToken);  // Store auth token

            return new AuthData(request.username(), authToken);
        } catch (Exception e) {
            return new AuthData(null, "Error: internal server error");
        }
    }
}


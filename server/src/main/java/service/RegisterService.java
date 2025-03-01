package service;

import dataaccess.UserDAO;
import dataaccess.AuthDAO;
import model.RegisterRequest;
import model.RegisterResult;

import java.util.UUID;


public class RegisterService {
    public RegisterResult register(RegisterRequest request) {
        // Validate if user exists, store user in database, return success or failure
        if (UserDAO.userExists(request.username())) {
            return new RegisterResult(null, null);
        }
        String authToken = UUID.randomUUID().toString();

        UserDAO.createUser(request.username(), request.password(), request.email());
        AuthDAO.createAuth(request.username(), authToken);

        return new RegisterResult(request.username(), authToken);
    }

}

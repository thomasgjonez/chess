package service;

import dataaccess.UserDAO;
import server.RegisterRequest;
import server.RegisterResult;



public class RegisterService {
    public RegisterResult register(RegisterRequest request) {
        // Validate if user exists, store user in database, return success or failure
        if (UserDAO.userExists(request.username())) {
            return new RegisterResult(false, "Username already taken");
        }

        UserDAO.createUser(request.username(), request.password());
        return new RegisterResult(true, "User registered successfully");
    }

}
